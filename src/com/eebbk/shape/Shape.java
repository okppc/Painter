package com.eebbk.shape;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * 形状基类，所有涂鸦板上的绘制的形状继承该基类
 */
public abstract class Shape{
	
	protected float startX;
    protected float startY;
    protected float stopX;
    protected float stopY;
    
    protected float currentX;
    protected float currentY;
    
    protected int color = Color.BLACK;
    protected int size = 5;
    
    public Shape(int color, int size) {
        this.color = color;
        this.size = size;
    }
    
    
    
	
	
}
