package com.supertempo.Resources;

import com.badlogic.gdx.Preferences;
import com.supertempo.SuperTempo;

/**
 * Created by Dominik on 7/26/2017.
 */

//represents a score and handles saving it in prefs
public class SongScore {

    private String name_;
    private int stars_, bestPoints_, bestTotalPoints_;

    public SongScore(String name){
        name_ = name;
    }

    public int stars(){ return stars_; }

    //saving and loading
    public void load(){
        Preferences prefs = SuperTempo.instance.prefs;
        stars_ = prefs.getInteger(name_ + ".stars", 0);
        bestPoints_ = prefs.getInteger(name_ + ".points", 0);
        bestTotalPoints_ = prefs.getInteger(name_ + ".totalPoints", 0);
    }

    public void save(){
        Preferences prefs = SuperTempo.instance.prefs;
        prefs.putInteger(name_ + ".stars", stars_);
        prefs.putInteger(name_ + ".points", bestPoints_);
        prefs.putInteger(name_ + ".totalPoints", bestTotalPoints_);
        prefs.flush();
        load();
    }

    public void updateScore(int stars, int points, int totalPoints){
        if(points >= bestPoints_){
            stars_ = stars;
            bestPoints_ = points;
            bestTotalPoints_ = totalPoints;

            save();
        }
    }
}
