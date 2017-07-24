package com.supertempo.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supertempo.Screens.Game.UI.GameEndScreen;
import com.supertempo.Screens.Game.UI.GameUI;
import com.supertempo.SuperTempo;

/**
 * Created by Dominik on 6/22/2017.
 */

public class GameScreen implements Screen {

    Vector2 res_;
    Camera camera_;

    //Content
    GameWorld gameWorld_;
    GameRenderer gameRenderer_;
    GameUI gameUi_;
    GameEndScreen gameEndScreen_;

    //Stage
    Stage stage_;

    //Input
    private InputMultiplexer inputs_;
    private GameInputHandler inputHandler;

    boolean isPaused_;

    public GameScreen(){

        res_ = SuperTempo.instance.res;
        camera_ = SuperTempo.instance.defaultCamera;
    }

    @Override
    public void render(float delta){

        if(!isPaused_){
            stage_.act(delta);
            gameWorld_.update(delta);
        }
        gameRenderer_.render();
        stage_.draw();
    }

    @Override
    public void hide(){
        pause();
    }

    @Override
    public void show(){

        gameWorld_ = new GameWorld(res_, SuperTempo.instance.currentSong, SuperTempo.instance.manager.get(SuperTempo.instance.currentSong.descriptor()));
        gameRenderer_ = new GameRenderer(gameWorld_, camera_);
        gameUi_ = new GameUI(res_, gameWorld_);
        gameEndScreen_ = new GameEndScreen(res_, gameWorld_);

        stage_ = new Stage(new FitViewport(res_.x, res_.y));
        stage_.addActor(gameUi_);
        stage_.addActor(gameEndScreen_);

        inputHandler = new GameInputHandler(gameWorld_);
        inputs_ = new InputMultiplexer(stage_, inputHandler);
        Gdx.input.setInputProcessor(inputs_);

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
        stage_.dispose();
    }

}
