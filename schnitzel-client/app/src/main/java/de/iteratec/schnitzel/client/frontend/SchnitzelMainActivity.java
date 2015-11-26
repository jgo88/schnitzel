package de.iteratec.schnitzel.client.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.iteratec.schnitzel.client.R;
import de.iteratec.schnitzel.client.communication.RESTClient;
import de.iteratec.schnitzel.common.model.Puzzle;
import de.iteratec.schnitzel.common.model.PuzzleStep;

public class SchnitzelMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schnitzel_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Puzzle> puzzlelist = testCreateClassesFromCommonProject();
        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(new PuzzleAdapter(getApplicationContext(),-1,puzzlelist.toArray(new Puzzle[puzzlelist.size()])));
    }

    private ArrayList<Puzzle> testCreateClassesFromCommonProject() {
        PuzzleStep puzzleStep = new PuzzleStep();
        puzzleStep.setDescription("First puzzle step");

        Puzzle puzzle = new Puzzle();
        puzzle.setName("MyPuzzle, yay!");
        puzzle.setFirstPuzzleStep(puzzleStep);

        Puzzle puzzle1 = new Puzzle();
        puzzle1.setName("MyPuzzle,Teil 2 yay!");
        puzzle1.setFirstPuzzleStep(null);

        ArrayList<Puzzle> puzzlelist = new ArrayList<>();
        puzzlelist.add(puzzle);
        puzzlelist.add(puzzle1);
        return puzzlelist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schnitzel_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, NexttargetActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
