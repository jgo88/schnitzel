package de.iteratec.schnitzel.client.frontend;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import de.iteratec.schnitzel.client.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NexttargetActivity extends Activity {
    private ImageView user;
    private Button info;
    private double currentPos = 0.85;
    private int height;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        setContentView(R.layout.activity_nexttarget);
        user = (ImageView) findViewById(R.id.iv_user);

        info = (Button) findViewById(R.id.b_info);

        final AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle("Hinweis:");
        diag.setMessage("TEST");
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
        lp.setMargins(width/2-view.getWidth()/2, (int)(currentPos*height), 0, 0);
        view.setLayoutParams(lp);
    }

    public void setCurrentPosition(double pos) {
        this.currentPos = pos;
    }

}
