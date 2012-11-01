package com.marakana.android.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String PROVIDER = LocationManager.GPS_PROVIDER;
	private static final long MIN_TIME = 10000; // milliseconds
	private static final float MIN_DISTANCE = 10; // meters
	private TextView locationOutput, sensorOutput;
	private LocationManager locationMmanager;
	private SensorManager sensorManager;
	private Sensor sensor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		locationOutput = (TextView) findViewById(R.id.location_output);
		sensorOutput = (TextView) findViewById(R.id.sensor_output);

		locationMmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Location location = locationMmanager.getLastKnownLocation(PROVIDER);
		updateLocation(location);

		// Sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

	}

	@Override
	protected void onResume() {
		super.onResume();
		locationMmanager.requestLocationUpdates(PROVIDER, MIN_TIME,
				MIN_DISTANCE, locationListener);
		sensorManager.registerListener(sensorListener, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationMmanager.removeUpdates(locationListener);
	}

	private final LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			updateLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	};

	/** Updates the UI with new location. */
	private void updateLocation(Location location) {

		// Check if location is valid
		if (location == null) {
			locationOutput.setText("Location is not known");
			return;
		}

		locationOutput.setText(String.format("Lat: %.2f\nLong: %.2f",
				location.getLatitude(), location.getLongitude()));

		if (location.hasAltitude())
			locationOutput.append(String.format("\nAlt: %.2f",
					location.getAltitude()));

		locationOutput.append(String.format("\n%s via %s", DateUtils
				.getRelativeTimeSpanString(location.getTime()).toString(),
				PROVIDER));
	}

	private SensorEventListener sensorListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			updateSensorEvent(event);
		}
	};

	private void updateSensorEvent(SensorEvent event) {
		if (event == null) {
			sensorOutput.setText("No sensor data yet...");
			return;
		}

		sensorOutput.setTag(String.format("Azimuth: %s\nPitch: %s\nRoll: %s",
				event.values[0], event.values[0], event.values[0]));
	}
}
