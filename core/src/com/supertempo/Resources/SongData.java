package com.supertempo.Resources;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Dominik on 6/29/2017.
 */

public class SongData{
    private String artist_, title_, filename_;
    private float length_;
    private Difficulty currentDifficulty_;
    private SongScore[] scores_;

    SongData(String artist, String title, String filename, float length){
        artist_ = artist;
        title_ = title;
        filename_ = filename;
        length_ = length;
        scores_ = new SongScore[Difficulty.size()];
        currentDifficulty_ = Difficulty.Normal;
        Difficulty[] difficulties = Difficulty.values();
        for(int i = 0; i<difficulties.length; i++){
            scores_[i] = new SongScore(name() + " - " + difficulties[i].name());
        }
    }

    public String name(){
        return artist_ + " - " + title_;
    }

    public String songPath(){
        return "music/" + filename_ + ".mp3";
    }

    public String notePath(){
        return "music/notes/" + currentDifficulty_.folderName() + filename_ + ".notes";
    }

    public float length(){
        return length_;
    }

    public int stars(){ return currentScore().stars(); }

    //load current difficulty
    public void load(){
        currentScore().load();
    }

    //load all difficulties
    public void loadAll(){
        for(SongScore score: scores_){
            score.load();
        }
    }

    //save current difficulty
    public void save(){
        currentScore().save();
    }

    //save all difficulties
    public void saveAll(){
        for(SongScore score: scores_){
            score.save();
        }
    }

    public void updateScore(int stars, int points, int totalPoints){
        currentScore().updateScore(stars, points, totalPoints);
    }

    public AssetDescriptor<Music> descriptor(){
        return new AssetDescriptor<Music>(songPath(), Music.class);
    }

    public void setDifficulty(Difficulty difficulty){
        currentDifficulty_ = difficulty;
        load();
    }

    private SongScore currentScore(){
        return scores_[currentDifficulty_.ordinal()];
    }
}
