package com.supertempo.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Dominik on 6/24/2017.
 */

public class Resources {



    public static SongData[] songData = {
            new SongData("Datsik", "Nasty", "datsik_nasty", 272),
            new SongData("Darude", "Sandstorm", "darude_sandstorm", 227),
            new SongData("Far Too Loud", "Firestorm", "far-too-loud_firestorm", 288),
            new SongData("Fantastik", "Reptilians", "fantastik_reptilians", 331),
    };

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
