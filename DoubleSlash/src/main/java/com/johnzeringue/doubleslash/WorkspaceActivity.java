package com.johnzeringue.doubleslash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WorkspaceActivity extends Activity {
    private NotesDBAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);
    }

    @Override
    protected void onResume() {
        super.onResume();

        _adapter = new NotesDBAdapter(this);
        _adapter.open();

        showProjects();
    }

    public void showProjects() {
        List<String> projectList = new ArrayList<String>();
        Cursor projects = _adapter.fetchProjects();

        if (projects.getCount() > 0) {
            projects.moveToFirst();

            while (!projects.isLast()) {
                projectList.add(projects.getString(0));
                projects.moveToNext();
            }
            projectList.add(projects.getString(0));
        }

        _adapter.close();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.simple_project_item,
                projectList.toArray(new String[projectList.size()]));

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
    }


    public void newProject(View view) {
        startActivity(new Intent(this, NewProjectActivity.class));
    }

    public void showProject(View view) {
        startActivity(new Intent(this, ProjectActivity.class).putExtra("projectName", ((TextView) view).getText()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.workspace, menu);
        return true;
    }
    
}
