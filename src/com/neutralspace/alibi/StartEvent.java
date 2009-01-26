package com.neutralspace.alibi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This is the default main screen of Alibi.
 */
public class StartEvent extends Activity {
    
	public static final int MENU_SETTINGS = Menu.FIRST;
	public static final int MENU_ABOUT = Menu.FIRST + 1;
	
    // Custom Activity result codes
    public static final int YOUR_CUSTOM_RESULT_CODE = RESULT_FIRST_USER + 1;
	
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
		
		MenuItem menuItem = menu.add(Menu.NONE, MENU_SETTINGS, Menu.NONE, R.string.menu_settings);
		menuItem.setIcon(android.R.drawable.ic_menu_preferences);
		
		menuItem = menu.add(Menu.NONE, MENU_ABOUT, Menu.NONE, R.string.menu_about);
		menuItem.setIcon(android.R.drawable.ic_menu_info_details);
		
		return retVal;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch (item.getItemId()) {
		case MENU_SETTINGS:	
            // Go to Setup screen
            Intent intent = new Intent(this, Setup.class);
            startActivity(intent);
			return true;
		case MENU_ABOUT:
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
//        Bundle extras = data != null ? data.getExtras() : null;

        switch(requestCode) {
            case YOUR_CUSTOM_RESULT_CODE:
                // Do something
                break;
        }
    }
}
