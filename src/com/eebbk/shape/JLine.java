package com.eebbk.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class JLine extends Shape {

	private Paint paint;

	public JLine(float x, float y, int color, int size) {
		super(x, y, color, size);
	}

	public JLine(float x, float y) {
		super(x, y);
	}


	@Override
	public void startDraw(Canvas canvas) {
		if(canvas == null){
			return;
		}
		if(paint == null){
			paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(size);
			paint.setColor(color);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeCap(Paint.Cap.ROUND);
			
		}
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	@Override
	protected boolean startMove(float x, float y) {
		
		if(!isNewPoint(x, y)){
			return false;
		}
		
		return true;
	}
	
	private boolean isNewPoint(float x ,float y){

		//均值策略1,半径
		if( Math.sqrt( Math.pow((stopX-x),2)+Math.pow((stopY-y),2) ) <= 1f  ){

			//绝对值策略,边长
			//		if( Math.abs(stopX - x) <= 5f || Math.abs(stopY - y) <= 5f  ){
			stopX = x;
			stopY = y;
			return false;
		}
		stopX = x;
		stopY = y;
		return true;
	}

	@Override
	public void onShapeMove(float x, float y) {
		startX += x;
		startY += y;
		stopX += x;
		stopY += y;
		
	}

	@Override
	public float calcMinDistn(float x, float y) {
		float value1 = (float) Math.sqrt(Math.pow(startX-x, 2)+Math.pow(startY-y, 2));
		float value2 = (float) Math.sqrt(Math.pow(stopX-x, 2)+Math.pow(stopY-y, 2));
		float value = Math.min(value1, value2);
		return value;
	}

	

}
