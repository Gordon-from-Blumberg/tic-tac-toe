package com.gordonfromblumberg.games.core.common.config;

public class Rangeable {
    private float min;
    private float max;
    private float range = -1;

    public float value(float t) {
        return min + t * getRange();
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max != 0 ? max : min + range;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getRange() {
        return range >= 0 ? range : max != 0 ? max - min : 0;
    }

    public void setRange(float range) {
        this.range = range;
    }
}
