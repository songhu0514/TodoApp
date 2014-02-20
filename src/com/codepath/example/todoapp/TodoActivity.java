package com.codepath.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	File fileDir;
	File todoFile;
	private final int REQUEST_CODE = 1; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        //populateArrayItems();
        fileDir = getFilesDir();
        todoFile = new File(fileDir, "todo.txt");
        readItems();
        todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        //todoAdapter.add("Item 4");
        setupListViewListener();
    }

    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,int pos, long id) {
				todoItems.remove(pos);	
				writeItems();
				todoAdapter.notifyDataSetChanged();
				return true;
			}
		
    	} );

    	lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra("content", todoItems.get(pos));
				i.putExtra("pos", pos);
				startActivityForResult(i, REQUEST_CODE);
			}
		
    	});
	}

	private void populateArrayItems() {
    	todoItems = new ArrayList<String> ();
    	todoItems.add("Item 1");
    	todoItems.add("Item 2");
    	todoItems.add("Item 3");
    }
	
	private void readItems() {
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch(IOException e) {
			todoItems = new ArrayList<String>();
		}
	}
	
	private void writeItems() {
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
    
    public void onAddItem(View v) {
	   	todoAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	writeItems();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		String updatedContent = data.getExtras().getString("updatedContent");
    		int pos = data.getExtras().getInt("pos");
    		//Toast.makeText(this, updatedContent, Toast.LENGTH_SHORT).show();
    		if (pos != -1) {
    			todoItems.set(pos, updatedContent);
    		}
    		writeItems();
    		todoAdapter.notifyDataSetChanged();
		}
    }
    
}
