package com.marakana.fibcommon;

import android.os.Parcel;
import android.os.Parcelable;

public class Request implements Parcelable {
	public static final int JAVA_RECURSIVE = 1;
	public static final int NATIVE_RECURSIVE = 2;

	// Member variables
	private int algorithm;
	private long n;

	// --- Parcelable-specific methods ---
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(algorithm);
		dest.writeLong(n);
	}

	public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {

		public Request createFromParcel(Parcel source) {
			return new Request(source.readInt(), source.readLong());
		}

		public Request[] newArray(int size) {
			return new Request[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	// --- standard pojo methods ---
	public Request(int algorithm, long n) {
		super();
		this.algorithm = algorithm;
		this.n = n;
	}

	public int getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(int algorithm) {
		this.algorithm = algorithm;
	}

	public long getN() {
		return n;
	}

	public void setN(long n) {
		this.n = n;
	}

}
