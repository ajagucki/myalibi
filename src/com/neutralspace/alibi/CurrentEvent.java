package com.neutralspace.alibi;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentEvent extends AlibiActivity implements LocationListener {
    private final long GPS_TIMEOUT_MILLIS = 30 * 1000;

    private LocationManager lm;
	private Handler timeHandler = new Handler();
	ListenerRunnable skipLocation = new ListenerRunnable(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_event);
        
        // Get GPS location manager and location provider
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setBearingRequired(false);
        c.setAltitudeRequired(false);
        c.setCostAllowed(false);
        String provider = lm.getBestProvider(c, true);

        // Set up GPS timer
        timeHandler.removeCallbacks(skipLocation);

        UserEventManager userEventManager = ((Alibi)getApplication()).getUserEventManager();
        UserEvent userEvent = userEventManager.getCurrentEvent(); 
        if (userEvent == null) {
            Log.e(Alibi.TAG, "CurrentEvent: userEvent is null!");
            // TODO: Error dialog, transfer to StartEvent activity
            return;
        }

        if (userEvent.getLocationTried()) {
            Log.d(Alibi.TAG, "Event already had location");
        } else {
            userEvent.setLocationTried(true);
            // Register the request for location updates, start time-out timer
            lm.requestLocationUpdates(provider, 0, 0, this);
            timeHandler.postDelayed(skipLocation, GPS_TIMEOUT_MILLIS);
        }
        
        ImageView categoryImage = (ImageView) findViewById(R.id.current_category_image);
        TextView startTimeLabel = (TextView) findViewById(R.id.current_start_time_label);

        setCurrentEventInfo(userEvent, categoryImage, startTimeLabel, null);

        Button stopButton = (Button) findViewById(R.id.stop);

        stopButton.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View view) {
                // jump directly to EventSummary, which will stop the event.
                Log.i(Alibi.TAG, "CurrentEvent -> EventSummary");
                timeHandler.removeCallbacks(skipLocation);
                lm.removeUpdates((LocationListener) view.getContext());
                Intent i = new Intent(view.getContext(), EventSummary.class);
                startActivity(i);
                finish();
            }
        });
    }

    //TODO: find a solution here, the offsets should not be hardcoded
    public static final int MENU_CHANGE_CATEGORY = Menu.FIRST + 3;
    public static final int MENU_CANCEL = Menu.FIRST + 4;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retVal = super.onCreateOptionsMenu(menu);

        menu.removeItem(MENU_SETTINGS);

        MenuItem menuItem = menu.add(Menu.NONE, MENU_CHANGE_CATEGORY, Menu.FIRST, R.string.menu_change_category);
        menuItem.setIcon(android.R.drawable.ic_menu_edit);

        menuItem = menu.add(Menu.NONE, MENU_CANCEL, Menu.FIRST + 1, R.string.menu_cancel);
        menuItem.setIcon(android.R.drawable.ic_menu_delete);

        return retVal;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case MENU_CANCEL: 
            Intent i = new Intent(this, StartEvent.class);
            startActivity(i);
    		timeHandler.removeCallbacks(skipLocation);
    		lm.removeUpdates(this);
    		UserEventManager uem = ((Alibi)getApplication()).getUserEventManager();
            uem.cancel();
            finish();
            return true;
        case MENU_CHANGE_CATEGORY:
            Intent j = new Intent(this, ChangeEvent.class);
            startActivity(j);
            finish();
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

	@Override
	public void onLocationChanged(Location loc) {
		lm.removeUpdates(this);
		timeHandler.removeCallbacks(skipLocation);
		
        if (loc == null) {
        	Log.e(Alibi.TAG, "Location is null. This is very bad");
        } else {
            try {
                ((Alibi) getApplication()).getUserEventManager().setLocation(loc);
                Log.i(Alibi.TAG, "Location set to " + loc.toString());
            } catch (Exception e) {
                // TODO: Error dialog
                e.printStackTrace();
            }
        }
	}

	@Override
	public void onProviderDisabled(String arg0) {				
	}
	@Override
	public void onProviderEnabled(String provider) {				
	}
	@Override
	public void onStatusChanged(String provider, int status,
			Bundle extras) {				
	}

	private class ListenerRunnable implements Runnable {
		private LocationListener ll;
		
		public ListenerRunnable(LocationListener l) {
			ll = l;
		}

		@Override
		public void run() {
			// go on without location information
			lm.removeUpdates(ll);
			Log.i(Alibi.TAG, "event started without GPS location");
		}
	};
}
