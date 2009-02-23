package com.neutralspace.alibi;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class FindLocation extends AlibiActivity {
	private Location userLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.retrieve_location);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setBearingRequired(false);
        c.setAltitudeRequired(false);
        c.setCostAllowed(false);
        String provider = lm.getBestProvider(c, true);
        
        String s = android.provider.Settings.System.getString
        (getContentResolver(), android.provider.Settings.System.LOCATION_PROVIDERS_ALLOWED);
        Log.d(Alibi.TAG, " avail GPS: " + s);
        
        userLocation = lm.getLastKnownLocation(provider);
        
        Log.d(Alibi.TAG, "Provider enabled status: " + lm.isProviderEnabled(provider));
       
        if (userLocation == null) {
        	userLocation = new Location("Unknown");
        	Log.e(Alibi.TAG, "Provider '" + provider + "' was disabled.");

        } else {
        	Log.i(Alibi.TAG, "Location set to " + userLocation.toString());
        	((Alibi) getApplication()).getUserEventManager().getCurrentEvent().setLocation(userLocation);
        }
        
        Log.i(Alibi.TAG, "Calling current event");

	    Intent currentEventIntent = new Intent(this.getBaseContext(), CurrentEvent.class);
		startActivity(currentEventIntent);
		finish();
	}

}
