package com.example.thomas.application_jeuaccelerometre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Thomas on 12/10/2017.
 */

public class ScoreActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        final Button button_scoremap = findViewById(R.id.buttonscoremap);
        button_scoremap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showScoreMap(v);
            }
        });
    }

    public void showScoreMap(View view) {
        Intent intent = new Intent(this, ScoreMapActivity.class);
        startActivity(intent);
    }
}
