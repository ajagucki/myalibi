package com.neutralspace.alibi;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Receives an intent from Android's AlarmManager when it's time to remind
 * the user that their event has been running for a while.
 */
public class ReminderAlarm extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 0;
    
    /**
     * Triggered when Reminder Time Interval expires
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Alibi alibi = (Alibi) context.getApplicationContext();
        UserEventManager uem = alibi.getUserEventManager();
        UserEvent event = uem.getCurrentEvent();

        Date date = new Date(event.getStartTime());
        String message = "My Alibi: Your activity " + event.getCategory().getTitle() +
                         " has been running since " + date;
     
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
        
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
        
        // TODO: Write CurrentEvent (AlibiActivity) class
        // TODO: Change this to use CurrentEvent.class
        Intent currentEvent = new Intent(context, StartEvent.class);
        PendingIntent pending = PendingIntent.getActivity(context, 0, currentEvent, 0);
        notification.setLatestEventInfo(context, "My Alibi Reminder", message, pending);

        // We never want more than one notification, so always use the same ID
        nm.notify(NOTIFICATION_ID, notification);
    }

}
