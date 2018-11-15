package csc472.depaul.edu.metalcrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;

public class DrawTest extends View{
    Paint mTextPaint;
    int mTextColor = 0xff00;

    Paint mPiePaint;
    int mTextHeight;

    Bitmap bitmap;
    int texHeight;
    int texWidth;

    float x=64;
    float y=64;


    public DrawTest(Context context)
    {
        super(context);        go = new DrawTest_GO(128,128,this);

    }

    public DrawTest(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);

        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setStyle(Paint.Style.FILL);
        mPiePaint.setTextSize(mTextHeight);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.andremad_display_bmp);
        go = new DrawTest_GO(128,128,this);

    }

    DrawTest_GO go;
    public void Get_GO(DrawTest_GO go)
    {
        this.go = go;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        switch(e.getAction())
        {

            case MotionEvent.ACTION_DOWN:
                Log.d("Blurb","Blurb");
                break;
        }
        return false;
    }


    public void Update()
    {
        GameManager.Instance().PerformTurn();
        invalidate();
    }

    Player player;
    public void SetPlayer(Player player)
    {
        //temp
        this.player = player;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        GameManager.Instance().Draw(canvas);
    }

}
