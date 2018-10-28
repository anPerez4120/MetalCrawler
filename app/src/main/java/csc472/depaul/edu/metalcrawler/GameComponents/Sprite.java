package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Sprite {
    Bitmap bitmap;
    Paint paint;

    int x;
    int y;

    final float TILE_SIZE = 16.0f;

    public void draw(Canvas canvas)
    {
        // TODO
        canvas.drawBitmap(bitmap, x*TILE_SIZE,y*TILE_SIZE,paint);
    }
}
