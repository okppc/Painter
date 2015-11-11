package com.eebbk.shape;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class JCircle extends Shape{

	private float radius;
	private Paint paint;
	
	private float cx;
	private float cy;

	public JCircle(float x, float y, int color, int size) {
		super(x, y, color, size);
	}

	public JCircle(float x, float y) {
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
		canvas.drawCircle( cx, cy , radius, paint);
		
	}
	
	
	@Override
	protected boolean startMove(float x, float y) {
		
		if(!isNewPoint(x, y)){
			return false;
		}
		
		radius = (float) Math.sqrt( Math.pow((startX-x),2)+Math.pow((startY-y), 2) );
		cx = (startX +stopX)/2;
		cy = (startY +stopY)/2;
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
		cx += x;
		cy += y;
		
	}

	@Override
	public float calcMinDistn(float x, float y) {
		
		float value = (float) Math.sqrt( Math.pow(cx-x, 2) + Math.pow(cy-y, 2) );
		
		return value;
	}

	
	

}
