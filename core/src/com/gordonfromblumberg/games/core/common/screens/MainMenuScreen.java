package com.gordonfromblumberg.games.core.common.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gordonfromblumberg.games.core.common.Main;
import com.gordonfromblumberg.games.core.common.actors.CellActor;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.tictactoe.IncorrectMoveException;
import com.gordonfromblumberg.games.tictactoe.Match;
import com.gordonfromblumberg.games.tictactoe.Texts;

public class MainMenuScreen extends AbstractScreen {

    TextureRegion grid;
    Label greetingsLabel;
    Label errorLabel;

    private final CellActor[] cells = new CellActor[9];
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

        greetingsLabel = new Label(Texts.getText(Texts.GREETING), uiSkin);
        uiRootTable.add(greetingsLabel).colspan(3);

        errorLabel = new Label("ERROR", uiSkin, "default-font", Color.RED);
        uiRootTable.row();
        uiRootTable.add(errorLabel).colspan(3);

        uiRootTable.row().expand();
        uiRootTable.add().colspan(3);

        addCells();
    }

    @Override
    protected void renderWorld(float delta) {
        Viewport vp = viewport;
        batch.draw(grid, 0, 0, vp.getWorldWidth(), vp.getWorldWidth());
    }

    private void addCells() {
        Viewport vp = viewport;
        float size = vp.getWorldWidth() / 3;
        for (int i = 0, n = cells.length; i < n; i++) {
            if (i % 3 == 0) {
                uiRootTable.row().height(size);
            }

            CellActor cell = new CellActor();
            cells[i] = cell;
            uiRootTable.add(cell).size(size);
            cell.addListener(clickListener(i));
        }
    }

    private ClickListener clickListener(int i) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    match.move(i);
                } catch (IncorrectMoveException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
