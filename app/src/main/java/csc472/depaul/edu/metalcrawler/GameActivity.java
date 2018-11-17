package csc472.depaul.edu.metalcrawler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;

public class GameActivity extends AppCompatActivity {

    Player player;
    private GameManager gameManager;
    private long backPressedTime;
    private Toast backToast;

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
                view.Update();

            }
        });
        findViewById(R.id.move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveRight();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        findViewById(R.id.move_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveUp();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        findViewById(R.id.move_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.GetPlayer().MoveDown();
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameManager.GameEnd();
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
}
