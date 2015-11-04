package com.eebbk.painter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button replay;
	private Button clear;
	private Button undo;
	private Button paintwidth;
	private Button translate;
	private PaintView mPaintView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		replay = (Button) findViewById(R.id.btn_replay);

		clear = (Button) findViewById(R.id.btn_clear);

		undo = (Button) findViewById(R.id.btn_undo);
		
		paintwidth = (Button) findViewById(R.id.btn_paintwidth);
		
		translate = (Button) findViewById(R.id.btn_translate);

		mPaintView = (PaintView) findViewById(R.id.pv_show);

		replay.setOnClickListener(this);
		clear.setOnClickListener(this);
		undo.setOnClickListener(this);
		paintwidth.setOnClickListener(this);
		translate.setOnClickListener(this);

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

			mPaintView.translate();

			break;

		default:
			break;
		}

	}



}
