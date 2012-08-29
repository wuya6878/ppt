package com.android.jialin;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Administrator
 * 
 */
public class AboutActivity extends Activity {

	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		init();
	}

	@Override
	protected void onResume() {
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

	/**
	 * 
	 * @Title: init
	 * @Description: initialize every component
	 */
	private void init() {
		webView = (WebView)findViewById(R.id.webView);
		try{
			WebSettings webSettings = webView.getSettings(); 
			webSettings.setSavePassword(false); 
			webSettings.setSaveFormData(false); 
			webSettings.setJavaScriptEnabled(true); 
			webSettings.setSupportZoom(true);
			webSettings.setAllowFileAccess(true);
			webView.setWebViewClient(new WebViewClient(){
                 public boolean shouldOverrideUrlLoading(WebView view, String Hurl){                    
                         view.loadUrl(Hurl);
                         return true;
                 }   
			});  

			
			webView.loadUrl("file:///android_asset/www/about.html");//local
		}catch(Exception ex){}
	}

}
