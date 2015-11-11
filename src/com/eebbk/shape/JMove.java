package com.eebbk.shape;

import android.graphics.Canvas;

public class JMove extends Shape {
	
	private Shape shape;
	
	public JMove(float x, float y, int color, int size) {
		super(x, y, color, size);
		
	}
	
	public JMove(float x, float y) {
		super(x, y);
		
	}
	
	@Override
	public void startDraw(Canvas canvas) {
		
		shape.startDraw(canvas);

	}

	@Override
	protected boolean startMove(float x, float y) {
		
		float xDis = x - startX ;
		float yDis = y - startY;
		startX = x;
		startY = y;
		shape.onShapeMove(xDis, yDis);
		// TODO Auto-generated method stub
		return true;
	}

	
	
	public void setShape(Shape shape){
		
		this.shape = shape;
		
	}

	@Override
	public void onShapeMove(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float calcMinDistn(float x, float y) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	

}
