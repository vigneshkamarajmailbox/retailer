package com.botree.retailerssfa.speechview;

import android.graphics.RectF;

public class RecognitionBar {

    private final int maxHeight;
    private final int startX;
    private final int startY;
    private final RectF rect;
    private int x;
    private int y;
    private int radius;
    private int height;

    public RecognitionBar(int x, int y, int height, int maxHeight, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.startX = x;
        this.startY = y;
        this.height = height;
        this.maxHeight = maxHeight;
        this.rect = new RectF((float) (x - radius),
                y - height / 2f,
                (float) (x + radius),
                y + height / 2f);
    }

    public void update() {
        rect.set((float) x - radius,
                y - height / 2f,
                (float) x + radius,
                y + height / 2f);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public RectF getRect() {
        return rect;
    }

    public int getRadius() {
        return radius;
    }
}