package com.example.basicprogramming.sqliteappdemo.roomconfig;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.basicprogramming.sqliteappdemo.entities.Books;

@Database(entities = {Books.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract BookDao bookDao();
}
