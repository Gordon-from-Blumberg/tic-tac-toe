package com.gordonfromblumberg.games.core.common.physics;

import com.badlogic.gdx.math.Vector2;

public class AccelerationMovingStrategy implements MovingStrategy {
    protected float maxVelocity, maxVelocity2;
    protected float maxAcceleration, maxAcceleration2;

    public AccelerationMovingStrategy() {}

    public AccelerationMovingStrategy(float maxVelocity, float maxAcceleration) {
        setMaxVelocity(maxVelocity);
        setMaxAcceleration(maxAcceleration);
    }

    @Override
    public void update(Vector2 position, Vector2 velocity, Vector2 acceleration, float dt) {
        if (maxAcceleration2 > 0)
            acceleration.limit2(maxAcceleration2);

        velocity.mulAdd(acceleration, dt);
        if (maxVelocity2 > 0)
                velocity.limit2(maxVelocity2);

        position.mulAdd(velocity, dt);
    }

    protected void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
        this.maxVelocity2 = maxVelocity * maxVelocity;
    }

    protected void setMaxAcceleration(float maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
        this.maxAcceleration2 = maxAcceleration * maxAcceleration;
    }
}
