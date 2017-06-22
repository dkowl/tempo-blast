package com.supertempo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class SuperTempo extends ApplicationAdapter {

	private Vector2 res;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;

	static final int GRID_W = 3, GRID_H = 3;
	private Rectangle gridRect, smallGridRect;
	private float
			smallGridScale = 0.4f,
			smallGridYShift = 0f,
			smallGridXShift = 0f;

	private Grid mainGrid, smallGrid;

	public ArrayList<Line> lines_;

	private float timeElapsed_;

	public Song song;

	private Color[] colors = new Color[laneCount()];

	private Texture keyTexture;
	private TextureRegion keyRegion, keyHighlightRegion;
	private SpriteBatch spriteBatch;

	private InputHandler inputHandler;
	
	@Override
	public void create () {
		res = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera = new OrthographicCamera(res.x, res.y);
		camera.setToOrtho(true);
		shapeRenderer = new ShapeRenderer();

		gridRect = new Rectangle(0, res.y-res.x, res.x, res.x);
		smallGridRect = shrink(gridRect, smallGridScale);
		smallGridRect.y -= res.y*smallGridYShift;
		smallGridRect.x += res.x*smallGridXShift;

		mainGrid = new Grid(gridRect, GRID_W, GRID_H);
		smallGrid = new Grid(smallGridRect, GRID_W, GRID_H);

		lines_ = new ArrayList<Line>(GRID_H*GRID_W);
		for(int i = 0; i<GRID_H; i++){
			for(int j = 0; j<GRID_W; j++){
				lines_.add(new Line(smallGrid.midPoint(i, j), mainGrid.midPoint(i, j), new Vector2(0, 0), mainGrid.elementSize()));
			}
		}

		timeElapsed_ = 0;

		song = new Song();
		song.randomize(200, laneCount(), 75);

		colors[0] = new Color(0.65f, 0.9f, 1, 1);
		colors[1] = new Color(0.9f, 0.65f, 1, 1);
		colors[2] = new Color(1, 0.75f, 0.65f, 1);
		colors[3] = new Color(0.75f, 1, 0.65f, 1);
		colors[4] = new Color(1, 0.65f, 0.65f, 1);
		colors[5] = new Color(0.65f, 0.65f, 1, 1);
		colors[6] = new Color(0.65f, 1, 0.65f, 1);
		colors[7] = new Color(1, 1, 0.65f, 1);
		colors[8] = new Color(1, 0.65f, 1, 1);

		keyTexture = new Texture(Gdx.files.internal("key.png"));
		keyRegion = new TextureRegion(keyTexture, 0, 0, 512, 512);
		keyHighlightRegion = new TextureRegion(keyTexture, 512, 0, 512, 512);
		spriteBatch = new SpriteBatch();

		inputHandler = new InputHandler(this);
		Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render () {

		update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		spriteBatch.setProjectionMatrix(camera.combined);

		//rendering lines
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.SKY);
		for(int i = 0; i<lines_.size(); i++){
			shapeRenderer.setColor(colors[i]);
			shapeRenderer.line(lines_.get(i).beginPoint_, lines_.get(i).endPoint_);
		}
		shapeRenderer.end();

		//rendering notes
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(int i = song.lastActiveNote_-1; i>=song.firstActiveNote_; i--){
			Note note = song.notes_.get(i);
			Rectangle rect = lines_.get(note.lane_).lerp(note.value_);
			Color color = colors[note.lane_];
			color.a = 0.5f;
			shapeRenderer.setColor(color);
			shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		shapeRenderer.end();

		//rendering keys
		spriteBatch.begin();
		for(int i = 0; i<lines_.size(); i++){
			Rectangle rect = lines_.get(i).lerp(1);
			Key key = song.keys_[i];
			if(key.value_ > 0){
				if(key.correct_){
					Color color = Color.GREEN;
					color.a = key.value_;
					spriteBatch.setColor(color);
				}
				else{
					spriteBatch.setColor(new Color(1, 0, 0, key.value_));
				}
				spriteBatch.draw(keyHighlightRegion, rect.x, rect.y, rect.width, rect.height);
				spriteBatch.setColor(Color.WHITE);
			}
			spriteBatch.draw(keyRegion, rect.x, rect.y, rect.width, rect.height);
		}
		spriteBatch.end();

	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
		keyTexture.dispose();
		spriteBatch.dispose();
	}

	void update(){
		timeElapsed_ += Gdx.graphics.getDeltaTime();

		song.updateTime(timeElapsed_);
	}

	Rectangle shrink(Rectangle rect, float scale){
		return new Rectangle(rect.x + (rect.width/2f)*(1f-scale), rect.y + (rect.height/2f)*(1f-scale), rect.width*scale, rect.height*scale);
	}

	public static int laneCount(){
		return GRID_W * GRID_H;
	}
}
