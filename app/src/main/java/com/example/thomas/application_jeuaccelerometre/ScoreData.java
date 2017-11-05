package com.example.thomas.application_jeuaccelerometre;

import java.io.Serializable;

/**
 * Created by Thomas on 05/11/2017.
 */

public class ScoreData implements Serializable {
    String player_name;
    int player_score;
    double player_lat, player_lng;

    public ScoreData(String p_n, int p_s, double p_lat, double p_lng){
        player_name = p_n;
        player_score = p_s;
        player_lat = p_lat;
        player_lng = p_lng;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public int getPlayer_score() {
        return player_score;
    }

    public double getPlayer_lat() {
        return player_lat;
    }

    public double getPlayer_lng() {
        return player_lng;
    }

    public void setPlayer_lat(double player_lat) {
        this.player_lat = player_lat;
    }

    public void setPlayer_lng(double player_lng) {
        this.player_lng = player_lng;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public void setPlayer_score(int player_score) {
        this.player_score = player_score;
    }
}