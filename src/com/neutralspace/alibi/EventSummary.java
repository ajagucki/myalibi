package com.neutralspace.alibi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * This is the screen that shows summary of event after event ends.
 */
public class EventSummary extends AlibiActivity {
	
    public static final String TAG = "Alibi.EventSummary";
    
    // Custom Activity result codes
    public static final int YOUR_CUSTOM_RESULT_CODE = RESULT_FIRST_USER + 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.start_event);
		Button editButton = (Button) findViewById(R.id.edit_event);
		Button finishButton = (Button) findViewById(R.id.finish_event);
		
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
			    UserEventManager userEventManager = 
			        ((Alibi)getApplication()).getUserEventManager();
			    try {
			        userEventManager.delete(); // removes event from memory
			    } catch (Exception e) {
			        Log.e(Alibi.TAG, "Couldn't finish event: " + e.getMessage());
			    }
			    
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
