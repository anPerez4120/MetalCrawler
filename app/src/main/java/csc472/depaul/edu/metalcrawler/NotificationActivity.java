package csc472.depaul.edu.metalcrawler;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import csc472.depaul.edu.metalcrawler.R;

public class NotificationActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextMessage;
    private Button buttonChannel1;
    private Button buttonChannel2;

    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_notifications);

        editTextTitle = findViewById(R.id.edittext_title);
        editTextMessage = findViewById(R.id.edittext_message);
        buttonChannel1 = findViewById(R.id.button_channel);
        buttonChannel2 = findViewById(R.id.button_channel2);

        notificationHelper = new NotificationHelper(this);

        buttonChannel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel1(editTextTitle.getText().toString(), editTextMessage.getText().toString());
            }
        });

        buttonChannel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel2(editTextTitle.getText().toString(), editTextMessage.getText().toString());
            }
        });
    }

    public void sendOnChannel1(String title, String message){
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification1(title, message);
        notificationHelper.getManager().notify(1, nb.build());

    }

    public void sendOnChannel2(String title, String message){
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification2(title, message);
        notificationHelper.getManager().notify(2, nb.build());
    }
}
