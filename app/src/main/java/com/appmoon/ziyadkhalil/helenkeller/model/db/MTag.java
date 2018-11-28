package com.appmoon.ziyadkhalil.helenkeller.model.db;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tags_table")
public class MTag {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name="tag_id")
    public String tagID;
    @ColumnInfo(name="deck_id")
    public int deckID;

    private String text;

    private int backgroundColor;

    private int textColor;

    public MTag(){}

    public MTag(String s, int rgb) {
        this.text = s;
        this.backgroundColor =rgb;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public int getDeckID() {
        return deckID;
    }

    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
