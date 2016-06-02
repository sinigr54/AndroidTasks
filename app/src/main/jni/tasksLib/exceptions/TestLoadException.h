//
// Created by admin-pc on 27.05.16.
//

#ifndef ANDROIDTASKS_TESTLOADEXCEPTION_H
#define ANDROIDTASKS_TESTLOADEXCEPTION_H

#include <exception>
#include <string>

using std::exception;
using std::string;

class TestLoadException: public exception {
	string whatString;

public:
	TestLoadException(const string &what);

	const char* what() const noexcept override;
};


#endif //ANDROIDTASKS_TESTLOADEXCEPTION_H
