package com.example.admin_pc.androidtasks.Tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskParser {
	String testType;
	String headTask;
	String formatText;
	int typeTask;
	int weight;
	int countVariants;
	List<String> variants = new ArrayList<>();
	int trueAnswer;

	public void parse(Task task) {
		testType = task.getType();
		String taskText = task.getText();

		headTask = taskText.substring(taskText.indexOf("bt") + 2, taskText.lastIndexOf("bt"));
		weight = Integer.valueOf(taskText.substring(taskText.indexOf("w") + 1, taskText.lastIndexOf("w")));
		formatText = taskText.substring(taskText.indexOf("tt") + 2, taskText.lastIndexOf("tt"));
		formatText = formatText.replaceAll("tttt", " ");

		countVariants = Integer.valueOf(taskText.substring(taskText.indexOf("cvt") + 3, taskText.lastIndexOf("cvt")));
		typeTask = Integer.valueOf(taskText.substring(taskText.lastIndexOf("cvt") + 3, taskText.indexOf("va")));

		int i = 0;
		while (taskText.contains("va")) {
			int indexFirstVariant = taskText.indexOf("va") + 2;
			taskText = taskText.replaceFirst("va", "mm");
			String variant = taskText.substring(indexFirstVariant, taskText.indexOf("va"));
			variants.add(variant);

			int indexFirstAnswer = taskText.indexOf("va") + 2;
			taskText = taskText.replaceFirst("va", "mm");
			String answer = taskText.substring(indexFirstAnswer, taskText.indexOf("va"));
			taskText = taskText.replaceFirst("va", "mm");
			if (answer.equals("1")) {
				trueAnswer = i;
			}

			taskText = taskText.replaceFirst("mm", "").replaceFirst("mm", "");
			++i;
		}
	}

	public String getTestType() {
		return testType;
	}

	public String getHeadTask() {
		return headTask;
	}

	public String getFormatText() {
		return formatText;
	}

	public int getTypeTask() {
		return typeTask;
	}

	public int getWeight() {
		return weight;
	}

	public int getCountVariants() {
		return countVariants;
	}

	public List<String> getVariants() {
		return variants;
	}

	public int getTrueAnswer() {
		return trueAnswer;
	}
}
