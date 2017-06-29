package com.supertempo.Resources;

/**
 * Created by Dominik on 6/29/2017.
 */

public class SongData{
    private String artist_, title_, filename_;

    SongData(String artist, String title, String filename){
        artist_ = artist;
        title_ = title;
        filename_ = filename;
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
}
