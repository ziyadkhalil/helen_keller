package com.appmoon.ziyadkhalil.helenkeller;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.appmoon.ziyadkhalil.helenkeller.model.db.Deck;
import com.appmoon.ziyadkhalil.helenkeller.model.db.DeckDao;
import com.appmoon.ziyadkhalil.helenkeller.model.db.MTag;
import com.appmoon.ziyadkhalil.helenkeller.model.db.TagDao;
import com.appmoon.ziyadkhalil.helenkeller.model.db.TagDatabase;

import java.nio.CharBuffer;
import java.util.List;

public class ViewModel extends AndroidViewModel {
    private TagDao tagDao;
    private DeckDao deckDao;
    private LiveData<List<MTag>> allTags;
    private LiveData<List<Deck>> allDecks;
    private TagDatabase tagDatabase;
    private Application application;


    public ViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
         tagDatabase = TagDatabase.getDatabase(application);
        tagDao = tagDatabase.tagDao();
        deckDao = tagDatabase.deckDao();
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        if(tagDatabase==null)
            tagDatabase=TagDatabase.getDatabase(application);
        if(deckDao==null)
            deckDao=tagDatabase.deckDao();
        if(allDecks==null)
            allDecks=deckDao.getAllDecks();
        return allDecks;
    }

    public void Insert(MTag tag){
        new insertAsyncTask(tagDao).execute(tag);
    }

    public long insert(MTag tag){
        return tagDao.insert(tag);
    }

    public void insertDeck(Deck deck) {
        new insertDeckAsyncTask(deckDao).execute(deck);
    }

    public void removeDecks() {
        new removeDecksAsyncTask(deckDao).execute();
    }

    public Deck getDeckByID (int ID){
        return deckDao.getDeckByID(ID);
    }

    public List<MTag> getTagsForDeck(int deckID) {
        return tagDao.getTagsForDeck( deckID);
    }

    public MTag getTagByID(long id) {
        return tagDao.getATagByID(id);
    }

    public LiveData<List<MTag>> getAllTags() {
        return tagDao.getAllTags();
    }

    public void incrementDeckTagNumber(int deckID) {
        deckDao.incrementDeckTagNumber(deckID);
    }

    public List<Deck> getAllDeckS() {
        return  deckDao.getDeckS();
    }


    private static class removeDecksAsyncTask extends AsyncTask<Void,Void,Void>{
        private DeckDao asyncDao;
        public removeDecksAsyncTask(DeckDao deckDao) {
            this.asyncDao=deckDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncDao.removeAll();
            return null;
        }
    }
    private static class insertAsyncTask extends AsyncTask<MTag, Void, Void> {

        private TagDao asyncTaskDao;

        insertAsyncTask(TagDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MTag... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class insertDeckAsyncTask extends AsyncTask<Deck, Void, Void> {

        private DeckDao asyncTaskDao;

        insertDeckAsyncTask(DeckDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Deck... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class getDeckAsyncTask extends AsyncTask<Integer,Void,Deck>{
        DeckDao deckDao;

        public getDeckAsyncTask(DeckDao deckDao) {
            this.deckDao = deckDao;
        }

        @Override
        protected Deck doInBackground(Integer... integers) {
            return deckDao.getDeckByID(integers[0]);
        }

        @Override
        protected void onPostExecute(Deck deck) {
            super.onPostExecute(deck);

        }
    }
}
