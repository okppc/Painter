package com.eebbk.painter;

import java.util.ArrayList;
import java.util.List;

import com.eebbk.behavior.Behavior.Action;
import com.eebbk.behavior.BehaviorManager;
import com.eebbk.shape.OnePath;
import com.eebbk.shape.Shape;
import com.eebbk.shape.ShapeManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PaintView extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceHolder mSurfaceHolder;
	private BehaviorManager mBehaviorManager;
	private ShapeManager mShapeManager;
	private Path path;
	private Paint paint;
	
	private List<Path> paths = new ArrayList<Path>();
	
	private int color = Color.BLACK;
	
	private int size = 5;
	
	private Canvas canvas;


	public PaintView(Context context) {
		this(context,null);
		
		// TODO Auto-generated constructor stub
	}

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);

		this.setFocusable(true);
		
		
		mBehaviorManager = new BehaviorManager();
		mShapeManager = new ShapeManager();
		
		path = new Path();
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(color);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);

	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		canvas = mSurfaceHolder.lockCanvas();
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
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int action = event.getAction();
		
		float x = event.getX();
		float y = event.getY();
		
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			
			mBehaviorManager.add(Action.START, x, y);
			
			path.moveTo(x, y);
			 
			 Log.i("aaa", "手指点下了,坐标位置为:X="+x+" Y="+y);
			break;
		case MotionEvent.ACTION_MOVE:
			mBehaviorManager.add(Action.MOVE, x, y);
			canvas = mSurfaceHolder.lockCanvas();
			
			path.lineTo(x, y);
			canvas.drawColor(Color.WHITE);
			
			if(paths != null && paths.size() > 0 ){
				for(Path path:paths){
					canvas.drawPath(path, paint);
				}
			}
			
			canvas.drawPath(path, paint);
			Log.i("aaa", "手指移动了,坐标位置为:X="+x+" Y="+y);
			mSurfaceHolder.unlockCanvasAndPost(canvas);
			
			break;
		case MotionEvent.ACTION_UP:
			
			Path path1 = new Path(path);
			paths.add(path1);
			path.reset();
			
			mBehaviorManager.add(Action.END, x, y);
			Log.i("aaa", "手指离开了,坐标位置为:X="+x+" Y="+y);
			
			break;

		default:
			break;
		}
		
		return true;
	}
	
	
	public void singSong(){
		
	}
	
	public void clear(){
		Canvas canvas = mSurfaceHolder.lockCanvas();
		
		canvas.drawColor(Color.WHITE);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	
	public void replay(){
		Canvas canvas = mSurfaceHolder.lockCanvas();
		
		if(paths != null && paths.size() > 0 ){
			for(Path path:paths){
				canvas.drawPath(path, paint);
			}
		}
		
		mSurfaceHolder.unlockCanvasAndPost(canvas);
		
	}
	
	




}
