package com.supertempo.Screens.Game.UI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.supertempo.Resources.Resources;
import com.supertempo.Screens.Game.GameWorld;

/**
 * Created by Dominik on 7/2/2017.
 */

public class GameEndScreen extends Table {

    Vector2 res_;
    GameWorld gameWorld_;

    private Label text_;
    private HorizontalGroup starGroup_;
    private Image[] stars_;
    private boolean starsSet_ = false;

    public GameEndScreen(Vector2 res, GameWorld gameWorld){
        super();

        //setDebug(true);

        res_ = res;
        gameWorld_ = gameWorld;

        setFillParent(true);
        center();
        setPosition(0, res_.y*0.25f);

        text_ = new Label("Song finished!", Resources.uiSkin);
        add(text_).center();
        row();

        setVisible(false);

    }

    void setStars(int stars){

        if(starsSet_) return;

        starGroup_ = new HorizontalGroup().space(16);
        stars_ = new Image[Resources.MAX_STARS];
        for(int i = 0; i<Resources.MAX_STARS; i++){
            String drawableName;
            if(stars>i) drawableName = "star_filled";
            else drawableName = "star";
            stars_[i] = new Image(Resources.uiSkin, drawableName);
            starGroup_.addActor(stars_[i]);
        }
        add(starGroup_);
        row();

        setVisible(true);
        starsSet_ = true;
    }

    @Override
    public void act(float delta){

        if(gameWorld_.song_.finished()){
            setStars(gameWorld_.song_.stars());
        }
    }
}
