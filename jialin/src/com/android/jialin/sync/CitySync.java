package com.android.jialin.sync;

import android.os.Bundle;

import com.android.jialin.RequestListener;
import com.android.jialin.util.HttpUtils;

public class CitySync {

	private static final String HTTP_URL = "http://7967.s20.javaidc.com/json/";

	public String request(String name) {

		Bundle params = new Bundle();

		String url = HTTP_URL + name + ".json";

		return HttpUtils.openUrl(url, "GET", params, "gbk");

	}

	public void asyncRequest(final String name, final RequestListener listener) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String responseXml = request(name);
					responseXml = new String(responseXml.getBytes(), "UTF-8");
					listener.onComplete(responseXml);
				} catch (Exception e) {
					listener.onException(e);
				}

			}
		}).start();
	}

}
