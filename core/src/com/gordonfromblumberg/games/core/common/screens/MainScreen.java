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
import com.gordonfromblumberg.games.tictactoe.Texts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.gordonfromblumberg.games.tictactoe.TicTacToe.*;

public class MainScreen extends AbstractScreen {
    private static final Random RAND = new Random();
    private static final Color WARNING_COLOR = new Color(1, 0.7f, 0.7f, 1);
    private static final Color DEFAULT_COLOR = new Color(Color.WHITE);

    private Label drawsLabel, defeatsLabel, currentMatchLabel, currentMoveLabel, message;
    private TextureRegionDrawable emptyCell, xCell, oCell;
    private final Image[] cells = new Image[9];
    private final Listener listener = new Listener();
    private int state = 0, currentMatch = 1, currentMove = 1;
    private int draws, defeats;
    private float delay;


    private final Map<Integer, Integer> moves = new HashMap<>();

    private boolean isPlayerMove = true;
    private boolean clickToContinue = false;

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

        uiRootTable.row().padTop(10);
        uiRootTable.add(message = new Label("", skin)).colspan(3).center().height(40);

        uiRootTable.row();
        uiRootTable.add(createGameTable(skin)).colspan(3).expand().center();

        message.setText(Texts.getGreeting() + '\n' + Texts.getFirstOppMove());

        stage.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickToContinue) {
                    endMatch();
                    clickToContinue = false;
                }
            }
        });
    }

    void playerMove(int cell) {
        state = move(state, cell);
        ++currentMove;
        if (isDraw(state)) {
            message.setText(Texts.getDraw() + "\nClick to play again");
            ++draws;
            clickToContinue = true;
        } else {
            message.setText(Texts.getEndOppMove() + '\n' + Texts.getStartMyMove());
            delay = 0.75f + RAND.nextFloat();
        }
        isPlayerMove = false;
    }

    void endMatch() {
        state = 0;
        currentMove = 1;
        ++currentMatch;
        message.setText(Texts.getFirstOppMove());
        isPlayerMove = true;
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

    @Override
    protected void update(float delta) {
        if (!isPlayerMove) {
            delay -= delta;

            if (delay <= 0) {
                if (!moves.containsKey(state))
                    myMove(state, moves);

                ++currentMove;
                state = moves.get(state);

                if (checkMyWin(state)) {
                    message.setText(Texts.getMyWin() + "\nClick to play again");
                    ++defeats;
                    clickToContinue = true;
                }
                isPlayerMove = true;
            }
        }

        drawsLabel.setText(draws);
        defeatsLabel.setText(defeats);
        currentMoveLabel.setText(currentMove);
        currentMatchLabel.setText(currentMatch);
    }

    @Override
    protected void renderWorld(float delta) {
        final int state = this.state;
        for (int i = 0; i < 9; ++i) {
            if (isEmpty(state, i))
                cells[i].setDrawable(emptyCell);
            else if (isMy(state, i))
                cells[i].setDrawable(oCell);
            else
                cells[i].setDrawable(xCell);
        }
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

            playerMove((int) image.getUserObject());
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
        table.add(drawsLabel = new Label("0", skin));

        table.row();
        table.add("Defeats").padRight(pad);
        table.add(defeatsLabel = new Label("0", skin));
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
