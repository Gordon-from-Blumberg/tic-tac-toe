package com.gordonfromblumberg.games.common.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gordonfromblumberg.games.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.common.utils.ConfigManager;

public abstract class AbstractScreen implements Screen {

    private static final float MIN_DELTA = 1.0f / 30;

    protected Stage stage;
    protected SpriteBatch batch;
    protected Viewport viewport;
    protected OrthographicCamera camera;

    @Override
    public void show() {
        batch = new SpriteBatch();
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        viewport = createViewport();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, MIN_DELTA);

        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batch.begin();
        renderWorld(delta);
        batch.end();

        renderUi();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    protected void update(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
    protected void renderWorld(float delta) {}
    protected void renderUi() {
        stage.act();
        stage.draw();
    }

    private Viewport createViewport() {
        ConfigManager configManager = AbstractFactory.instance.configManager();
        float worldWidth = configManager.getFloat("worldWidth");
        float minRatio = configManager.getFloat("minRatio");
        float maxRatio = configManager.getFloat("maxRatio");
        return new ExtendViewport(
                worldWidth, worldWidth / maxRatio,
                worldWidth, worldWidth / minRatio,
                camera);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
