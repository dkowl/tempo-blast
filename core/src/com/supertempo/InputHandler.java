package com.supertempo;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Dominik on 6/22/2017.
 */

public class InputHandler extends InputAdapter {

    private SuperTempo game_;

    public InputHandler(SuperTempo game){
        game_ = game;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){

        for(int i = 0; i<game_.lines_.size(); i++){
            Line line = game_.lines_.get(i);
            if(line.click(x, y)){
                game_.song.hitNote(i);
            }
        }

        return true;
    }
}
