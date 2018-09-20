package com.example.basicprogramming.sqliteappdemo.app;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.basicprogramming.sqliteappdemo.R;
import com.example.basicprogramming.sqliteappdemo.adapter.BooksAdapter;
import com.example.basicprogramming.sqliteappdemo.entities.Books;
import com.example.basicprogramming.sqliteappdemo.roomconfig.AppDb;
import com.example.basicprogramming.sqliteappdemo.roomconfig.BookDao;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
        , AdapterView.OnItemClickListener
        , AdapterView.OnItemLongClickListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ListView listView;
    private BooksAdapter booksAdapter;
    private List<Books> booksList;
    private Books books;
    private BookDao bookDao;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookDao = Room.databaseBuilder(getApplicationContext(),
                AppDb.class,
                "book-database")
                .allowMainThreadQueries()
                .build().bookDao();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);

        // register view here
        booksList = new ArrayList<>();
        listView = findViewById(R.id.my_book_list_view);
        dialog = new Dialog(this, R.style.appDialog);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        fab.setOnClickListener(this);
        getAllBooks();
    }

    private void getAllBooks() {

        booksList = bookDao.getBooks();
        booksAdapter = new BooksAdapter(MainActivity.this, booksList);
        listView.setAdapter(booksAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                MDToast mdToast = MDToast.makeText(MainActivity.this,
                        "Setting Menu Is Clicked By User",
                        MDToast.LENGTH_LONG,
                        MDToast.TYPE_INFO);
                mdToast.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, AddActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        books = booksList.get(position);

        dialog.setTitle("Update Books");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.update_books_layout);

        //register editText view

        EditText name = dialog.findViewById(R.id.edit_book_name_edit_text);
        EditText author = dialog.findViewById(R.id.edit_author_name_edit_text);
        EditText description = dialog.findViewById(R.id.edit_description_edit_text);
        EditText price = dialog.findViewById(R.id.edit_book_price_edit_text);
        Button updateButton = dialog.findViewById(R.id.edit_book_button);

        // get value from bookslist and add into editText

        name.setText(books.getTitle());
        author.setText(books.getAuthor());
        description.setText(books.getDescription());
        price.setText("" + books.getPrice());

        //set onclick listener

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String book_name = name.getText().toString().trim();
                String author_name = author.getText().toString().trim();
                String book_desc = description.getText().toString().trim();
                String book_price = price.getText().toString().trim();
                Double aDouble = Double.parseDouble(book_price);

                // update book

                books.setTitle(book_name);
                books.setAuthor(author_name);
                books.setDescription(book_desc);
                books.setPrice(aDouble);

                setResult(RESULT_OK);
                bookDao.update(books);
                MDToast mdToast = MDToast.makeText(MainActivity.this,
                        "Update Book Success",
                        MDToast.LENGTH_LONG,
                        MDToast.TYPE_SUCCESS);
                mdToast.show();
                booksAdapter.notifyDataSetChanged();
                dialog.dismiss();


            }
        });

        dialog.show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        books = booksList.get(position);
        bookDao.delete(books);
        booksList.remove(position);

        MDToast mdToast = MDToast.makeText(MainActivity.this,
                "Book Delete From List",
                MDToast.LENGTH_LONG,
                MDToast.TYPE_WARNING);
        mdToast.show();

        booksAdapter.notifyDataSetChanged();
        listView.setAdapter(booksAdapter);

        return true;
    }
}
