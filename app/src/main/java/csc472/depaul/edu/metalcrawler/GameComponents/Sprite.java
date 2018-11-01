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

    final float TILE_SIZE = 64.0f;

    public Sprite(View view)
    {
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.tile);
    }

    public Sprite(View view, int x, int y)
    {
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.tile);
        this.x = x;
        this.y = y;
    }


    public void draw(Canvas canvas)
    {
        // TODO
        canvas.drawBitmap(bitmap, x*TILE_SIZE,y*TILE_SIZE,paint);
    }
}
