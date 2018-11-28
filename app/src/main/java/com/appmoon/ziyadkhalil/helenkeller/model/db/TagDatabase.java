package com.appmoon.ziyadkhalil.helenkeller.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MTag.class,Deck.class},version = 3,exportSchema = false)
public abstract class TagDatabase extends RoomDatabase {
    public abstract  TagDao tagDao();
    public abstract  DeckDao deckDao();
    private static volatile TagDatabase INSTANCE;

    public static TagDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (TagDatabase.class) {
                if (INSTANCE == null) {
                    Builder<TagDatabase> builder = Room.databaseBuilder(context.getApplicationContext(),
                            TagDatabase.class,"tag_database");
                    builder.allowMainThreadQueries();
                    INSTANCE = builder.build();

                }
            }
        }
        return INSTANCE;
    }


}
