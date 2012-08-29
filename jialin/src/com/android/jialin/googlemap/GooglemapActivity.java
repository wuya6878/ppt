package com.android.jialin.googlemap;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.android.jialin.R;
import com.android.jialin.db.CityColumns;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class GooglemapActivity extends MapActivity {
	/** Called when the activity is first created. */
	private MapView mapView;
	private MapController mapController;
	Double lng, lng1;
	Double lat, lat1;
	DecimalFormat dflat1 = new DecimalFormat("#.00");
	DecimalFormat dflng1 = new DecimalFormat("#.00");
	private String showadd;
	GeoPoint point;

	private String cityName;
	private String cityId;

	// AdressNname addressname=new AdressNname();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.googlemap);
		mapView = (MapView) findViewById(R.id.mapview);
		mapController = mapView.getController();
		Intent intent = this.getIntent();
		cityId = intent.getStringExtra(CityColumns.ID);
		cityName = intent.getStringExtra(CityColumns.NAME);
		String longitude = intent.getStringExtra(CityColumns.LONGITUDE);
		String latitude = intent.getStringExtra(CityColumns.LATITUDE);
		lat1 = Double.valueOf(latitude);
		lat = lat1 * 1e6;
		lng1 = Double.valueOf(longitude);
		lng = lng1 * 1e6;

		// Toast.makeText(this, showadd, 1).show();
		point = new GeoPoint(lat.intValue(), lng.intValue());

		mapController.setCenter(point);
		mapController.setZoom(11);
		mapController.animateTo(point);
		// set to traffic view
		// mapView.setTraffic(true);
		// set to Satellite view
		// mapView.setSatellite(true);
		// set to street view
		mapView.setStreetView(true);
		mapView.setEnabled(true);
		mapView.setClickable(true);
		// Set the map to support the scaling
		mapView.setBuiltInZoomControls(true);
		// set multiple（1-21）
		mapController.setZoom(12);
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
		List<Overlay> list = mapView.getOverlays();
		list.add(myLocationOverlay);
	}

	protected boolean isRouteDisplayed() {
		return false;
	}

	protected class MyLocationOverlay extends com.google.android.maps.Overlay {

		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			Paint paint = new Paint();
			super.draw(canvas, mapView, shadow);
			Point myScreenCoords = new Point();
			mapView.getProjection().toPixels(point, myScreenCoords);
			paint.setStrokeWidth(1);
			paint.setARGB(255, 255, 0, 0);
			paint.setStyle(Paint.Style.STROKE);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.here);

			canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
			canvas.drawText(cityName, myScreenCoords.x, myScreenCoords.y, paint);
			return true;
		}
	}
}