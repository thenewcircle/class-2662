package com.marakana.fibservice;

import android.os.RemoteException;

import com.marakana.fibcommon.IFibService;
import com.marakana.fibcommon.Request;
import com.marakana.fibcommon.Response;
import com.marakana.fibnative.FibLib;

public class IFibServiceImpl extends IFibService.Stub {

	public long fibJ(long n) throws RemoteException {
		return FibLib.fibJ(n);
	}

	public long fibN(long n) throws RemoteException {
		return FibLib.fibN(n);
	}

	public Response fib(Request request) throws RemoteException {
		long time = System.currentTimeMillis();
		long result = -1;

		if (request.getAlgorithm() == Request.JAVA_RECURSIVE) {
			result = FibLib.fibJ(request.getN());
		} else if (request.getAlgorithm() == Request.NATIVE_RECURSIVE) {
			result = FibLib.fibN(request.getN());
		}
		
		// time delta
		time = System.currentTimeMillis() - time;

		return new Response(time, result);
	}

}
