package csc472.depaul.edu.metalcrawler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onesignal.OneSignal;

import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;

public class MainActivity extends AppCompatActivity {

    Player player;
    TextView highScore;
    int score = 0;
    SharedPreferences sp;


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

        sp = this.getSharedPreferences("HIGH_SCORE", Activity.MODE_PRIVATE);
        score = sp.getInt("highscore", 0);

        highScore = findViewById(R.id.highScore);
        highScore.setText("High Score: " + score);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel mChannel =
                    new NotificationChannel(ChannelConstants.CHANNEL_ID, ChannelConstants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(ChannelConstants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);

            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(mChannel);
        }

        System.out.println("ONCREATE-------------------");

        Button settingsActivity = findViewById(R.id.settingsButton);
        if(settingsActivity != null){
            settingsActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getMainActivity(), SettingsActivity.class);
                    getMainActivity().startActivity(intent);
                }
            });
        }

        Button gameActivity = findViewById(R.id.gameButton);
        if(gameActivity != null){
            gameActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getMainActivity(), GameActivity.class);
                    getMainActivity().startActivity(intent);
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        score = sp.getInt("highscore", 0);

        highScore = findViewById(R.id.highScore);
        highScore.setText("High Score: " + score);
    }

    public MainActivity getMainActivity(){
        return this;
    }
}
