package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import csc472.depaul.edu.metalcrawler.R;

public class Sprite {
    Bitmap bitmap;
    Paint paint;

    int x;
    int y;

    int drawLayer = 0;
    public static final float TILE_SIZE = 64.0f;

    public Sprite(){}
    public Sprite(View view)
    {
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.tile);
        SetDrawLayer();
        GameManager.Instance().AddSprite(this);
    }

    public Sprite(View view, int x, int y)
    {
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.tile);
        SetDrawLayer();
        GameManager.Instance().AddSprite(this);
        this.x = x;
        this.y = y;
    }


    public void draw(Canvas canvas)
    {
        // TODO
        canvas.drawBitmap(bitmap, x*TILE_SIZE,y*TILE_SIZE,paint);
    }

    public int GetDrawLayer()
    {
        return drawLayer;
    }

    protected void SetDrawLayer()
    {
        // OVERRIDE
    }

    public int GetX()
    {
        return x;
    }

    public int GetY()
    {
        return y;
    }

    public void SetPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Bitmap GetBitmap() {return bitmap;}
}
