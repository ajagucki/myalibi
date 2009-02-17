package com.neutralspace.alibi;

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

        if (event == null) {
            Log.d(Alibi.TAG, "ReminderAlarm: null current event");
            return;
        }
        
        String msgNotify = getNotificationMessage(event);
        String msgToast = context.getString(R.string.reminder_title) + "\n" +
                          msgNotify + " since " + event.getNiceStartTime();
            
        setNotification(context, msgNotify);
        createToast(context, msgToast);
    }

    private static void setNotification(Context context, String message) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
        
        Intent currentEvent = new Intent(context, CurrentEvent.class);
        PendingIntent target = PendingIntent.getActivity(context, 0, currentEvent, 0);
        String title = context.getString(R.string.reminder_title);
        notification.setLatestEventInfo(context, title, message, target);

        // We never want more than one notification, so always use the same ID
        nm.notify(NOTIFICATION_ID, notification);
    }

    public static void cancelNotification(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);   
        nm.cancel(NOTIFICATION_ID);
    }

    public static void updateNotification(Context context) {
        Alibi alibi = (Alibi) context.getApplicationContext();
        UserEvent event = alibi.getUserEventManager().getCurrentEvent();
        
        /* Don't set a notification if there isn't already one active; if the
         * event has not been running as long as the reminder interval, there
         * hasn't been a notification set yet.
         */
        long interval = alibi.getSettingsManager().getReminderDelayMillis();
        long startTime = event.getStartTime();
        long currentTime = System.currentTimeMillis();
        
        if (currentTime >= startTime + interval)
            setNotification(context, getNotificationMessage(event));
    }    

    private static String getNotificationMessage(UserEvent event) {
        return "Activity " + event.getCategory().getTitle() + " in progress";
    }

    private static void createToast(Context context, String message) {        
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
