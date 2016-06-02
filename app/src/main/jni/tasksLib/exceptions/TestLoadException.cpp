//
// Created by admin-pc on 27.05.16.
//

#include "TestLoadException.h"

TestLoadException::TestLoadException(const string &what):whatString(what) {

}

const char *TestLoadException::what() const noexcept {
	return whatString.c_str();
}
