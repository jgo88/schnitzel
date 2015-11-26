package de.iteratec.schnitzel.client.frontend;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private Button info;
    private double currentPos = 0.85;
    private int height;
    private int width;
    private Beacon currentTarget;
    private BeaconFinder bf;
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
        bf = new BeaconFinder();
        bf.addObserver(this);
        bf.startBeaconSearch(this, currentTarget.getBeaconUuid(), this);

        setContentView(R.layout.activity_nexttarget);
        user = (ImageView) findViewById(R.id.iv_user);

        info = (Button) findViewById(R.id.b_info);

        final AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle("Hinweis:");
        diag.setMessage(currentPuzzleStep.getDescription());
        diag.setCancelable(true);
        diag.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDiag = diag.create();
                alertDiag.show();
            }
        });
    }

    private void setUserIconPosition(ImageView view){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
        if(currentPos > 0.85) {
            currentPos = 0.85;
        }
        int newPos = (int)(currentPos*height);
        lp.setMargins(width/2-view.getWidth()/2, newPos, 0, 0);
        view.setLayoutParams(lp);
    }

    public void setCurrentPosition(double pos) {
        this.currentPos = pos;
    }

    @Override
    public void update(Observable observable, Object o) {
        double result = new Double(""+o);
        double pos = (100.0-result)/100.0;

        if (pos != 0 && pos != -1){
            setCurrentPosition(pos);
            setUserIconPosition(user);

            if(currentPos < 0.6 && currentPuzzleStep.getSuccessor() == null) {
                // endgültiges ziel erreicht -> letzte seite anzeigen
                Intent intent = new Intent(this, LetzteSeite.class);
                startActivity(intent);
                finish();
            } else if (currentPos < 0.6) {
                // zwischenziel erreicht
                // dialog einblenden mit tipp, wo das nächste ziel ist
                // currentpos reseten
                // currenttargetuuid setzen
                final AlertDialog.Builder diag = new AlertDialog.Builder(this);
                diag.setTitle("Zwischenziel gefunden!!");
                diag.setMessage(currentPuzzleStep.getDescription());
                diag.setCancelable(true);
                diag.setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                currentPuzzleStep = currentPuzzleStep.getSuccessor();
                                currentTarget = currentPuzzleStep.getBeacon();
                                bf.startBeaconSearch(NexttargetActivity.this, currentTarget.getBeaconUuid(), getApplicationContext());
                                dialog.cancel();
                            }
                        });
                bf.stopBeaconSearch();
                diag.create().show();
            }
        }
    }
}
