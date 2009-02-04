package com.neutralspace.alibi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CurrentEvent extends AlibiActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.current_event);
        
        Button stopButton = (Button) findViewById(R.id.stop);
        Button changeButton = (Button) findViewById(R.id.change);
        Button cancelButton = (Button) findViewById(R.id.cancel);
        
        stopButton.setOnClickListener(new View.OnClickListener(){
            
            public void onClick(View view){
                //jump directly to EventSummary, which will stop the event.
                Log.i(Alibi.TAG, "CurrentEvent -> EventSummary");
                Intent i = new Intent(view.getContext(), EventSummary.class);
                startActivity(i);
            }
        });
        
        changeButton.setOnClickListener(new View.OnClickListener(){
            
            public void onClick(View view){
                //  ...
            	Intent i = new Intent(view.getContext(), ChangeEvent.class);
            	startActivity(i);
            }
        });
        
        cancelButton.setOnClickListener(new View.OnClickListener(){
            
            public void onClick(View view){
                Intent i = new Intent(view.getContext(), StartEvent.class);
                startActivity(i);
                
                UserEventManager uem = ((Alibi) getApplication()).getUserEventManager();
                uem.delete();
                finish();                
            }
        });
;
    }

}
