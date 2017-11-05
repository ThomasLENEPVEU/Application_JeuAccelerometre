package com.example.thomas.application_jeuaccelerometre;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.thomas.application_jeuaccelerometre.ScoreData;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;

public class ScoreMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<ScoreData> score_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoremap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        score_list = new ArrayList<ScoreData>();
        loadScoreDataFile();
        //test
        /*
        score_list.add(new ScoreData("test1", 100, 0, 10));
        score_list.add(new ScoreData("test2", 200, 0, 10));
        score_list.add(new ScoreData("test3", 300, 0, 10));
        score_list.add(new ScoreData("test4", 400, 0, 10));
        */
        System.out.println("Test Score list");
        for(int i=0; i<score_list.size(); i++){
            System.out.println(score_list.get(i).getPlayer_name());

            System.out.println(score_list.get(i).getPlayer_score());
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(int i=0; i<score_list.size(); i++){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(score_list.get(i).getPlayer_lat(),score_list.get(i).getPlayer_lng()))
                    .title(score_list.get(i).getPlayer_name()
                            + ", Score : " + score_list.get(i).getPlayer_score()
                            + ", Lat : " + score_list.get(i).getPlayer_lat()
                            + ", Lng : " + score_list.get(i).getPlayer_lng()
                    )
            );
        }
        if(getIntent().hasExtra("score_latitude")) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
                    getIntent().getDoubleExtra("score_latitude", 0),
                    getIntent().getDoubleExtra("score_longitude", 0)
            )));
        }else{
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng((double) 0, (double) 0)));
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