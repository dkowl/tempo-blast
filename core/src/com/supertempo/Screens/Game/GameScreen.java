package com.supertempo.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.supertempo.InputHandlerGame;
import com.supertempo.SuperTempo;

/**
 * Created by Dominik on 6/22/2017.
 */

public class GameScreen implements Screen {

    SuperTempo game_;
    Vector2 res_;
    Camera camera_;

    GameWorld gameWorld_;
    GameRenderer gameRenderer_;

    private InputHandlerGame inputHandler;

    boolean isPaused_;

    public GameScreen(SuperTempo game){

        game_ = game;

        res_ = game_.res;
        camera_ = game.defaultCamera;

        gameWorld_ = new GameWorld(res_, game_.currentSong);
        gameRenderer_ = new GameRenderer(gameWorld_, camera_);

        inputHandler = new InputHandlerGame(gameWorld_);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render(float delta){

        if(!isPaused_) gameWorld_.update(delta);
        gameRenderer_.render();
    }

    @Override
    public void hide(){
        pause();
    }

    @Override
    public void show(){
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
        gameRenderer_.dispose();
    }

}
