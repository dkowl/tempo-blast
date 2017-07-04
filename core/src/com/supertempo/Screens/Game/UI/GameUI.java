package com.supertempo.Screens.Game.UI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.supertempo.Resources.Resources;
import com.supertempo.Screens.Game.GameScreen;
import com.supertempo.Screens.Game.GameWorld;
import com.supertempo.Song;

/**
 * Created by Dominik on 6/29/2017.
 */

public class GameUI extends Table {

    Vector2 res_;

    GameWorld gameWorld_;

    private ProgressBar progressBar;
    private Label songName_, labelStreak, labelAccuracy;

    public GameUI(Vector2 res, GameWorld gameWorld){

        super();

        res_ = res;
        gameWorld_ = gameWorld;
        Song song = gameWorld_.song_;

        setFillParent(true);
        //setDebug(true);
        top();

        //progress bar
        progressBar = new ProgressBar(0f, 1f, 0.001f, false, Resources.uiSkin);
        float barHeight = res_.y * 0.033f;

        progressBar.getStyle().background.setMinHeight(barHeight);
        progressBar.getStyle().knob.setMinHeight(barHeight);
        progressBar.getStyle().knobBefore.setMinHeight(barHeight);

        add(progressBar).expandX().fillX().colspan(2);
        row().height(barHeight);

        //Song name
        songName_ = new Label(song.name_, Resources.uiSkin);
        add(songName_).center().colspan(2).pad(16, 0, 16, 0);
        row();

        //Streak
        labelStreak = new Label("Streak: ", Resources.uiSkin);
        add(labelStreak).uniform();

        //Accuracy
        labelAccuracy = new Label("Accuracy: ", Resources.uiSkin);
        add(labelAccuracy).uniform();


    }

    @Override
    public void act(float delta){

        Song song = gameWorld_.song_;

        progressBar.setValue(song.playedRatio());
        labelStreak.setText("Streak: " + song.streak());
        labelAccuracy.setText(Math.round(song.accuracy()*100) + "%");

        super.act(delta);
    }



}
