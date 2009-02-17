package com.neutralspace.alibi;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChangeEvent extends AlibiActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_event);
		
		Button workButton = (Button) findViewById(R.id.work);
		Button playButton = (Button) findViewById(R.id.play);
		Button eatButton = (Button) findViewById(R.id.eat);
		Button otherButton = (Button) findViewById(R.id.other);
		
		workButton.setOnClickListener(new ChangeListener(UserEvent.Category.WORK));
		playButton.setOnClickListener(new ChangeListener(UserEvent.Category.PLAY));
		eatButton.setOnClickListener(new ChangeListener(UserEvent.Category.EAT));
		otherButton.setOnClickListener(new ChangeListener(UserEvent.Category.OTHER));

		

			
	}
	
	class ChangeListener implements OnClickListener{
		UserEvent.Category category;
		
		public ChangeListener(UserEvent.Category category){
			this.category = category;
		}
		
		public void onClick(View view){
			
			try{
				((Alibi) getApplication()).getUserEventManager().setCategory(category);
			} catch (Exception e){
				Log.e(Alibi.TAG, "Unable to change location");
			}
			
			Intent i = new Intent(view.getContext(), CurrentEvent.class);
			startActivity(i);
			
			finish();
		}
	};
	


		

}
