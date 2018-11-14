package csc472.depaul.edu.metalcrawler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.onesignal.OneSignal;

import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;

public class MainActivity extends AppCompatActivity {

    Player player;
//    For notifications (w/o internet)
    NotificationCompat.Builder notification;
    private static final int uniqueID = 164737;
    private static final String tag = "MainActivity";
    private  static final int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*/
        // Method 1 : using a thread to maintain a draw loop
        // drawback : can't use xml / basic button implementation
        setContentView(new GameView(this));
        /*/
        // Method 2 : invalidate a view to cause redrawing
        // drawback : less control ? and no realtime
        setContentView(R.layout.activity_main);

        System.out.println("ONCREATE-------------------");

        Button settingsActivity = findViewById(R.id.settingsButton);
        if(settingsActivity != null){
            settingsActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
        }

        // Used for online notifications, not sure why it is not working
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);


        findViewById(R.id.felipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.Instance().GenerateNewMap();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        //*/
        GameManager.Instance().GameStart(this,findViewById(R.id.drawTest));

        findViewById(R.id.move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.Instance().GetPlayer().MoveLeft();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        findViewById(R.id.move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.Instance().GetPlayer().MoveRight();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        findViewById(R.id.move_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.Instance().GetPlayer().MoveUp();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        findViewById(R.id.move_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.Instance().GetPlayer().MoveDown();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();
               // sendNotification(view);

            }
        });

    }

    public void sendNotification(View view){

        notification.setSmallIcon(R.drawable.player);
        notification.setTicker("Play My Game");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Metal Crawler");
        notification.setContentText("Crawl through the metsl metal jungle!!!");

        Intent intent = new Intent(this, MainActivity.class);
        //Gives phone access to the app
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Issues Notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }

}
