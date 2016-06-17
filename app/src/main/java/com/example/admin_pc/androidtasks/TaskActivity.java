package com.example.admin_pc.androidtasks;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		if (currentTaskNumber < allTasksFromTest.size()) {
			Fragment fragment = TaskFragment.instance(allTasksFromTest.get(currentTaskNumber));

			if (currentTaskNumber > 0) {
				Fragment oldFragment = manager.findFragmentById(R.id.fragment_container);
				transaction
						.remove(oldFragment);
				manager.popBackStack();
			}

			transaction
					.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_out_left, R.animator.slide_in_right)
					.add(R.id.fragment_container, fragment)
					.commit();

			++currentTaskNumber;
		} else {
			int size = allTasksFromTest.size();
			Fragment fragment = ResultFragment.instance(size, size - trueAnswers, trueAnswers, trueAnswers / (double) size);
			transaction
					.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_out_left, R.animator.slide_in_right)
					.replace(R.id.fragment_container, fragment, "fragment")
					.commit();
		}
	}

	@Override
	public void receiveAnswer(boolean answer) {
		if (answer) {
			trueAnswers++;
		}
	}

	@Override
	public void onBackPressed() {
		final Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
		if (fragment instanceof TaskFragment) {
			showDialog();
		} else {
			super.onBackPressed();
		}
	}

	public static class ExitDialogFragment extends DialogFragment {

		public static ExitDialogFragment newInstance(int title) {
			ExitDialogFragment frag = new ExitDialogFragment();
			Bundle args = new Bundle();
			args.putInt("title", title);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			int title = getArguments().getInt("title");

			return new AlertDialog.Builder(getActivity())
					.setTitle(title)
					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									((TaskActivity) getActivity()).doPositiveClick();
								}
							}
					)
					.setNegativeButton(R.string.alert_dialog_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									((TaskActivity) getActivity()).doNegativeClick();
								}
							}
					)
					.create();
		}
	}

	public void showDialog() {
		DialogFragment newFragment = ExitDialogFragment.newInstance(
				R.string.alert_dialog_two_buttons_title);
		newFragment.show(getFragmentManager(), "dialog");
	}

	public void doPositiveClick() {
		super.onBackPressed();
	}

	public void doNegativeClick() {

	}
}
