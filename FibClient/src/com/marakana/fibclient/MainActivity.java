package com.marakana.fibclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.marakana.fibcommon.IFibService;
import com.marakana.fibcommon.Request;
import com.marakana.fibcommon.Response;

public class MainActivity extends Activity {
	private static final Intent IFIB_SERVICE_INTENT = new Intent(
			"com.marakana.fibcommon.IFibService");
	private EditText input;
	private TextView output;
	private IFibService service;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);

		bindService(IFIB_SERVICE_INTENT, SERVICE_CONN, BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(SERVICE_CONN);
	}

	ServiceConnection SERVICE_CONN = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder binder) {
			service = IFibService.Stub.asInterface(binder);
		}

		public void onServiceDisconnected(ComponentName name) {
			service = null;
		}
	};

	public void onClickGo(View v) throws RemoteException {
		// Assert
		if (service == null)
			return;

		long n = Long.parseLong(input.getText().toString());

		Request javaReqursive = new Request(Request.JAVA_RECURSIVE, n);
		Response responseJ = service.fib(javaReqursive);
		output.append(String.format("\nfibJI(%d)=%d (%d ms)", n,
				responseJ.getResult(), responseJ.getTime()));

		Request nativeReqursive = new Request(Request.NATIVE_RECURSIVE, n);
		Response responseN = service.fib(nativeReqursive);
		output.append(String.format("\nfibNI(%d)=%d (%d ms)", n,
				responseN.getResult(), responseN.getTime()));

	}
}
