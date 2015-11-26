package de.iteratec.schnitzel.client.frontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Observable;
import java.util.Observer;

import de.iteratec.schnitzel.client.R;
import de.iteratec.schnitzel.client.beacon.BeaconFinder;
import de.iteratec.schnitzel.common.model.Beacon;
import de.iteratec.schnitzel.common.model.PuzzleStep;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NexttargetActivity extends Activity implements Observer{
    private ImageView user;
    private Button up;
    private double currentPos = 0.85;
    private int height;
    private int width;
    private Beacon currentTarget;
    private PuzzleStep currentPuzzleStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        Intent intent = getIntent();
        currentPuzzleStep = (PuzzleStep) intent.getSerializableExtra(SchnitzelMainActivity.PUZZLESTEP);
        currentTarget = currentPuzzleStep.getBeacon();
        BeaconFinder bf = new BeaconFinder();
        bf.addObserver(this);
        bf.startBeaconSearch(this, currentTarget.getBeaconUuid(), this);

        setContentView(R.layout.activity_nexttarget);
        user = (ImageView) findViewById(R.id.iv_user);

        up = (Button) findViewById(R.id.b_up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentPosition(currentPos - 0.05);
                setUserIconPosition(user);
            }
        });
    }

    private void setUserIconPosition(ImageView view){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
        lp.setMargins(width/2-view.getWidth()/2, (int)(currentPos*height), 0, 0);
        view.setLayoutParams(lp);
    }

    public void setCurrentPosition(double pos) {
        this.currentPos = pos;
    }

    @Override
    public void update(Observable observable, Object o) {
        double pos = (double) o;

        if (pos != -1){
            setCurrentPosition(pos/100);

            if(currentPos < 0.03 && currentPuzzleStep.getSuccessor() == null) {
                // endgültiges ziel erreicht -> letzte seite anzeigen
                Intent intent = new Intent(this, LetzteSeite.class);
                startActivity(intent);
                finish();
            } else if (currentPos < 0.03) {
                // zwischenziel erreicht
                // dialog einblenden mit tipp, wo das nächste ziel ist
                // currentpos reseten
                // currenttargetuuid setzen
            }
        }
    }
}
