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
import android.view.View;
import android.widget.Button;

public class FindLocation extends AlibiActivity implements LocationListener {
//	private Location userLocation;
	private LocationManager lm;
	private Handler timeHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retrieve_location);
		
		//GPS time-out setup
		ListenerRunnable skipLocation = new ListenerRunnable(this);
		timeHandler.removeCallbacks(skipLocation);
		timeHandler.postDelayed(skipLocation, 30000);
		
		
		Button skipButton = (Button) findViewById(R.id.skip_location);		
		skipButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				lm.removeUpdates((LocationListener) v.getContext());
				Log.i(Alibi.TAG, "event started without GPS location");
			    Intent currentEventIntent = new Intent(v.getContext(), CurrentEvent.class);
				startActivity(currentEventIntent);
				finish();				
			}			
		});
	

		// get location manager and location provider
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setBearingRequired(false);
        c.setAltitudeRequired(false);
        c.setCostAllowed(false);
        String provider = lm.getBestProvider(c, true);
        
        // debugging output
        String s = android.provider.Settings.System.getString
        (getContentResolver(), android.provider.Settings.System.LOCATION_PROVIDERS_ALLOWED);
        Log.d(Alibi.TAG, " avail GPS: " + s);

        //register the request for location updates
        lm.requestLocationUpdates(provider, 0, 0, this);
        

	}

	
	@Override
	public void onLocationChanged(Location loc){
		lm.removeUpdates(this);
		
        if (loc == null) {
        	Log.e(Alibi.TAG, "Location is null. This is bad");
        } else {
        	Log.i(Alibi.TAG, "Location set to " + loc.toString());
            ((Alibi) getApplication()).getUserEventManager().getCurrentEvent().setLocation(loc);
        }
        
        Log.i(Alibi.TAG, "Calling current event");

	    Intent currentEventIntent = new Intent(this.getBaseContext(), CurrentEvent.class);
		startActivity(currentEventIntent);
		finish();
		
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
	
	
	private class ListenerRunnable implements Runnable{
		private LocationListener ll;
		
		public ListenerRunnable(LocationListener l){
			ll = l;
		}
		
		@Override
		public void run(){
			// go on without location information
			lm.removeUpdates(ll);
			Log.i(Alibi.TAG, "event started without GPS location");
	    	Intent currentEventIntent = new Intent(((AlibiActivity)ll).getBaseContext(), CurrentEvent.class);
			startActivity(currentEventIntent);
			finish();
		}
	};

}
