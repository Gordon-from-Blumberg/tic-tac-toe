package com.gordonfromblumberg.games.core.common.utils;

public class SVGPathComposer {
    private final StringBuilder sb = new StringBuilder();

    public SVGPathComposer moveTo(float x, float y) {
        sb.append(" M ");
        append(x, y);
        return this;
    }

    public SVGPathComposer move(float dx, float dy) {
        sb.append(" m ");
        append(dx, dy);
        return this;
    }

    public SVGPathComposer lineTo(float x, float y) {
        sb.append(" L ");
        append(x, y);
        return this;
    }

    public SVGPathComposer line(float dx, float dy) {
        sb.append(" l ");
        append(dx, dy);
        return this;
    }

    public SVGPathComposer cubicTo(float cx1, float cy1, float cx2, float cy2, float x, float y) {
        sb.append(" C ");
        append(cx1, cy1);
        sb.append(" ");
        append(cx2, cy2);
        sb.append(" ");
        append(x, y);
        return this;
    }

    public SVGPathComposer cubic(float dcx1, float dcy1, float dcx2, float dcy2, float dx, float dy) {
        sb.append(" c ");
        append(dcx1, dcy1);
        sb.append(" ");
        append(dcx2, dcy2);
        sb.append(" ");
        append(dx, dy);
        return this;
    }

    public SVGPathComposer cubicSmoothTo(float cx2, float cy2, float x, float y) {
        sb.append(" S ");
        append(cx2, cy2);
        sb.append(" ");
        append(x, y);
        return this;
    }

    public SVGPathComposer cubicSmooth(float dcx2, float dcy2, float dx, float dy) {
        sb.append(" s ");
        append(dcx2, dcy2);
        sb.append(" ");
        append(dx, dy);
        return this;
    }

    public SVGPathComposer repeat(float x, float y) {
        sb.append(" ");
        append(x, y);
        return this;
    }

    public String compose() {
        return sb.toString();
    }

    private void append(float x, float y) {
        sb.append(round(x)).append(",").append(round(y));
    }

    private float round(float value) {
        return round(value, 3);
    }

    private float round(float value, int precision) {
        int n = precision;
        while (n-- > 0)
            value *= 10;
        value = (float) Math.round(value);
        n = precision;
        while (n-- > 0)
            value /= 10;
        return value;
    }
}
