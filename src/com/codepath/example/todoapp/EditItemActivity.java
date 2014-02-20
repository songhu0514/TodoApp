package com.codepath.example.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	private String content;
	private EditText etEditItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		content = getIntent().getStringExtra("content");
		etEditItem = (EditText) findViewById(R.id.etEditItem);
		etEditItem.setText(content);
		etEditItem.setSelection(content.length());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void onSaveEdit(View v) {
		Intent data = new Intent();
		data.putExtra("updatedContent", etEditItem.getText().toString());
		data.putExtra("pos", getIntent().getIntExtra("pos", -1));
		setResult(RESULT_OK, data);
		finish();
	}

}
