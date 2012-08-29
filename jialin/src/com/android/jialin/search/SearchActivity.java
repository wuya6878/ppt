package com.android.jialin.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.jialin.R;
import com.android.jialin.db.CityColumns;
import com.android.jialin.db.CityService;
import com.android.jialin.googlemap.GooglemapActivity;
import com.android.jialin.search.CityAdapter.ProductViewHolder;
import com.android.jialin.sync.City;
import com.android.jialin.util.Pager;
import com.android.jialin.util.StringUtil;

public class SearchActivity extends Activity {

	private EditText oneText;

	private ImageView reset_logo;

	Menu mMenu;

	ListView listview;
	Pager pager;
	private Resources res;

	private CityService cityService;

	CityAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search);
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
		oneText = (EditText) this.findViewById(R.id.et_one);
		reset_logo = (ImageView) findViewById(R.id.reset_logo);
		listview = (ListView) findViewById(R.id.list_view);
		res = this.getResources();
		reset_logo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				oneText.setText("");
			}

		});
		cityService = new CityService(this);
		adapter = new CityAdapter(this, pager);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					ProductViewHolder holder;
					holder = (ProductViewHolder) arg1.getTag();
					String idStr = holder.getId();
					Toast.makeText(SearchActivity.this, R.string.map_show,
							Toast.LENGTH_LONG).show();
					City city = cityService.find(Integer.parseInt(idStr));
					Intent i = new Intent(SearchActivity.this,
							GooglemapActivity.class);
					i.putExtra(CityColumns.ID, idStr);
					i.putExtra(CityColumns.NAME, city.getName());
					i.putExtra(CityColumns.LONGITUDE, city.getLongitude());
					i.putExtra(CityColumns.LATITUDE, city.getLatitude());
					startActivity(i);
				} catch (Exception ex) {

				}
			}
		});
	}

	/**
	 * @param v
	 */
	public void onClick(View v) {
		if (v.getId() == R.id.one_search) {
			queryPager();
		}
	}

	/**
	 * 
	 * @Description: initialize data of List
	 */
	private void queryPager() {
		try {
			if (pager == null) {
				pager = new Pager();
			}
			String keys = StringUtil.nvl(oneText.getText().toString());
			keys = keys.replace(res.getString(R.string.one_hint), "");
			pager.setKeyword(keys);
			final ProgressDialog progress = ProgressDialog.show(this,
					res.getString(R.string.city_searching),
					res.getString(R.string.getting));
			progress.show();
			this.runOnUiThread(new Runnable() {
				public void run() {
					try {
						pager = cityService.getScrollData(pager);
						adapter.refreshPager(pager);
						adapter.notifyDataSetChanged();
						if (progress != null) {
							progress.dismiss();
						}
						if (pager.getList() == null
								|| pager.getList().size() == 0) {
							Toast.makeText(SearchActivity.this,
									R.string.no_data, Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});

		} catch (Exception ex) {
			Toast.makeText(this, R.string.get_nothing, Toast.LENGTH_LONG)
					.show();
		}

	}

	/**
	 * 
	 */
	private void firstPage() {
		if (pager == null) {
			return;
		}
		pager.setPageNumber(1);
		queryPager();
	}

	private void nextPage() {
		if (pager == null) {
			return;
		}
		if (pager.hasNextPage()) {
			pager.setPageNumber(pager.getPageNumber() + 1);
			queryPager();
		}else{
			Toast.makeText(this, R.string.last_error, Toast.LENGTH_SHORT).show();
		}
	}

	private void prevPage() {
		if (pager == null) {
			return;
		}
		if (pager.hasPrePage()) {
			pager.setPageNumber(pager.getPageNumber() - 1);
			queryPager();
		}else{
			Toast.makeText(this, R.string.first_error, Toast.LENGTH_SHORT).show();
		}
	}

	private void lastPage() {
		if (pager == null) {
			return;
		}
		int pc = pager.getPageCount();
		pager.setPageNumber(pc);
		queryPager();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		mMenu = menu;
		MenuInflater mInflater = getMenuInflater();
		mInflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.firstMenuItem:
			firstPage();
			return true;
		case R.id.prevMenuItem:
			prevPage();
			return true;
		case R.id.nextMenuItem:
			nextPage();
			return true;
		case R.id.lastMenuItem:
			lastPage();
			return true;
		default:
			if (!item.hasSubMenu()) {
				Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
				return true;
			}
			break;
		}
		return false;

	}

}
