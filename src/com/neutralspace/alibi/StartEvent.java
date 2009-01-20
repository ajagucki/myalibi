package com.neutralspace.alibi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StartEvent extends Activity {
	public static final int MENU_SETTINGS = Menu.FIRST;
	public static final int MENU_ABOUT = Menu.FIRST + 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.start_event);
		Button workButton = (Button) findViewById(R.id.work);
		Button playButton = (Button) findViewById(R.id.play);
		Button eatButton = (Button) findViewById(R.id.eat);
		Button otherButton = (Button) findViewById(R.id.other);
		
		workButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		
		playButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		
		/*
		eatButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		otherButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		
		workButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		*/
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean retVal = super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_SETTINGS, 0, R.string.menu_settings);
		menu.add(0, MENU_ABOUT, 0, R.string.menu_about);
		return retVal;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch (item.getItemId()) {
		case MENU_SETTINGS:	
			Intent i = new Intent();
	//		i...
			return true;
		case MENU_ABOUT:
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	

}
