#include <jni.h>
#include "TaskConstructor.h"
#include "TasksLoader.h"

using std::string;

/*
 * Объект, осуществляющий работу с каталогом заданий
 * и их динамическую загрузку из библиотек
 */
static TasksLoader loader;

extern "C" {

/*
 * Устанавливает рабочий каталог, гду будут храниться все тесты
 */
JNIEXPORT void JNICALL
Java_com_example_admin_1pc_androidtasks_Tasks_TasksManager_setLoaderWorkDirectory(JNIEnv *env,
                                                                                  jobject instance,
                                                                                  jstring directory_) {
	string directory = env->GetStringUTFChars(directory_, 0);
	loader.setTasksDirectory(directory);
}

/*
 * Возвращает список всех доступных заданий
 */
JNIEXPORT jobjectArray JNICALL
Java_com_example_admin_1pc_androidtasks_Tasks_TasksManager_allTaskFromNative(JNIEnv *env,
                                                                             jobject instance) {
	jobjectArray result;

	auto names = loader.getAllExistsTasks();
	result = (jobjectArray) env->NewObjectArray((jsize) names.size(), env->FindClass("java/lang/String"),
	                                            env->NewStringUTF(""));
	for (int i = 0; i < names.size(); ++i) {
		env->SetObjectArrayElement(result, i, env->NewStringUTF(names[i].c_str()));
	}

	return result;
}

// Тест работы NDK
JNIEXPORT jstring JNICALL
Java_com_example_admin_1pc_androidtasks_MainActivity_helloFromLibrary(JNIEnv *env, jobject instance,
                                                                      jstring tasksPath_) {
	string tasksPath = env->GetStringUTFChars(tasksPath_, 0);
	loader.setTasksDirectory(tasksPath + "/fileDir");

	string text = loader.readFile("file.txt");

	return env->NewStringUTF(text.c_str());
}

}