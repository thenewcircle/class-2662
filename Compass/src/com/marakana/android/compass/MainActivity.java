package com.marakana.android.compass;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.GpsStatus;
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
	private LocationManager locationManager;
	private Geocoder geocoder;
	private GpsStatus gpsStatus;
	private SensorManager sensorManager;
	private Sensor sensor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		locationOutput = (TextView) findViewById(R.id.location_output);
		sensorOutput = (TextView) findViewById(R.id.sensor_output);

		geocoder = new Geocoder(this);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(PROVIDER);
		updateLocation(location);

		// Sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(PROVIDER, MIN_TIME,
				MIN_DISTANCE, locationListener);
		locationManager.addGpsStatusListener(gpsListener);
		sensorManager.registerListener(sensorListener, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
		locationManager.removeGpsStatusListener(gpsListener);
		sensorManager.unregisterListener(sensorListener);
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

		locationOutput.setText("");

		// Geocode the location
		if (Geocoder.isPresent()) {
			List<Address> addresses;
			try {
				addresses = geocoder.getFromLocation(location.getLatitude(),
						location.getLongitude(), 1);
				if (!addresses.isEmpty()) {
					Address address = addresses.get(0);
					locationOutput.append(String.format("\n%s\n%s\n%s, %s",
							address.getAddressLine(0),
							address.getAddressLine(1), address.getLocality(),
							address.getAdminArea()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Update the location
		locationOutput.append(String.format("\nLat: %.2f Long: %.2f",
				location.getLatitude(), location.getLongitude()));

		if (location.hasAltitude())
			locationOutput.append(String.format(" Alt: %.2f",
					location.getAltitude()));

		locationOutput.append(String.format("\n%s via %s", DateUtils
				.getRelativeTimeSpanString(location.getTime()).toString(),
				PROVIDER));

	}

	private GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
		@Override
		public void onGpsStatusChanged(int event) {
			gpsStatus = locationManager.getGpsStatus(null);
			((TextView) findViewById(R.id.satellites_output))
					.setText("\nSatellites time-to-first-fix: "
							+ gpsStatus.getTimeToFirstFix() + "ms");
		}
	};

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

		sensorOutput.setText(String.format(
				"Azimuth: %.2f\nPitch: %.2f\nRoll: %.2f", event.values[0],
				event.values[0], event.values[0]));
	}
}
