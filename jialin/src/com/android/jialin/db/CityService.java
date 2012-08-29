package com.android.jialin.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.jialin.sync.City;
import com.android.jialin.util.Pager;
import com.android.jialin.util.StringUtil;

/**
 * @author Administrator
 * 
 */
public class CityService {
	private Context context;
	private ContentResolver resolver;

	public CityService(Context context) {
		this.context = context;
		this.resolver = context.getContentResolver();
	}

	/**
	 * save datas
	 * 
	 * @param cityLst
	 */
	public void syncDatas(List<City> cityLst) {
		try {
			deleteAll();
			if (cityLst != null) {
				for (int i = 0, count = cityLst.size(); i < count; i++) {
					try {
						City city = cityLst.get(i);
						insert(city);
					} catch (Exception ex) {
						String error = ex.getMessage();
						Log.e("db", error);
					}
				}
			}
		} finally {
		}
	}

	/**
	 * @param city
	 */
	public void insert(City city) {
		Uri uri = CityContentProvider.CONTENT_URI;
		ContentValues values = city.convert();
		values.remove(CityColumns.ID);
		resolver.insert(uri, values);
	}

	/**
	 * 
	 */
	private void deleteAll() {
		Uri uri = CityContentProvider.CONTENT_URI;
		resolver.delete(uri, null, null);
	}

	/**
	 * @param id
	 * @return
	 */
	public City find(Integer id) {
		Uri uri = Uri.parse(CityContentProvider.URI_PREFIX
				+ CityContentProvider.URI + "/" + id);
		Cursor cursor = resolver.query(uri, CityColumns.PROJECTION, null, null,
				null);
		if (cursor.moveToFirst()) {
			City city = City.convert(cursor);
			cursor.close();
			return city;
		}
		return null;
	}

	/**
	 * @param pager
	 * @return
	 */
	public Pager getScrollData(Pager pager) {
		int pageSize = pager.getPageSize();
		int curPage = pager.getPageNumber();
		if (pageSize < 1) {
			pageSize = Pager.MAX_PAGE_SIZE;
			pager.setPageSize(pageSize);
		}
		if (curPage < 1) {
			curPage = 1;
			pager.setPageNumber(1);
		}
		int start = (curPage - 1) * pageSize;
		int end = pageSize;
		Uri uri = CityContentProvider.CONTENT_URI;
		String where = null;
		String[] whereArg = null;
		String name = StringUtil.nvl(pager.getKeyword());
		if ("".equals(name)) {
		} else {
			where = "name" + " like ? ";
			whereArg = new String[] { "%" + name + "%" };
		}
		List<City> citys = new ArrayList<City>();
		String orderBy = CityColumns.ID + " desc limit " + end + " offset "
				+ start;
		Cursor cursor = resolver.query(uri, CityColumns.PROJECTION, where,
				whereArg, orderBy);
		while (cursor.moveToNext()) {
			City city = City.convert(cursor);
			citys.add(city);
		}
		cursor.close();
		pager.setList(citys);
		if (name.equals("")) {
			cursor = resolver.query(uri, new String[] { "count(*)" }, null,
					null, CityColumns.ID + " desc ");
			if (cursor.moveToFirst()) {
				pager.setTotalCount(cursor.getLong(0));
			}

		} else {
			cursor = resolver.query(uri, new String[] { "count(*)" }, "name"
					+ " like ? ", new String[] { name + "%" }, CityColumns.ID
					+ " desc ");
			if (cursor.moveToFirst()) {
				pager.setTotalCount(cursor.getLong(0));
			}
		}
		cursor.close();
		return pager;
	}

}
