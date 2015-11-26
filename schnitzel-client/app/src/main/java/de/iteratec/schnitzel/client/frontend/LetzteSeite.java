package de.iteratec.schnitzel.client.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import de.iteratec.schnitzel.client.R;

public class LetzteSeite extends AppCompatActivity implements View.OnClickListener{
    ImageView smileImage;
    Button zurueckButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_letzte_seite);
        smileImage = (ImageView)findViewById(R.id.imageView);
        zurueckButton = (Button)findViewById(R.id.zurueck_button);
        zurueckButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SchnitzelMainActivity.class);
        startActivity(intent);
        finish();
    }
}
