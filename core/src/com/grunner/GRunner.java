package com.grunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GRunner extends ApplicationAdapter {

	private final int TEX_ROW = 5, TEX_COL = 6;
	private boolean start_ani = false;
	
	public int ani_x = 100;


	private float ani_time;

	private SpriteBatch batch;
	private Animation<TextureRegion> anim;
	private TextureAtlas runnerAtlas;
	private TextureRegion currentFrame;
	private Object[] frameRegion;
	private TextureRegion lastFrame;
	

	@Override
	public void create() {
		batch = new SpriteBatch();		
		ani_time = 0.0f;
		runnerAtlas = new TextureAtlas(Gdx.files.internal("runner_pack.pack"));
		anim = new Animation<TextureRegion>(1/30f, runnerAtlas.getRegions());
		frameRegion = anim.getKeyFrames();  // <-- get all frames into 1D array
		lastFrame = (TextureRegion) frameRegion[0];

		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		ani_time += Gdx.graphics.getDeltaTime();

		if(start_ani) {
			currentFrame = anim.getKeyFrame(ani_time, true);
			lastFrame = currentFrame;
		}
		else {
			currentFrame = lastFrame;

		}
		batch.draw( currentFrame, ani_x, 100);
		batch.end();
		
		logic();
	}

	@Override
	public void dispose() {
		batch.dispose();
		runnerAtlas.dispose();

	}
	
	
	public void logic() {
		
		if( Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		if( Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			start_ani = true;
			ani_x += 200 * Gdx.graphics.getDeltaTime();
			if( ani_x > Gdx.graphics.getWidth() ) {
				ani_x = -30;
			}
		}
		else {
			start_ani = false;
			
		}
	}
}
