package com.example.admin_pc.androidtasks.Tasks;

public class TasksManager {
	static {
		System.loadLibrary("tasks");
	}

	/*
		Directory for all task libraries
	*/
	private String workDirectory;

	public TasksManager(String workDirectory) {
		this.workDirectory = workDirectory;
	}

	public String getWorkDirectory() {
		return workDirectory;
	}

	public void setWorkDirectory(String workDirectory) {
		this.workDirectory = workDirectory;
	}
}
