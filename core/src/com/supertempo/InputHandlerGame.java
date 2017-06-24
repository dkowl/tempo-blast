package com.supertempo;

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
}
