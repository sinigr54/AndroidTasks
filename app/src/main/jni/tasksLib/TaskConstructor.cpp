//
// Created by admin-pc on 05.05.16.
//

#include "TaskConstructor.h"

TaskConstructor::TaskConstructor(): taskText(""), taskType(""), taskName("") {

}

void TaskConstructor::weight() {

}

string TaskConstructor::getTaskText() {
	return taskText;
}

void TaskConstructor::textTest(const string &text) {
	taskText += text + "\n";
}

void TaskConstructor::beginTest(const string &text) {
	taskText += text + "\n";
}

void TaskConstructor::endTest() {

}

void TaskConstructor::countVariantsAndType(int count, char typeAnswer) {

}

void TaskConstructor::variant(int trueAnswer, const string &answer, const string &cmt, const string &numpic) {

}

void TaskConstructor::addOldTask() {

}

string TaskConstructor::getTaskType() {
	return taskType;
}

string TaskConstructor::getTaskName() {
	return taskName;
}

void TaskConstructor::setTaskType(const string &taskType) {
	this->taskType = taskType;
}

void TaskConstructor::setTaskName(const string &taskName) {
	this->taskName = taskName;
}
