//
// Created by admin-pc on 05.05.16.
//

#include "TasksLoader.h"
#include <fstream>
#include <iostream>
#include <dirent.h>
#include <android/log.h>

TasksLoader::TasksLoader(const string &tasksDirectory) : workDirectory(tasksDirectory) {

}

string TasksLoader::readFile(const string &fileName) {
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
		if (string(dir->d_name) != "." && string(dir->d_name) != "..")
			tasks.push_back(string(dir->d_name));
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
