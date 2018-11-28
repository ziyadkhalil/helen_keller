package com.appmoon.ziyadkhalil.helenkeller.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
@Entity (tableName = "deckTable")
public class Deck {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "deckID")
    public int id;
    @Ignore
    ArrayList<MTag> tags;
    @NonNull
    @ColumnInfo(name = "deck_name")
    String deckName;
    @ColumnInfo(name="tags_number")
    int tagsNumber;
    public Deck(String deckName){this.deckName=deckName;}
    public static Deck createNewDeck(String deckName){
        return new Deck(deckName);
    }

    public ArrayList<MTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<MTag> tags) {
        this.tags = tags;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
        tagsNumber = 0;
    }

    public int getTagsNumber() {
        return tagsNumber;
    }

    public void setTagsNumber(int tagsNumber) {
        this.tagsNumber = tagsNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
