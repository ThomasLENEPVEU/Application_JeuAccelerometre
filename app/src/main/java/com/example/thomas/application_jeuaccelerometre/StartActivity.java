package com.example.thomas.application_jeuaccelerometre;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Thomas on 12/10/2017.
 */

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final Button button_start = findViewById(R.id.buttonstart);
        button_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame(v);
            }
        });

        final Button button_score = findViewById(R.id.buttonscore);
        button_score.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showScore(v);
            }
        });

        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mPlayer.start();
        mPlayer.setLooping(true);
    }

    public void startGame(View view) {
        EditText name = null;
        name = (EditText)findViewById(R.id.username);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("user_name",name.getText().toString());
        startActivity(intent);
    }

    public void showScore(View view) {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }
}