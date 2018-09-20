package com.example.basicprogramming.sqliteappdemo.roomconfig;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.basicprogramming.sqliteappdemo.entities.Books;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insertAll(Books... books);

    @Query("SELECT * FROM `books-table`")
    List<Books> getBooks();

    @Update
    void update(Books... books);

    @Delete
    void delete(Books... books);
}
