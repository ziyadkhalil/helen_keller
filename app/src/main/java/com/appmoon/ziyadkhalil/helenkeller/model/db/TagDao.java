package com.appmoon.ziyadkhalil.helenkeller.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TagDao {
    @Insert
    long insert(MTag tag);

    @Query("DELETE FROM tags_table")
    void deleteAll();

    @Query("SELECT * FROM tags_table")
    LiveData<List<MTag>> getAllTags();

    @Query("SELECT * FROM tags_table WHERE tag_id = :id ")
    LiveData<List<MTag>> getTagByID(String id);

    @Query("SELECT * FROM tags_table WHERE deck_id=:deckID")
    LiveData<List<MTag>> getTagByDeckID(String deckID);

    @Query("SELECT * FROM tags_table WHERE deck_id =:deckID")
    List<MTag> getTagsForDeck(int deckID);

    @Query("SELECT * FROM tags_table WHERE id = :id")
    MTag getATagByID(long id);
}
