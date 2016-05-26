package com.example.admin_pc.androidtasks.Tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TasksManager {
	static {
		System.loadLibrary("tasks");
	}

	private static TasksManager manager = new TasksManager();

	private TasksManager() {

	}

	public static TasksManager getTaskManager() {
		return manager;
	}

	public static TasksManager getTaskManager(String workDirectory) {
		manager.setWorkDirectory(workDirectory);
		return  manager;
	}

	/*
		Directory for all task libraries
	*/
	private String workDirectory;

	public String getWorkDirectory() {
		return workDirectory;
	}

	public void setWorkDirectory(String workDirectory) {
		this.workDirectory = workDirectory;
		setLoaderWorkDirectory(workDirectory);
	}

	public List<String> allTasks() {
		String[] tasks = allTaskFromNative();
		List<String> list = new ArrayList<>();
		Collections.addAll(list, tasks);

		return list;
	}

	private native void setLoaderWorkDirectory(String directory);

	private native String[] allTaskFromNative();
}
