package com.example.basicprogramming.sqliteappdemo.app;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.basicprogramming.sqliteappdemo.R;
import com.example.basicprogramming.sqliteappdemo.entities.Books;
import com.example.basicprogramming.sqliteappdemo.roomconfig.AppDb;
import com.example.basicprogramming.sqliteappdemo.roomconfig.BookDao;
import com.valdesekamdem.library.mdtoast.MDToast;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Books books;
    private BookDao bookDao;
    private EditText name, author, description, price;
    private MDToast mdToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bookDao = Room.databaseBuilder(getApplicationContext(),
                AppDb.class,
                "book-database")
                .allowMainThreadQueries()
                .build().bookDao();

        // register view here
        fab = findViewById(R.id.fab);
        name = findViewById(R.id.book_name_edit_text);
        author = findViewById(R.id.author_name_edit_text);
        description = findViewById(R.id.book_description_edit_text);
        price = findViewById(R.id.book_price_edit_text);

        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addBooks() {

        String book_name = name.getText().toString().trim();
        String author_name = author.getText().toString().trim();
        String book_description = description.getText().toString().trim();
        String book_price = price.getText().toString().trim();

        if (book_name.isEmpty() && book_name.length() <= 10) {
            mdToast = MDToast.makeText(AddActivity.this,
                    "book name is require more then 10 char",
                    MDToast.LENGTH_LONG,
                    MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        if (author_name.isEmpty()) {
            mdToast = MDToast.makeText(AddActivity.this,
                    "author name is require",
                    MDToast.LENGTH_LONG,
                    MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        if (book_description.isEmpty()) {
            mdToast = MDToast.makeText(AddActivity.this,
                    "description is require",
                    MDToast.LENGTH_LONG,
                    MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        if (book_price.isEmpty()) {
            mdToast = MDToast.makeText(AddActivity.this,
                    "book price is require",
                    MDToast.LENGTH_LONG,
                    MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        books = new Books();

        // insert new books here

        books.setTitle(book_name);
        books.setAuthor(author_name);
        books.setDescription(book_description);
        books.setPrice(Double.parseDouble(book_price));

        // add books into Dao here

        bookDao.insertAll(books);

        setResult(RESULT_OK);
        mdToast = MDToast.makeText(AddActivity.this,
                "new book saved successfully",
                MDToast.LENGTH_LONG,
                MDToast.TYPE_SUCCESS);
        mdToast.show();

        // clear editText here

        name.setText("");
        author.setText("");
        description.setText("");
        price.setText("");

        startActivity(new Intent(AddActivity.this, MainActivity.class));

    }

    @Override
    public void onClick(View v) {
        addBooks();
    }
}
