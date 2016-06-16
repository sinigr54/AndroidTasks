package com.example.admin_pc.androidtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin_pc.androidtasks.Tasks.TasksManager;

public class ListTasksFragment extends ListFragment {
	public static String TEST_NAME = "testName";

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		TasksManager manager = TasksManager.getTaskManager();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
									android.R.layout.simple_list_item_1, manager.allTasks());
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();

		Intent intent = new Intent(getActivity(), TaskActivity.class);
		intent.putExtra(TEST_NAME, adapter.getItem(position));

		startActivity(intent);
	}
}
