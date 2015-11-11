package com.eebbk.painter;

import java.util.List;

import com.eebbk.painter.PaintView.OnReceiveListener;
import com.eebbk.shape.Shape;
import com.eebbk.shape.ShapeManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TestView extends SurfaceView implements SurfaceHolder.Callback{
	
	private SurfaceHolder mSurfaceHolder;
	
	private Paint paint;
	private int color = Color.WHITE;
	private int paintWidth = 5;
	
	public TestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mSurfaceHolder = this.getHolder();
		
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(color);
		paint.setStrokeWidth(paintWidth);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas = mSurfaceHolder.lockCanvas();
		if(canvas == null){
			return;
		}
		canvas.drawColor(Color.WHITE);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	
	//画出历史图画
		public void drawHistory(List<Shape> shapes){
			Canvas canvas = mSurfaceHolder.lockCanvas();
			canvas.drawColor(Color.WHITE);
			if( shapes == null || shapes.isEmpty() ){
				return ;
			}
			for(Shape shape : shapes){
				shape.startDraw(canvas);
			}
			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}
	
	

}
