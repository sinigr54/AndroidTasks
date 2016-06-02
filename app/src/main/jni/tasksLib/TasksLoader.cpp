//
// Created by admin-pc on 05.05.16.
//

#include "TasksLoader.h"
#include <fstream>
#include <iostream>
#include <dirent.h>
#include <android/log.h>
#include <dlfcn.h>
#include <sstream>
#include "exceptions/TestLoadException.h"

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
	TaskConstructor constructor;

	std::stringstream stream;
	stream << taskNumber;
	string functionName("task");

	string taskFile = workDirectory + "/lib" + taskType + ".so";
	void *handle = dlopen(taskFile.c_str(), RTLD_LAZY);
	if (handle == nullptr) {
		__android_log_print(ANDROID_LOG_ERROR, "TASK_LOADER", dlerror());
		throw TestLoadException("Test library can't dynamically load!");
	} else {
		__android_log_print(ANDROID_LOG_ERROR, "TASK_LOADER", "All rights");
	}

	char* (*loadTaskText)();
	loadTaskText = (char *(*)()) dlsym(handle, (functionName + stream.str()).c_str());

	constructor.setTaskText(loadTaskText());

	dlclose(handle);

	return constructor;
}
