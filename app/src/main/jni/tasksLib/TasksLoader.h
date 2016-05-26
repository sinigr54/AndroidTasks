//
// Created by admin-pc on 05.05.16.
//

#ifndef ANDROIDTASKS_TASKSLOADER_H
#define ANDROIDTASKS_TASKSLOADER_H

#include <memory>
#include <string>
#include <vector>
#include "TaskConstructor.h"

using std::shared_ptr;
using std::string;
using std::vector;

class TasksLoader {
	string workDirectory;

public:
	const string &getWorkDirectory() const;

	TasksLoader(const string &tasksDirectory = "");

	void setTasksDirectory(const string &directoryName);

	string readTextFile(const string &fileName);

	vector<string> getAllExistsTasks();

	TaskConstructor loadTaskFromLibrary(const string &taskName) const;
};


#endif //ANDROIDTASKS_TASKSLOADER_H
