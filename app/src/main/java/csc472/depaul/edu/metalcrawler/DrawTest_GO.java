package csc472.depaul.edu.metalcrawler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.view.View;

public class DrawTest_GO {
    float x,y;
    float sx,sy;

    Bitmap bitmap;
    Paint mAndre;

    public DrawTest_GO(float x, float y, View view)
    {
        this.sx = x;
        this.sy = y;
        this.x = x;
        this.y = y;
        bitmap = BitmapFactory.decodeResource(view.getResources(),R.drawable.andremad_display_bmp);
    }


    public void Update()
    {
        angle += rate;
        x = sx + (float) (Math.cos(angle)) * radius;
        y = sy + (float) (Math.sin(angle)) * radius;
        System.out.println("GO_UPDATE");
    }

    public void FlipDirection()
    {
        rate = -rate;
    }

    float angle = 0;
    float rate = 1;
    float radius = 100;

    public Bitmap GetBitmap()
    {
        return bitmap;
    }

    public Paint GetPaint()
    {
        return mAndre;
    }

    public float GetX()
    {
        return x;
    }

    public float GetY()
    {
        return y;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap,x,y,mAndre);
    }



}
