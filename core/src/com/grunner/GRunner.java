package com.grunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GRunner extends ApplicationAdapter {

	private final int TEX_ROW = 5, TEX_COL = 6;
	private boolean start_ani = false;
	
	public int ani_x = 100;


	private float ani_time;

	SpriteBatch batch;
	Texture animation_sheet;
	Animation animation;

	@Override
	public void create() {
		batch = new SpriteBatch();

		
		ani_time = 0.0f;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ani_time += Gdx.graphics.getDeltaTime();
		
		if (start_ani) {
			crr_frame = animation.getKeyFrame(ani_time, true);
		}
		else {
			crr_frame = ani_frames[10];
		}
		batch.draw(crr_frame, ani_x, 100);
		batch.end();
		
		logic();
	}

	@Override
	public void dispose() {
		batch.dispose();
		animation_sheet.dispose();

	}
	
	
	public void logic() {
		
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
