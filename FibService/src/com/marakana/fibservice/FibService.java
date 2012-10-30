package com.marakana.fibservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FibService extends Service {
	private static IFibServiceImpl serviceImpl=null;  
	
	@Override
	public IBinder onBind(Intent intent) {
		if(serviceImpl==null) {
			serviceImpl = new IFibServiceImpl();
		}
		return serviceImpl;
	}

}
