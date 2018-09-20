package com.example.basicprogramming.sqliteappdemo.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "books-table")
public class Books {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private Integer id;
    @ColumnInfo(name = "book_name")
    private String title;
    @ColumnInfo(name = "author_name")
    private String author;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "book_price")
    private Double price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
