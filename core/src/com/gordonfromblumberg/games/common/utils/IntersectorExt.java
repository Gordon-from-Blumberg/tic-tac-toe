package com.gordonfromblumberg.games.common.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class IntersectorExt {
    private static final Vector2 center = new Vector2();
    private static final Vector2 vec1 = new Vector2();
    private static final Vector2 vec2 = new Vector2();

    public static boolean overlaps(Polygon polygon, Circle circle) {
        if (polygon.contains(circle.x, circle.y)) {
            return true;
        }

        final float[] vertices = polygon.getTransformedVertices();
        center.set(circle.x, circle.y);
        final float squareRadius = circle.radius * circle.radius;

        for (int i = 0, n = vertices.length; i < n; i += 2) {
            vec1.set(vertices[i], vertices[i + 1]);
            vec2.set(vertices[(i + 2) % n], vertices[(i + 3) % n]);

            if (Intersector.intersectSegmentCircle(vec1, vec2, center, squareRadius)) {
                return true;
            }
        }

        return false;
    }
}
