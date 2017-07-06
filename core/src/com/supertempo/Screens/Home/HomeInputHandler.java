package com.supertempo.Screens.Home;

import com.badlogic.gdx.InputAdapter;
import com.supertempo.SuperTempo;

/**
 * Created by Dominik on 7/4/2017.
 */

public class HomeInputHandler extends InputAdapter {

    HomeScreen homeScreen_;

    public HomeInputHandler(HomeScreen homeScreen){
        homeScreen_ = homeScreen;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!homeScreen_.isLoading_) {
            homeScreen_.finish();
        }

        return true;
    }
}
