package com.gordonfromblumberg.games.core.common.physics;

import com.badlogic.gdx.math.Vector2;

public class GravityMovingStrategy extends AccelerationMovingStrategy {
    protected final Vector2 gravity = new Vector2();

    public GravityMovingStrategy() {}

    public GravityMovingStrategy(float gravityX, float gravityY) {
        setGravity(gravityX, gravityY);
    }

    @Override
    public void update(Vector2 position, Vector2 velocity, Vector2 acceleration, float dt) {
        velocity.mulAdd(gravity, dt);
        super.update(position, velocity, acceleration, dt);
    }

    public void setGravity(float gravityX, float gravityY) {
        gravity.x = gravityX;
        gravity.y = gravityY;
    }
}
