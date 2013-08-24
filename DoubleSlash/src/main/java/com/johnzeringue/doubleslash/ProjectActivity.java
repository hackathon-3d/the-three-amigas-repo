package com.johnzeringue.doubleslash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectActivity extends Activity {
    private NotesDBAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
    }

    @Override
    protected void onResume() {
        super.onResume();

        _adapter = new NotesDBAdapter(this);
        _adapter.open();

        showNotes();
    }

    private void showNotes() {
        List<String> noteList = new ArrayList<String>();
        Cursor notes = _adapter.fetchProjectNoteTitles(getIntent().getStringExtra("projectName"));

        if (notes.getCount() > 0) {
            notes.moveToFirst();

            while (!notes.isLast()) {
                noteList.add(notes.getString(0));
                notes.moveToNext();
            }
            noteList.add(notes.getString(0));
        }

        _adapter.close();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.simple_note_item,
                noteList.toArray(new String[noteList.size()]));

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
    }

    public void showItem(View view) {
        Intent intent = new Intent(this, EditNoteActivity.class)
                .putExtras(getIntent().getExtras())
                .putExtra("title", ((TextView) view).getText());
        startActivity(intent);
    }

    public void newNote(View view) {
        startActivity(new Intent(this, NewNoteActivity.class).putExtras(getIntent().getExtras()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project, menu);
        return true;
    }
    
}
