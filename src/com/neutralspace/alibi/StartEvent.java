package com.neutralspace.alibi;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This is the default main screen of Alibi.
 */
public class StartEvent extends AlibiActivity {

    public static final int MENU_ABOUT = MENU_SETTINGS + 1;
    public static final int MENU_HELP = MENU_SETTINGS + 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		UserEventManager uem = ((Alibi) getApplication()).getUserEventManager();
		
		// Check to see if there is a current event. If so, go to CurrentEvent
		if(uem.getCurrentEvent() != null) {
		    Intent i = new Intent(this, CurrentEvent.class);
		    startActivity(i);
		    finish();
		    return;
		}
		
		setContentView(R.layout.start_event);
		Button workButton = (Button) findViewById(R.id.work);
		Button playButton = (Button) findViewById(R.id.play);
		Button eatButton = (Button) findViewById(R.id.eat);
		Button otherButton = (Button) findViewById(R.id.other);
		
		workButton.setBackgroundResource(R.drawable.category_work);
		eatButton.setBackgroundResource(R.drawable.category_eat);
		playButton.setBackgroundResource(R.drawable.category_play);
		otherButton.setBackgroundResource(R.drawable.category_other);
		workButton.setOnClickListener(new StartEventListener(UserEvent.Category.WORK));
        eatButton.setOnClickListener(new StartEventListener(UserEvent.Category.EAT));
        playButton.setOnClickListener(new StartEventListener(UserEvent.Category.PLAY));
        otherButton.setOnClickListener(new StartEventListener(UserEvent.Category.OTHER));
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retVal = super.onCreateOptionsMenu(menu);
        
        MenuItem menuItem = menu.add(Menu.NONE, MENU_ABOUT, Menu.CATEGORY_SECONDARY + 2, R.string.menu_about);
        menuItem.setIcon(android.R.drawable.ic_menu_info_details);
        
        menuItem = menu.add(Menu.NONE, MENU_HELP, Menu.CATEGORY_SECONDARY + 1, R.string.menu_help);
        menuItem.setIcon(android.R.drawable.ic_menu_help);

        return retVal;
    }
		
	private class StartEventListener implements View.OnClickListener {
	    UserEvent.Category category;
	    
	    public StartEventListener(UserEvent.Category category) {
	        this.category = category;
	    }
	    
		public void onClick(View view) {
            Alibi app = (Alibi) getApplication();
            Location loc = new Location("Unknown");
            UserEventManager uem = app.getUserEventManager();
            long startTime = System.currentTimeMillis();
            UserEvent newEvent = new UserEvent(loc, this.category, startTime);

            try {
                uem.start(newEvent);
            } catch (Exception e) {
                Log.e(Alibi.TAG, "Error starting UserEvent");
                e.printStackTrace();
            }
            
            Intent i = new Intent(view.getContext(), CurrentEvent.class);
            startActivity(i);
            
            finish();

		}
	};
	
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
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
}
