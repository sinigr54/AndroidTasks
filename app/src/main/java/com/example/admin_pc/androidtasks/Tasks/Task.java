package com.example.admin_pc.androidtasks.Tasks;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
	private String type;
	private String name;
	private String text;

	public Task() {

	}

	protected Task(Parcel in) {
		type = in.readString();
		name = in.readString();
		text = in.readString();
	}

	public static final Creator<Task> CREATOR = new Creator<Task>() {
		@Override
		public Task createFromParcel(Parcel in) {
			return new Task(in);
		}

		@Override
		public Task[] newArray(int size) {
			return new Task[size];
		}
	};

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(type);
		parcel.writeString(name);
		parcel.writeString(text);
	}
}
