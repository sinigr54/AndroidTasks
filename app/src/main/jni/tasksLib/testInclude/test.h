//
// Created by admin-pc on 02.06.16.
//

#ifndef ANDROIDTASKS_TEST_H
#define ANDROIDTASKS_TEST_H

#include <string>
#include "../../../../../../distribution/taskconstructor/include/TaskConstructor.h"
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

	virtual void initialize();

	static string topic();

public:

	Test(const TaskConstructor &);

	virtual ~Test();

	virtual void constructTask(int number);

	virtual TaskConstructor getCompleteTest();

	virtual int countOfTests();
};

extern "C" Test *createTest(const TaskConstructor &constructor);

extern "C" void destroyTest(Test *test);

#endif //ANDROIDTASKS_TEST_H
