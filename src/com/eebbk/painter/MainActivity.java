package com.eebbk.painter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button replay;
	private Button clear;
	private Button undo;
	private PaintView mPaintView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		replay = (Button) findViewById(R.id.btn_replay);

		clear = (Button) findViewById(R.id.btn_clear);

		undo = (Button) findViewById(R.id.btn_undo);

		mPaintView = (PaintView) findViewById(R.id.pv_show);




		replay.setOnClickListener(this);
		clear.setOnClickListener(this);


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

			mPaintView.clear();

			break;

		default:
			break;
		}

	}



}
