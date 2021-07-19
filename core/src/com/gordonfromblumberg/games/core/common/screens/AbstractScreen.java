package com.gordonfromblumberg.games.core.common.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gordonfromblumberg.games.core.common.Main;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;

public abstract class AbstractScreen implements Screen {

    private static final float MIN_DELTA = 1.0f / 30;

    protected AssetManager assets;

    protected final SpriteBatch batch;
    protected Color color = Color.BLACK;

    protected Stage stage;
    protected Viewport viewport, uiViewport;
    protected OrthographicCamera camera, uiCamera;

    protected Table uiRootTable;

    protected AbstractScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        assets = Main.getInstance().assets();

        final ConfigManager configManager = AbstractFactory.getInstance().configManager();
        final float worldWidth = configManager.getFloat("worldWidth");
        final float minRatio = configManager.getFloat("minRatio");
        final float maxRatio = configManager.getFloat("maxRatio");
        final float minWorldHeight = worldWidth / maxRatio;
        final float maxWorldHeight = worldWidth / minRatio;

        createWorldViewport(worldWidth, minWorldHeight, maxWorldHeight);
        createUiViewport(worldWidth, minWorldHeight, maxWorldHeight);

        Gdx.input.setInputProcessor(stage);

        uiRootTable = new Table();
        uiRootTable.setFillParent(true);
        stage.addActor(uiRootTable);
    }

    @Override
    public final void render(float delta) {
        delta = Math.min(delta, MIN_DELTA);

        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batch.begin();
        renderWorld(delta);
        batch.end();

        renderUi();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
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

    private void createWorldViewport(float worldWidth, float minWorldHeight, float maxWorldHeight) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new ExtendViewport(worldWidth, minWorldHeight, worldWidth, maxWorldHeight, camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    private void createUiViewport(float worldWidth, float minWorldHeight, float maxWorldHeight) {
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false);
        uiViewport = new ExtendViewport(worldWidth, minWorldHeight, worldWidth, maxWorldHeight, uiCamera);
        stage = new Stage(uiViewport, batch);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
