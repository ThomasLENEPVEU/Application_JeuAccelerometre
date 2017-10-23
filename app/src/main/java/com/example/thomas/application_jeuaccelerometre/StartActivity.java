package com.example.thomas.application_jeuaccelerometre;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Thomas on 12/10/2017.
 */

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final Button button_start = findViewById(R.id.button);
        button_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame(v);
            }
        });
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}