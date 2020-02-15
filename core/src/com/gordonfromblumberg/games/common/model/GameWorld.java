package com.gordonfromblumberg.games.common.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.gordonfromblumberg.games.common.Main;
import com.gordonfromblumberg.games.common.screens.AbstractScreen;
import com.gordonfromblumberg.games.common.utils.BSPTree;

import java.util.Iterator;

public class GameWorld implements Disposable {

    private Viewport viewport;
    private final Array<GameObject> gameObjects = new Array<>();
    private final BSPTree tree;

    public Rectangle visibleArea;

    private int maxCount = 0;

    private float time = 0;

    public GameWorld(Viewport viewport) {
        this.viewport = viewport;
        visibleArea = new Rectangle(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        tree = new BSPTree(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.setGameWorld(this);
        gameObject.active = true;
        if (gameObjects.size > maxCount) maxCount = gameObjects.size;
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.removeValue(gameObject, true);
        gameObject.setGameWorld(null);
        gameObject.active = false;
    }

    public void update(float delta) {
        time += delta;
        visibleArea.set(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        tree.resetAndMove(0, 0);

        for (GameObject gameObject : gameObjects) {
            gameObject.update(delta);
            if (gameObject.active) {
                tree.addObject(gameObject);
            }
        }

        detectCollisions();

        if (time > 2) {
            time = 0;
            Gdx.app.log("GameWorld", gameObjects.size + " objects in the world of maximum " + maxCount);
        }
    }

    public void render(Batch batch) {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(batch);
        }
    }

    public void convertWorldToScreen(Vector3 coords) {
        viewport.project(coords);
    }

    public void convertScreenToWorld(Vector3 coords) {
        viewport.unproject(coords);
    }

    public float getMinVisibleX() {
        return visibleArea.x;
    }

    public float getMaxVisibleX() {
        return visibleArea.x + visibleArea.width;
    }

    public float getMinVisibleY() {
        return visibleArea.y;
    }

    public float getMaxVisibleY() {
        return visibleArea.y + visibleArea.height;
    }

    public void gameOver() {
        AbstractScreen screen = Main.getInstance().getCurrentScreen();
        Main.getInstance().goToMainMenu();
        screen.dispose();
    }

    private void detectCollisions() {
        while (tree.hasNext()) {
            final Iterator<GameObject> iterator = tree.next();
            final Iterator<GameObject> internalIterator = tree.internalIterator();
            while (iterator.hasNext()) {
                final GameObject gameObject = iterator.next();
                if (!gameObject.active) continue;

                while(internalIterator.hasNext()) {
                    final GameObject internalGameObject = internalIterator.next();
                    if (!internalGameObject.active) continue;

                    if (gameObject.getBoundingRectangle().overlaps(internalGameObject.getBoundingRectangle())
                            && gameObject.checkCollision(internalGameObject)) {
                        gameObject.collide(internalGameObject);
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        for (GameObject gameObject : gameObjects) {
            gameObject.dispose();
        }
    }

    private <T extends Disposable> void dispose(Pool<T> pool) {
        int free = pool.getFree();
        while(free-- > 0) {
            pool.obtain().dispose();
        }
    }
}
