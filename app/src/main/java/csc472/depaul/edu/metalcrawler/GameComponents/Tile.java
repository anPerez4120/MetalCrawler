package csc472.depaul.edu.metalcrawler.GameComponents;

public class Tile extends Sprite {
    Entity occupyingEntity;

    public Tile()
    {
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



}
