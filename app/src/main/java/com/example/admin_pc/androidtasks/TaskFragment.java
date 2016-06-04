package com.example.admin_pc.androidtasks;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin_pc.androidtasks.Tasks.Task;
import com.example.admin_pc.androidtasks.Tasks.TasksManager;

public class TaskFragment extends Fragment {

	private static final String LOG_TAG = TaskFragment.class.getSimpleName();
	private String taskName;

	public TaskFragment() {

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if (bundle != null) {
			taskName = bundle.getString(ListTasksFragment.TASK_NAME);
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task, container, false);

		final TextView textView = (TextView) view.findViewById(R.id.text_view);
		textView.setText(taskName);

		Button button = (Button) view.findViewById(R.id.button);
		button.setText("Показать задание");
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(LOG_TAG, "onClick");

				TasksManager tasksManager = TasksManager.getTaskManager();
				Task task = tasksManager.loadTask(taskName, 1);
				textView.setText(task.getName() + System.lineSeparator() + task.getText());
			}
		});

		return view;
	}

	public static TaskFragment instance(String taskName) {
		Bundle bundle = new Bundle();
		bundle.putString(ListTasksFragment.TASK_NAME, taskName);

		TaskFragment fragment = new TaskFragment();
		fragment.setArguments(bundle);

		return fragment;
	}
}
