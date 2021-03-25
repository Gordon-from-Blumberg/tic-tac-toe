package com.gordonfromblumberg.games.core.common.utils;

import com.badlogic.gdx.math.MathUtils;

public class MathHelper {
    public static boolean isSameSign(float n1, float n2) {
        if (MathUtils.isZero(n1) || MathUtils.isZero(n2)) {
            return false;
        }
        return Math.signum(n1) == Math.signum(n2);
    }
}
