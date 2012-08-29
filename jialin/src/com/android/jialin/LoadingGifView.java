package com.android.jialin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Administrator
 * 
 */
public class LoadingGifView extends View implements Runnable {
	GifOpenHelper gHelper;
	private boolean isStop = false;
	int delta = 1;
	String title;

	Bitmap bmp;

	// construct - refer for java
	public LoadingGifView(Context context) {
		this(context, null);

	}

	// construct - refer for xml
	public LoadingGifView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 
	 * @param stop
	 */
	public void setStop() {
		isStop = false;
	}

	/**
	 */
	public void setStart() {
		isStop = true;

		Thread updateTimer = new Thread(this);
		updateTimer.start();
	}

	/**
	 * @param id
	 */
	public void setSrc(int id) {

		gHelper = new GifOpenHelper();
		gHelper.read(LoadingGifView.this.getResources().openRawResource(id));
		bmp = gHelper.getImage();
	}

	public void setDelta(int is) {
		delta = is;
	}

	// to meaure its Width & Height
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		return gHelper.getWidth();
	}

	private int measureHeight(int measureSpec) {
		return gHelper.getHeigh();
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(bmp, 0, 0, new Paint());
		bmp = gHelper.nextBitmap();

	}

	public void run() {
		// TODO Auto-generated method stub
		while (isStop) {
			try {
				this.postInvalidate();
				Thread.sleep(gHelper.nextDelay() / delta);
			} catch (Exception ex) {

			}
		}
	}

}