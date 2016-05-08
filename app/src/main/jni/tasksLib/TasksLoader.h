//
// Created by admin-pc on 05.05.16.
//

#ifndef ANDROIDTASKS_TASKSLOADER_H
#define ANDROIDTASKS_TASKSLOADER_H

#include <android/asset_manager_jni.h>
#include <android/asset_manager.h>

#include <memory>
#include <string>
#include <vector>

using std::shared_ptr;
using std::string;
using std::vector;

class TasksLoader {
	string workDirectory;

public:
	const string &getWorkDirectory() const;

	TasksLoader();

	TasksLoader(const string &tasksDirectory);

	void setTasksDirectory(const string &directoryName);

	string readFile(const string &fileName);

	vector<string> getAllExistsTasks();
};


#endif //ANDROIDTASKS_TASKSLOADER_H
