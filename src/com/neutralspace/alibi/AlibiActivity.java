package com.neutralspace.alibi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Base activity class for Alibi activities. It provides common implementations
 * all alibi activities should have, such as menu creation, etc.
 */
public class AlibiActivity extends Activity {
    
    public static final int MENU_SETTINGS = Menu.FIRST;
    public static final int MENU_ABOUT = Menu.FIRST + 1;
    public static final int MENU_HELP = Menu.FIRST + 2;
    
    // TODO: Change to Alibi's help website
    private final String URL_HELP = "http://www.google.com";
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }

	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retVal = super.onCreateOptionsMenu(menu);
        
        MenuItem menuItem = menu.add(Menu.NONE, MENU_SETTINGS, Menu.NONE, R.string.menu_settings);
        menuItem.setIcon(android.R.drawable.ic_menu_preferences);
        
        menuItem = menu.add(Menu.NONE, MENU_ABOUT, Menu.CATEGORY_SECONDARY, R.string.menu_about);
        menuItem.setIcon(android.R.drawable.ic_menu_info_details);
        
        menuItem = menu.add(Menu.NONE, MENU_HELP, Menu.NONE, R.string.menu_help);
        menuItem.setIcon(android.R.drawable.ic_menu_help);
        
        return retVal;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Intent intent;
    	switch (item.getItemId()) {
        case MENU_SETTINGS: 
            // Go to Setup screen
            intent = new Intent(this, Setup.class);
            startActivity(intent);
            return true;
        case MENU_ABOUT:
        	// Open the About myAlibi dialog
            AboutDialog about = new AboutDialog(this);
            about.show();
            return true;
        case MENU_HELP:
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_HELP));
            startActivity(intent);
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    
    /**
     * Sets the current category information for category image, startTime, and stopTime.
     * If null is passed to any argument, that view element will be ignored.
     * Intended for use in CurrentEvent and EventSummary.
     * @param categoryImage     An ImageView to be set with the current category image
     * @param startTimeLabel    A TextView to be set with the start time of the current event
     * @param stopTimeLabel     A TextView to be set with the stop time of the current event
     */
    protected void setCurrentEventInfo(UserEvent userEvent, ImageView categoryImage, TextView startTimeLabel, TextView stopTimeLabel) {
            
        if (userEvent != null) {

            // display appropriate category image
            if(categoryImage != null) {
                Integer imageResource = 0;
                switch (userEvent.getCategory()) {
                case WORK:
                    imageResource = R.drawable.category_work;
                    break;
                case PLAY: 
                    imageResource = R.drawable.category_play;
                    break;
                case EAT:
                    imageResource = R.drawable.category_eat;
                    break;
                case OTHER:
                    imageResource = R.drawable.category_other;
                    break;
                }
                categoryImage.setImageResource(imageResource);
            }
            
            if (startTimeLabel != null) {
                startTimeLabel.setText("Event Started: " + userEvent.getNiceStartTime());
            }
            
            if (stopTimeLabel != null) {
                stopTimeLabel.setText("Event stopped: " + userEvent.getNiceEndTime());
            }
        }
    }

}
