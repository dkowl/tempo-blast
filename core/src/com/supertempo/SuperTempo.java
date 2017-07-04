package com.supertempo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supertempo.Resources.Resources;
import com.supertempo.Resources.SongData;
import com.supertempo.Screens.Game.GameScreen;

public class SuperTempo extends Game {

	public Vector2 res;
	public OrthographicCamera defaultCamera;

	//Global data
	public SongData currentSong;

	//Screens
	class ScreenID{
		public static final int
				Home = 0,
				Game = 1,
				Count = 2;
	}
	Screen[] screens;

	GameScreen gameScreen;

	
	@Override
	public void create () {
		res = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		defaultCamera = new OrthographicCamera(res.x, res.y);
		defaultCamera.setToOrtho(true);

		currentSong = Resources.songData[2];

		screens = new Screen[ScreenID.Count];

		gameScreen = new GameScreen(this);
		screens[ScreenID.Game] = gameScreen;

		setScreen(ScreenID.Game);


	}

	@Override
	public void render () {
		screen.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		for(int i = 0; i<screens.length; i++){
			screens[i].dispose();
		}
	}

	void setScreen(int id){
		setScreen(screens[id]);
	}


}
