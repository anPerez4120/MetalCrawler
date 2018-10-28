package csc472.depaul.edu.metalcrawler.GameComponents;

public interface IMoving {
    void MoveUp();
    void MoveDown();
    void MoveLeft();
    void MoveRight();

    void JumpToPosition(int x, int y);
}
