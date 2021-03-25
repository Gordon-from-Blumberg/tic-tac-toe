package com.gordonfromblumberg.games.core.common.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.model.GameWorld;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;

public class GameScreen extends AbstractScreen {
    private static final String SCORE_LABEL = "SCORE";

    private Label scoreLabel;
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
    protected void renderUi() {
        scoreLabel.setText(getScore());
        super.renderUi();
    }

    @Override
    public void dispose() {
        gameWorld.dispose();

        super.dispose();
    }

    private void createUI() {
        final ConfigManager configManager = AbstractFactory.instance.configManager();
        final Skin uiSkin = assets.get("ui/uiskin.json", Skin.class);

        scoreLabel = new Label(getScore(), uiSkin);
        uiRootTable.add(scoreLabel).left();

        // create ui here
    }

    private String getScore() {
        return String.format("%s: %06d", SCORE_LABEL, gameWorld.getScore());
    }
}
