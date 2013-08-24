package com.johnzeringue.doubleslash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by John on 8/24/13.
 */
public class NotesDBAdapter {
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_PROJECT = "project";

    public static final String TAG = "NotesDBAdapter";
    private DatabaseHelper _dbHelper;
    private SQLiteDatabase _db;

    private static final String DATABASE_CREATE =
            "create table notes (_id integer primary key autoincrement, "
            + "project text not null, title text not null, body text not null);";

    private static final String DATABASE_NAME = "doubleslash-data";
    private static final String DATABASE_TABLE = "notes";
    private static final int DATABASE_VERSION = 5;

    private final Context _context;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    public NotesDBAdapter(Context ctx) {
        this._context = ctx;
    }

    public NotesDBAdapter open() throws SQLException {
        _dbHelper = new DatabaseHelper(_context);
        _db = _dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        _dbHelper.close();
    }

    public long createNote(String project, String title, String body) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_PROJECT, project);
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);

        return _db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteNote(long rowId) {
        return _db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchProjects() {
        return _db.query(true, DATABASE_TABLE, new String[] {KEY_PROJECT}, null, null, null, null, null, null);
    }

    public Cursor fetchProjectNoteTitles(String project) {
        return _db.query(true, DATABASE_TABLE, new String[] {KEY_TITLE}, KEY_PROJECT + "='" + project + "'",
                null, null, null, null, null);
    }

    public Cursor fetchNoteByTitle(String project, String title) {
        return _db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE, KEY_BODY}, KEY_PROJECT + "=? AND " + KEY_TITLE + "=?",
                new String[] {project, title}, null, null, null, null);
    }

    public boolean updateNote(long rowId, String title, String body) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_BODY, body);

        return _db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
