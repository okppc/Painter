package com.eebbk.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 形状基类，所有涂鸦板上的绘制的形状继承该基类
 */
public abstract class Shape implements Mobile{
	
	protected float startX;
    protected float startY;
    protected float stopX;
    protected float stopY;
    
    protected Paint paint;
    protected int color = Color.BLACK;
    protected int size = 5;
    
    public Shape(float x, float y, int color, int size) {
    	startX = x;
    	startY = y;
    	stopX = x;
    	stopY = y;
        this.color = color;
        this.size = size;
    }
    
    public Shape(float x, float y) {
    	startX = x;
    	startY = y;
    	stopX = x;
    	stopY = y;
    }
    
    public Shape() {
    	
    }
    
    
    
    /**
     * 各种图形实现各自的绘制方法
     * 
     * @param canvas
     */
    public abstract void startDraw(Canvas canvas);
    
    /**
     * 各种图形绘制移动过程的方法
     * 
     * @param x
     * @param y
     */
    protected abstract boolean startMove(float x, float y);
    
    
	
}
