package com.eebbk.painter;

import java.util.ArrayList;
import java.util.List;

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

import com.eebbk.behavior.Behavior;
import com.eebbk.behavior.Behavior.Action;
import com.eebbk.behavior.BehaviorManager;
import com.eebbk.shape.Shape;
import com.eebbk.shape.ShapeManager;

public class PaintView extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceHolder mSurfaceHolder;
	private BehaviorManager mBehaviorManager;
	private ShapeManager mShapeManager;
	
	private Paint paint;
	private int paintWidth = 5;
	
	private float paintOffsetY = 0.0f; // 绘制时的Y偏移（去掉ActionBar,StatusBar,marginTop等高度）
    private float paintOffsetX = 0.0f; // 绘制事的X偏移（去掉marginLeft的宽度）

	private int color = Color.BLACK;
	private int size = 5;
	private Canvas canvas;
	
	public interface OnReceiveListener{
		public void onReceive(List<Shape> shapes);
	}
	
	private List<Shape> lists = new ArrayList<Shape>();
	
	private OnComListener mComListener = new OnComListener() {
		
		@Override
		public void onResult(List<Shape> shapes) {
			if(shapes == null){
				return;
			}
			lists.addAll(shapes);
		}
	};

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
		mShapeManager = new ShapeManager(mComListener);


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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getRawX();
		float y = event.getRawY();
		
		x -= paintOffsetX;
		y -=paintOffsetY;
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
//			mBehaviorManager.add(Action.START, x, y);
			mShapeManager.handleShpStart(x,y);
			Log.i("aaa", "手指点下了,坐标位置为:X="+x+" Y="+y);
			break;
		case MotionEvent.ACTION_MOVE:
//			mBehaviorManager.add(Action.MOVE, x, y);
			Canvas canvas = mSurfaceHolder.lockCanvas();
			mShapeManager.handleShpMove(x, y, canvas);
			Log.i("bbb", "手指移动的坐标为:"+x+","+y);
			mSurfaceHolder.unlockCanvasAndPost(canvas);
			break;
		case MotionEvent.ACTION_UP:
			mShapeManager.handleShpEnd();
//			mBehaviorManager.add(Action.END, x, y);
			break;
		default:
			break;
		}

		return true;
	}
	
	public void clear(){
		Canvas canvas = mSurfaceHolder.lockCanvas();
		mShapeManager.clear(canvas);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
//		mBehaviorManager.add(Action.CLEAR, 0, 0);
	}

	public void replay(){
		Canvas canvas = mSurfaceHolder.lockCanvas();
		mShapeManager.replay(canvas);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}
	public void undo(){
		Canvas canvas = mSurfaceHolder.lockCanvas();
		mShapeManager.undo(canvas);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}

	public List<Shape> translate(){
		List<Shape> lists = null;
			Canvas canvas = mSurfaceHolder.lockCanvas();
			lists = mShapeManager.translate(canvas);
			mSurfaceHolder.unlockCanvasAndPost(canvas);
			return lists;
	}
	
	public int setPaintWidth(){
		return mShapeManager.setPaintWidth();
	}
	
	public void setColor(){
		mShapeManager.setColor();
	}
	
	public void eraser(boolean erase){
		mShapeManager.eraser(erase);
	}
	
	public void drawOrderPic(int type){
		mShapeManager.setShpType(type);
	}
	
	//设置绘画的实际参照点
	public void setPaintOffset(float paintOffsetX, float paintOffsetY){
		this.paintOffsetX = paintOffsetX;
		this.paintOffsetY = paintOffsetY;
	}
	
	public List<Shape> getShapes(){
		return lists;
	}
	
	public void beginTranslate(){
		Canvas canvas = mSurfaceHolder.lockCanvas();
		mShapeManager.beginTranslate(canvas);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
		
		
	}


}
