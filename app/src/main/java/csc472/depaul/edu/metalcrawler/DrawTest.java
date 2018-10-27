package csc472.depaul.edu.metalcrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

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
        super(context);
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
    }

    DrawTest_GO go;
    public void Get_GO(DrawTest_GO go)
    {
        this.go = go;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

}
