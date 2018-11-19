package csc472.depaul.edu.metalcrawler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;

public class ClassActivity extends AppCompatActivity {

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class);

        findViewById(R.id.defaultClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: apply class to the player
                Intent intent = new Intent(getClassActivity(), GameActivity.class);
                intent.putExtra("player", 0);
                getClassActivity().startActivity(intent);
            }
        });

        findViewById(R.id.bruiser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: apply class to the player
                Intent intent = new Intent(getClassActivity(), GameActivity.class);
                intent.putExtra("player", 1);
                getClassActivity().startActivity(intent);
            }
        });

        findViewById(R.id.strider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: apply class to the player
                Intent intent = new Intent(getClassActivity(), GameActivity.class);
                intent.putExtra("player", 2);
                getClassActivity().startActivity(intent);
            }
        });

    }

    public ClassActivity getClassActivity(){
        return this;
    }
}
