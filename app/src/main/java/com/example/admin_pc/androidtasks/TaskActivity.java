package com.example.admin_pc.androidtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.admin_pc.androidtasks.Tasks.Task;
import com.example.admin_pc.androidtasks.Tasks.TasksManager;

import java.util.List;

public class TaskActivity extends AppCompatActivity implements TaskFragment.TaskChangeable {

	private List<Task> allTasksFromTest;
	private int currentTaskNumber = 0;

	private int trueAnswers = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		Intent receiveIntent = getIntent();
		String testName = receiveIntent.getStringExtra(ListTasksFragment.TEST_NAME);

		allTasksFromTest = TasksManager.getTaskManager().getTasks(testName);
		nextTask();

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void nextTask() {
		if (currentTaskNumber < allTasksFromTest.size()) {
			FragmentManager manager = getSupportFragmentManager();
			Fragment fragment = TaskFragment.instance(allTasksFromTest.get(currentTaskNumber));
			manager.beginTransaction()
					.add(R.id.fragment_container, fragment)
					.commit();

			++currentTaskNumber;
		}
	}

	@Override
	public void receiveAnswer(boolean answer) {
		Log.d("TaskActivity", "recieve");
		if (answer)
			trueAnswers++;
	}
}
