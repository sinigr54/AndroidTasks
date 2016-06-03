//
// Created by admin-pc on 02.06.16.
//

#ifndef ANDROIDTASKS_TEST_H
#define ANDROIDTASKS_TEST_H

#include <string>
#include "TaskConstructor.h"
#include <vector>
#include <functional>

using std::string;
using std::vector;
using std::function;

class Test {
	TaskConstructor constructor;

	using TaskPointer = function<void(TaskConstructor &constructor)>;
	vector<TaskPointer> tasks;

	static void task1(TaskConstructor &constructor);

	void initialize();

public:

	Test(const TaskConstructor &constructor_);

	virtual ~Test();

	virtual TaskConstructor constructTask(int number);

	virtual string topic();

	virtual int countOfTests();
};

extern "C" Test *createTest(const TaskConstructor &constructor);

extern "C" void destroyTest(Test *test);

#endif //ANDROIDTASKS_TEST_H
