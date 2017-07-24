package com.supertempo.Screens.Songs;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.supertempo.Resources.Resources;
import com.supertempo.Resources.SongData;
import com.supertempo.SuperTempo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dominik on 7/5/2017.
 */

public class SongBar extends Table {

    SongData songData_;
    Vector2 res_;

    Label name_;
    Table starGroup_;
    Image[] stars_;

    public SongBar(final SongData songData, Vector2 res){
        super();

        songData_ = songData;
        res_ = res;

        setSkin(Resources.uiSkin);
        setBackground("songbar_bg");
        pad(16);

        name_ = new Label(songData.name(), Resources.uiSkin);
        name_.setWrap(true);
        name_.setAlignment(Align.center);
        name_.setFontScale(1.3f * res_.y / Resources.referenceRes.y);
        add(name_).left().width(res_.x * 0.6f);

        starGroup_ = new Table();
        stars_ = new Image[Resources.MAX_STARS];
        for(int i = 0; i<Resources.MAX_STARS; i++){
            String drawableName;
            if(songData.stars()>i) drawableName = "star_filled";
            else drawableName = "star";
            stars_[i] = new Image(Resources.uiSkin, drawableName);
            starGroup_.add(stars_[i]);
        }
        add(starGroup_).width(res_.x*0.15f).height(res_.x*0.15f/Resources.MAX_STARS).padRight(res_.x*0.025f);

        //Input
        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                SuperTempo.instance.currentSong = songData_;
                SuperTempo.instance.homeScreen.load(new ArrayList<AssetDescriptor<?>>(Arrays.asList(songData_.descriptor())));
                SuperTempo.instance.homeScreen.setDestScreen(SuperTempo.ScreenID.Game);
                SuperTempo.instance.homeScreen.waitForInput(false);
                SuperTempo.SetScreen(SuperTempo.ScreenID.Home);
            }
        });
    }

    public void refresh(){
        name_.setText(songData_.name());
        for(int i = 0; i<Resources.MAX_STARS; i++){
            String drawableName;
            if(songData_.stars()>i) drawableName = "star_filled";
            else drawableName = "star";
            stars_[i] = new Image(Resources.uiSkin, drawableName);
        }
    }
}
