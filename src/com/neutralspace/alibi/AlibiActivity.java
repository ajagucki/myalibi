package com.neutralspace.alibi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Base activity class for Alibi activities. It provides common implementations
 * all alibi activities should have, such as menu creation, etc.
 */
public class AlibiActivity extends Activity {
    
    public static final int MENU_SETTINGS = Menu.FIRST;
    public static final int MENU_ABOUT = Menu.FIRST + 1;

    private TextView captionText = null;
/*    private TextView categoryLabelText = null;
    private TextView startTimeText = null;
    private TextView endTimeText = null;
    private TextView locationText = null;
  */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	captionText = (TextView) findViewById(R.id.caption_text);
    }
	
	public void outputEvent(String caption, UserEvent ue){
		if (captionText != null){
			captionText.setText(caption);
		}
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
            // TODO: Go to About screen
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

}
