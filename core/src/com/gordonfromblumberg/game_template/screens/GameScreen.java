package com.gordonfromblumberg.game_template.screens;

import com.badlogic.gdx.graphics.Texture;
import com.gordonfromblumberg.game_template.model.GameWorld;

public class GameScreen extends AbstractScreen {

    private GameWorld gameWorld;

    @Override
    public void show() {
        super.show();

        gameWorld = new GameWorld(viewport);
    }

    @Override
    protected void update(float delta) {
        // delta = 1f / 60;
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
