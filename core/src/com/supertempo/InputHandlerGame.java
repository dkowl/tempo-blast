package com.supertempo;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.supertempo.Screens.Game.GameScreen;
import com.supertempo.Screens.Game.GameWorld;

/**
 * Created by Dominik on 6/22/2017.
 */

public class InputHandlerGame extends InputAdapter {

    private GameWorld gameWorld_;

    public InputHandlerGame(GameWorld gameWorld){
        gameWorld_ = gameWorld;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){

        gameWorld_.touchDown(x, y);

        return true;
    }

    @Override
    public boolean keyDown(int keyCode){
        if(keyCode >= Input.Keys.NUMPAD_1 && keyCode <= Input.Keys.NUMPAD_9){
            int keyId = keyCode - Input.Keys.NUMPAD_1;
            if(keyId < 3) keyId += 6;
            else if (keyId > 5) keyId -=6;
            gameWorld_.song_.hitNote(keyId);
        }

        return true;
    }
}
