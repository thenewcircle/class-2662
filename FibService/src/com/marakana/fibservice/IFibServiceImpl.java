package com.marakana.fibservice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;

import com.marakana.fibcommon.IFibService;
import com.marakana.fibcommon.Request;
import com.marakana.fibcommon.Response;
import com.marakana.fibcommon.ResponseListener;
import com.marakana.fibnative.FibLib;

public class IFibServiceImpl extends IFibService.Stub {
	private Context context;

	public IFibServiceImpl(Context context) {
		this.context = context;
	}

	public long fibJ(long n) throws RemoteException {
		enforcePermission();
		return FibLib.fibJ(n);
	}

	public long fibN(long n) throws RemoteException {
		return FibLib.fibN(n);
	}

	public Response fib(Request request) throws RemoteException {
		long time = System.currentTimeMillis();
		long result = -1;

		if (request.getAlgorithm() == Request.JAVA_RECURSIVE) {
			enforcePermission();
			result = FibLib.fibJ(request.getN());
		} else if (request.getAlgorithm() == Request.NATIVE_RECURSIVE) {
			result = FibLib.fibN(request.getN());
		}

		// time delta
		time = System.currentTimeMillis() - time;

		return new Response(time, result);
	}

	public void asyncFib(Request request, ResponseListener listener)
			throws RemoteException {
		listener.onResponse(fib(request));
	}

	private void enforcePermission() {
		// Check permission for slow algorithm
		if (context.checkCallingPermission("com.marakana.permission.FIB_SLOW") == PackageManager.PERMISSION_DENIED) {
			SecurityException e = new SecurityException("Not allowed to use the slow algorithm");
			Log.e("FibService", "Not allowed to use the slow algorithm", e);
			throw e;
		}
	}
}
