package csc472.depaul.edu.metalcrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;

public class MainActivity extends AppCompatActivity {

    Player player;

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
        player =  new Player(findViewById(R.id.drawTest));

        findViewById(R.id.move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.MoveLeft();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        findViewById(R.id.move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.MoveRight();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        findViewById(R.id.move_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.MoveUp();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        findViewById(R.id.move_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.MoveDown();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });

        //*/
        GameManager.Instance().GameStart(findViewById(R.id.drawTest));

    }


}
