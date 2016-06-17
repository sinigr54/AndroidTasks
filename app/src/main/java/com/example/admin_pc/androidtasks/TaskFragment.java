package com.example.admin_pc.androidtasks;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.admin_pc.androidtasks.Tasks.Task;
import com.example.admin_pc.androidtasks.Tasks.TaskParser;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

	private static final String LOG_TAG = TaskFragment.class.getSimpleName();
	private static final String TASK_NAME = "taskName";
	private Task currentTask;

	public interface TaskChangeable {
		void nextTask();

		void receiveAnswer(boolean answer);
	}

	public TaskFragment() {

	}

	private TaskChangeable changeable;

	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			changeable = (TaskChangeable) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + "must implement TaskChangeable");
		}
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if (bundle != null) {
			currentTask = bundle.getParcelable(TASK_NAME);
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final TaskParser parser = new TaskParser();
		parser.parse(currentTask);

		View view;
		if (parser.getTypeTask() == 3) {
			view = inflater.inflate(R.layout.fragment_task_radio_buttons, container, false);
			setRadioButtons(view, parser);
		} else if (parser.getTypeTask() == 2) {
			view = inflater.inflate(R.layout.fragment_task2, container, false);
		} else {
			view = inflater.inflate(R.layout.fragment_task4, container, false);
		}

		final TextView taskText = (TextView) view.findViewById(R.id.task_text);
		final TextView taskNameView = (TextView) view.findViewById(R.id.task_name);

		taskText.setText(parser.getFormatText());
		taskNameView.setText(parser.getHeadTask());

		return view;
	}

	private void setRadioButtons(View view, TaskParser parser) {
		final List<RadioButton> radioButtons = new ArrayList<>();

		radioButtons.add((RadioButton) view.findViewById(R.id.radio1));
		radioButtons.add((RadioButton) view.findViewById(R.id.radio2));
		radioButtons.add((RadioButton) view.findViewById(R.id.radio3));
		radioButtons.add((RadioButton) view.findViewById(R.id.radio4));

		List<String> variants = parser.getVariants();
		for (int i = 0; i < variants.size(); ++i) {
			radioButtons.get(i).setText(variants.get(i));
		}

		if (variants.size() == 2) {
			radioButtons.get(1).setVisibility(View.INVISIBLE);
			radioButtons.get(3).setVisibility(View.INVISIBLE);
		}

		CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				for (RadioButton button : radioButtons) {
					button.setChecked(false);
				}
				compoundButton.setChecked(b);
			}
		};

		for (RadioButton button : radioButtons) {
			button.setOnCheckedChangeListener(listener);
		}

		final int trueVariant = parser.getTrueAnswer();
		Button next = (Button) view.findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				boolean isNotChecked = true;
				for (RadioButton button : radioButtons) {
					isNotChecked = isNotChecked && !button.isChecked();
				}

				if (isNotChecked) {
					Snackbar.make(view, R.string.choise_one_answer, Snackbar.LENGTH_LONG)
							.setAction("Action", null).show();
				} else {
					RadioButton checkButton = null;
					for (int i = 0; i < radioButtons.size(); ++i) {
						RadioButton button = radioButtons.get(i);
						if (button.isChecked())
							checkButton = button;
					}

					boolean isTrueAnswer;
					if (trueVariant == radioButtons.indexOf(checkButton)) {
						isTrueAnswer = true;
						Snackbar.make(view, R.string.true_answer, Snackbar.LENGTH_LONG)
								.setAction("Action", null).show();
					} else {
						isTrueAnswer = false;
						Snackbar.make(view, R.string.false_answer, Snackbar.LENGTH_LONG)
								.setAction("Action", null).show();
					}

					changeable.receiveAnswer(isTrueAnswer);
					changeable.nextTask();
				}
			}
		});
	}

	public static TaskFragment instance(Task task) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(TASK_NAME, task);

		TaskFragment fragment = new TaskFragment();
		fragment.setArguments(bundle);

		return fragment;
	}
}
