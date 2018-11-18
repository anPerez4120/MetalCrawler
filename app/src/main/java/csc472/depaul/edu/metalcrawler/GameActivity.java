package csc472.depaul.edu.metalcrawler;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;
import csc472.depaul.edu.metalcrawler.GameComponents.UI.HealthBar;

public class GameActivity extends AppCompatActivity {

    Player player;
    private GameManager gameManager;
    private long backPressedTime;
    private Toast backToast;
    HealthBar healthBar;
    TextView currentScore;
    TextView highScore;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_game);

        System.out.println("ONCREATE-------------------");

        healthBar = findViewById(R.id.healthBar);
        //To show the damage that has been dealt i made the background red
        healthBar.setBackgroundColor(Color.RED);

        //displaying the current high score on screen
        int score = getHighScore();


        highScore = findViewById(R.id.highScore);
        highScore.setText("High Score: " + score);

        currentScore = findViewById(R.id.currentScore);
        currentScore.setText("Current Score: 0");

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
                view.Update();
                healthBar.invalidate();
                highScore.setText("High Score: " + getHighScore());
                currentScore.setText("Current Score: " + gameManager.getScore());
                checkIfGameOver();
            }
        });
        findViewById(R.id.move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveRight();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();
                healthBar.invalidate();
                highScore.setText("High Score: " + getHighScore());
                currentScore.setText("Current Score: " + gameManager.getScore());
                checkIfGameOver();
            }
        });
        findViewById(R.id.move_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveUp();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();
                healthBar.invalidate();
                highScore.setText("High Score: " + getHighScore());
                currentScore.setText("Current Score: " + gameManager.getScore());
                checkIfGameOver();
            }
        });
        findViewById(R.id.move_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveDown();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();
                healthBar.invalidate();
                highScore.setText("High Score: " + getHighScore());
                currentScore.setText("Current Score: " + gameManager.getScore());
                checkIfGameOver();
            }
        });

    }

    private int getHighScore(){
        SharedPreferences sp = this.getSharedPreferences("HIGH_SCORE", Activity.MODE_PRIVATE);
        return sp.getInt("highscore", 0);
    }

    //Checks to see if the player died, if so then gameOver
    private void checkIfGameOver(){
        if (gameManager.GetPlayer().GetHealth() <= 0){
            gameManager.GameEnd();
            gameManager = GameManager.Instance();
            gameManager.GameStart(getGameActivity(), findViewById(R.id.drawTest));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameManager.saveHighScorePreference();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("GameManager", gameManager);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            gameManager.GameEnd();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Double tap\n(This will end your current Crawl!)", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    public GameActivity getGameActivity(){
        return this;
    }
}
