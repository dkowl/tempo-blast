package com.supertempo;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dominik on 6/21/2017.
 */

public class GridElement {
    Rectangle rect_;
    Vector2 midPoint_;

    public GridElement(float x, float y, float width, float height){
        rect_ = new Rectangle(x, y, width, height);
        midPoint_ = new Vector2(x+width/2, y+height/2);
    }
}
