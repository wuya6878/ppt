package com.android.jialin.sync;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.jialin.R;
import com.android.jialin.util.ActivityUtils;

/**
 * 
 * @author Administrator
 * 
 */
public class CitySyncActivity extends Activity {

	private Spinner companies;

	private Resources res;

	private String[] cityCodes;

	private int pos = 0;

	private CitySync citySync;

	@Override
	protected void onResume() {
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sync);

		res = this.getResources();

		cityCodes = res.getStringArray(R.array.sync_code);

		companies = (Spinner) this.findViewById(R.id.ems_com);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.sync_name, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		companies.setAdapter(adapter);
		companies.setOnItemSelectedListener(selectedListener);

		citySync = new CitySync();
	}

	private AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			pos = position;
		}

		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	public String getCode() {

		return cityCodes[pos - 1]; // because city spinner has one more selection
	}

	
	public boolean validateSelect() {
		if (pos < 1 || pos > cityCodes.length) {
			return false;
		} else {
			return true;
		}
	}

	public void onClick(View v) {

		if (v.getId() == R.id.city_sync) {
			if (this.validateSelect()) {
				// test success
				String code = this.getCode();
				Bundle bundle = new Bundle();
				bundle.putString("code", code);
				this.getIntent().putExtras(bundle);
				CityListener listener = new CityListener(this);
				citySync.asyncRequest(code, listener);
			} else {
				ActivityUtils.showDialog(this, res.getString(R.string.ok),
						res.getString(R.string.tip),
						res.getString(R.string.sync_noselect));
			}

		}
	}
}
