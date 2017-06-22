package com.supertempo;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dominik on 6/21/2017.
 */

public class Grid {

    private GridElement[][] grid_;
    private float rectW_, rectH_;

    public Grid(Rectangle rect, int width, int height){ //width and height - number of elements each dimension
        grid_ = new GridElement[height][width];

        rectW_ = rect.width / width;
        rectH_ = rect.height / height; //dimensions of a single grid element


        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                float
                        currentX = rect.x + j*rectW_,
                        currentY = rect.y + i*rectH_;

                grid_[i][j] = new GridElement(currentX, currentY, rectW_, rectH_);
            }
        }
    }

    public Vector2 midPoint(int i, int j){
        return grid_[i][j].midPoint_;
    }

    public Vector2 elementSize(){
        return new Vector2(rectW_, rectH_);
    }
}
