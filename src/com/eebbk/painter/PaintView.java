package com.eebbk.painter;

import java.util.ArrayList;
import java.util.List;

import com.eebbk.behavior.Behavior;
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
	private int paintWidth = 5;

	private float currentX,currentY;

	private List<Path> paths = new ArrayList<Path>();
	private List<Path> pathsBackup = new ArrayList<Path>();

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

		float x = event.getX();
		float y = event.getY();

		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:

			mBehaviorManager.add(Action.START, x, y);

			path.moveTo(x, y);

			currentX = x;
			currentY = y;

			Log.i("aaa", "手指点下了,坐标位置为:X="+x+" Y="+y);
			break;
		case MotionEvent.ACTION_MOVE:
			mBehaviorManager.add(Action.MOVE, x, y);
			startMove(x, y);

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

	private void startMove(float x, float y) {

		if(!isNewPoint(x, y)){
			return;
		}

		Canvas canvas = mSurfaceHolder.lockCanvas();

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
	}


	public void clear(){
		Canvas canvas = mSurfaceHolder.lockCanvas();

		canvas.drawColor(Color.WHITE);
		if(paths != null && paths.size() > 0 ){
			pathsBackup.clear();
			for(Path path: paths){
				pathsBackup.add(path);
			}
			
			paths.clear();
		}

		mSurfaceHolder.unlockCanvasAndPost(canvas);

		mBehaviorManager.add(Action.CLEAR, 0, 0);

	}


	public void replay(){
		Canvas canvas = mSurfaceHolder.lockCanvas();
		canvas.drawColor(Color.WHITE);
		
		if(pathsBackup != null && pathsBackup.size() > 0 ){
			for(Path path:pathsBackup){
				canvas.drawPath(path, paint);
			}
		}

		mSurfaceHolder.unlockCanvasAndPost(canvas);

	}

	public void undo(){


		if(paths == null || paths.isEmpty()){
			return;
		}

		paths.remove(paths.size()-1);

		Canvas canvas = mSurfaceHolder.lockCanvas();

		canvas.drawColor(Color.WHITE);
		for(Path path:paths){
			canvas.drawPath(path, paint);
		}

		mSurfaceHolder.unlockCanvasAndPost(canvas);

		mBehaviorManager.add(Action.UNDO, 0, 0);

	}

	public void translate(){
		List<Behavior> behaviors = mBehaviorManager.getAll();
		if(behaviors == null){
			Log.i("bbb", "behaviors列表为空！");
			return;
		}
		Path p = new Path();
		List<Path> realBehav = new ArrayList<Path>();

		if( Action.CLEAR == behaviors.get(behaviors.size()-1).getStep() ){
			behaviors.remove(behaviors.size()-1);
		}

		if(behaviors.isEmpty()){
			return;
		}

		for(Behavior behavior : behaviors){

			int step = behavior.getStep();
			float x = behavior.getX();
			float y = behavior.getY();

			switch (step) {
			case Action.START:
				p.moveTo(x, y);
				p.lineTo(x, y);
				break;
			case Action.MOVE:
				p.lineTo(x, y);

				break;
			case Action.END:
				Path pa = new Path(p);
				realBehav.add(pa);
				p.reset();

				break;
			case Action.CLEAR:

				realBehav.clear();

				break;
			case Action.UNDO:
				if(realBehav == null || realBehav.isEmpty()){
					break;
				}
				realBehav.remove(realBehav.size()-1);

				break;

			default:
				break;
			}
		}



		if(realBehav != null && realBehav.size()>0 ){
			Canvas canvas = mSurfaceHolder.lockCanvas();
			canvas.drawColor(Color.WHITE);
			Log.i("bbb", "画接收到的Path！path的大小："+realBehav.size());

			for(Path path:realBehav){
				canvas.drawPath(path, paint);
			}

			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}

	}


	public int setPaintWidth(){

		if(paint == null){
			paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(color);
			paint.setStrokeWidth(paintWidth);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeCap(Paint.Cap.ROUND);
		}
		if(paintWidth > 20){
			paintWidth = 5;
		}
		paint.setStrokeWidth(++paintWidth);
		return paintWidth;
	}



	public boolean isNewPoint(float x ,float y){

		//均值策略1,半径
		if( Math.sqrt( Math.pow((currentX-x),2)+Math.pow((currentY-y),2) ) <= 5f  ){
			
		//绝对值策略,边长
//		if( Math.abs(currentX - x) <= 5f || Math.abs(currentY - y) <= 5f  ){
			currentX = x;
			currentY = y;
			return false;
		}
		currentX = x;
		currentY = y;
		return true;

	}



}
