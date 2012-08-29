package com.android.jialin;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Administrator
 * 
 */
public class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		int index = new Random().nextInt(4);

		try {
			this.getWindow()
					.setBackgroundDrawableResource(
							R.drawable.class.getDeclaredField("b" + index)
									.getInt(null));
		} catch (Exception ex) {
		}
		LoadingGifView view = (LoadingGifView) findViewById(R.id.gifView1);
		view.setSrc(R.drawable.loading_balls);
		view.setStart();
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(6 * 1000);
					loadSystem();
				} catch (Exception ex) {
				}

			}
		}.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/***
	 * 
	 * @Title: loadSystem
	 * @Description: loading UI
	 */
	private void loadSystem() {
		Intent i = new Intent(SplashActivity.this, JialinMainActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}