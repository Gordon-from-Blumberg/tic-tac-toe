package com.gordonfromblumberg.games.core.common.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.gordonfromblumberg.games.tictactoe.TicTacToe.*;

public class MainScreen extends AbstractScreen {
    private static final Color WARNING_COLOR = new Color(1, 0.7f, 0.7f, 1);
    private static final Color DEFAULT_COLOR = new Color(Color.WHITE);

    private Label draws, defeats, currentMatchLabel, currentMoveLabel;
    private TextureRegionDrawable emptyCell, xCell, oCell;
    private final Image[] cells = new Image[9];
    private final Listener listener = new Listener();
    private int state = 0, match = 1, move = 1;

    private boolean isPlayerMove = true;

    public MainScreen(SpriteBatch batch) {
        super(batch);

        color = Color.FOREST;
    }

    @Override
    public void show() {
        super.show();

        loadImages();

        Skin skin = assets.get("ui/uiskin.json", Skin.class);
        uiRootTable.add(createScoreTable(skin)).pad(3).padRight(10).top().left();
        uiRootTable.add(createCurrentMatchTable(skin)).pad(3).top().left();
        uiRootTable.add().expandX();

        uiRootTable.row();
        uiRootTable.add(createGameTable(skin)).colspan(3).expand().center();
    }

    Table createGameTable(Skin skin) {
        Table table = new Table(skin);
        for (int i = 0; i < 9; ++i) {
            if (i > 0 && i % 3 == 0)
                table.row();
            Image image = new Image(emptyCell);
            cells[i] = image;
            image.setUserObject(i);
            image.addListener(listener);
            table.add(image).pad(5);
        }
        return table;
    }

    class Listener extends ClickListener {
        Listener() {
            super(Input.Buttons.LEFT);
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Image image = (Image) event.getTarget();
            if (!isPlayerMove)
                return;

            if (!canMove(image)) {
                image.clearActions();
                image.setColor(Color.RED);
                image.addAction(Actions.color(WARNING_COLOR, 0.25f));
                return;
            }

            int move = (int) image.getUserObject();
            image.setDrawable(xCell);
            state = move(state, move);
            ++MainScreen.this.move;
            currentMoveLabel.setText(MainScreen.this.move);
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            Image image = (Image) event.getTarget();
            if (isPlayerMove && !canMove(image)) {
                image.addAction(Actions.color(WARNING_COLOR));
            }
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            Image image = (Image) event.getTarget();
            if (image == toActor) {
                return;
            }
            image.clearActions();
            image.setColor(DEFAULT_COLOR);
        }

        private boolean canMove(Image image) {
            return isEmpty(state, (int) image.getUserObject());
        }
    }

    Table createScoreTable(Skin skin) {
        Table table = new Table(skin);
        float pad = 2f;
        table.add("Victories").padRight(pad);
        table.add("0");

        table.row();
        table.add("Draws").padRight(pad);
        table.add(draws = new Label("0", skin));

        table.row();
        table.add("Defeats").padRight(pad);
        table.add(defeats = new Label("0", skin));
        return table;
    }

    Table createCurrentMatchTable(Skin skin) {
        Table table = new Table(skin);
        float pad = 2f;
        table.add("Current match").padRight(pad);
        table.add(currentMatchLabel = new Label("1", skin));

        table.row();
        table.add("Current move").padRight(pad);
        table.add(currentMoveLabel = new Label("1", skin));
        return table;
    }

    void loadImages() {
        TextureAtlas atlas = assets.get("image/texture_pack.atlas", TextureAtlas.class);
        emptyCell = new TextureRegionDrawable(atlas.findRegion("empty"));
        xCell = new TextureRegionDrawable(atlas.findRegion("x"));
        oCell = new TextureRegionDrawable(atlas.findRegion("o"));
    }
}
