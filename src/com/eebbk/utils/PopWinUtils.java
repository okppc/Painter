package com.eebbk.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.eebbk.painter.R;

public class PopWinUtils {


	public static void getDefaultWindow(Context context, View parent, final OnPopupItemListener mItemListener){
		final PopupWindow pop = new PopupWindow(context);
		View contentView = LayoutInflater.from(context).inflate(R.layout.item_popup_content, null);
		Button path = (Button) contentView.findViewById(R.id.btn_path);
		Button circle = (Button) contentView.findViewById(R.id.btn_circle_stroke);
		Button rect = (Button) contentView.findViewById(R.id.btn_reject);
		Button triangle = (Button) contentView.findViewById(R.id.btn_triangle);
		Button move = (Button) contentView.findViewById(R.id.btn_move);
		move.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mItemListener.onPopItemClick(Constant.SHAPE_TYPE_MOVE);
				if(pop.isShowing()){
					pop.dismiss();
				}
				
			}
		});
		path.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mItemListener.onPopItemClick(Constant.SHAPE_TYPE_PATH);
				if(pop.isShowing()){
					pop.dismiss();
				}
			}
		});
		circle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mItemListener.onPopItemClick(Constant.SHAPE_TYPE_CIRCLE);
				if(pop.isShowing()){
					pop.dismiss();
				}
			}
		});
		rect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mItemListener.onPopItemClick(Constant.SHAPE_TYPE_RECT);
				if(pop.isShowing()){
					pop.dismiss();
				}
			}
		});
		triangle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mItemListener.onPopItemClick(Constant.SHAPE_TYPE_LINE);
				if(pop.isShowing()){
					pop.dismiss();
				}
			}
		});
		pop.setContentView(contentView);
		pop.setWindowLayoutMode(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		pop.setFocusable(true);
//		pop = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, 
//				LayoutParams.WRAP_CONTENT, true);
		pop.setTouchable(true);
		pop.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				return false;
			}
		});
		//这一句代码是个bug,必须要加，不加popup无法取消
		pop.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.picture_popup_bg));
		pop.showAsDropDown(parent, 0, 0);
	}


	public interface OnPopupItemListener{
		public void onPopItemClick(int type);
	}


}
