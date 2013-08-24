package com.johnzeringue.doubleslash;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class NewProjectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_project, menu);
        return true;
    }

    public void makeProject(View view) {
        NotesDBAdapter adapter = new NotesDBAdapter(this);
        adapter.open();

        adapter.createNote(String.valueOf(((TextView) findViewById(R.id.editText)).getText()),
                "New Note", "Replace this text with your own!");

        adapter.close();

        finish();
    }
}
