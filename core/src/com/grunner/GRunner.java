package com.grunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GRunner extends ApplicationAdapter {

	private final int TEX_ROW = 5, TEX_COL = 6;
	private boolean start_ani = true, gravity_up = false;

	public int ani_x = 100, ani_y = 50;

	private float ani_time;

	private float scrWidth, scrHeight;

	private SpriteBatch batch;
	private Animation<TextureRegion> anim;
	private TextureAtlas runnerAtlas;
	private TextureRegion currentFrame;
	private Object[] frameRegion;
	private TextureRegion lastFrame;
	private OrthographicCamera camera;
	private int gravity = 1;

	private boolean canFlip = true;

	@Override
	public void create() {
		scrWidth = Gdx.graphics.getWidth();
		scrHeight = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, scrWidth, scrHeight);

		ani_time = 0.0f;
		runnerAtlas = new TextureAtlas(Gdx.files.internal("runner_pack.pack"));
		anim = new Animation<TextureRegion>(1 / 30f, runnerAtlas.getRegions());
		frameRegion = anim.getKeyFrames(); // <-- get all frames into 1D array
		lastFrame = (TextureRegion) frameRegion[0];

		ani_time += Gdx.graphics.getDeltaTime();
		currentFrame = anim.getKeyFrame(ani_time, true);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(currentFrame, ani_x, ani_y);

		batch.end();

		logic();
	}

	@Override
	public void dispose() {
		batch.dispose();
		runnerAtlas.dispose();

	}

	public void logic() {

		ani_time += Gdx.graphics.getDeltaTime();
		currentFrame = anim.getKeyFrame(ani_time, true);

		if (ani_x >= (scrWidth - 150)) {
			// camera.position.x += 80 * Gdx.graphics.getDeltaTime();
		}
		ani_x += 100 * Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if (gravity_up && canFlip) {
				gravity_up = false;
			} else if (!gravity_up) {
				if (canFlip) {
					gravity_up = true;
				}
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			ani_x = 0;
		}

		if (gravity_up) {
			if (ani_x >= (scrWidth - 150)) {
				// camera.position.x += 80 * Gdx.graphics.getDeltaTime();
			}
			ani_x += 100 * Gdx.graphics.getDeltaTime();
			if ( (ani_y < (scrHeight - 100) ) ) {
				ani_y += 200 * Gdx.graphics.getDeltaTime();
				canFlip = false;
			}

			if (ani_x > scrWidth) {
				ani_x = -30;
			}
			if (ani_y >= (scrHeight - 100)) {
				ani_y = (int) scrHeight - 100;
				canFlip = true;
			}
			if (ani_y <= 0) {
				ani_y = 50;
			}

			// flip the frame
			if (currentFrame.isFlipY() == false) {
				currentFrame.flip(false, true);
			}

		} else

		{ // <-- gravity_up = false
			if (currentFrame.isFlipY() == true)
				currentFrame.flip(false, true);

			if( ani_y > 0) {
				ani_y -= 200 * Gdx.graphics.getDeltaTime();
				canFlip = false;
			}
			// currentFrame.flip(false, true);
			if (ani_y <= 5) {
				ani_y = 5;
				canFlip = true;
			}

		}
		System.out.printf("ani_X = %d\nani_y = %d\n\n", ani_x, ani_y);
		System.out.printf("flipped = %b\n", currentFrame.isFlipY());
		System.out.printf("canFlip = %b\n", canFlip);
	}
}
