package csc472.depaul.edu.metalcrawler.GameComponents.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;

import csc472.depaul.edu.metalcrawler.GameComponents.Actor;
import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;

public class HealthBar extends SurfaceView {

    Paint paint = new Paint();
    Rect rect = new Rect();
    private float ratio = 1;
    //the Rect will be the actual health the player has remaining

    public HealthBar(Context context) {
        super(context);
    }

    public HealthBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HealthBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.rgb(51,153,102));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);

        //make calculation for the ratio required to get rect width
        ratio = GameManager.Instance().GetPlayer().GetHealth() / 100;

        rect.set(0, 0, (int) (canvas.getWidth()*ratio), canvas.getHeight());
        canvas.drawRect(rect, paint);
    }

}
