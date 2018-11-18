package csc472.depaul.edu.metalcrawler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {


    //TODO need to finish this
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gameover);

        findViewById(R.id.continueGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getGameOverActivity(), GameActivity.class);
                getGameOverActivity().startActivity(intent);
            }
        });

        findViewById(R.id.endGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getGameOverActivity(), MainActivity.class);
                getGameOverActivity().startActivity(intent);
            }
        });

    }

    private GameOverActivity getGameOverActivity(){
        return this;
    }


}
