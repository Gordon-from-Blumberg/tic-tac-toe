package com.gordonfromblumberg.games.common.screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.gordonfromblumberg.games.common.Main;
import com.gordonfromblumberg.games.common.model.GameWorld;

public class GameScreen extends AbstractScreen {

    private GameWorld gameWorld;

    @Override
    public void show() {
        super.show();

        gameWorld = new GameWorld(viewport);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    protected void update(float delta) {
        gameWorld.update(delta);
        camera.translate(0, gameWorld.speedY * delta);
        super.update(delta);
    }

    @Override
    protected void renderWorld(float delta) {
        gameWorld.render(batch);
    }

    @Override
    public void dispose() {
        gameWorld.dispose();

        super.dispose();
    }
}
