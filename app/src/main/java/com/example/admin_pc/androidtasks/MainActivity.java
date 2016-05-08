package com.example.admin_pc.androidtasks;

import android.content.res.AssetManager;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

	static {
		System.loadLibrary("tasks");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		String path = "";
		FileOutputStream outputStream;
		FileInputStream inputStream;
		if (Environment.isExternalStorageEmulated()) {
			File dir = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "fileDir");
			dir.mkdirs();

			File file = new File(dir, "file.txt");
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

	public void clickButton(View view) {
		TextView textView = (TextView) findViewById(R.id.text_view);
		if (textView != null) {
			helloFromLibrary(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).
					getAbsolutePath());

			StringBuilder builder = new StringBuilder();
			for (String name : getAllTaskNames()) {
				builder.append(name).append(System.lineSeparator());
			}

			textView.setText(builder.toString());
		}
	}

	public native String helloFromLibrary(String tasksPath);

	public native String getTaskName(AssetManager manager);

	public native String[] getAllTaskNames();
}
