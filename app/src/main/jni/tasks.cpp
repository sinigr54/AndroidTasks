#include <jni.h>
#include "TaskConstructor.h"
#include "TasksLoader.h"

using std::string;

TasksLoader loader;

extern "C" {

// Возвращает имена всех файлов из рабочей папки
JNIEXPORT jobjectArray JNICALL
Java_com_example_admin_1pc_androidtasks_MainActivity_getAllTaskNames(JNIEnv *env, jobject instance) {
	jobjectArray result;

	auto names = loader.getAllExistsTasks();
	result = (jobjectArray) env->NewObjectArray(names.size(), env->FindClass("java/lang/String"),
	                                            env->NewStringUTF(""));
	for (int i = 0; i < names.size(); ++i) {
		env->SetObjectArrayElement(result, i, env->NewStringUTF(names[i].c_str()));
	}

	return result;
}

JNIEXPORT jstring JNICALL
Java_com_example_admin_1pc_androidtasks_MainActivity_getTaskName(JNIEnv *env, jobject instance,
                                                                 jobject manager) {
	/*auto assetManager = AAssetManager_fromJava(env, manager);
	TasksLoader loader(assetManager);

	string textFile;
	textFile = loader.readFile("file1.txt");

	return env->NewStringUTF(textFile.c_str());*/
}

JNIEXPORT jstring JNICALL
Java_com_example_admin_1pc_androidtasks_MainActivity_helloFromLibrary(JNIEnv *env, jobject instance,
                                                                      jstring tasksPath_) {
	string tasksPath = env->GetStringUTFChars(tasksPath_, 0);
	loader.setTasksDirectory(tasksPath + "/fileDir");

	string text = loader.readFile("file.txt");

	return env->NewStringUTF(text.c_str());
}

}