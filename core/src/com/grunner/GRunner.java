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

	TextureRegion crr_frame;
	TextureRegion[][] tmp;
	TextureRegion[] ani_frames;

	private float ani_time;

	SpriteBatch batch;
	Texture animation_sheet;
	Animation<TextureRegion> animation;

	@Override
	public void create() {
		batch = new SpriteBatch();

		// load animation sheet
		animation_sheet = new Texture(Gdx.files.internal("sprite_animation4.png"));

		// split frames from sheet into 2D (split method returns only 2D array)
		tmp = TextureRegion.split(animation_sheet, // <-- the sheet
				animation_sheet.getWidth() / TEX_COL, // <-- width of each frame
				animation_sheet.getHeight() / TEX_ROW); // <-- height of each frame

		System.out.printf("sheet_width = %d\n," + "sheet_heigth = %d\n", animation_sheet.getWidth() / TEX_ROW,
				animation_sheet.getHeight() / TEX_COL);

		// load splitted frames into 1D array (Animation constructor requires 1D array.
		// huh...)
		ani_frames = new TextureRegion[TEX_ROW * TEX_COL];
		System.out.println("TEX_ROW * TEX_COL = " + ani_frames.length);
		System.out.println("tmp size=" + tmp.length);

		int index = 0;
		for (int i = 0; i < TEX_ROW; i++) {
			for (int j = 0; j < TEX_COL; j++) {
				System.out.printf("ani_frames[%d] = tmp[%d][%d]\n", index, i, j);
				ani_frames[index++] = tmp[i][j];
			}
		}

		// initialize Animation constructor with frame interval and frames
		animation = new Animation<TextureRegion>(0.030f, ani_frames);

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
