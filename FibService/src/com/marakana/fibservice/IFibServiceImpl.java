package com.marakana.fibservice;

import android.os.RemoteException;
import com.marakana.fibcommon.IFibService;
import com.marakana.fibcommon.Request;
import com.marakana.fibnative.FibLib;

public class IFibServiceImpl extends IFibService.Stub {

	public long fibJ(long n) throws RemoteException {
		return FibLib.fibJ(n);
	}

	public long fibN(long n) throws RemoteException {
		return FibLib.fibN(n);
	}

	public long fib(Request request) throws RemoteException {
		if (request.getAlgorithm() == Request.JAVA_RECURSIVE) {
			return FibLib.fibJ(request.getN());
		} else if (request.getAlgorithm() == Request.NATIVE_RECURSIVE) {
			return FibLib.fibN(request.getN());
		} else {
			return -1;
		}

	}

}
