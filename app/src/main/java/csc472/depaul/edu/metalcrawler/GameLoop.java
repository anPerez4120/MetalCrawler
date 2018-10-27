package csc472.depaul.edu.metalcrawler;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    public static Canvas canvas;
    SurfaceHolder surfaceHolder;
    boolean running;
    GameView gameView;

    public GameLoop(SurfaceHolder surfaceHolder, GameView gameView)
    {
        this.gameView = gameView;        System.out.println("GAMELOOP-------------------");
        this.surfaceHolder = surfaceHolder;
    }

    /*
    @Override
    public void start()
    {
        Run();
    }
*/

    @Override
    public void run()
    {
        System.out.println("RUN-------------------");

        while (running)
        {
            System.out.println("TRUE-------------------");

            canvas = null;
            try{
                System.out.println("TRY-------------------");

                canvas = surfaceHolder.lockCanvas();
                System.out.println("LOCK-------------------");

                synchronized (surfaceHolder)
                {
                    System.out.println("SYNC-------------------");

                    // UPDATE + DRAW
                    System.out.println("UPDATE/DRAW-------------------");

                    gameView.Update();
                    gameView.draw(canvas);
                }
            } catch( Exception e)
            {
                System.out.println("CATCH-------------------");
                e.printStackTrace();
            }
            finally
            {
                System.out.println("FIN-------------------");

                if (canvas != null)
                {
                    System.out.println("NOT NULL-------------------");

                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e)
                    {

                    }
                }
            }

            try{
                sleep(10);
            }catch(Exception e){}


        }
    }

    public void SetRunning(boolean running)
    { this.running = running; }


}
