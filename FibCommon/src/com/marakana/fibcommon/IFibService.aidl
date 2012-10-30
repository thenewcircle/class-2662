package com.marakana.fibcommon;

import com.marakana.fibcommon.Request;
import com.marakana.fibcommon.Response;

interface IFibService {
	long fibJ(long n);
	long fibN(long n);
	
	Response fib(in Request request);
}