package com.gordonfromblumberg.game_template.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import com.gordonfromblumberg.game_template.Main;
import com.gordonfromblumberg.game_template.utils.MathHelper;

public abstract class GameObject implements Disposable, Pool.Poolable {

    public GameWorld gameWorld;

    protected final Sprite sprite = new Sprite();
    public final Polygon polygon = new Polygon(new float[8]);

    protected final Vector2 positionDelta = new Vector2();
    protected final Vector2 velocity = new Vector2();
    public final Vector2 acceleration = new Vector2();

    public final Vector3 targetPosition = new Vector3();
    public final Vector2 targetMovement = new Vector2();
    protected final Vector2 targetVelocity = new Vector2();

    public float maxVelocity = 1000;
    public float maxAcceleration = 1000;
    protected float decelerationDistance2;

    protected Pool<GameObject> pool;
    protected boolean active = false;

    public GameObject(String textureName) {
        sprite.setRegion( getTextureAtlas().findRegion(textureName) );
    }

    public GameObject(Pool<GameObject> pool) {
        this.pool = pool;
    }

    public void update(float delta) {
        final float halfDelta = delta / 2;
        positionDelta.set(velocity.x * halfDelta, velocity.y * halfDelta);

        updateAcceleration(delta);

        updateVelocity(delta);

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

    public abstract Rectangle getBoundingRectangle();

    public abstract boolean checkCollision(GameObject gameObject);
    public void collide(GameObject gameObject) {}

    protected void updateAcceleration(float delta) {}

    protected void updateVelocity(float delta) {
        velocity.mulAdd(acceleration, delta)
                .limit(maxVelocity);
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

    @Deprecated
    protected void decelerate() {
        if (!targetMovement.isZero() && targetMovement.len2() < decelerationDistance2) {
            if (MathHelper.isSameSign(targetMovement.x, velocity.x)
                    && velocity.x * velocity.x >= 2 * maxAcceleration * Math.abs(targetMovement.x)) {
                acceleration.x = - (velocity.x * velocity.x) / (2 * targetMovement.x);
            }
            if (MathHelper.isSameSign(targetMovement.y, velocity.y)
                    && velocity.y * velocity.y >= 2 * maxAcceleration * Math.abs(targetMovement.y)) {
                acceleration.y = - (velocity.y * velocity.y) / (2 * targetMovement.y);
            }

            acceleration.limit(maxAcceleration);
        } else {
            acceleration.setLength(maxAcceleration);
        }
    }

    protected void setVelocityAccelerationLimits(float maxVelocity, float maxAcceleration) {
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;
        this.decelerationDistance2 = maxVelocity * maxVelocity / (2 * maxAcceleration);
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

    protected TextureAtlas getTextureAtlas() {
        return Main.getInstance().assets().get("image/texture_pack.atlas", TextureAtlas.class);
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
