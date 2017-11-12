package com.santiago.canchaapp.servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.santiago.canchaapp.LoginActivity;
import com.santiago.canchaapp.R;

public class MessagingService extends FirebaseMessagingService{
    @Override
    public void onMessageReceived (RemoteMessage remoteMessage){

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setContentTitle("CanchApp Notificación")
            .setContentText(remoteMessage.getNotification().getBody()) //Body
            .setAutoCancel(true)
            .setSound(soundUri)
            .setSmallIcon(R.mipmap.ic_logo)
            .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }
}
