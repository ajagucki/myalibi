package com.neutralspace.alibi;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neutralspace.alibi.UserEvent.Category;

/**
 * This is the screen that shows summary of event after event ends.
 */
public class EventSummary extends AlibiActivity {

	just breakin' it one more time..

    public static final String TAG = "Alibi.EventSummary";
    
    // Custom Activity result codes
    public static final int YOUR_CUSTOM_RESULT_CODE = RESULT_FIRST_USER + 1;
    
    private UserEventManager userEventManager;
   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userEventManager = ((Alibi)getApplication()).getUserEventManager();
		UserEvent userEvent = null;
		
		try {
		    userEvent = userEventManager.getCurrentEvent(); 
		    //XXX: would there ever be a case where userEvent would be null?
            userEventManager.stop(); //stops, saves, and cleans up event resources
        } catch (Exception e) {
            Log.e(Alibi.TAG, "Couldn't finish event: " + e.getMessage());
            //XXX: what do we do to handle this error - go back to start?
        }
		
       
		setContentView(R.layout.event_summary);
		Button editButton = (Button) findViewById(R.id.edit_event);
		Button finishButton = (Button) findViewById(R.id.finish_event);
		
		if (userEvent != null) {
		    // get uri, category, start-time, stop-time
		    
		    TextView categoryLabel = (TextView) findViewById(R.id.category_label);
		    categoryLabel.setText("Category: " + userEvent.getCategory().getTitle());

		    TextView startTimeLabel = (TextView) findViewById(R.id.start_time_label);
		    Date d = new Date(userEvent.getStartTime());
		    startTimeLabel.setText("Event Started: " + d.toString());

		    TextView stopTimeLabel = (TextView) findViewById(R.id.stop_time_label);
		    d = new Date(userEvent.getEndTime()); //XXX: 'endTime' should be 'stopTime'?
            stopTimeLabel.setText("Event stopped: " + d.toString() );
            
            //TODO: get calendar uri which editButton will need.
		}
		
		
		
		editButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			    // TODO: How do we start calendar?
			    Log.i(TAG, "Activating Calendar & waiting for result..");
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		
		finishButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			    
			    // Finish event & return to Start screen.
			   
			    Log.i(TAG, "Ending event...");
			    
			    
			    Intent i = new Intent(view.getContext(), StartEvent.class);
			    startActivity(i);
			}
		});
		
	}
	
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
