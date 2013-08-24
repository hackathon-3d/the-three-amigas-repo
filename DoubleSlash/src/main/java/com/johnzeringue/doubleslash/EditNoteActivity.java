package com.johnzeringue.doubleslash;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class EditNoteActivity extends Activity {
    private NotesDBAdapter _adapter;
    private long _id;
    private boolean _deleting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _deleting = false;

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

    public void deleteNote(View view) {
        _adapter.deleteNote(_id);
        _adapter.close();

        _deleting = true;

        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!_deleting) {
            _adapter.updateNote(_id,
                    String.valueOf(((TextView) findViewById(R.id.editText)).getText()),
                    String.valueOf(((TextView) findViewById(R.id.editText2)).getText()));

            _adapter.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_note, menu);
        return true;
    }
    
}
