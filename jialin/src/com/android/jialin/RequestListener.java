package com.android.jialin;

/**
 * @author Administrator
 * 
 */
public interface RequestListener {

	public void onComplete(String result);

	public void onException(Exception e);

}
