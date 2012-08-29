package com.android.jialin.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import com.android.jialin.R;

public class ImageScale {

	public static BitmapDrawable getImage(Context context) {
		// load the picture needs to operate
		Bitmap bitmapOrg = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.icon);

		// Get the picture width and height
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		// define prefer width and height of the picture
		int newWidth = 22;
		int newHeight = 22;

		// Calculate the zoom factor, new factor/old factor
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create matrix object
		Matrix matrix = new Matrix();

		
		matrix.postScale(scaleWidth, scaleHeight);

		// rotate picture action
		// matrix.postRotate(45);

		// create new picture
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);

		// transfer Bitmap to Drawable'object，make it work in ImageView, ImageButton中
		return new BitmapDrawable(resizedBitmap);

	}

}
