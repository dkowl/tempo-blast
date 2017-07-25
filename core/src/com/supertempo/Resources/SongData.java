package com.supertempo.Resources;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Dominik on 6/29/2017.
 */

public class SongData{
    private String artist_, title_, filename_;
    private float length_;
    private int stars_, bestPoints_, bestTotalPoints_;

    SongData(String artist, String title, String filename, float length){
        artist_ = artist;
        title_ = title;
        filename_ = filename;
        length_ = length;
        stars_ = 0; bestPoints_ = 0; bestTotalPoints_ = 0;
    }

    public String name(){
        return artist_ + " - " + title_;
    }

    public String songPath(){
        return "music/" + filename_ + ".mp3";
    }

    public String notePath(){
        return "music/notes/easy/" + filename_ + ".notes";
    }

    public float length(){
        return length_;
    }

    public int stars(){ return stars_; }

    //saving and loading
    public void load(Preferences prefs){
        stars_ = prefs.getInteger(name() + ".stars", 0);
        bestPoints_ = prefs.getInteger(name() + ".points", 0);
        bestTotalPoints_ = prefs.getInteger(name() + ".totalPoints", 0);
    }

    public void save(Preferences prefs){
        prefs.putInteger(name() + ".stars", stars_);
        prefs.putInteger(name() + ".points", bestPoints_);
        prefs.putInteger(name() + ".totalPoints", bestTotalPoints_);
        prefs.flush();
        load(prefs);
    }

    public void updateScore(int stars, int points, int totalPoints, Preferences prefs){
        if(points >= bestPoints_){
            stars_ = stars;
            bestPoints_ = points;
            bestTotalPoints_ = totalPoints;

            save(prefs);
        }
    }

    public AssetDescriptor<Music> descriptor(){
        return new AssetDescriptor<Music>(songPath(), Music.class);
    }
}
