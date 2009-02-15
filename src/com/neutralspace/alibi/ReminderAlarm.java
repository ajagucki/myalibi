package com.neutralspace.alibi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

        String msgNotify = "Activity " + event.getCategory().getTitle() +
                          " in progress";
        String msgToast = msgNotify + " since " + event.getNiceStartTime();
        Toast.makeText(
                context,
                context.getString(R.string.reminder_title) + "\n" + msgToast,
                Toast.LENGTH_LONG
        ).show();
        
        createNotification(context, msgNotify);
    }
    
    private void createNotification(Context context, String message) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
        
        Intent currentEvent = new Intent(context, CurrentEvent.class);
        PendingIntent target = PendingIntent.getActivity(context, 0, currentEvent, 0);
        String title = context.getString(R.string.reminder_title);
        notification.setLatestEventInfo(context, title, message, target);

        // We never want more than one notification, so always use the same ID
        nm.notify(NOTIFICATION_ID, notification);
    }

    public static void destroyNotification(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);   
        nm.cancel(NOTIFICATION_ID);
    }
}
