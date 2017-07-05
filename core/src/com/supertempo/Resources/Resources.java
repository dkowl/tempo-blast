package com.supertempo.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Dominik on 6/24/2017.
 */

public class Resources {

    public static Vector2 referenceRes = new Vector2(1080, 1920);

    //Preference name
    public static String PREF_NAME = "PlayerData";

    //Asset descriptors
    public static AssetDescriptor<Texture>
            homeBackground = new AssetDescriptor<Texture>("background.png", Texture.class),
            actionBackground = new AssetDescriptor<Texture>("background_action.jpg", Texture.class),
            keyTexture = new AssetDescriptor<Texture>("key.png", Texture.class);

    public static int MAX_STARS = 3;

    public static SongData[] songData = {
            new SongData("Datsik", "Nasty", "datsik_nasty", 272),
            new SongData("Darude", "Sandstorm", "darude_sandstorm", 227),
            new SongData("Far Too Loud", "Firestorm", "far-too-loud_firestorm", 288),
            new SongData("Fantastik", "Reptilians", "fantastik_reptilians", 331),
    };

    public static void loadSongData(Preferences prefs){
        for(SongData data: songData){
            data.load(prefs);
        }
    }

    public static Skin uiSkin = new Skin(Gdx.files.internal("ui/skin/uiskin.gayson"));

    private static Color[] noteColors = {
            new Color(0.65f, 0.9f, 1, 1),
            new Color(0.9f, 0.65f, 1, 1),
            new Color(1, 0.75f, 0.65f, 1),
            new Color(0.75f, 1, 0.65f, 1),
            new Color(1, 0.65f, 0.65f, 1),
            new Color(0.65f, 0.65f, 1, 1),
            new Color(0.65f, 1, 0.65f, 1),
            new Color(1, 1, 0.65f, 1),
            new Color(1, 0.65f, 1, 1),
    };

    public static Color noteColor(int id){
        if(id >= noteColors.length){
            id = id % noteColors.length;
        }

        return noteColors[id];
    }
}
