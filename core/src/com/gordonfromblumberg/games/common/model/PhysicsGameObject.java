package com.gordonfromblumberg.games.common.model;

import com.badlogic.gdx.math.Vector2;

public class PhysicsGameObject extends GameObject {
    protected static final float MAX_VELOCITY = 1000f;
    protected static final float MAX_ACCELERATION = 1000f;

    public final Vector2 velocity = new Vector2();
    public final Vector2 acceleration = new Vector2();

    protected boolean moveToTarget = false;
    public final Vector2 desiredMovement = new Vector2();
    protected final Vector2 desiredVelocity = new Vector2();

    protected float decelerationDistance, decelerationDistance2;
    public float maxVelocity, maxVelocity2;
    protected float maxAcceleration, maxDeceleration;

    {
//        setMaxVelocity(MAX_VELOCITY);
//        setMaxAcceleration(MAX_ACCELERATION);
//        setMaxDeceleration(MAX_ACCELERATION);
        this.colliding = true;
    }

    @Override
    public void update(final float delta) {
        super.update(delta);

        setTarget();

        updateAcceleration(delta);

        updateVelocity(delta);

        position.mulAdd(velocity, delta);
        adjustPosition();
        polygon.setPosition(position.x, position.y);
    }

    protected void setTarget() {
        moveToTarget = false;
    }

    protected void updateAcceleration(float delta) {
        if (moveToTarget) {
            Vector2 desiredMovement = this.desiredMovement;
            Vector2 desiredVelocity = this.desiredVelocity;
            Vector2 acceleration = this.acceleration;

            float desiredMovementLen2 = desiredMovement.len2();
            float velocityLimit2 = desiredMovementLen2 < decelerationDistance2
                    ? desiredMovementLen2 / decelerationDistance2 * maxVelocity2
                    : maxVelocity2;
            desiredVelocity.set(desiredMovement).limit2(velocityLimit2);

            acceleration.set(desiredVelocity).sub(velocity);

            float accelerationLimit = acceleration.dot(velocity) > 0
                    ? maxAcceleration
                    : maxDeceleration;

            acceleration.scl(1 / delta).limit2(accelerationLimit * accelerationLimit);
        }
    }

    protected void updateVelocity(float delta) {
        velocity.mulAdd(acceleration, delta)
                .limit2(maxVelocity2);
    }

    public void setDecelerationDistance(float decelerationDistance) {
        this.decelerationDistance = decelerationDistance;
        this.decelerationDistance2 = decelerationDistance * decelerationDistance;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
        this.maxVelocity2 = maxVelocity * maxVelocity;

        float calculatedDecelerationDist = calcDecelerationDist();
        if (calculatedDecelerationDist > decelerationDistance) {
            setDecelerationDistance(calculatedDecelerationDist);
        }
    }

    public void setMaxAcceleration(float maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public void setMaxDeceleration(float maxDeceleration) {
        this.maxDeceleration = maxDeceleration;

        float calculatedDecelerationDist = calcDecelerationDist();
        if (calculatedDecelerationDist > decelerationDistance) {
            setDecelerationDistance(calculatedDecelerationDist);
        }
    }

    protected float calcDecelerationDist() {
        return maxVelocity2 / (2 * maxDeceleration);
    }

    /**
     * Checks whether position out of limit bounds and handles such case
     */
    protected void adjustPosition() {}
}
