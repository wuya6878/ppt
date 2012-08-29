package com.android.jialin.sync;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.util.Log;

import com.android.jialin.R;
import com.android.jialin.RequestListener;
import com.android.jialin.db.CityService;
import com.android.jialin.util.ActivityUtils;
import com.android.jialin.util.Pager;

public class CityListener implements RequestListener {

	private Activity context;

	private ProgressDialog progress;

	private Resources res;

	public CityListener(Activity context) {
		this.context = context;
		res = context.getResources();
		progress = ProgressDialog.show(context,
				res.getString(R.string.city_searching),
				res.getString(R.string.getting));
		progress.show();
	}

	public void onComplete(final String result) {

		context.runOnUiThread(new Runnable() {

			public void run() {

				Log.v("result", result);
				try {
					Pager pager = CityJsonParser.parseDatas(result);

					if (pager != null && pager.getList() != null) {
						CityService service = new CityService(context);
						service.syncDatas(pager.getList());
						if (progress != null) {
							progress.dismiss();
						}
						ActivityUtils.showDialogClose(context,
								res.getString(R.string.ok),
								res.getString(R.string.tip),
								res.getString(R.string.sync_success));
					} else {
						if (progress != null) {
							progress.dismiss();
						}
						ActivityUtils.showDialog(context,
								res.getString(R.string.ok),
								res.getString(R.string.tip),
								res.getString(R.string.sync_error));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	public void onException(Exception e) {

		context.runOnUiThread(new Runnable() {

			public void run() {

				if (progress != null) {
					progress.dismiss();
				}
				ActivityUtils.showDialog(context, res.getString(R.string.ok),
						res.getString(R.string.tip),
						res.getString(R.string.get_nothing));
			}
		});

	}

}
