package com.neutralspace.alibi;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Base activity class for Alibi activities. It provides common implementations
 * all alibi activities should have, such as menu creation, etc.
 */
public class AlibiActivity extends Activity {
    
    public static final int MENU_SETTINGS = Menu.FIRST;
    public static final int MENU_ABOUT = Menu.FIRST + 1;

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
