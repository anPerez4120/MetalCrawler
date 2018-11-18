package csc472.depaul.edu.metalcrawler.GameComponents;

public class PlayerClass_Bruiser extends PlayerClass {
    @Override
    protected void Init() {
        maxHealth = 120;
        momentumScaling = 2;
        riposteScaling = 0.5f;
    }
}
