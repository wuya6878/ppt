package com.android.jialin.sync;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.android.jialin.util.Pager;
import com.android.jialin.util.StringUtil;

public class CityJsonParser {

	/**
	 * @param str
	 * @return
	 */
	public static Pager parseDatas(String str) {
		Pager pager = new Pager();
		try {
			str = StringUtil.nvl(str);
			List<City> result = new ArrayList<City>();
			if (!str.equals("")) {
				JSONObject obj = new JSONObject(str);
				pager.setNameTitle(obj.getString("name"));
				pager.setTotalCount(obj.getLong("count"));
				JSONArray array = obj.getJSONArray("items");
				String arr = obj.getString("items");
				if (array != null) {
					for (int i = 0, count = array.length(); i < count; i++) {
						JSONObject item = array.getJSONObject(i);
						City city = getCityFromJsonObj(item);
						result.add(city);
					}
				}
			}
			pager.setList(result);

		} catch (Exception ex) {
			Log.e("CityJsonParser", ex.getMessage());
		}
		return pager;
	}

	private static City getCityFromJsonObj(JSONObject item) throws Exception {
		City city = new City();
		String name = StringUtil.nvl(item.getString("name"));
		String Longitude = StringUtil.nvl(item.getString("Longitude"));// longtitude
		String Latitude = StringUtil.nvl(item.getString("Latitude"));// latitude
		String population = StringUtil.nvl(item.getString("population"));// population
		String postcode = StringUtil.nvl(item.getString("postcode"));// postcode
		city.setName(name);
		city.setLongitude(Longitude);
		city.setLatitude(Latitude);
		city.setPopulation(population);
		city.setPostcode(postcode);
		return city;
	}

}
