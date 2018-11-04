package csc472.depaul.edu.metalcrawler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID1 = "channelID1";
    public static final String channelName1 = "Channel 1";
    public static final String channelID2 = "channelID2";
    public static final String channelName2 = "Channel 2";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels(){
        NotificationChannel channel1 = new NotificationChannel(channelID1, channelName1, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel1);

        NotificationChannel channel2 = new NotificationChannel(channelID2, channelName1, NotificationManager.IMPORTANCE_DEFAULT);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorPrimary);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel2);
    }

    public NotificationManager getManager(){
        if(manager == null){
            manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return manager;
    }

    public NotificationCompat.Builder getChannelNotification1(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channelID1)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.player);
    }

    public NotificationCompat.Builder getChannelNotification2(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channelID2)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.junkie);
    }
}
