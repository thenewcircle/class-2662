package com.marakana.android.stream;

import android.provider.BaseColumns;

public class StreamContract {

	/** Column names. */
	public static final class Columns implements BaseColumns {
		public static final String TITLE = "title";
		public static final String LINK = "link";
		public static final String AUTHOR = "author";
		public static final String PUB_DATE = "pub_date";
		public static final String CATEGORY = "category";
		public static final String DESCRIPTION = "description";
	}
}
