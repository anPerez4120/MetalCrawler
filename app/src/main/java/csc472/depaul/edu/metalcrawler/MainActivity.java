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
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

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

        requestWriteToExternalStoragePermission();

        File sdCard = android.os.Environment.getExternalStorageDirectory();
        File file = new File(sdCard, "csc472/MetalCrawler/highscore.txt");
        String sdScore = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            sdScore = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
        int sdScoreNum = 0;
        if(!sdScore.isEmpty()){
            sdScoreNum = Integer.valueOf(sdScore);
        }

        sp = this.getSharedPreferences("HIGH_SCORE", Activity.MODE_PRIVATE);
        int score = sp.getInt("highscore", 0);

        if (score > sdScoreNum){
            saveToSD(score + "");
        } else {
            score = sdScoreNum;
            saveHighScorePreference(score);
        }

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
                    Intent intent = new Intent(getMainActivity(), ClassActivity.class);
                    getMainActivity().startActivity(intent);
                }
            });
        }

        Button glossaryActivity = findViewById(R.id.glossaryButton);
        if(glossaryActivity != null){
            glossaryActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getMainActivity(), GlossaryActivity.class);
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

    //save the highscore to preferences
    private void saveHighScorePreference(int num){
        SharedPreferences sp = getMainActivity().getSharedPreferences("HIGH_SCORE", Activity.MODE_PRIVATE);
        if (sp != null){
            //get the saved highscore, default will be 0
            SharedPreferences.Editor editor = sp.edit();
            if (editor != null){
                //put in the score in preferences
                editor.putInt("highscore", num);
                editor.commit();
            }
        }
    }

    //save the highScore to the sdCard
    private void saveToSD(String sNum){
        try{
            requestWriteToExternalStoragePermission();

            File sdCard = android.os.Environment.getExternalStorageDirectory();
            //Create a directory to store the file
            File dir = new File(sdCard.getAbsolutePath() + "/csc472/MetalCrawler");
            dir.mkdirs();

            File file = new File(dir , "/highscore.txt");
            file.createNewFile();

            FileOutputStream outputFile = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputFile);
            outputStreamWriter.append((sNum));
            outputStreamWriter.close();
            outputFile.close();

        } catch (IOException e) {
            //See what the error is
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void requestWriteToExternalStoragePermission()
    {
        int writePermission = ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED)
        {
            int REQUEST_EXTERNAL_STORAGE = 1;

            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            ActivityCompat.requestPermissions(
                    getMainActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public MainActivity getMainActivity(){
        return this;
    }
}
