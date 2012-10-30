package com.marakana.fibcommon;

import android.os.Parcel;
import android.os.Parcelable;

public class Response implements Parcelable {

	// Member variables
	private long time;
	private long result;

	// --- Parcelable-specific methods ---
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(time);
		dest.writeLong(result);
	}

	public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {

		public Response createFromParcel(Parcel source) {
			return new Response(source.readLong(), source.readLong());
		}

		public Response[] newArray(int size) {
			return new Response[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	// --- standard pojo methods ---
	public Response(long time, long result) {
		super();
		this.time = time;
		this.result = result;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getResult() {
		return result;
	}

	public void setResult(long result) {
		this.result = result;
	}
}
