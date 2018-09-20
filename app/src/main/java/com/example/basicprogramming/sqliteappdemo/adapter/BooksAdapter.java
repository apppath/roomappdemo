package com.example.basicprogramming.sqliteappdemo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.basicprogramming.sqliteappdemo.R;
import com.example.basicprogramming.sqliteappdemo.entities.Books;

import java.util.List;

public class BooksAdapter extends BaseAdapter {

    private Context context;
    private List<Books> booksList;
    private TextView textView1;

    public BooksAdapter(Context context, List<Books> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.book_list_layout, null);
        }

        textView1 = convertView.findViewById(R.id.book_name_text_view);
        TextView textView2 = convertView.findViewById(R.id.author_name_text_view);
        TextView textView3 = convertView.findViewById(R.id.book_price_text_view);

        Books books = booksList.get(position);

        textView1.setText(books.getTitle());
        textView2.setText(books.getAuthor());
        textView3.setText("$" + books.getPrice());
        setListener(position);

        return convertView;
    }

    public void setListener(int position) {

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(context, R.style.appDialog);
                dialog.setTitle("Book Detail");
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.details_book_layout);

                // get book
                Books book = booksList.get(position);

                TextView tv1 = dialog.findViewById(R.id.view_book_name);
                TextView tv2 = dialog.findViewById(R.id.view_author_name);
                TextView tv3 = dialog.findViewById(R.id.view_book_description);
                TextView tv4 = dialog.findViewById(R.id.view_book_price);

                tv1.setText(book.getTitle() + "\n");
                tv2.setText("Author (" + book.getAuthor() + ")\n");
                tv3.setText(book.getDescription() + "\n");
                tv4.setText("Price ($" + book.getPrice() + ")");

                dialog.show();

            }
        });
    }
}
