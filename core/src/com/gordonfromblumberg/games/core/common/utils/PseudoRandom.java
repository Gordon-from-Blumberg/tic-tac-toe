package com.gordonfromblumberg.games.core.common.utils;

import com.badlogic.gdx.math.Vector2;

public class PseudoRandom {
    private final Vector2 vector;
    private final float k;

    public PseudoRandom(float ax, float ay, float k) {
        this.vector = new Vector2(ax, ay);
        this.k = k;
    }

    public float rand(float x, float y) {
        return (float) Math.sin(vector.dot(x, y) * k);
    }
}
