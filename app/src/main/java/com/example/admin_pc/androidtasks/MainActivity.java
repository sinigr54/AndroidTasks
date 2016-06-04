package com.example.admin_pc.androidtasks;

import android.os.Build;
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

	private static final String taskDirectory = "Tests";
	private static final String libraryBuild = File.separator + taskDirectory + File.separator + Build.CPU_ABI;

	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return false;
		}
		return false;
	}

	private String getTaskDirectory() {
		String fullTasksDirectory;
		if (isExternalStorageWritable()) {
			fullTasksDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).
					getAbsolutePath();
		} else {
			fullTasksDirectory = getFilesDir().
					getAbsolutePath();
		}
		fullTasksDirectory += libraryBuild;
		return fullTasksDirectory;
	}

	private boolean createTaskDirectory(String path) {
		File file = new File(path);
		if (file.exists()) {
			return false;
		}

		return file.mkdirs();
	}

	// Test Function
	private void copyLibraryFromAssetsToTestsDirectory(String path) throws IOException {
		FileOutputStream outputStream;
		InputStream inputStream = getAssets().open("libtest.so");
		String fileName = path + File.separator + "libtest.so";

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

		String fullTasksDirectory = getTaskDirectory();
		createTaskDirectory(fullTasksDirectory);

		tasksManager = TasksManager.getTaskManager(fullTasksDirectory);

		try {
			copyLibraryFromAssetsToTestsDirectory(fullTasksDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
}
