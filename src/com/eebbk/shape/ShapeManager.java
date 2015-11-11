package com.eebbk.shape;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.eebbk.behavior.Behavior;
import com.eebbk.behavior.Behavior.Action;
import com.eebbk.behavior.BehaviorManager;
import com.eebbk.painter.OnComListener;
import com.eebbk.shape.ShapeHelper.Flag;
import com.eebbk.utils.Constant;

public class ShapeManager {
	//可绘制的图形
	private JPath mJPath;
	private JCircle mJCircle;
	private JRect mJRect;
	private JLine mJLine;
	private JMove mJMove;

	//判断移动图形只在第一次移动中判断,以后不再判断,沿用同一个图形
	private boolean isNewShape = true;

	//行为管理器
	private BehaviorManager mBehaviorManager;

	private ShapeHelper mShapeHelper;
	private int[] colors = {Color.BLACK,Color.RED,Color.MAGENTA,Color.YELLOW,Color.GREEN,
			Color.CYAN,Color.BLUE,Color.GRAY,Color.LTGRAY,Color.DKGRAY,Color.WHITE};
	private String[] str = {"黑色","红色","品红色","黄色","绿色","青色","蓝色","灰色","浅灰色","深灰色","白色"};
	private int colorIndex;
	private int colorEraseTemp;
	private int size = 5;
	
	private OnComListener mComListener;

	//默认画的是路径
	private int paintType = Constant.SHAPE_TYPE_PATH;

	public ShapeManager(OnComListener mComListener){
		this.mComListener = mComListener;
		mShapeHelper = new ShapeHelper();
		mBehaviorManager = new BehaviorManager();
	}

	public void handleShpStart(float x, float y){
		switch (paintType) {
		case Constant.SHAPE_TYPE_PATH:
			mJPath = new JPath(x, y,colors[colorIndex],size);
			mBehaviorManager.add(Action.START, x, y);
			break;
		case Constant.SHAPE_TYPE_CIRCLE:
			mJCircle = new JCircle(x, y,colors[colorIndex],size);
			break;
		case Constant.SHAPE_TYPE_RECT:
			mJRect = new JRect(x, y,colors[colorIndex],size);
			break;
		case Constant.SHAPE_TYPE_LINE:
			mJLine = new JLine(x, y,colors[colorIndex],size);
			break;
		case Constant.SHAPE_TYPE_MOVE:
			mJMove = new JMove(x, y);
			isNewShape = true;
			break;
		default:
			break;
		}

	}

	public void handleShpMove(float x, float y, Canvas canvas){
		Shape s = null;
		switch (paintType) {
		case Constant.SHAPE_TYPE_PATH:
			s = mJPath;
			break;
		case Constant.SHAPE_TYPE_CIRCLE:
			s = mJCircle;
			break;
		case Constant.SHAPE_TYPE_RECT:
			s = mJRect;
			break;
		case Constant.SHAPE_TYPE_LINE:
			s = mJLine;
			break;
		case Constant.SHAPE_TYPE_MOVE:
			s = mJMove;
			break;
		default:
			break;
		}
		onShpMove(s, x, y, canvas);
	}

	public void handleShpEnd(){
		Shape s = null;
		switch (paintType) {
		case Constant.SHAPE_TYPE_PATH:
			s = mJPath;
			break;
		case Constant.SHAPE_TYPE_CIRCLE:
			s = mJCircle;
			break;
		case Constant.SHAPE_TYPE_RECT:
			s = mJRect;
			break;
		case Constant.SHAPE_TYPE_LINE:
			s = mJLine;
			break;
		case Constant.SHAPE_TYPE_MOVE:
			s = mJMove;
			break;
		default:
			break;
		}
		onShpEnd(s);
	}

	private void onShpMove(Shape shape, float x, float y, Canvas canvas){
		if(paintType == Constant.SHAPE_TYPE_MOVE && isNewShape){
			/**1.判断远程库是否为空
			 * 2.和远程图形库里的数据进行比对,根据移动策略找出应该移动的shape对象
			 * 3.对该对象进行参数设置,控制其移动
			 */
			List<Shape> lists = mShapeHelper.getAll(Flag.REMOTE);
			if(lists  == null){
				return;
			}
			int len = lists.size();
			float min = 0;
			int index = 0;
			float values[] = new float[len];
			for(int i = 0; i<len;i++){
				values[i] = lists.get(i).calcMinDistn(shape.startX, shape.startY);
				if(0 == i){
					min = values[i];
					index = i;
				}else{
					if(min > values[i]){
						min = values[i];
						index = i;
					}
				}
			}
			Log.i("bbb", "最小索引为:"+index);
			((JMove)shape).setShape(lists.get(index));
			mShapeHelper.delShape(lists.get(index), Flag.USER);
			mShapeHelper.delShape(lists.get(index), Flag.USERBAK);
			mShapeHelper.delShape(lists.get(index), Flag.REMOTEBAK);
//			mShapeHelper.delShape(lists.get(index), Flag.REMOTE);
			isNewShape = false;
		}

		if( shape.startMove(x, y) ){
			drawHistory(mShapeHelper.getAll(Flag.USER),canvas);
			shape.startDraw(canvas);
			if(paintType == Constant.SHAPE_TYPE_PATH){
				mBehaviorManager.add(Action.MOVE, x, y);
			}

		}

	}

