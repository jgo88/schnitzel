package de.iteratec.schnitzel.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LetzteSeite extends AppCompatActivity implements View.OnClickListener{
    ImageView smileImage;
    Button zurueckButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_letzte_seite);
        smileImage = (ImageView)findViewById(R.id.imageView);
        zurueckButton = (Button)findViewById(R.id.zurueck_button);
        smileImage.setImageResource(R.drawable.smile_top_img);
        zurueckButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SchnitzelMainActivity.class);
        startActivity(intent);
    }
}
