package com.neutralspace.alibi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentEvent extends AlibiActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.current_event);
        
        //Next section of code finds the current event and displays
        //the category and start time
    
        ImageView categoryImage = (ImageView) findViewById(R.id.current_category_image);
        TextView startTimeLabel = (TextView) findViewById(R.id.current_start_time_label);
        
        setCurrentCategoryInfo(categoryImage, startTimeLabel, null);
        
        Button stopButton = (Button) findViewById(R.id.stop);;
        
        stopButton.setOnClickListener(new View.OnClickListener(){
            
            public void onClick(View view){
                //jump directly to EventSummary, which will stop the event.
                Log.i(Alibi.TAG, "CurrentEvent -> EventSummary");
                Intent i = new Intent(view.getContext(), EventSummary.class);
                startActivity(i);
                finish();
            }
        });
        
    }
    
    //TODO: find a solution here, the offsets should not be hardcoded
    public static final int MENU_CHANGE_CATEGORY = Menu.FIRST + 2;
    public static final int MENU_CANCEL = Menu.FIRST + 3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retVal = super.onCreateOptionsMenu(menu);
        
        menu.removeItem(MENU_SETTINGS);
        
        MenuItem menuItem = menu.add(Menu.NONE, MENU_CHANGE_CATEGORY, Menu.CATEGORY_ALTERNATIVE, R.string.menu_change_category);
        menuItem.setIcon(android.R.drawable.ic_menu_edit);
        
        menuItem = menu.add(Menu.NONE, MENU_CANCEL, Menu.CATEGORY_ALTERNATIVE + 1, R.string.menu_cancel);
        menuItem.setIcon(android.R.drawable.ic_menu_delete);
        

        
        return retVal;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case MENU_CANCEL: 
            Intent i = new Intent(this, StartEvent.class);
            startActivity(i);
            
            UserEventManager uem = ((Alibi)getApplication()).getUserEventManager();
            
            uem.delete();
            finish();
            return true;
        case MENU_CHANGE_CATEGORY:
            Intent j = new Intent(this, ChangeEvent.class);
            startActivity(j);
            finish();
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

}
