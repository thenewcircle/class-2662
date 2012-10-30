package com.marakana.fibclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.marakana.fibcommon.IFibService;
import com.marakana.fibcommon.Request;
import com.marakana.fibcommon.Response;
import com.marakana.fibcommon.ResponseListener;

public class MainActivity extends Activity {
	private static final Intent IFIB_SERVICE_INTENT = new Intent(
			"com.marakana.fibcommon.IFibService");
	private EditText input;
	private static TextView output;
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
		service.asyncFib(javaReqursive, LISTENER);
	}

	private static final int WHAT = 47;
	
	private static final ResponseListener LISTENER = new ResponseListener.Stub() {
		public void onResponse(Response response) throws RemoteException {
			Message msg = HANDLER.obtainMessage(WHAT);
			msg.obj = response;
			HANDLER.sendMessage(msg);
		}
	};

	private static final Handler HANDLER = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what!=WHAT) return; // assert
			Response response = (Response)msg.obj;
			output.append(String.format("\nfibJ()=%d (%d ms)",
					response.getResult(), response.getTime()));
		}
	};
}
