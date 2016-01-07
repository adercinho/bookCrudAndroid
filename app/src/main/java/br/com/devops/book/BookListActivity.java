package br.com.devops.book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.devops.book.manager.DatabaseManager;
import br.com.devops.book.model.Book;

public class BookListActivity extends AppCompatActivity {

    private ListView bookListView;
    private Button searchButton;
    private EditText searchText;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseManager.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bookListView = (ListView) findViewById(R.id.bookListView);
        searchText = (EditText) findViewById(R.id.search_text);
        searchButton = (Button) findViewById(R.id.search_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadGrid(BookListActivity.this);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadGrid(this);
    }

    private void loadGrid(final Activity activity) {
        final List<Book> books = DatabaseManager.getInstance().findBookByName(searchText.getText().toString());
        List<String> titles = new ArrayList<String>();
        for (Book book : books) {
            titles.add(book.getName().toUpperCase());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        bookListView.setAdapter(adapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = books.get(position);
                Intent intent = new Intent(activity, BookDetailActivity.class);
                intent.putExtra("bookId", book.getId());
                startActivity(intent);
            }
        });
    }


}
