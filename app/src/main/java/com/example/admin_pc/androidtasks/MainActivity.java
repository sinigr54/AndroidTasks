package com.example.admin_pc.androidtasks;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin_pc.androidtasks.Tasks.Task;
import com.example.admin_pc.androidtasks.Tasks.TasksManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

	private static String LOG_TAG = MainActivity.class.getSimpleName();

	static {
		System.loadLibrary("tasks");
	}

	TasksManager tasksManager;

	private static final String taskDirectory = "/Tests";

	// I cant create files on SD card
	private File getTestsStorageDir(String dirName) {
		File file = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOCUMENTS), dirName);
		if (!file.mkdir()) {
			Log.e(LOG_TAG, "Directory not created");
		}

		return file;
	}

	// Test Function
	private void copyLibraryFromAssetsToTestsDirectory(String path) throws IOException {
		FileOutputStream outputStream;
		InputStream inputStream = getAssets().open("test.so");
		String fileName = path + "/libtest.so";

		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}

		try {
			byte[] buffer = new byte[1024];
			outputStream = new FileOutputStream(file);

			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.close();
			inputStream.close();

			Log.d(LOG_TAG, "All rights");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		final String fullTasksDirectory = getFilesDir().
				getAbsolutePath() + taskDirectory;

		File file = new File(fullTasksDirectory);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				Log.e(LOG_TAG, "Error create dirs");
			}
		}

		tasksManager = TasksManager.getTaskManager(fullTasksDirectory);

		try {
			copyLibraryFromAssetsToTestsDirectory(fullTasksDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}

		TextView textView = (TextView) findViewById(R.id.text_view);
		textView.setText(fullTasksDirectory);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		if (fab != null) {
			fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
							.setAction("Action", null).show();
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	// Тест работы
	public void clickButton(View view) {
		TextView textView = (TextView) findViewById(R.id.text_view);
		if (textView != null) {
			/*StringBuilder builder = new StringBuilder();
			for (String task : tasksManager.allTasks()) {
				builder.append(task).append(System.lineSeparator());
			}

			String result = builder.toString();
			textView.setText(result.substring(0, result.length() - 1));*/
			Task task = tasksManager.loadTask("test", 1);
			textView.setText(task.getText());
		}
	}

	public native String helloFromLibrary(String tasksPath);
}
