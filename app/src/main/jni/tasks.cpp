#include <jni.h>
#include "TaskConstructor.h"
#include "TasksLoader.h"
#include "exceptions/TestLoadException.h"
#include <android/log.h>

using std::string;

/*
 * Объект, осуществляющий работу с каталогом заданий
 * и их динамическую загрузку из библиотек
 */
static TasksLoader loader;

extern "C" {

/*
 * Загружает задание указанного типа под указанным номеров в объект Task
 */
JNIEXPORT void JNICALL
Java_com_example_admin_1pc_androidtasks_Tasks_TasksManager_loadTask__Lcom_example_admin_1pc_androidtasks_Tasks_Task_2Ljava_lang_String_2I(
		JNIEnv *env, jobject instance, jobject task, jstring type_, jint number) {
	const char *type = env->GetStringUTFChars(type_, 0);

	TaskConstructor constructor;
	try {
		constructor = loader.loadTaskFromLibrary(string(type), number);

		jclass taskClass = env->GetObjectClass(task);
		jfieldID taskNameID = env->GetFieldID(taskClass, "name", "Ljava/lang/String;");
		jfieldID taskTypeID = env->GetFieldID(taskClass, "type", "Ljava/lang/String;");
		jfieldID taskTextID = env->GetFieldID(taskClass, "text", "Ljava/lang/String;");

		jstring taskName = env->NewStringUTF(constructor.getTaskName().c_str());
		jstring taskType = env->NewStringUTF(constructor.getTaskType().c_str());
		jstring taskText = env->NewStringUTF(constructor.getTaskText().c_str());

		env->SetObjectField(task, taskNameID, taskName);
		env->SetObjectField(task, taskTypeID, taskType);
		env->SetObjectField(task, taskTextID, taskText);
	} catch (TestLoadException e) {
		__android_log_print(ANDROID_LOG_ERROR, "TASKS", e.what());
	}

	env->ReleaseStringUTFChars(type_, type);
}

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
	result = (jobjectArray) env->NewObjectArray((jsize) names.size(),
	                                            env->FindClass("java/lang/String"),
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

	string text = loader.readTextFile("file.txt");

	return env->NewStringUTF(text.c_str());
}

}