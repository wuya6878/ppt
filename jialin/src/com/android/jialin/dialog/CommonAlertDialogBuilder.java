package com.android.jialin.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.ContextThemeWrapper;

import com.android.jialin.R;

/**
 * @author Administrator
 * 
 */
public class CommonAlertDialogBuilder {
	private static Builder instance;
	private static ContextThemeWrapper localContextThemeWrapper;

	public static Builder getInstance(Context context) {
		localContextThemeWrapper = new ContextThemeWrapper(context,
				R.style.Theme_Dialog);
		instance = new AlertDialog.Builder(localContextThemeWrapper);
		return instance;
	}
}
