package com.example.placido.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Gabe on 11/24/2015.
 * This class contains all of the names of the columns, tables, etc of the database. Whenever you work with the database, you should refer back to here.
 */
public class ToDoItemsContract {
    public static abstract class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        //These are the different columns that store all of the data of each todoitem.
        public static final String COLUMN_ONE_ENTRY_ID = "nameid";
        public static final String COLUMN_TWO_NAME = "name";
        public static final String COLUMN_THREE_ISCOMPLETE = "isComplete";
        public static final String COLUMN_FOUR_DATE_CREATED = "date";
        public static final String COLUMN_FIVE_DATE_DUE = "dateDue";
    }
    //These define the different pieces of SQLite text that need to be sent to set up a database, initialize it, query it, etc
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoEntry.TABLE_NAME + " (" +
                    TodoEntry._ID + " INTEGER PRIMARY KEY," +
                    TodoEntry.COLUMN_ONE_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    TodoEntry.COLUMN_TWO_NAME + TEXT_TYPE + COMMA_SEP +
                    TodoEntry.COLUMN_THREE_ISCOMPLETE + INT_TYPE + COMMA_SEP +
                    TodoEntry.COLUMN_FOUR_DATE_CREATED + TEXT_TYPE + COMMA_SEP +
                    TodoEntry.COLUMN_FIVE_DATE_DUE + TEXT_TYPE +
                    " )";
    public static String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;

    public class todoDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 3;
        public static final String DATABSE_NAME = "TodoReader.db";

        public todoDbHelper(Context context){
            super(context, DATABSE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
