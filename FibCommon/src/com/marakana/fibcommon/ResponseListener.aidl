package com.marakana.fibcommon;

import com.marakana.fibcommon.Response;

oneway interface ResponseListener {
	void onResponse( in Response response );
}