package com.neutralspace.alibi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
//		Button eatButton = (Button) findViewById(R.id.eat);
//		Button otherButton = (Button) findViewById(R.id.other);
		
		workButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		
		playButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		
		/*
		eatButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		otherButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		
		workButton.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view){
			//	Intent i = new Intent(this, CurrentEvent.class);
			//	i.putExtra(...)
			//	startActivity(i)
			}
		});
		*/
		
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
