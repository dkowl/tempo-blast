package com.supertempo;

/**
 * Created by Dominik on 6/22/2017.
 */

public class Key {

    public boolean correct_;
    public float value_;
    public float stayTime_= 0.3f;

    public void updateDelta(float deltaTime){
        value_ -= deltaTime/stayTime_;
        if(value_ < 0) value_ = 0;
    }

    public void click(boolean correct){
        correct_ = correct;
        value_ = 1;
    }
}
