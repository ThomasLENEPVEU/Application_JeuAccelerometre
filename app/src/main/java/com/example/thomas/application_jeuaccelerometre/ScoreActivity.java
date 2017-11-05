package com.example.thomas.application_jeuaccelerometre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import com.example.thomas.application_jeuaccelerometre.ScoreData;

/**
 * Created by Thomas on 12/10/2017.
 */

public class ScoreActivity extends Activity{
    ArrayList<ScoreData> score_list;

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
        final Button button_title = findViewById(R.id.buttontitle);
        button_title.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnToStart(v);
            }
        });

        score_list = new ArrayList<ScoreData>();
        loadScoreDataFile();
        if(getIntent().hasExtra("user_name")){
            score_list.add(new ScoreData(
                    getIntent().getStringExtra("user_name"),
                    getIntent().getIntExtra("user_score", 0),
                    getIntent().getDoubleExtra("user_latitude", 0),
                    getIntent().getDoubleExtra("user_longitude", 0)
            ));
        }
        //test
        /*
        score_list.add(new ScoreData("test1", 100, 0, 0));
        score_list.add(new ScoreData("test2", 200, 0, 10));
        score_list.add(new ScoreData("test3", 300, 10, 0));
        score_list.add(new ScoreData("test4", 400, 10, 10));
        */
        System.out.println("Test Score list");
        for(int i=0; i<score_list.size(); i++){
            System.out.println(score_list.get(i).getPlayer_name());

            System.out.println(score_list.get(i).getPlayer_score());
        }

        saveScoreDataFile();
    }

    public void showScoreMap(View view) {
        Intent intent = new Intent(this, ScoreMapActivity.class);
        startActivity(intent);
    }

    public void returnToStart(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void saveScoreDataFile(){
        File d = getFilesDir();
        File f = new File(d, "ScoreDataFile.ser");
        FileOutputStream fos = null;
        try{/*
            fos = openFileOutput("ScoreDataFile.res", Context.MODE_PRIVATE);
            */
            fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(score_list);
            oos.close();
            fos.close();
            System.out.println("Score list Save successful");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadScoreDataFile(){

        FileInputStream fis;
        try{
            fis = openFileInput("ScoreDataFile.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            score_list = (ArrayList<ScoreData>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Score list Load successful");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
