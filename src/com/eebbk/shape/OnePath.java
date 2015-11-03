package com.eebbk.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class OnePath extends Shape {
	
	public Path path;
	
	public Paint paint;
	
	public OnePath(int color, int size) {
		super(color, size);
		
		path = new Path();
		
		
	}
	
	public void MoveTo(float x ,float y){
		
		path.moveTo(x, y);
		
	}
	

	public void LineTo(float x, float y) {
		
		path.lineTo(x, y);
		

	}

	public void drawPath(Canvas canvas) {
		
		if(canvas == null){
			return;
		}
		
		
		if(paint == null){
			
			paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(color);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeCap(Paint.Cap.ROUND);
		}
		
		canvas.drawPath(path, paint);
	}
	
	
	public boolean isNewPoint(float x ,float y){
		
		if( Math.abs(currentX - x) <= 0.1f || Math.abs(currentY - y) <= 0.1f  ){
			currentX = x;
			currentY = y;
			return false;
		}
		currentX = x;
		currentY = y;
		return true;
	
	}
	
	

}
