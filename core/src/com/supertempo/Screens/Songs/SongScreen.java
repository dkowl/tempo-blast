package com.supertempo.Screens.Songs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supertempo.Resources.Resources;
import com.supertempo.Resources.SongData;
import com.supertempo.SuperTempo;

import static com.supertempo.Resources.Resources.songData;

/**
 * Created by Dominik on 7/5/2017.
 */

public class SongScreen implements Screen {

    boolean isPaused_;

    Vector2 res_;
    Stage stage_;

    Table table_;
    SongBar[] songBars_;

    public SongScreen(){

        res_ = SuperTempo.instance.res;

        stage_ = new Stage(new FitViewport(res_.x, res_.y));

        table_ = new Table();
        //table_.setDebug(true);
        table_.setFillParent(true);
        table_.top();

        songBars_ = new SongBar[Resources.songData.length];
        for(int i = 0; i<Resources.songData.length; i++){
            SongData songData = Resources.songData[i];
            songBars_[i] = new SongBar(songData, res_);
            table_.row().padTop(res_.y*0.02f);;
            table_.add(songBars_[i]).left().fillX();
        }

        stage_.addActor(table_);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage_.act();

        stage_.draw();
    }

    @Override
    public void hide(){
        pause();
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage_);
        resume();
    }

    @Override
    public void pause(){
        isPaused_ = true;
    }

    @Override
    public void resume(){
        isPaused_ = false;
    }

    @Override
    public void resize(int x, int y){

    }

    @Override
    public void dispose(){
        stage_.dispose();
    }

    public void refresh(){
        for(SongBar bar: songBars_){
            bar.refresh();
        }
    }

}
