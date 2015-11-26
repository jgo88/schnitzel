package de.iteratec.schnitzel.client.frontend;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import de.iteratec.schnitzel.client.R;

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


    }

    private void setUserIconPosition(int posX, int posY){

    }

}
