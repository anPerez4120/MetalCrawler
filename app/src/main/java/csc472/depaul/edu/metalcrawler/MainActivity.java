package csc472.depaul.edu.metalcrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
        Button button = findViewById(R.id.felipe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("FELPIE");
                DrawTest view = findViewById( R.id.drawTest);
                view.Update();

            }
        });
        //*/
    }


}
