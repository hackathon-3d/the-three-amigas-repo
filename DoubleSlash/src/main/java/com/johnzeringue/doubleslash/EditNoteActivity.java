package com.johnzeringue.doubleslash;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class EditNoteActivity extends Activity {
    private NotesDBAdapter _adapter;
    private long _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        _adapter = new NotesDBAdapter(this);
        _adapter.open();

        Cursor cursor = _adapter.fetchNoteByTitle(getIntent().getStringExtra("projectName"),
                getIntent().getStringExtra("title"));
        cursor.moveToFirst();
        _id = cursor.getLong(cursor.getColumnIndex("_id"));
        ((TextView) findViewById(R.id.editText)).setText(cursor.getString(cursor.getColumnIndex("title")));
        ((TextView) findViewById(R.id.editText2)).setText(cursor.getString(cursor.getColumnIndex("body")));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        _adapter.updateNote(_id,
                String.valueOf(((TextView) findViewById(R.id.editText)).getText()),
                String.valueOf(((TextView) findViewById(R.id.editText2)).getText()));

        _adapter.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_note, menu);
        return true;
    }
    
}
