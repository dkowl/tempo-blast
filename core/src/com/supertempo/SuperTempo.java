package com.supertempo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supertempo.Resources.Resources;
import com.supertempo.Resources.SongData;
import com.supertempo.Screens.Game.GameScreen;
import com.supertempo.Screens.Home.HomeScreen;
import com.supertempo.Screens.Songs.SongScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuperTempo extends Game {

	public Vector2 res;
	public OrthographicCamera defaultCamera;

	//Preferences
	public Preferences prefs;

	//Global data
	public SongData currentSong;

	//Asset manager
	public AssetManager manager;

	//Screens
	public class ScreenID{
		public static final int
				Home = 0,
				Songs = 1,
				Game = 2,
				Count = 3;
	}
	Screen[] screens;

	public HomeScreen homeScreen;
	public SongScreen songScreen;
	GameScreen gameScreen;
	
	@Override
	public void create () {
		res = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		defaultCamera = new OrthographicCamera(res.x, res.y);
		defaultCamera.setToOrtho(true);

		//Preferences
		prefs = Gdx.app.getPreferences(Resources.PREF_NAME);
		prefs.putString("chuj", "penis");

		//Global data
		Resources.loadSongData(prefs);
		currentSong = Resources.songData[2];

		//Asset manager
		manager = new AssetManager();

		//Screens
		screens = new Screen[ScreenID.Count];

		homeScreen = new HomeScreen(this, ScreenID.Songs);
		homeScreen.load(new ArrayList<AssetDescriptor<?>>(Arrays.asList(Resources.actionBackground, Resources.keyTexture)));
		screens[ScreenID.Home] = homeScreen;

		songScreen = new SongScreen(this);
		screens[ScreenID.Songs] = songScreen;

		gameScreen = new GameScreen(this);
		screens[ScreenID.Game] = gameScreen;

		setScreen(ScreenID.Home);



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

	public void setScreen(int id){
		setScreen(screens[id]);
	}


}
