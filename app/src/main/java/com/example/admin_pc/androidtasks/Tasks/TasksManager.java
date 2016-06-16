package com.example.admin_pc.androidtasks.Tasks;

import java.util.ArrayList;
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
		return manager;
	}

	/*
		Directory for all task libraries
	*/
	private String workDirectory;
	private String loadedType;
	private List<Task> loadedTasks;

	public String getWorkDirectory() {
		return workDirectory;
	}

	public void setWorkDirectory(String workDirectory) {
		this.workDirectory = workDirectory;
		setLoaderWorkDirectory(workDirectory);
	}

	public String[] allTasks() {
		String[] tasks = allTaskFromNative();
		return tasks;
	}

	public List<Task> getTasks(String type) {
		if (loadedType != null && loadedType.equals(type)) {
			return loadedTasks;
		} else {
			loadedType = type;
			loadedTasks = loadAllTasks(loadedType);
			return loadedTasks;
		}
	}

	private Task loadTask(String type, int number) {
		Task task = new Task();
		loadTask(task, type, number);

		return task;
	}

	private List<Task> loadAllTasks(String type) {
		List<Task> result = new ArrayList<>();

		int count = countTasksInTest(type);
		for (int i = 1; i <= count; ++i) {
			Task task = new Task();
			loadTask(task, type, i);
			result.add(task);
		}

		return result;
	}

	private native int countTasksInTest(String type);

	private native void setLoaderWorkDirectory(String directory);

	private native void loadTask(Task task, String type, int number);

	private native String[] allTaskFromNative();
}
