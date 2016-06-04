package com.example.admin_pc.androidtasks.Tasks;

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

	public String[] allTasks() {
		String[] tasks = allTaskFromNative();
		return tasks;
	}

	public Task loadTask(String type, int number) {
		Task task = new Task();
		loadTask(task, type, number);

		return task;
	}

	private native void setLoaderWorkDirectory(String directory);

	private native void loadTask(Task task, String type, int number);

	private native String[] allTaskFromNative();
}
