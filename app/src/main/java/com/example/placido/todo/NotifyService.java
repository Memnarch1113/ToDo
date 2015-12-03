package com.example.placido.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by Placido on 12/2/15.
 */
public class NotifyService extends Service {

    @Override
    public IBinder onBind(Intent intent){ //required class
        return null;
    }

    @Override
    public void onCreate() { //method for notification with sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //hear default notification sound everday

        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), Time.class); //starts time activity when starting
        //PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Have you completed your tasks?")
                .setContentText("JUST DO IT")
                        //.setSmallIcon(R.drawable.ic_launcher) //tried to get this to work but it does not
                        //.setContentIntent(pIntent)
                .setSound(sound)
                        //.addAction(0, "Load Website", pIntent) //MAY NOT NEED ACTION
                .build();

        mNM.notify(1, mNotify);
    }
}