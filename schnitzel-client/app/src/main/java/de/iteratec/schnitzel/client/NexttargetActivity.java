package de.iteratec.schnitzel.client;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NexttargetActivity extends Activity {



    private ImageView backgroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nexttarget);

        backgroundView = (ImageView) findViewById(R.id.iv_background);


    }

    private void draw(){

    }

}
