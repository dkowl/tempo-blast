package com.supertempo;

/**
 * Created by Dominik on 6/21/2017.
 */

public class Note {

    public int lane_;
    public float time_;
    public float value_;
    public boolean wasPressed_;

    public Note(int lane, float time){
        lane_ = lane;
        time_ = time;
        wasPressed_ = false;
    }
}
