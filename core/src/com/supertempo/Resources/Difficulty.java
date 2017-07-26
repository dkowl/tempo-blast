package com.supertempo.Resources;

/**
 * Created by Dominik on 7/26/2017.
 */

//Difficulty levels
public enum Difficulty{
    Easy("Easy"),
    Normal("Normal"),
    Hard("Hard"),
    Insane("Insane");

    private static final int size_ = values().length;

    private final String name_;

    public static int size(){
        return size_;
    }

    Difficulty(String name){
        name_ = name;
    }

    public String folderName(){
        return name_.toLowerCase() + "/";
    }
}
