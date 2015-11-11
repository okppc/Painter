package com.eebbk.painter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.eebbk.shape.Shape;
import com.eebbk.utils.PopWinUtils;
import com.eebbk.utils.PopWinUtils.OnPopupItemListener;

public class MainActivity extends Activity implements OnClickListener {

	private Button replay;
	private Button clear;
	private Button undo;
	private Button paintwidth;
	private Button translate;

	private Button color;
	private Button eraser;
	private Button orderPic;

	private PaintView mPaintView;
//	private TestView mTestView;
	
	private List<Shape> lists = new ArrayList<Shape>();
	private boolean isFirstClick = true;
	
//	private Handler handler = new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			List<Shape> s = mPaintView.translate();
//			if(s == null || s.isEmpty()){
//				return;
//			}
//			lists.addAll(s);
//			mTestView.drawHistory(lists);
//		};
//	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		replay = (Button) findViewById(R.id.btn_replay);
		clear = (Button) findViewById(R.id.btn_clear);
		undo = (Button) findViewById(R.id.btn_undo);
		paintwidth = (Button) findViewById(R.id.btn_paintwidth);
		translate = (Button) findViewById(R.id.btn_translate);

		color = (Button) findViewById(R.id.btn_color);
		eraser = (Button) findViewById(R.id.btn_eraser);
		orderPic = (Button) findViewById(R.id.btn_orderPic);

		replay.setOnClickListener(this);
		clear.setOnClickListener(this);
		undo.setOnClickListener(this);
		paintwidth.setOnClickListener(this);
		translate.setOnClickListener(this);

		color.setOnClickListener(this);
		eraser.setOnClickListener(this);
		orderPic.setOnClickListener(this);

		mPaintView = (PaintView) findViewById(R.id.pv_edit);
//		mTestView = (TestView) findViewById(R.id.pv_show);
		setPaintViewOffset();
		
		
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_clear:
			mPaintView.clear();
			break;
		case R.id.btn_replay:
			mPaintView.replay();
			break;
		case R.id.btn_undo:
			mPaintView.undo();
			break;
		case R.id.btn_paintwidth:
			int width = mPaintView.setPaintWidth();
			Toast.makeText(this, "当前画笔大小为:"+width, Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_translate:
//			if(isFirstClick){
//				
//				new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						while(true){
//							handler.sendEmptyMessageDelayed(100, 3000);
//						}
//					}
//				}).start();
//				
//				
//				isFirstClick = false;
//			}
			
//			lists.addAll(mPaintView.translate());
//			mTestView.drawHistory(lists);
			
			
//			mTestView.drawHistory(mPaintView.getShapes());
			
			break;
		case R.id.btn_color:
			mPaintView.setColor();
			break;
		case R.id.btn_eraser:
			if("橡皮擦".equals( eraser.getText() ) ){
				mPaintView.eraser(true);
				eraser.setText("开始绘制");
			}else{
				mPaintView.eraser(false);
				eraser.setText("橡皮擦");
			}
			break;
		case R.id.btn_orderPic:
			PopWinUtils.getDefaultWindow(this, this.orderPic, mItemListener);
			break;
		default:
			break;
		}
	}

	private OnPopupItemListener mItemListener = new OnPopupItemListener() {

		@Override
		public void onPopItemClick(int type) {
			mPaintView.drawOrderPic(type);
		}
	}; 


	public void setPaintViewOffset(){
		TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
		float actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0);
		Log.i("paintView", "actionBarHeight =" + actionBarHeight);

		//        Rect frame = new Rect();
		//        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		//        int statusBarHeight = frame.top;
		int statusBarHeight = 0;
		try {
			Class clazz=Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			Field field=clazz.getField("status_bar_height");
			int id = Integer.parseInt(field.get(object).toString());
			statusBarHeight = this.getResources().getDimensionPixelSize(id);
		} catch (Exception e) {
			// TODO: handle exception
		}

		Log.i("paintView", "statusBarHeight =" + statusBarHeight);

		int marginTop = mPaintView.getTop();
		Log.i("paintView", "doodleView marginTop =" + marginTop);

		int marginLeft = mPaintView.getLeft();
		Log.i("paintView", "doodleView marginLeft =" + marginLeft);

		float offsetX = marginLeft;
		float offsetY = statusBarHeight + marginTop;

		mPaintView.setPaintOffset(offsetX, offsetY);
		Log.i("paintView", "client1 offsetX = " + offsetX + ", offsetY = " + offsetY);
	}



}
