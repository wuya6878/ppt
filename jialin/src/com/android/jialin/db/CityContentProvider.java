/**
 * 
 */
package com.android.jialin.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * @author Administrator
 * 
 */
public class CityContentProvider extends ContentProvider {

	private DBOpenHelper dbHelper;

	private SQLiteDatabase db;

	public static final String AUTHORITY = "com.android.jialin.db";

	public static final String URI_PREFIX = "content://" + AUTHORITY + "/";

	public static final Uri CONTENT_URI = Uri.parse(URI_PREFIX + "city");

	public static final String URI = "city";

	public static final String ITEM_URI = URI + "/#";// _ID OR NAME

	public static final int PERSON = 1;

	public static final int PERSON_ITEM = 2;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, URI, PERSON);
		uriMatcher.addURI(AUTHORITY, ITEM_URI, PERSON_ITEM);
	}

	public CityContentProvider() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#onCreate()
	 */
	public boolean onCreate() {
		dbHelper = new DBOpenHelper(getContext());
		db = dbHelper.getWritableDatabase();
		return (db == null) ? false : true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#delete(android.net.Uri,
	 * java.lang.String, java.lang.String[])
	 */
	public int delete(Uri uri, String where, String[] selectionArgs) {
		int count;
		int v = uriMatcher.match(uri);
		switch (v) {
		case PERSON:
			count = db.delete("city", where, selectionArgs);
			break;
		case PERSON_ITEM:
			String id = uri.getPathSegments().get(1);
			count = db.delete("city", CityColumns.ID + "=" + id, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#insert(android.net.Uri,
	 * android.content.ContentValues)
	 */
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (uriMatcher.match(uri) != PERSON) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		if (values.containsKey("name") == false) {
			values.put("name", "");
		}

		if (values.containsKey("latitude") == false) {
			values.put("latitude", "");
		}
		if (values.containsKey("longitude") == false) {
			values.put("longitude", "");
		}
		values.remove(CityColumns.ID);
		long rowId = db.insert("city", null, values);
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		} else {
			throw new SQLException("Failed to insert row into " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String where = "1=1 ";
		String tableName = "city";
		int t = uriMatcher.match(uri);
		switch (t) {
		case PERSON_ITEM:
			where = CityColumns.ID + "=" + uri.getPathSegments().get(1);
			break;
		}

		if (null != selection && selection.trim().length() > 0) {
			where = where + " and " + selection;
		}
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = CityColumns.ID;
		} else {
			orderBy = sortOrder;
		}
		Cursor c = db.query(tableName, projection, where, selectionArgs, null,
				null, orderBy);
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#update(android.net.Uri,
	 * android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	public int update(Uri uri, ContentValues values, String where,
			String[] selectionArgs) {

		int count;
		switch (uriMatcher.match(uri)) {
		case PERSON:
			count = db.update("city", values, where, selectionArgs);
			break;
		case PERSON_ITEM:
			String id = uri.getPathSegments().get(1);
			count = db.update("city", values, CityColumns.ID + "=" + id,
					selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case PERSON:
			return "vnd.android.cursor.dir/vnd.ambow.mjp_person";
		case PERSON_ITEM:
			return "vnd.android.cursor.item/vnd.ambow.mjp_person";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

}
