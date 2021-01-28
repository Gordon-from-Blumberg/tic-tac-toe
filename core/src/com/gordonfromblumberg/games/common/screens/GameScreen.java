package com.gordonfromblumberg.games.common.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gordonfromblumberg.games.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.common.model.GameWorld;
import com.gordonfromblumberg.games.common.utils.ConfigManager;

public class GameScreen extends AbstractScreen {

    private GameWorld gameWorld;

    protected GameScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        super.show();

        gameWorld = new GameWorld(viewport);

        createUI();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
        uiViewport.update(width, height, true);
    }

    @Override
    protected void update(float delta) {
        camera.translate(0, 0);         // move camera if needed
        super.update(delta);            // apply camera moving and update batch projection matrix
        gameWorld.update(delta);        // update game state
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

    private void createUI() {
        final ConfigManager configManager = AbstractFactory.instance.configManager();
        final Skin uiSkin = assets.get("ui/uiskin.json", Skin.class);

        // create ui here
    }
}
