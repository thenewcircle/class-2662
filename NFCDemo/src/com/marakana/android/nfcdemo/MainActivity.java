package com.marakana.android.nfcdemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "NFCDemo";
	private TextView output;
	private EditText input;
	private NfcManager manager;
	private NfcAdapter adapter;
	private PendingIntent pendingIntent;
	private IntentFilter ndef;
	private IntentFilter[] intentFiltersArray;
	private String[][] techListsArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// UI setup
		setContentView(R.layout.activity_main);
		output = (TextView) findViewById(R.id.output);
		input = (EditText) findViewById(R.id.input);

		// Setup NFC Manager and Adapter
		manager = (NfcManager) super.getSystemService(Context.NFC_SERVICE);
		adapter = manager.getDefaultAdapter();

		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			// Handles all MIME based dispatches.
			// You should specify only the ones that you need.
			ndef.addDataType("*/*");
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("fail", e);
		}
		intentFiltersArray = new IntentFilter[] { ndef };
		techListsArray = new String[][] { new String[] { NfcF.class.getName() } };
		
		Log.d(TAG, "onCreated");
	}
	
	public void onResume() {
		super.onResume();
		adapter.enableForegroundDispatch(this, pendingIntent,
				null, null);
		Log.d(TAG, "onResumed");
	}
	
	public void onPause() {
	    super.onPause();
	    adapter.disableForegroundDispatch(this);
	    Log.d(TAG, "onPaused");
	}

	public void onNewIntent(Intent intent) {
	    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	    
	    Log.d(TAG, "onNewIntent tag: "+tag.describeContents());
	    
	    // Do something with tagFromIntent
	    output.append("\n\n"+tag.describeContents());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
