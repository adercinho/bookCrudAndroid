package br.com.devops.book.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import br.com.devops.book.model.Book;

/**
 * Created by adercio on 23/12/15.
 */
public class BookHelper extends OrmLiteSqliteOpenHelper{

        // name of the database file for your application -- change to something appropriate for your app
        private static final String DATABASE_NAME = "bookDB.sqlite";

        // any time you make changes to your database objects, you may have to increase the database version
        private static final int DATABASE_VERSION = 3;

        // the DAO object we use to access the SimpleData table
        private Dao<Book, Long> bookDao = null;

        public BookHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
            try {
                TableUtils.createTable(connectionSource, Book.class);
            } catch (SQLException e) {
                Log.e(BookHelper.class.getName(), "Can't create database", e);
                throw new RuntimeException(e);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
            try {
                List<String> allSql = new ArrayList<String>();
                switch(oldVersion)
                {
                    case 1:
                        //allSql.add("alter table AdData add column `new_col` VARCHAR");
                        //allSql.add("alter table AdData add column `new_col2` VARCHAR");
                }
                for (String sql : allSql) {
                    db.execSQL(sql);
                }
            } catch (SQLException e) {
                Log.e(BookHelper.class.getName(), "exception during onUpgrade", e);
                throw new RuntimeException(e);
            }

        }

        public Dao<Book, Long> getBookDao() {
            if (null == bookDao) {
                try {
                    bookDao = getDao(Book.class);
                }catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
            return bookDao;
        }
}
