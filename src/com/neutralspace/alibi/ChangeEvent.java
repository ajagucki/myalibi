package com.neutralspace.alibi;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChangeEvent extends AlibiActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_event);
		
		//Button allButtons[] = new Button [4];
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
			((Alibi) getApplication()).getUserEventManager().getCurrentEvent().setCategory(category);
			finish();
		}
	};
	


		

}
