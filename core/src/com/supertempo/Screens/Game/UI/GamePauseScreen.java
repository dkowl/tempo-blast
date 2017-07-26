package com.supertempo.Screens.Game.UI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.supertempo.Resources.Resources;
import com.supertempo.SuperTempo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dominik on 7/26/2017.
 */

public class GamePauseScreen {

    private Stage stage_;
    private Dialog dialog_;

    public GamePauseScreen(Stage stage){
        stage_ = stage;

        dialog_ = new Dialog("", Resources.uiSkin){
            protected void result(Object object){
                if(object.equals(1)){
                    new Timer().schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    SuperTempo.instance.gameScreen.resume();
                                }
                            },
                            1500
                    );
                }
                else{
                    SuperTempo.SetScreen(SuperTempo.ScreenID.Songs);
                }
            }
        };

        dialog_.button("Resume   ", 1);
        dialog_.button("Main Menu", 2);

        dialog_.pad(32);
        dialog_.text("Paused").center();
        dialog_.getButtonTable();

    }

    public void show(){
        dialog_.show(stage_);
    }

    public void hide(){
        dialog_.hide();
    }
}
