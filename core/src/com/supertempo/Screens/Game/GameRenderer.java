package com.supertempo.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.supertempo.Key;
import com.supertempo.Lane;
import com.supertempo.Note;
import com.supertempo.Resources.Resources;
import com.supertempo.Song;
import com.supertempo.SuperTempo;

import java.util.ArrayList;

/**
 * Created by Dominik on 6/24/2017.
 */

public class GameRenderer {

    //reference to data
    GameWorld world_;

    //camera
    Camera camera_;

    //rendering classes
    private ShapeRenderer shapeRenderer_;
    private SpriteBatch spriteBatch_;

    //textures
    //background
    private Texture bgTexture_;

    //keys
    private Texture keyTexture_;
    private TextureRegion keyRegion_, keyHighlightRegion_;

    public GameRenderer(GameWorld gameWorld, Camera camera){
        world_ = gameWorld;
        camera_ = camera;
        shapeRenderer_ = new ShapeRenderer();
        spriteBatch_ = new SpriteBatch();

        bgTexture_ = new Texture(Gdx.files.internal("background_action.jpg"));

        keyTexture_ = new Texture(Gdx.files.internal("key.png"));
        keyRegion_ = new TextureRegion(keyTexture_, 0, 0, 512, 512);
        keyHighlightRegion_ = new TextureRegion(keyTexture_, 512, 0, 512, 512);
    }

    void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer_.setProjectionMatrix(camera_.combined);
        spriteBatch_.setProjectionMatrix(camera_.combined);

        //rendering background
        spriteBatch_.begin();
        spriteBatch_.setColor(1, 1, 1, Math.max(0, Math.min(1, (world_.song_.streak()-10))/100f));
        spriteBatch_.draw(bgTexture_, 0, 0, world_.res_.x, world_.res_.y);
        spriteBatch_.end();

        //rendering lanes
        ArrayList<Lane> lanes = world_.lanes_;

        shapeRenderer_.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer_.setColor(Color.SKY);
        for(int i = 0; i<lanes.size(); i++){
            shapeRenderer_.setColor(Resources.noteColor(i));
            shapeRenderer_.line(lanes.get(i).beginPoint_, lanes.get(i).endPoint_);
        }
        shapeRenderer_.end();

        //rendering notes
        Song song = world_.song_;
        ArrayList<Note> notes = song.activeNotes();

        shapeRenderer_.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i<notes.size(); i++){
            Note note = notes.get(i);
            Rectangle rect = lanes.get(note.lane_).lerpRect(note.value_);
            Color color = Resources.noteColor(note.lane_);
            color.a = 0.5f;
            shapeRenderer_.setColor(color);
            shapeRenderer_.rect(rect.x, rect.y, rect.width, rect.height);
        }
        shapeRenderer_.end();

        //rendering keys
        spriteBatch_.begin();
        spriteBatch_.setColor(1, 1, 1, 1);
        for(int i = 0; i<lanes.size(); i++){
            Rectangle rect = lanes.get(i).lerpRect(1);
            Key key = song.keys_[i];
            if(key.value_ > 0){
                if(key.correct_){
                    Color color = Color.GREEN;
                    color.a = key.value_;
                    spriteBatch_.setColor(color);
                }
                else{
                    spriteBatch_.setColor(new Color(1, 0, 0, key.value_));
                }
                spriteBatch_.draw(keyHighlightRegion_, rect.x, rect.y, rect.width, rect.height);
                spriteBatch_.setColor(Color.WHITE);
            }
            spriteBatch_.draw(keyRegion_, rect.x, rect.y, rect.width, rect.height);
        }
        spriteBatch_.end();

    }

    public void dispose(){
        shapeRenderer_.dispose();
        spriteBatch_.dispose();
        keyTexture_.dispose();
    }
}
