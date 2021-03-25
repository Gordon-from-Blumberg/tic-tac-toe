package com.gordonfromblumberg.games.core.common.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.gordonfromblumberg.games.core.common.model.GameObject;

import java.util.Iterator;

//todo adjust bounds after resize
public class BSPTree implements Iterator<Iterator<GameObject>> {

    private int maxLevel = 3;
    private final Node root;
    private final Array<Node> leafs = new Array<>();
    private int leafIndex;

    private final ObjectIterator iterator = new ObjectIterator();
    private final ObjectIterator internalIterator = new ObjectIterator();

    public BSPTree(float x, float y, float width, float height) {
        root = new Node(x, y, width, height);
    }

    public BSPTree(float x, float y, float width, float height, int maxLevel) {
        this.maxLevel = maxLevel;
        root = new Node(x, y, width, height);
    }

    public void addObject(GameObject object) {
        root.addObject(object);
    }

    @Override
    public boolean hasNext() {
        return leafIndex < leafs.size;
    }

    @Override
    public Iterator<GameObject> next() {
        return iterator(leafIndex++);
    }

    public Iterator<GameObject> internalIterator() {
        return internalIterator;
    }

    public void resetAndMove(float x, float y) {
        leafIndex = 0;
        root.moveAndClear(x, y);
    }

    int leafCount() {
        return leafs.size;
    }

    private ObjectIterator iterator(int leafIndex) {
        iterator.setNode(leafs.get(leafIndex));
        iterator.index = 0;
        return iterator;
    }

    private class Node {
        private final Node parent;
        private final int level;
        private Node leftNode;
        private Node rightNode;
        private Array<GameObject> objects = new Array<>();
        private final Rectangle bounds;

        private Node(float x, float y, float width, float height) {
            this(null, x, y, width, height, 0);
        }

        private Node(Node parent, float x, float y, float width, float height, int level) {
            this.parent = parent;
            this.level = level;
            bounds = new Rectangle(x, y, width, height);

            if (level < maxLevel) {
                createChildren();
            } else {
                leafs.add(this);
            }
        }

        void addObject(GameObject object) {
            final Rectangle objectBounds = object.getBoundingRectangle();
            if (leftNode != null && leftNode.bounds.contains(objectBounds)) {
                leftNode.addObject(object);
            } else if (rightNode != null && rightNode.bounds.contains(objectBounds)) {
                rightNode.addObject(object);
            } else {
                objects.add(object);
            }
        }

        void createChildren() {
            if (bounds.height > bounds.width) {
                leftNode = new Node(this, bounds.x, bounds.y, bounds.width, bounds.height / 2, level + 1);
                rightNode = new Node(this, bounds.x, bounds.y + bounds.height / 2, bounds.width, bounds.height / 2, level + 1);
            } else {
                leftNode = new Node(this, bounds.x, bounds.y, bounds.width / 2, bounds.height, level + 1);
                rightNode = new Node(this, bounds.x + bounds.width / 2, bounds.y, bounds.width / 2, bounds.height, level + 1);
            }
        }

        void moveAndClear(float x, float y) {
            bounds.setPosition(bounds.x + x, bounds.y + y);
            objects.clear();
            if (leftNode != null) leftNode.moveAndClear(x, y);
            if (rightNode != null) rightNode.moveAndClear(x, y);
        }
    }

    private class ObjectIterator implements Iterator<GameObject> {
        private Node node;
        private int index = 0;

        @Override
        public boolean hasNext() {
            if (index < node.objects.size) return true;
            Node parentNode = node.parent;
            while (parentNode != null) {
                if (!parentNode.objects.isEmpty()) return true;
                parentNode = parentNode.parent;
            }
            return false;
        }

        @Override
        public GameObject next() {
            if (index < node.objects.size) {
                if (this == iterator) {
                    internalIterator.node = node;
                    internalIterator.index = index + 1;
                }
                return node.objects.get(index++);
            }
            index = 0;
            node = node.parent;
            return next();
        }

        void setNode(Node node) {
            this.node = node;
        }
    }
}
