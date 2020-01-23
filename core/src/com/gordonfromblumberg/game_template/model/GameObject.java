package com.gordonfromblumberg.game_template.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import com.gordonfromblumberg.game_template.utils.MathHelper;

public abstract class GameObject implements Disposable, Pool.Poolable {

    protected final Sprite sprite = new Sprite();
    protected final Polygon polygon = new Polygon(new float[8]);

    protected final Vector2 positionDelta = new Vector2();
    protected final Vector2 velocity = new Vector2();
    protected final Vector2 acceleration = new Vector2();

    protected final Vector3 targetPosition = new Vector3();
    protected final Vector2 targetMovement = new Vector2();
    protected final Vector2 targetVelocity = new Vector2();

    protected float velocityMax = 1000;
    protected float accelerationMax = 1000;
    protected float decelerationDistance2;

    protected GameWorld gameWorld;
    protected Pool<GameObject> pool;
    protected boolean active = false;

    public GameObject(String textureName) {
        setTexture(new Texture(textureName));
    }

    public GameObject(Pool<GameObject> pool) {
        this.pool = pool;
    }

    public void update(float delta) {
        final float halfDelta = delta / 2;
        positionDelta.set(velocity.x * halfDelta, velocity.y * halfDelta);

        updateAcceleration(delta);
        updateVelocity(delta);
//        velocity.x = MathUtils.clamp(velocity.x, -velocityMax, velocityMax);
//        velocity.y = MathUtils.clamp(velocity.y, -velocityMax, velocityMax);


        positionDelta.mulAdd(velocity, halfDelta);
        polygon.translate(positionDelta.x, positionDelta.y);

        adjustPosition();
        updatePosition();
    }

    public void render(Batch batch) {
        final float[] vertices = polygon.getVertices();
        sprite.setRotation(polygon.getRotation());
        sprite.setBounds(polygon.getX() + vertices[0], polygon.getY() + vertices[1],
                vertices[2] - vertices[0], vertices[7] - vertices[1]);
        sprite.setOriginCenter();
        sprite.draw(batch);
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void setTexture(Texture texture) {
        sprite.setTexture(texture);
        sprite.setRegion(0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public abstract Rectangle getBoundingRectangle();

    public abstract boolean checkCollision(GameObject gameObject);
    public void collide(GameObject gameObject) {}

    protected void updateAcceleration(float delta) {}

    protected void updateVelocity(float delta) {
        velocity.mulAdd(acceleration, delta)
                .limit(velocityMax);

    }

    /**
     * Checks whether position out of limit bounds and handles such case
     */
    protected void adjustPosition() {}

    /**
     * When subclasses use additional objects for {@link #getBoundingRectangle()} implementation
     * then they should override this method to update these objects
     */
    protected void updatePosition() {}

    public void setPosition(float x, float y) {
        polygon.setPosition(x, y);
        updatePosition();
    }

    protected void decelerate() {
        if (targetMovement.len2() < decelerationDistance2) {
            if (MathHelper.isSameSign(targetMovement.x, velocity.x)
                    && velocity.x * velocity.x >= 1.8 * accelerationMax * Math.abs(targetMovement.x)) {
                acceleration.x = - (velocity.x * velocity.x) / (2 * targetMovement.x);
            }
            if (MathHelper.isSameSign(targetMovement.y, velocity.y)
                    && velocity.y * velocity.y >= 1.8 * accelerationMax * Math.abs(targetMovement.y)) {
                acceleration.y = - (velocity.y * velocity.y) / (2 * targetMovement.y);
            }

            acceleration.limit(accelerationMax);
        } else {
            acceleration.setLength(accelerationMax);
        }
    }

    protected void setVelocityAccelerationLimits(float velocityMax, float accelerationMax) {
        this.velocityMax = velocityMax;
        this.accelerationMax = accelerationMax;
        this.decelerationDistance2 = velocityMax * velocityMax / (2 * accelerationMax);
        this.decelerationDistance2 *= decelerationDistance2;
    }

    protected void setSize(float width, float height) {
        final float[] vertices = polygon.getVertices();
        vertices[0] = - width / 2;
        vertices[1] = - height / 2;
        vertices[2] = width / 2;
        vertices[3] = - height / 2;
        vertices[4] = width / 2;
        vertices[5] = height / 2;
        vertices[6] = - width / 2;
        vertices[7] = height / 2;
    }

    @Override
    public void reset() {
        setPosition(0, 0);
        polygon.setRotation(0);
        velocity.setZero();
        gameWorld.removeGameObject(this);
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
