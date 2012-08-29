/**
 * 
 */
package com.android.jialin.db;

import android.provider.BaseColumns;

/**
 * @author Administrator
 * 
 */
public class CityColumns implements BaseColumns {

	/** column names **/
	public static final String NAME = "name";
	public static final String ID = "_id";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String POPULATION = "population";
	public static final String POSTCODE = "postcode";

	/** column names index **/
	public static final int _ID_COLUMN = 0;
	public static final int NAME_COLUMN = 1;
	public static final int LATITUDE_COLUMN = 2;
	public static final int LONGITUDE_COLUMN = 3;
	public static final int POPULATION_COLUMN = 4;
	public static final int POSTCODE_COLUMN = 5;

	/** column names order array **/
	public static final String[] PROJECTION = { _ID, NAME, LATITUDE, LONGITUDE,POPULATION,POSTCODE };
}
