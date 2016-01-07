package br.com.devops.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;

import br.com.devops.book.manager.DatabaseManager;
import br.com.devops.book.model.Book;

public class RegisterActivity extends AppCompatActivity{

    private EditText name;
    private EditText isbn;
    private EditText editora;
    private EditText author;
    private Long idBook;
    private Button btnRegisterOrUpdate;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager.init(this);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            idBook = bundle.getLong("bookId");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignFieldToRegister();

        populateFieldIfEdit(idBook);

        onClickRegister();

    }

    private void assignFieldToRegister() {
        name = (EditText) findViewById(R.id.name);
        author = (EditText) findViewById(R.id.author);
        editora = (EditText) findViewById(R.id.editora);
        isbn = (EditText) findViewById(R.id.isbn);
        btnRegisterOrUpdate = (Button) findViewById(R.id.register_button);
    }

    private void populateFieldIfEdit(final Long idBook) {
        if(idBook != null) {
            book = DatabaseManager.getInstance().searchBookById(idBook);
            name.setText(book.getName());
            author.setText(book.getAuthor());
            isbn.setText(book.getIsbn());
            editora.setText(book.getEditora());
            btnRegisterOrUpdate.setText("Update");
        }

    }

    private void onClickRegister() {

        btnRegisterOrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignFieldToRegister();
                if (isValidField(name, "Name is Required!")
                        && isValidField(author, "Author is Required!")
                        && isValidField(editora, "Editora is Required!")
                        && isValidField(isbn, "ISBN is Required!")) {
                    saveOrUpdateBook(name.getText().toString(), author.getText().toString(), editora.getText().toString(), isbn.getText().toString());
                    Intent intent = new Intent(RegisterActivity.this, BookListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    private void saveOrUpdateBook(final String name, final String author, final String editora, final String isbn) {
        final Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setEditora(editora);

        if(idBook == null){
            DatabaseManager.getInstance().saveBook(book);
        }else{
            // update
            book.setId(idBook);
            DatabaseManager.getInstance().updateBook(book);
        }

    }

    private boolean isValidField(final EditText field, final String message){
        if ( field.getText().toString().equals("") ) {
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
