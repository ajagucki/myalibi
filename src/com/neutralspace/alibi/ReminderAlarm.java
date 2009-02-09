package com.neutralspace.alibi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

        String message = "Your activity " + event.getCategory().getTitle() +
                         " has been running since " + event.getNiceStartTime();
        Toast.makeText(
                context,
                "My Alibi Reminder" + "\n" + message,
                Toast.LENGTH_LONG
        ).show();
        
        createNotification(context, message);
    }
    
    private void createNotification(Context context, String message) {
        // We never want more than one notification, so always use the same ID
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
        
        Intent currentEvent = new Intent(context, CurrentEvent.class);
        PendingIntent pending = PendingIntent.getActivity(context, 0, currentEvent, 0);
        notification.setLatestEventInfo(context, "My Alibi Reminder", message, pending);

        nm.notify(NOTIFICATION_ID, notification);
    }

    public static void destroyNotification(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);   
        nm.cancel(NOTIFICATION_ID);
    }
}
