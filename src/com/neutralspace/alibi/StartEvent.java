package com.neutralspace.alibi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
            // TODO: Use location from actual GPS unit
            Location loc = findLocation();
          //  Location loc = new Location("+100.0");
            UserEventManager uem = app.getUserEventManager();
            long startTime = System.currentTimeMillis();
            UserEvent newEvent = new UserEvent(loc, this.category, startTime);
            
            try {
                uem.start(newEvent);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
		    Intent i = new Intent(view.getContext(), CurrentEvent.class);
			startActivity(i);
		}
		
		private Location findLocation(){
			setContentView(R.layout.retrieve_location);
			
			LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria c = new Criteria();
			c.setAccuracy(Criteria.ACCURACY_FINE);
			c.setBearingRequired(false);
			c.setAltitudeRequired(false);
			c.setCostAllowed(false);
			String provider = lm.getBestProvider(c, true);
			return lm.getLastKnownLocation(provider);

			
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
