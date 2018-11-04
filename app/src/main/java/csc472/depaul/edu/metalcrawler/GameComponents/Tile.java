package csc472.depaul.edu.metalcrawler.GameComponents;

import android.view.View;

public class Tile extends Sprite {
    Entity occupyingEntity;

    public Tile(View view)
    {
        super(view);
        drawLayer = 0;
        occupyingEntity = null;
    }

    public Tile(View view, int x, int y)
    {
        super(view,x,y);
        occupyingEntity = null;
    }


    public Entity GetEntity()
    {
        return occupyingEntity;
    }

    public void SetEntity(Entity entity)
    {
        occupyingEntity = entity;
    }

    public void Recycle()
    {
        TileFactory.Instance().ReturnTile(this);
    }

    @Override
    protected void SetDrawLayer()
    {
        drawLayer = 0;
    }

}
