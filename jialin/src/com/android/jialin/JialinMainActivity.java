package com.android.jialin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jialin.dialog.ExitDialog;
import com.android.jialin.search.SearchActivity;
import com.android.jialin.sync.CitySyncActivity;

/**
 * @author Administrator
 * 
 */
public class JialinMainActivity extends Activity {

	// references to our images

	private int back = 0;

	private GridView gridView = null;

	private ImageView reset_logo;

	private String[] gridTitles = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
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
		reset_logo = (ImageView) findViewById(R.id.reset_logo);
		reset_logo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		gridTitles = this.getResources().getStringArray(R.array.grid_title);
		this.gridView = (GridView) findViewById(R.id.cview);

		gridView.setAdapter(new ImageAdapter(this));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (position == 0) {
					// sync data
					Intent intent = new Intent();
					intent.setClass(JialinMainActivity.this, CitySyncActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.left_in, R.anim.left_out);
				} else if (position == 1) {
					// search data
					Intent intent = new Intent();
					intent.setClass(JialinMainActivity.this, SearchActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.left_in, R.anim.left_out);
				} else if (position == 2) {
					// about me
//					ourClickListener.onClick(v);
					Intent intent = new Intent();
					intent.setClass(JialinMainActivity.this, AboutActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.left_in, R.anim.left_out);
				} else {
					// exit system
					ExitDialog.getExitDialog(JialinMainActivity.this).create().show();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back++;
			switch (back) {
			case 1:
				Toast.makeText(JialinMainActivity.this, R.string.back_exit,
						Toast.LENGTH_LONG).show();
				break;
			case 2:
				back = 0;// initialize back value
				myback();
				break;
			}
			return true;// set as false let "back" doesn't work ，true means "back" works
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void myback() { // close app
		JialinMainActivity.this.finish();
		android.os.Process.killProcess(android.os.Process.myPid());// close thread
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return 4;
		}

		public View getView(int position, View convertView, ViewGroup arg2) {
			View view = View.inflate(mContext, R.layout.catalogview, null);
			RelativeLayout rl = (RelativeLayout) view
					.findViewById(R.id.relaGrid);
			GridView gv = (GridView) arg2;
			gv.setNumColumns(2);// set the number of rows in each column
			gv.setGravity(Gravity.CENTER);// position to center
			ImageView image = (ImageView) rl.findViewById(R.id.chooseImage);
			TextView text = (TextView) rl.findViewById(R.id.chooseText);
			if (position == 0) {
				image.setImageResource(R.drawable.a_5);
			} else if (position == 1) {
				image.setImageResource(R.drawable.a_6);
			} else if (position == 2) {
				image.setImageResource(R.drawable.a_3);
			} else if (position == 3) {
				image.setImageResource(R.drawable.a_4);
			}
			text.setText(gridTitles[position]);

			return rl;
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}
	}

	/**
	 * 
	 */
	private OnClickListener ourClickListener = new OnClickListener() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		public void onClick(View v) {
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(
					JialinMainActivity.this);
			mBuilder.setMessage(R.string.about_me);
			mBuilder.setTitle(R.string.author_info);
			mBuilder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

						}
					});
			mBuilder.create();
			mBuilder.show();

		}

	};

}
