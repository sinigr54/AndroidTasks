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
	string newLine = "\n";

	std::fstream file(workDirectory + "/" + fileName);

	while (!file.eof()) {
		string line;
		getline(file, line);
		textFromFile += line + newLine;
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

	int prefixSize = 3;
	while ((dir = readdir(directory)) != nullptr) {
		if (string(dir->d_name) != "." && string(dir->d_name) != "..") {
			string taskType = dir->d_name;
			taskType = taskType.substr(prefixSize, taskType.find('.') - prefixSize);
			tasks.push_back(taskType);
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

bool isHaveDlError(const void *handle) {
	if (handle == nullptr) {
		__android_log_print(ANDROID_LOG_ERROR, "TASK_LOADER", dlerror());
		return true;
	}
	__android_log_print(ANDROID_LOG_ERROR, "TASK_LOADER", "Library has been loaded");
	return false;
}

TaskConstructor TasksLoader::loadTaskFromLibrary(const string &taskType, int taskNumber) const {
	string functionCreate = "createTest";
	string functionDestroy = "destroyTest";

	string taskLibPrefix = "/lib";
	string taskLibPostfix = ".so";

	string taskFile = workDirectory + taskLibPrefix + taskType + taskLibPostfix;
	void *handle = dlopen(taskFile.c_str(), RTLD_LAZY);
	if (isHaveDlError(handle))
		throw TestLoadException("Test library can't dynamically load!");

	using CreateType = Test *(TaskConstructor &);
	using DestroyType = void(Test *);

	std::function<CreateType> createTestFunc;
	std::function<DestroyType> destroyTestFunc;

	createTestFunc = (Test *(*)(TaskConstructor &)) dlsym(handle, functionCreate.c_str());
	destroyTestFunc = (void (*)(Test *test)) dlsym(handle, functionDestroy.c_str());

	TaskConstructor constructor;
	Test *test = createTestFunc(constructor);

	test->constructTask(taskNumber);
	constructor = test->getCompleteTest();

	destroyTestFunc(test);
	dlclose(handle);

	return constructor;
}
