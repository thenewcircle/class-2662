package com.marakana.fibcommon;

import com.marakana.fibcommon.Request;

interface IFibService {
	long fibJ(long n);
	long fibN(long n);
	
	long fib(in Request request);
}