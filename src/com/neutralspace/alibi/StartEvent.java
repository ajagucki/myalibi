package com.neutralspace.alibi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

/**
 * This is the default main screen of Alibi.
 */
public class StartEvent extends AlibiActivity {
	
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
		
		workButton.setOnClickListener(new StartEventListener(UserEvent.Category.WORK));
        eatButton.setOnClickListener(new StartEventListener(UserEvent.Category.EAT));
        playButton.setOnClickListener(new StartEventListener(UserEvent.Category.PLAY));
        otherButton.setOnClickListener(new StartEventListener(UserEvent.Category.OTHER));
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
            
            Intent locationIntent = new Intent(view.getContext(), FindLocation.class);
            startActivity(locationIntent);

		}


	};
	
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
