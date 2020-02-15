package com.gordonfromblumberg.game_template.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gordonfromblumberg.game_template.Main;

public class MainMenuScreen extends AbstractScreen {

    TextureRegion background;
    Table rootTable;
    TextButton textButton;

    @Override
    public void show() {
        super.show();

        final AssetManager assetManager = Main.getInstance().assets();
        background = assetManager.get("image/texture_pack.atlas", TextureAtlas.class).findRegion("");
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        Skin uiSkin = assetManager.get("ui/uiskin.json", Skin.class);

        textButton = new TextButton("PLAY", uiSkin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new GameScreen());
            }
        });
        rootTable.add(textButton);
    }

    @Override
    protected void renderWorld(float delta) {
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    }

//    @Override
//    public void dispose() {
//
//        super.dispose();
//    }
}
