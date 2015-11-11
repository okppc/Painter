package com.eebbk.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class JPath extends Shape {

	public Path path;

	public Paint paint;

	public JPath(float x, float y, int color, int size) {
		super(x, y, color, size);

		path = new Path();

		path.moveTo(x, y);

		path.lineTo(x, y);

	}
	
	public JPath(float x, float y) {
		super(x, y);

		path = new Path();

		path.moveTo(x, y);

		path.lineTo(x, y);

	}
	
	
	public JPath() {
		path = new Path();
	}
	
	public JPath(Path path){
		this.path = new Path(path);
	}
	
	public void onStart(float x, float y){
		path.moveTo(x, y);
	}
	
	
	@Override
	public void startDraw(Canvas canvas) {

		if(canvas == null){
			return;
		}

		if(paint == null){

			paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(color);
			paint.setStrokeWidth(size);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeCap(Paint.Cap.ROUND);
		}

		canvas.drawPath(path, paint);

	}


	@Override
	protected boolean startMove(float x, float y) {

		if(!isNewPoint(x, y)){
			return false;
		}

		path.lineTo(x, y);
		return true;

	}

	
	private boolean isNewPoint(float x ,float y){

		//均值策略1,半径
		if( Math.sqrt( Math.pow((stopX-x),2)+Math.pow((stopY-y),2) ) <= 5f  ){

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public float calcMinDistn(float x, float y) {
		// TODO Auto-generated method stub
		return 0;
	}

	



}
