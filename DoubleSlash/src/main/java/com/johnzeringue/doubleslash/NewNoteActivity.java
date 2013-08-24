package com.johnzeringue.doubleslash;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class NewNoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_note, menu);
        return true;
    }

    public void makeNote(View view) {
        NotesDBAdapter adapter = new NotesDBAdapter(this);
        adapter.open();

        adapter.createNote(getIntent().getStringExtra("projectName"),
                String.valueOf(((TextView) findViewById(R.id.note_name)).getText()),
                String.valueOf(((TextView) findViewById(R.id.note_body)).getText()));

        adapter.close();

        finish();
    }
}
