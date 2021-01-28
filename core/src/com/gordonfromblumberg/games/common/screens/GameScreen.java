package com.gordonfromblumberg.games.common.screens;

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
        gameWorld.update(delta);        // update game state
        camera.translate(0, 0);   // move camera if needed
        super.update(delta);            // apply camera moving and update batch projection matrix
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
