package com.gordonfromblumberg.game_template.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.gordonfromblumberg.game_template.Main;

public class MainMenuScreen extends AbstractScreen {

    Texture background;
    TextButton textButton;

    @Override
    public void show() {
        super.show();

        background = new Texture("");

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        textButton = new TextButton("PLAY", buttonStyle);
        textButton.getLabel().setAlignment(Align.center);
        textButton.setPosition((600 - textButton.getWidth()) / 2, (800 - textButton.getHeight()) / 2);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.INSTANCE.setScreen(new GameScreen());
            }
        });
        stage.addActor(textButton);
    }

    @Override
    protected void renderWorld(float delta) {
        batch.draw(background, 0, 0, 600, 800);
    }

    @Override
    public void dispose() {
        background.dispose();

        super.dispose();
    }
}
