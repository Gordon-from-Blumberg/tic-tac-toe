package com.gordonfromblumberg.games.core.common.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gordonfromblumberg.games.core.common.Main;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.tictactoe.Match;
import com.gordonfromblumberg.games.tictactoe.Texts;

public class MainMenuScreen extends AbstractScreen {

    TextureRegion grid;
    Label greetingsLabel;

    private Match match;

    public MainMenuScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        super.show();

        color = Color.valueOf(AbstractFactory.getInstance().configManager().getString("backgroundColor"));

        final AssetManager assetManager = Main.getInstance().assets();
        grid = assetManager.get("image/texture_pack.atlas", TextureAtlas.class).findRegion("grid");

        final Skin uiSkin = assets.get("ui/uiskin.json", Skin.class);

        greetingsLabel = new Label(Texts.getText(Texts.GREETING), uiSkin.get(Label.LabelStyle.class));

        uiRootTable.add(greetingsLabel);
        uiRootTable.row().expand();
        uiRootTable.add();
    }

    @Override
    protected void renderWorld(float delta) {
        Viewport vp = viewport;
        batch.draw(grid, 0, 0, vp.getWorldWidth(), vp.getWorldWidth());
    }
}
