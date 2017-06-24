package com.supertempo;

import com.supertempo.Screens.Game.GameWorld;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Dominik on 6/21/2017.
 */

public class Song {

    String name_;
    ArrayList<Note> notes_;
    public Key[] keys_ = new Key[GameWorld.laneCount()];
    float noteLifeTime = 1f;
    int firstActiveNote_ = 0, lastActiveNote_ = 0;
    float time_ = 0f;

    public Song(String name){
        name_ = name;
        for(int i = 0; i<keys_.length; i++){
            keys_[i] = new Key();
        }
    }

    //returns all active notes in reverse order
    public ArrayList<Note> activeNotes(){
        ArrayList<Note> result = new ArrayList<Note>();
        for(int i = lastActiveNote_ - 1; i>=firstActiveNote_; i--){
            result.add(notes_.get(i));
        }
        return result;
    }

    Note firstActiveNote(){
        return notes_.get(firstActiveNote_);
    }

    public void hitNote(int lane){
        boolean correct = false;
        if(firstActiveNote().lane_ == lane){
            correct = true;
            firstActiveNote_++;
        }

        keys_[lane].click(correct);
    }

    public void updateTime(float time){
        while(firstActiveNote_ < notes_.size() && firstActiveNote_<notes_.size() && notes_.get(firstActiveNote_).time_ < time){
            keys_[firstActiveNote().lane_].click(false);
            firstActiveNote_++;
        }
        while(lastActiveNote_ < notes_.size() - 1 && lastActiveNote_<notes_.size() && notes_.get(lastActiveNote_).time_ < time + noteLifeTime){
            lastActiveNote_++;
        }

        for(int i = firstActiveNote_; i<=lastActiveNote_; i++) {
            notes_.get(i).value_ = (time - (notes_.get(i).time_ - noteLifeTime)) / noteLifeTime;
        }

        for(int i = 0; i<keys_.length; i++){
            keys_[i].updateDelta(time - time_);
        }

        time_ = time;
    }

    public void randomize(int noteNo, int laneNo, float length){
        notes_ = new ArrayList<Note>(noteNo);
        for(int i = 0; i<noteNo; i++){
            int lane = ThreadLocalRandom.current().nextInt(0, laneNo);
            float time = (float)i * length/noteNo + 3;
            notes_.add(new Note(lane, time));
        }
    }

}
