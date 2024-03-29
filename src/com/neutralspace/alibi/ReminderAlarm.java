/************************************************************************
Copyright 2009
Adam DiCarlo, Armando Diaz-Jagucki, Kelsey Cairns,
Nathan Ertner, Peter Welte, Sky Cunningham, and
neutralSpace Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
************************************************************************/

package com.neutralspace.alibi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Receives an intent from Android's AlarmManager when it's time to remind
 * the user that their event has been running for a while.
 */
public class ReminderAlarm extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 0;
    private static final long VIBRATE_MILLIS = 600;

    /**
     * Triggered by the OS when Reminder Time Interval expires
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
        remind(context, msgToast);
    }

    /**
     * Turns on a notification.
     * @param context
     * @param message Message for notification
     */
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

    /**
     * Cancels the previously set notification
     * @param context
     */
    public static void cancelNotification(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);   
        nm.cancel(NOTIFICATION_ID);
    }

    /**
     * Updates a reminder that previously has been set, such as for when the
     * currently running event's category changes, and as such the notification
     * message must reflect that change.
     * @param context
     */
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

    /**
     * Builds the base notification message.
     * @param event Event we're notifying about
     * @return Base notification message
     */
    private static String getNotificationMessage(UserEvent event) {
        return "Activity " + event.getCategory().getTitle() + " in progress";
    }

    /**
     * Creates a momentary pop-up message with the given string and vibrates
     * the phone.
     * @param context
     * @param message
     */
    private static void remind(Context context, String message) {        
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATE_MILLIS);
    }
}
