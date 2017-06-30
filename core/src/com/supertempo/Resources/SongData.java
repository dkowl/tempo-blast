package com.supertempo.Resources;

/**
 * Created by Dominik on 6/29/2017.
 */

public class SongData{
    private String artist_, title_, filename_;
    private float length_;

    SongData(String artist, String title, String filename, float length){
        artist_ = artist;
        title_ = title;
        filename_ = filename;
        length_ = length;
    }

    public String name(){
        return artist_ + " - " + title_;
    }

    public String songPath(){
        return "music/" + filename_ + ".mp3";
    }

    public String notePath(){
        return "music/notes/" + filename_ + ".notes";
    }

    public float length(){
        return length_;
    }
}
