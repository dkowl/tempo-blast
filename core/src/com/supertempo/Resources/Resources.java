package com.supertempo.Resources;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Dominik on 6/24/2017.
 */

public class Resources {



    public static SongData[] songData = {
            new SongData("Datsik", "Nasty", "datsik_nasty"),
            new SongData("Darude", "Sandstorm", "darude_sandstorm"),
            new SongData("Far Too Loud", "Firestorm", "far-too-loud_firestorm"),
            new SongData("Fantastik", "Reptilians", "fantastik_reptilians"),
    };

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
