//
// Created by admin-pc on 05.05.16.
//

#ifndef ANDROIDTASKS_TASKCONSTRUCTOR_H
#define ANDROIDTASKS_TASKCONSTRUCTOR_H

#include <string>

using std::string;

class TaskConstructor {
	string taskText;
public:

	TaskConstructor();

	void weight();

	string getTaskText();

	void textTest(const string &text);

	void beginTest(const string &text);

	void endTest();

	void countVariantsAndType(int count, char typeAnswer);

	void variant(int trueAnswer, const string &answer, const string &cmt, const string &numpic = "");

	void addOldTask();
};


#endif //ANDROIDTASKS_TASKCONSTRUCTOR_H
