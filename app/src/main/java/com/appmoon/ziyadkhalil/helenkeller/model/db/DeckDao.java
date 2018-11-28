package com.appmoon.ziyadkhalil.helenkeller.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DeckDao {
    @Insert
    void insert(Deck deck);

    @Query("SELECT * FROM deckTable")
    LiveData<List<Deck>> getAllDecks();

    @Query("DELETE FROM deckTable")
    void removeAll();

    @Query("SELECT * FROM deckTable WHERE deckID = :id")
    Deck getDeckByID(int id);

    @Query("UPDATE deckTable SET tags_number = (tags_number + 1) WHERE deckID = :DeckID  ")
    void incrementDeckTagNumber(int DeckID);


    @Query("SELECT * FROM deckTable")
    List<Deck> getDeckS();
}
