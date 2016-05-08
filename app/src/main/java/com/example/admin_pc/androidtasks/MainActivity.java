package com.example.admin_pc.androidtasks;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin_pc.androidtasks.Tasks.TasksManager;

public class MainActivity extends AppCompatActivity {

	static {
		System.loadLibrary("tasks");
	}

	TasksManager tasksManager;

	String taskDirectory = "/fileDir";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		tasksManager = TasksManager.getTaskManager();
		tasksManager.setWorkDirectory(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).
				getAbsolutePath() + taskDirectory);

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
			StringBuilder builder = new StringBuilder();
			for (String task : tasksManager.allTasks()) {
				builder.append(task).append(System.lineSeparator());
			}

			String result = builder.toString();
			textView.setText(result.substring(0, result.length() - 1));
		}
	}

	public native String helloFromLibrary(String tasksPath);
}
