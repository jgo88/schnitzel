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


       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PuzzleStep step = null;
                try {
                    step = RESTClient.getFirstPuzzleStep(1l);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Snackbar.make(view, step!=null? step.getDescription() : "PuzzleStep is NULL", Snackbar.LENGTH_LONG).show();

            }
        });

        testCreateClassesFromCommonProject();
    }

    private void testCreateClassesFromCommonProject() {
        PuzzleStep puzzleStep = new PuzzleStep();
        puzzleStep.setDescription("First puzzle step");

        Puzzle puzzle = new Puzzle();
        puzzle.setName("MyPuzzle, yay!");
        puzzle.setFirstPuzzleStep(puzzleStep);
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