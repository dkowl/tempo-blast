package com.supertempo.Screens.Home;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.supertempo.Resources.Resources;
import com.supertempo.SuperTempo;

/**
 * Created by Dominik on 7/4/2017.
 */

public class HomeScreen implements Screen {

    boolean isPaused_;
    boolean isLoading_;

    SuperTempo game_;
    Vector2 res_;
    Camera camera_;
    AssetManager manager_;

    //Drawing
    TextureRegion backgroundRegion_;
    SpriteBatch spriteBatch_;

    //Stage
    Stage stage_;

    //UI
    Table table_;
    Label text_, progress_;

    //Text fading
    float timeElapsed_;
    static final float FADE_TIME = 2f;

    //Input
    HomeInputHandler inputHandler_;

    public HomeScreen(SuperTempo game){

        isLoading_ = true;

        game_ = game;
        res_ = game_.res;
        camera_ = game_.defaultCamera;
        manager_ = game_.manager;

        //Loading the loading screen resources
        manager_.load(Resources.homeBackground);
        manager_.finishLoading();
        backgroundRegion_ = new TextureRegion(manager_.get(Resources.homeBackground));
        backgroundRegion_.flip(false, true);

        //Loading other resources
        manager_.load(Resources.actionBackground);
        manager_.load(Resources.keyTexture);

        //Drawing
        spriteBatch_ = new SpriteBatch();

        //Stage
        stage_ = new Stage();

        //UI elements
        table_ = new Table(Resources.uiSkin);
        table_.setFillParent(true);

        text_ = new Label("Loading...", Resources.uiSkin);
        table_.add(text_);
        table_.row();

        progress_ = new Label("0%", Resources.uiSkin);
        table_.add(progress_);
        table_.row();

        stage_.addActor(table_);

        //Text fading
        timeElapsed_ = 0;

        //Input
        inputHandler_ = new HomeInputHandler(this);

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch_.setProjectionMatrix(camera_.combined);
        spriteBatch_.begin();
        spriteBatch_.draw(backgroundRegion_, 0, 0, res_.x, res_.y);
        spriteBatch_.end();

        //Update
        if(isLoading_){
            if(manager_.update()){
                isLoading_ = false;
                text_.setText("Tap to play");
                progress_.setText("");
            }
            else{
                progress_.setText(manager_.getProgress() + "%");
            }
        }
        stage_.act();
        table_.addAction(Actions.alpha(textAlpha()));

        timeElapsed_ += delta;

        //Draw
        stage_.draw();
    }

    @Override
    public void hide(){
        pause();
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(inputHandler_);
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
        spriteBatch_.dispose();
    }

    float textAlpha(){
        return Math.abs(0.5f-(timeElapsed_%FADE_TIME)/FADE_TIME)*2;
    }
}
