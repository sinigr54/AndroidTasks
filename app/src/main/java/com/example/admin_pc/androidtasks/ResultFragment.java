package com.example.admin_pc.androidtasks;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultFragment extends Fragment {
	private static final String COUNT = "count";
	private static final String COUNT_TRUE = "count_true";
	private static final String COUNT_FALSE = "count_false";
	private static final String RESULT = "result";

	int count;
	int countFalse;
	int countTrue;
	double result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if (bundle != null) {
			count = bundle.getInt(COUNT);
			countTrue = bundle.getInt(COUNT_TRUE);
			countFalse = bundle.getInt(COUNT_FALSE);
			result = bundle.getDouble(RESULT);
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_results, container, false);

		TextView questions = (TextView) view.findViewById(R.id.questions_size);
		questions.setText(String.valueOf(count));

		TextView trueAnswers = (TextView) view.findViewById(R.id.true_answers_size);
		trueAnswers.setText(String.valueOf(countTrue));

		TextView falseAnswers = (TextView) view.findViewById(R.id.false_answers_size);
		falseAnswers.setText(String.valueOf(countFalse));

		TextView resultValue = (TextView) view.findViewById(R.id.result_value);
		resultValue.setText(String.valueOf(result * 100) + "%");

		return view;
	}

	public static Fragment instance(int count, int countFalse, int countTrue, double result) {
		Bundle bundle = new Bundle();
		bundle.putInt(COUNT, count);
		bundle.putInt(COUNT_TRUE, countTrue);
		bundle.putInt(COUNT_FALSE, countFalse);
		bundle.putDouble(RESULT, result);

		ResultFragment fragment = new ResultFragment();
		fragment.setArguments(bundle);

		return fragment;
	}
}
