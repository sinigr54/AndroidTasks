//
// Created by admin-pc on 05.05.16.
//

#include "TasksLoader.h"
#include <fstream>
#include <iostream>
#include <dirent.h>
#include <android/log.h>
#include <dlfcn.h>
#include "exceptions/TestLoadException.h"
#include "testInclude/test.h"

TasksLoader::TasksLoader(const string &tasksDirectory) : workDirectory(tasksDirectory) {

}

string TasksLoader::readTextFile(const string &fileName) {
	string textFromFile = "";
	std::fstream file(workDirectory + "/" + fileName);

	while (!file.eof()) {
		string line;
		getline(file, line);
		textFromFile += line + "\n";
	}

	file.close();

	textFromFile.resize(textFromFile.size() - 1);
	return textFromFile;
}

vector<string> TasksLoader::getAllExistsTasks() {
	vector<string> tasks;

	DIR *directory;
	class dirent *dir;

	if ((directory = (opendir(workDirectory.c_str()))) == nullptr) {
		__android_log_print(ANDROID_LOG_ERROR, "TASK_LOADER", "Dir has not been opened");
		return tasks;
	}

	while ((dir = readdir(directory)) != nullptr) {
		if (string(dir->d_name) != "." && string(dir->d_name) != "..") {
			string taskType = dir->d_name;
			tasks.push_back(taskType.substr(0, taskType.find('.')));
		}
	}

	closedir(directory);

	return tasks;
}

void TasksLoader::setTasksDirectory(const string &directoryName) {
	workDirectory = directoryName;
}

const string &TasksLoader::getWorkDirectory() const {
	return workDirectory;
}

TaskConstructor TasksLoader::loadTaskFromLibrary(const string &taskType, int taskNumber) const {
	string functionCreate = "createTest";
	string functionDestroy = "destroyTest";

	string taskFile = workDirectory + "/lib" + taskType + ".so";
	void *handle = dlopen(taskFile.c_str(), RTLD_LAZY);
	if (handle == nullptr) {
		__android_log_print(ANDROID_LOG_ERROR, "TASK_LOADER", dlerror());
		throw TestLoadException("Test library can't dynamically load!");
	} else {
		__android_log_print(ANDROID_LOG_ERROR, "TASK_LOADER", "All rights");
	}

	Test *(*createTestFunc)(const TaskConstructor &constructor);
	void (*destroyTestFunc)(Test *test);

	createTestFunc = (Test *(*)(const TaskConstructor &constructor)) dlsym(handle, functionCreate.c_str());
	destroyTestFunc = (void (*)(Test *)) dlsym(handle, functionDestroy.c_str());

	TaskConstructor constructor;
	Test *test = createTestFunc(constructor);

	constructor = test->constructTask(taskNumber);
	int count = test->countOfTests();
	constructor.setTaskName(test->topic());

	destroyTestFunc(test);

	dlclose(handle);

	return constructor;
}
