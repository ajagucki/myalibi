package com.neutralspace.alibi;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CurrentEvent extends AlibiActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.current_event);
        
        //Next section of code finds the current event and displays
        //the category and start time
        UserEventManager uem = ((Alibi)getApplication()).getUserEventManager();
        UserEvent userEvent = null;
        
        try {    
            userEvent = uem.getCurrentEvent();
        } catch (Exception e) {
            //What to do here?
        }
        
        if(userEvent != null) {
            TextView categoryLabel = (TextView) findViewById(R.id.current_category_label);
            categoryLabel.setText("Category: " + userEvent.getCategory().getTitle());

            TextView startTimeLabel = (TextView) findViewById(R.id.current_start_time_label);
            Date d = new Date(userEvent.getStartTime());
            startTimeLabel.setText("Event Started: " + d.toString());
        }
        
        Button stopButton = (Button) findViewById(R.id.stop);
        Button changeButton = (Button) findViewById(R.id.change);
        Button cancelButton = (Button) findViewById(R.id.cancel);
        
        stopButton.setOnClickListener(new View.OnClickListener(){
            
            public void onClick(View view){
                //jump directly to EventSummary, which will stop the event.
                Log.i(Alibi.TAG, "CurrentEvent -> EventSummary");
                Intent i = new Intent(view.getContext(), EventSummary.class);
                startActivity(i);
                finish();
            }
        });
        
        changeButton.setOnClickListener(new View.OnClickListener(){
            
            public void onClick(View view){
                //  ...
            	Intent i = new Intent(view.getContext(), ChangeEvent.class);
            	startActivity(i);
            	finish();
            }
        });
        
        cancelButton.setOnClickListener(new View.OnClickListener(){
            
            public void onClick(View view){
                Intent i = new Intent(view.getContext(), StartEvent.class);
                startActivity(i);
                
                UserEventManager uem = ((Alibi)getApplication()).getUserEventManager();
                
                uem.delete();
                finish();                
            }
        });
;
    }

}
