package com.example.admin_pc.androidtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TaskActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		Intent receiveIntent = getIntent();
		String taskName = receiveIntent.getStringExtra(ListTasksFragment.TASK_NAME);

		FragmentManager manager = getSupportFragmentManager();
		Fragment fragment = TaskFragment.instance(taskName);
		manager.beginTransaction()
				.add(R.id.fragment_container, fragment)
				.commit();

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

}
