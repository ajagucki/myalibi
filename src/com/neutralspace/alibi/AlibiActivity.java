/************************************************************************
Copyright 2009
Adam DiCarlo, Armando Diaz-Jagucki, Kelsey Cairns,
Nathan Ertner, Peter Welte, Sky Cunningham, and
neutralSpace Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
************************************************************************/

package com.neutralspace.alibi;

import android.app.Activity;
import android.content.Intent;
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
    
    public final String URL_HELP = "http://neutralspace.com/myAlibi/help/";
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retVal = super.onCreateOptionsMenu(menu);
        
        MenuItem menuItem = menu.add(Menu.NONE, MENU_SETTINGS, Menu.CATEGORY_SECONDARY, R.string.menu_settings);
        menuItem.setIcon(android.R.drawable.ic_menu_preferences);
        
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
                startTimeLabel.setText(getString(R.string.event_started) + " " + userEvent.getNiceStartTime());
            }
            
            if (stopTimeLabel != null) {
                stopTimeLabel.setText(getString(R.string.event_stopped) + " " + userEvent.getNiceEndTime());
            }
        }
    }

}
