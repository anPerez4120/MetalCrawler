package csc472.depaul.edu.metalcrawler;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import csc472.depaul.edu.metalcrawler.GameComponents.Actor;
import csc472.depaul.edu.metalcrawler.GameComponents.Blurb;
import csc472.depaul.edu.metalcrawler.GameComponents.DrawState;
import csc472.depaul.edu.metalcrawler.GameComponents.DrawStates;
import csc472.depaul.edu.metalcrawler.GameComponents.Entity;
import csc472.depaul.edu.metalcrawler.GameComponents.Environment;
import csc472.depaul.edu.metalcrawler.GameComponents.GameManager;
import csc472.depaul.edu.metalcrawler.GameComponents.Player;
import csc472.depaul.edu.metalcrawler.GameComponents.Sprite;

public class DrawTest extends View{
    TextPaint mTextPaint;
    int mTextColor = 0xff00;

    Paint mPiePaint;
    int mTextHeight;

    Bitmap bitmap;
    int texHeight;
    int texWidth;

    float x=64;
    float y=64;

    DrawState drawState;

    Blurb blurb = new Blurb();

    public DrawTest(Context context)
    {
        super(context);

        go = new DrawTest_GO(128,128,this);
        drawState = DrawStates.drawState_Base;
    }

    public DrawTest(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);

        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setStyle(Paint.Style.FILL);
        mPiePaint.setTextSize(mTextHeight);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.andremad_display_bmp);
        go = new DrawTest_GO(128,128,this);
        drawState = DrawStates.drawState_Base;

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
                Entity entity = ScanForEntity(GameManager.Instance().GetCurrentEnvironment(),e.getX(), e.getY());
                if (entity != null) {
                    entity.PrintEntity();
                    blurb.SetSprite(Bitmap.createScaledBitmap(entity.GetBitmap(),entity.GetBitmap().getWidth()*2,entity.GetBitmap().getHeight()*2,false));
                    blurb.SetText(entity.GetDescription());
                    drawState = DrawStates.drawState_Blurb;
                }
                else
                {
                    drawState = DrawStates.drawState_Base;
                }
                invalidate();

                break;
        }
        return false;
    }

    public Entity ScanForEntity(Environment environment, float x, float y)
    {
        for (Entity entity : GameManager.Instance().GetEntities())
        {
            if (x > entity.GetX() * Sprite.TILE_SIZE && x < entity.GetX() * Sprite.TILE_SIZE + Sprite.TILE_SIZE && y > entity.GetY() * Sprite.TILE_SIZE && y < entity.GetY() * Sprite.TILE_SIZE + Sprite.TILE_SIZE)
            {
                return entity;
            }
        }
        for (Actor actor : GameManager.Instance().GetActors())
        {
            if (x > actor.GetX() * Sprite.TILE_SIZE && x < actor.GetX() * Sprite.TILE_SIZE + Sprite.TILE_SIZE && y > actor.GetY() * Sprite.TILE_SIZE && y < actor.GetY() * Sprite.TILE_SIZE + Sprite.TILE_SIZE)
            {
                return actor;
            }
        }
        return null;
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
        drawState.Draw(canvas,this);
    }

    public void DrawGame(Canvas canvas)
    {
        GameManager.Instance().Draw(canvas);
    }


    public void DrawBlurb(Canvas canvas)
    {
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(ResourcesCompat.getFont(getContext(),R.font.dpcomic));
        mTextPaint.setTextSize(64);
        mPiePaint.setColor(Color.BLACK);
        mPiePaint.setAlpha(200);

        Point point = new Point();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getSize(point);
        canvas.drawRect(40,40,point.x -40,point.x-40,mPiePaint);

        canvas.drawBitmap(blurb.GetSprite(),50,50,
                mPiePaint);

        canvas.save();
        canvas.translate(60,180);
        StaticLayout.Builder.obtain(blurb.GetString(),0,blurb.GetString().length(),mTextPaint,point.x - 100).build().draw(canvas);
        canvas.restore();
    }

    public void ResetDrawState()
    {
        drawState = DrawStates.drawState_Base;
    }

}
