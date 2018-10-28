package csc472.depaul.edu.metalcrawler;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    DrawTest_GO go;

    GameLoop gameLoop;
    Button button;
    public GameView(Context context)
    {
        super(context);
        go = new DrawTest_GO(128,128,this);
        getHolder().addCallback((this));

        gameLoop = new GameLoop(getHolder(), this);

        setFocusable(true);        System.out.println("GAMEVIEW-------------------");

        button = new Button(context);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                go.FlipDirection();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            go.FlipDirection();
            break;
        }
        return true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        gameLoop.SetRunning(true);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while (retry)
        {
            try{
                gameLoop.SetRunning((false));
                gameLoop.join();
            }catch(InterruptedException e)
            {

            }
        }
    }

    public void Update()
    {
        go.Update();
        Log.d("UPDATE: ","UPDATE HAPPENED");
        System.out.println("UPDATE HAPPENED");
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        go.draw(canvas);
        button.draw(canvas);
    }



}
