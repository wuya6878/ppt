package com.android.jialin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author Administrator
 * 
 */
public final class ActivityUtils {

	public static void showDialog(Context context, String button, String title,
			String message) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setNeutralButton(button, null).create().show();
	}

	public static void showDialogClose(final Activity context, String button,
			String title, String message) {
		new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setNeutralButton(button,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								context.finish();
							}
						}).create().show();
	}

	// test if str is null or ""
	public static boolean validateNull(String str) {
		if (str == null || str.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	
}
