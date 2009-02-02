package com.neutralspace.alibi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

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
                //  ...
            }
        });
        
        changeButton.setOnClickListener(new View.OnClickListener(){
            
            public void onClick(View view){
                //  ...
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
