#include "com_marakana_fibnative_FibLib.h"

// Pure C implementation
static long fib(long n) {
	if (n == 0)
		return 0;
	if (n == 1)
		return 1;
	return fib(n - 1) + fib(n - 2);
}

static long fibI(long n) {
	long previous = -1;
	long result = 1;
	long i = 0;
	for (; i <= n; i++) {
		long sum = result + previous;
		previous = result;
		result = sum;
	}
	return result;
}
JNIEXPORT jlong JNICALL Java_com_marakana_fibnative_FibLib_fibN(JNIEnv *env,
		jclass clazz, jlong n) {
	return fib(n);
}
JNIEXPORT jlong JNICALL Java_com_marakana_fibnative_FibLib_fibNI(JNIEnv *env,
		jclass clazz, jlong n) {
	return fibI(n);
}