	private void onShpEnd(Shape shape){
		if(shape == null){
			return;
		}

		mShapeHelper.addShape(shape,Flag.USER);
		if(mShapeHelper.isUsrBakBig()){
			mShapeHelper.clearAll(Flag.USERBAK);
		}
		mShapeHelper.addShape(shape, Flag.USERBAK);

		//2.更新所有图形库中的对应项
		if( paintType != Constant.SHAPE_TYPE_PATH && paintType != Constant.SHAPE_TYPE_MOVE){
			mShapeHelper.addShape(shape, Flag.REMOTE);
			if( mShapeHelper.isRemoteBakBig() ){
				mShapeHelper.clearAll(Flag.REMOTEBAK);
			}
			mShapeHelper.addShape(shape, Flag.REMOTEBAK);
			shape = null;
			mBehaviorManager.add(Action.PIC, 0, 0);
			return;
		}
		mBehaviorManager.add(Action.END, 0, 0);
		
	}

	//画出历史图画
	public void drawHistory(List<Shape> shapes, Canvas canvas){
		canvas.drawColor(Color.WHITE);
		if( shapes == null || shapes.isEmpty() ){
			return ;
		}
		for(Shape shape : shapes){
			shape.startDraw(canvas);
		}

	}

	public void clear(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		List<Shape> shapes = mShapeHelper.getAll(Flag.USER);
		if(shapes != null && shapes.size() > 0 ){
			mShapeHelper.clearAll(Flag.USER);
			mBehaviorManager.add(Action.CLEAR, 0, 0);
		}
	}

	//上一步
	public void undo(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		List<Shape> lists = mShapeHelper.getAll(Flag.USER);
		if(lists == null || lists.isEmpty()){
			return;
		}
		lists.remove(lists.size()-1);
		drawHistory(lists, canvas);
		lists = null;
		lists = mShapeHelper.getAll(Flag.USERBAK);
		lists.remove(lists.size()-1);
		mBehaviorManager.add(Action.UNDO, 0, 0);
	}

	//清除回放
	public void replay(Canvas canvas){
		List<Shape> lists = mShapeHelper.getAll(Flag.USERBAK);
		if(lists == null || lists.isEmpty()){
			return;
		}
		mShapeHelper.clearAll(Flag.USER);
		for(Shape shape:lists){
			mShapeHelper.addShape(shape, Flag.USER);
		}
		drawHistory(lists, canvas);
	}


	public void setColor(){
		colorIndex = colorIndex<colors.length-2?++colorIndex:0;
	}

	public void eraser(boolean erase){
		if(erase){
			paintType = Constant.SHAPE_TYPE_PATH;
			size = 10;
			colorEraseTemp = colorIndex;
			colorIndex = colors.length-1;
		}else{
			colorIndex = colorEraseTemp;
		}
	}

	public int setPaintWidth(){
		size = size<20?++size:5;
		return size;
	}

	/**
	 * 这应该是一个实时的方法
	 * 
	 * @param canvas
	 */
	public List<Shape>  translate(Canvas canvas){
		List<Behavior> behaviors = mBehaviorManager.getAll();
		if(behaviors == null){
			Log.i("bbb", "behaviors列表为空！");
			return null;
		}
		JPath jPath = null;
		int which = 0;
		List<Shape> realShapes = new ArrayList<Shape>();
		//清除在末尾的clear,测试用
		//		if( Action.CLEAR == behaviors.get(behaviors.size()-1).getStep() ){
		//			behaviors.remove(behaviors.size()-1);
		//		}
		for(Behavior behavior : behaviors){
			int step = behavior.getStep();
			float x = behavior.getX();
			float y = behavior.getY();
			switch (step) {
			case Action.START:
				jPath = new JPath(x, y);
				break;
			case Action.MOVE:
				jPath.startMove(x, y);
				break;
			case Action.END:
				realShapes.add(jPath);
				jPath = null;
				break;
			case Action.CLEAR:
				realShapes.clear();
				for( int i=0;i<which;i++){
					mShapeHelper.delShape(0, Flag.REMOTE);
					mShapeHelper.delShape(0, Flag.REMOTEBAK);
				}
				Log.i("bbb", "这个方法调用了没?"+mShapeHelper.getAll(Flag.REMOTE).size());
				break;
			case Action.UNDO:
				if(realShapes == null || realShapes.isEmpty()){
					break;
				}
				realShapes.remove(realShapes.size()-1);
				break;
			case Action.PIC:
				if(which < mShapeHelper.getAll(Flag.REMOTE).size()){
					realShapes.add(mShapeHelper.getAll(Flag.REMOTE).get(which++));
					
				}
				break;
			default:
				break;
			}
			
		}
		behaviors.clear();
//		drawHistory(realShapes, canvas);
		mComListener.onResult(realShapes);
		return realShapes;
	}


	public void setShpType(int type){
		paintType = type;
	}
	
	public void beginTranslate(final Canvas canvas){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					translate(canvas);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		}).start();
	}


}
