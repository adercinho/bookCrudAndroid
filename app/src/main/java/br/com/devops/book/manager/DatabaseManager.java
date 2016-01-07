package br.com.devops.book.manager;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.devops.book.helper.BookHelper;
import br.com.devops.book.model.Book;

/**
 * Created by adercio on 23/12/15.
 */
public class DatabaseManager {
    static private DatabaseManager instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private BookHelper helper;
    private DatabaseManager(Context ctx) {
        helper = new BookHelper(ctx);
    }

    private BookHelper getHelper() {
        return helper;
    }

    public List<Book> getAllBookLists() {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = getHelper().getBookDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public List<Book> findBookByName(final String name) {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = getHelper().getBookDao().queryBuilder().where().like("name", "%"+name+"%").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public void saveBook(final Book book) {
        try {
            getHelper().getBookDao().create(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(final Book book) {
        try {
            getHelper().getBookDao().update(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(final Book book) {
        try {
            getHelper().getBookDao().deleteById(book.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book searchBookById(final Long id) {
        Book book = null;
        try {
            book = getHelper().getBookDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
}
