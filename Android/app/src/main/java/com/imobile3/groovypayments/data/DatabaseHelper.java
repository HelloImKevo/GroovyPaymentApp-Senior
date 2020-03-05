package com.imobile3.groovypayments.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

public class DatabaseHelper {

    //region Singleton Implementation

    private static DatabaseHelper sInstance;

    private DatabaseHelper() {
    }

    @NonNull
    public static synchronized DatabaseHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DatabaseHelper();
        }
        return sInstance;
    }

    //endregion

    private GroovyDatabase mDatabase;

    public void init(@NonNull Context context) {
        // Database location can be found here:
        // context.getDatabasePath(GroovyDatabase.NAME)
        mDatabase = Room.databaseBuilder(context, GroovyDatabase.class, GroovyDatabase.NAME)
                .allowMainThreadQueries()
                .build();
    }

    public void eraseDatabase(@NonNull Context context) {
        context.deleteDatabase(GroovyDatabase.NAME);
    }

    public GroovyDatabase getDatabase() {
        return mDatabase;
    }
}
