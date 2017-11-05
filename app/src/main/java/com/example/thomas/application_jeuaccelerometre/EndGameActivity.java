/*
  Copyright 2017 Google Inc. All Rights Reserved.
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.example.thomas.application_jeuaccelerometre;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

/**
 * Location sample.
 * <p>
 * Demonstrates use of the Location API to retrieve the last known location for a device.
 */
public class EndGameActivity extends AppCompatActivity {

    private static final String TAG = EndGameActivity.class.getSimpleName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    private String mUserLabel;
    private String mScoreLabel;
    private TextView mUserText;
    private TextView mScoreText;
    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    protected String mUserName;
    protected int mUserScore;
    protected double mLatitude;
    protected double mLongitude;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        mLatitude=0;
        mLongitude=0;
        mUserName = getIntent().getStringExtra("user_name");
        mUserScore = getIntent().getIntExtra("user_score", 0);

        mUserLabel = getResources().getString(R.string.user_label);
        mScoreLabel = getResources().getString(R.string.score_label);
        mUserText = (TextView) findViewById((R.id.user_text));
        mScoreText = (TextView) findViewById((R.id.score_text));
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));

        mUserText.setText(String.format("%s: %s",
                mUserLabel,
                mUserName));
        mScoreText.setText(String.format("%s: %s",
                mScoreLabel,
                String.valueOf(mUserScore)));
        mLatitudeText.setText(String.format("%s: %s",
                mLatitudeLabel,
                String.valueOf(mLatitude)));
        mLongitudeText.setText(String.format("%s: %s",
                mLongitudeLabel,
                String.valueOf(mLongitude)));

        final Button button_save_true = (Button)findViewById(R.id.buttonsavetrue);
        button_save_true.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showScoreWithSave(v);
            }
        });

        final Button button_save_false = (Button)findViewById(R.id.buttonsavefalse);
        button_save_false.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showScoreWithoutSave(v);
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            mLatitude = mLastLocation.getLatitude();
                            mLongitude = mLastLocation.getLongitude();

                            mLatitudeText.setText(String.format("%s: %s",
                                    mLatitudeLabel,
                                    String.valueOf(mLatitude)));
                            mLongitudeText.setText(String.format("%s: %s",
                                    mLongitudeLabel,
                                    String.valueOf(mLongitude)));
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                        }
                    }
                });
    }

    /**
     * Return the current state of the permissions needed.
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(EndGameActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        Log.i(TAG, "Requesting permission");
        // Request permission. It's possible this can be auto answered if device policy
        // sets the permission in a given state or the user denied the permission
        // previously and checked "Never ask again".
        startLocationPermissionRequest();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
            }
        }
    }

    public void showScoreWithSave(View view) {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("user_name", mUserName);
        intent.putExtra("user_score", mUserScore);
        intent.putExtra("user_latitude", mLatitude);
        intent.putExtra("user_longitude", mLongitude);
        startActivity(intent);
    }

    public void showScoreWithoutSave(View view) {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }
}
