package com.hankkin.weixingroupicondemo.model;

/**
 * Created by Hankkin on 15/11/19.
 * Bitmap 实体类
 */
public class BitmapBean {
    private float x;
    private float y;
    private float width;
    private float height;
    private static int devide = 1;
    private int index = -1;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public static int getDevide() {
        return devide;
    }

    public static void setDevide(int devide) {
        BitmapBean.devide = devide;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "BitmapBean{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", index=" + index +
                '}';
    }
}
