package com.supertempo.Screens.Game.UI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.supertempo.Resources.Resources;
import com.supertempo.Screens.Game.GameWorld;
import com.supertempo.Song;
import com.supertempo.SuperTempo;

/**
 * Created by Dominik on 7/2/2017.
 */

public class GameEndScreen extends Table {

    Vector2 res_;
    GameWorld gameWorld_;

    private Label text_, score_;
    private HorizontalGroup starGroup_;
    private Image[] stars_;
    private boolean initialized_ = false;

    public GameEndScreen(Vector2 res, GameWorld gameWorld){
        super();

        //setDebug(true);

        res_ = res;
        gameWorld_ = gameWorld;

        setFillParent(true);
        center();
        setPosition(0, res_.y*0.25f);

        setVisible(false);

    }

    void initialize(Song song){

        if(initialized_) return;

        int stars = song.stars();
        int points = song.correct_;
        int maxPoints = song.total_;

        //saving best scores
        song.updateSongData(SuperTempo.instance.prefs);

        text_ = new Label("Song finished!", Resources.uiSkin);
        add(text_).center();
        row();

        score_ = new Label(Integer.toString(song.correct_) + " / " + Integer.toString(song.total_), Resources.uiSkin);
        add(score_).center();
        row();


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

        //input
        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                SuperTempo.instance.songScreen.refresh();
                SuperTempo.SetScreen(SuperTempo.ScreenID.Songs);
            }
        });

        setVisible(true);
        initialized_ = true;
    }

    @Override
    public void act(float delta){

        if(gameWorld_.song_.finished()){
            initialize(gameWorld_.song_);
        }
    }
}
