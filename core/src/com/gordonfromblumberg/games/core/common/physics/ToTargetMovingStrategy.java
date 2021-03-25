package com.gordonfromblumberg.games.core.common.physics;

import com.badlogic.gdx.math.Vector2;

public class ToTargetMovingStrategy extends AccelerationMovingStrategy {
    protected final Vector2 target = new Vector2();
    protected final Vector2 desiredMovement = new Vector2();
    protected final Vector2 desiredVelocity = new Vector2();

    public ToTargetMovingStrategy() {}

    public ToTargetMovingStrategy(float targetX, float targetY) {
        setTarget(targetX, targetY);
    }

    public ToTargetMovingStrategy(float maxVelocity, float maxAcceleration, float targetX, float targetY) {
        super(maxVelocity, maxAcceleration);

        setTarget(targetX, targetY);
    }

    @Override
    public void update(Vector2 position, Vector2 velocity, Vector2 acceleration, float dt) {
        final Vector2 desiredMovement = this.desiredMovement;
        final Vector2 desiredVelocity = this.desiredVelocity;

        desiredMovement.set(target).sub(position);
        desiredVelocity.set(desiredMovement);
        if (maxVelocity2 > 0)
            desiredVelocity.limit2(getVelocityLimit());

        acceleration.set(desiredVelocity).sub(velocity);
        limitAcceleration(velocity, acceleration);

        super.update(position, velocity, acceleration, dt);
    }

    public void setTarget(float x, float y) {
        target.x = x;
        target.y = y;
    }

    protected float getVelocityLimit() {
        return maxVelocity2;
    }

    protected void limitAcceleration(Vector2 velocity, Vector2 acceleration) {
        if (maxAcceleration2 > 0) {
            acceleration.limit2(maxAcceleration2);
        }
    }
}
