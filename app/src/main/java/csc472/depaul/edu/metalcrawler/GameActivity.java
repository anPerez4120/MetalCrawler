package csc472.depaul.edu.metalcrawler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;

public class GameActivity extends AppCompatActivity {

    Player player;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_game);

        System.out.println("ONCREATE-------------------");

        if (savedInstance != null)
            gameManager = savedInstance.getParcelable("GameManager");
        else {
            gameManager = GameManager.Instance();
            gameManager.GameStart(this, findViewById(R.id.drawTest));
        }

        findViewById(R.id.move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveLeft();
                DrawTest view = findViewById( R.id.drawTest);
                view.ResetDrawState();
                view.Update();

            }
        });
        findViewById(R.id.move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveRight();
                DrawTest view = findViewById( R.id.drawTest);
                view.ResetDrawState();
                view.Update();

            }
        });
        findViewById(R.id.move_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveUp();
                DrawTest view = findViewById( R.id.drawTest);
                view.ResetDrawState();
                view.Update();

            }
        });
        findViewById(R.id.move_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveDown();
                DrawTest view = findViewById( R.id.drawTest);
                view.ResetDrawState();
                view.Update();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("GameManager", gameManager);
    }



}