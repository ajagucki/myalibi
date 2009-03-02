package com.neutralspace.alibi;


import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is the screen that shows summary of event after event ends.
 */
public class EventSummary extends AlibiActivity {

    public static final String TAG = "Alibi.EventSummary";
    
    //These are here because we can't resolve 
    //  android.provider.Calendar.EVENT_BEGIN_TIME or android.provider.Calendar.END_TIME
    //  so - NOTE! this could break if CalendarProvider.apk implementation changes.
    private static final String EVENT_BEGIN_TIME = "beginTime";
    private static final String EVENT_END_TIME = "endTime";
    
    private UserEventManager userEventManager;
    private UserEvent userEvent = null;
    private Uri userEventUri = null; // from calendar
   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		//If savedInstanceState is null then we are accessing 
		//this activity with an unfinished event. So we need
		//to save a reference to it, stop it, and save a ref
		//to the URI so we can recover information.
		if(savedInstanceState == null) {
		
		    userEventManager = ((Alibi)getApplication()).getUserEventManager();
		
		    try {
		        userEvent = userEventManager.getCurrentEvent(); 
		        //XXX: would there ever be a case where userEvent would be null?
		        userEventUri = userEventManager.stop(); //stops, saves, and cleans up event resources
		    } catch (Exception e) {
		        Log.e(Alibi.TAG, "Couldn't finish event: " + e.getMessage());
		        //XXX: what do we do to handle this error - go back to start?
		    }
        
		    //If savedInstanceState is not null, then we are accessing
		    //this activity after an orientation change, so we can
		    //pull the userEvent and the userEventUri out of savedInstanceState.
		} else {
		    
		    userEvent = (UserEvent) savedInstanceState.getParcelable("userEvent");
		    userEventUri = (Uri) savedInstanceState.getParcelable("userEventUri");
		    
		}
        
        setContentView(R.layout.event_summary);
        
        ImageView categoryImage = (ImageView) findViewById(R.id.category_image);
        TextView startTimeLabel = (TextView) findViewById(R.id.start_time_label);
        TextView stopTimeLabel = (TextView) findViewById(R.id.stop_time_label);
        
        setCurrentEventInfo(userEvent, categoryImage, startTimeLabel, stopTimeLabel);
        
		Button editButton = (Button) findViewById(R.id.edit_event);
		Button finishButton = (Button) findViewById(R.id.finish_event);
		
		editButton.setOnClickListener(new View.OnClickListener() {
			
		    public void onClick(View view) {
		        Log.i(TAG, "Editing event in calendar...");
		        Intent intent = new Intent(Intent.ACTION_EDIT);
		        ComponentName componentName = new ComponentName("com.android.calendar", "com.android.calendar.EditEvent");
		        intent.setComponent(componentName);
		        intent.setData(userEventUri);
		        intent.putExtra(EVENT_BEGIN_TIME, userEvent.getStartTime());
                intent.putExtra(EVENT_END_TIME, userEvent.getEndTime()); 
                
                //Add a StartEvent activity to the stack so when we go 'back' from editing, the app is still there
                Intent i = new Intent(view.getContext(), StartEvent.class);
                startActivity(i);
              
                //Next the edit event screen goes on the stack
		        startActivity(intent); 
		        
		        //Now take EventSummary off the stack since we don't want to get back to it
		        finish();
		        
		        //XXX: next - if event is updated, how to access new start/end times for summary?
		        //Answer: after editing the event we don't go back to EventSummary
		    }
		});
		
		finishButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
			    // Finish event & return to Start screen.
			    Log.i(TAG, "Finished " + userEvent.getCategory().getTitle() + " event.");
			    
			    Intent j = new Intent(view.getContext(), StartEvent.class);
			    startActivity(j);
			    finish(); // done with this activity now.
			}
		});
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    
	    //So we don't loose information during an orientation
	    //change, save the userEvent and userEventUri by
	    //putting them in the savedInstanceState (outState)
	    //bundle
	    
	    outState.putParcelable("userEvent", userEvent);
	    outState.putParcelable("userEventUri", userEventUri);
	    
	}
	
}

