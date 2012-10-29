#include <jni.h>
#include <android/log.h>

namespace com_marakana_fibnative {

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
		for (long i = 0; i <= n; i++) {
			long sum = result + previous;
			previous = result;
			result = sum;
		}
		return result;
	}

	// JNI wrapper
	static jlong fibN(JNIEnv *env, jclass clazz, jlong n) {
		return fib(n);
	}
	static jlong fibNI(JNIEnv *env, jclass clazz, jlong n) {
		return fibI(n);
	}
	static void version(JNIEnv *env, jclass clazz, jstring version) {
		const char *cVersion = env->GetStringUTFChars(version, 0); /*  */
		if (cVersion == 0) {
			return; /* OutOfMemoryError already thrown */
		} else {
			__android_log_print(ANDROID_LOG_DEBUG, "FibLib", "Version: %s", cVersion);
			env->ReleaseStringUTFChars(version, cVersion); /*  */
		}
	}

	static JNINativeMethod method_table[] = {
			{ "fibN", "(J)J", (void *) fibN },
			{ "fibNI", "(J)J", (void *) fibNI },
			{ "version", "(Ljava/lang/String;)V", (void *) version }
	};
}

using namespace com_marakana_fibnative;

// Boilerplate JNI_OnLoad
extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	JNIEnv* env;
	if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
		return -1;
	} else {
		jclass clazz = env->FindClass("com/marakana/fibnative/FibLib");
		if (clazz) {
			env->RegisterNatives(clazz, method_table,
					sizeof(method_table) / sizeof(method_table[0]));
			env->DeleteLocalRef(clazz);
			return JNI_VERSION_1_6;
		} else {
			return -1;
		}
	}
}
