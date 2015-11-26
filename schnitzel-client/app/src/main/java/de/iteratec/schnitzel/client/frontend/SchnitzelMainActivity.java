package de.iteratec.schnitzel.client.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.iteratec.schnitzel.client.R;
import de.iteratec.schnitzel.client.communication.RESTClient;
import de.iteratec.schnitzel.common.model.Puzzle;

public class SchnitzelMainActivity extends AppCompatActivity {
    public static final String PUZZLESTEP = "iteraschnitzel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schnitzel_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Puzzle> puzzlelist = new ArrayList<>();
        try {
            puzzlelist = RESTClient.getPuzzles();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListView listview = (ListView) findViewById(R.id.listView);
        if(puzzlelist != null) {
            listview.setAdapter(new PuzzleAdapter(getApplicationContext(), -1, puzzlelist.toArray(new Puzzle[puzzlelist.size()])));
        }

        final List<Puzzle> finalpuzzlelist = puzzlelist;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SchnitzelMainActivity.this, NexttargetActivity.class);
                intent.putExtra(PUZZLESTEP, finalpuzzlelist.get(position).getFirstPuzzleStep());
                startActivity(intent);
            }
        });
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
