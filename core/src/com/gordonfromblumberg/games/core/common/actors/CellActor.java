package com.gordonfromblumberg.games.core.common.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CellActor extends Actor {
    private TextureRegion textureRegion;

    public CellActor() {}

    public CellActor(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (textureRegion != null) {
            final Color color = batch.getColor();
            final float originAlpha = color.a;
            color.mul(1, 1, 1, parentAlpha);
            batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
            color.set(color.r, color.g, color.b, originAlpha);
        }
    }
}
