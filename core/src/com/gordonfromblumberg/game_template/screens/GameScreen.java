package com.gordonfromblumberg.game_template.screens;

import com.gordonfromblumberg.game_template.model.GameWorld;

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
