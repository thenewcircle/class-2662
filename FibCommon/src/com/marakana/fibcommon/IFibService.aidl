package com.marakana.fibcommon;

import com.marakana.fibcommon.Request;
import com.marakana.fibcommon.Response;
import com.marakana.fibcommon.ResponseListener;

interface IFibService {
	long fibJ(long n);
	long fibN(long n);
	
	Response fib(in Request request);
	
	oneway void asyncFib(in Request request, in ResponseListener listener);
}