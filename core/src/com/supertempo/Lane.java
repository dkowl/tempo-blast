package com.supertempo;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dominik on 6/21/2017.
 */

public class Lane {

    public Vector2 beginPoint_, endPoint_, beginSize_, endSize_;

    public Lane(Vector2 beginPoint, Vector2 endPoint, Vector2 beginSize, Vector2 endSize){
        beginPoint_ = beginPoint;
        endPoint_ = endPoint;
        beginSize_ = beginSize;
        endSize_ = endSize;
    }

    public boolean click(int x, int y){
        if(x<endPoint_.x - endSize_.x/2f || x > endPoint_.x + endSize_.x/2f) return false;
        if(y<endPoint_.y - endSize_.y/2f || y > endPoint_.y + endSize_.y/2f) return false;

        return true;
    }

    public Rectangle lerpRect(float value){
        Vector2
                size = lerp(beginSize_, endSize_, value),
                point = lerp(beginPoint_, endPoint_, value);

        return new Rectangle(
                point.x - size.x/2,
                point.y - size.y/2,
                size.x,
                size.y
        );
    }

    public float lerp(float x, float y, float value){
        return x + (y - x) * value;
    }

    Vector2 lerp(Vector2 p1, Vector2 p2, float value){
        return new Vector2(lerp(p1.x, p2.x, value), lerp(p1.y, p2.y, value));
    }
}
