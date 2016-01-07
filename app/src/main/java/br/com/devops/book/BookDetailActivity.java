package br.com.devops.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.devops.book.manager.DatabaseManager;
import br.com.devops.book.model.Book;

public class BookDetailActivity extends AppCompatActivity {

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseManager.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        Long idBook = bundle.getLong("bookId");
        book = DatabaseManager.getInstance().searchBookById(idBook);

        setInformationView(book);

        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager.getInstance().deleteBook(book);
                Intent intent = new Intent(BookDetailActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });

        Button btnEdit = (Button) findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(BookDetailActivity.this, RegisterActivity.class);
               intent.putExtra("bookId", book.getId());
               startActivity(intent);
           }
        });
    }



    private void setInformationView(Book book) {
        TextView textViewName = (TextView)findViewById(R.id.name);
        textViewName.setText("Name Book: " + book.getName());

        TextView textViewAuthor = (TextView)findViewById(R.id.author);
        textViewAuthor.setText("Author: " + book.getAuthor());

        TextView textViewEditora = (TextView)findViewById(R.id.editora);
        textViewEditora.setText("Editora: " + book.getEditora());

        TextView textViewIsbn = (TextView)findViewById(R.id.isbn);
        textViewIsbn.setText("ISBN: " + book.getIsbn());

    }
}
