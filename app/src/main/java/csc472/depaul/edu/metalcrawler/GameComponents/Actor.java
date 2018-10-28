package csc472.depaul.edu.metalcrawler.GameComponents;

public class Actor extends Entity implements IMoving, IDamage {
    // TODO: Move
    public void MoveUp(){}
    public void MoveDown(){}
    public void MoveLeft(){}
    public void MoveRight(){}
    public void JumpToPosition(int x, int y){}

    // TODO: Daamge
    public void Damage(float damage){};
    public void Heal(float heal){};
    public void RestoreHealth(){};
    public void Die(){}
}
