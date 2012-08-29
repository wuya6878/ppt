package com.android.jialin.sync;

import java.io.Serializable;

import android.content.ContentValues;
import android.database.Cursor;

import com.android.jialin.db.CityColumns;
import com.android.jialin.util.StringUtil;

public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -41209166676316404L;

	private int _id;

	private String name;
	private String longitude;
	private String latitude;

	private String imageUrl;
	private String population;
	private String postcode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return this._id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public static City convert(Cursor cursor) {
		City vo = null;
		if (cursor != null) {
			vo = new City();
			vo.setId(cursor.getInt(CityColumns._ID_COLUMN));
			vo.setName(StringUtil.nvl(cursor.getString(CityColumns.NAME_COLUMN)));
			vo.setLongitude(StringUtil.nvl(cursor
					.getString(CityColumns.LONGITUDE_COLUMN)));
			vo.setLatitude(StringUtil.nvl(cursor
					.getString(CityColumns.LATITUDE_COLUMN)));
			vo.setPopulation(StringUtil.nvl(cursor
					.getString(CityColumns.POPULATION_COLUMN)));
			vo.setPostcode(StringUtil.nvl(cursor
					.getString(CityColumns.POSTCODE_COLUMN)));
		}
		return vo;
	}

	public ContentValues convert() {
		ContentValues values = new ContentValues();
		values.put(CityColumns.ID, this.getId());
		values.put(CityColumns.NAME, this.getName());
		values.put(CityColumns.LONGITUDE, this.getLongitude());
		values.put(CityColumns.LATITUDE, this.getLatitude());
		values.put(CityColumns.POPULATION, this.getPopulation());
		values.put(CityColumns.POSTCODE, this.getPostcode());
		return values;
	}

}
