package csc472.depaul.edu.metalcrawler.GameComponents;

public abstract class PlayerClass {
    float maxHealth;
    float momentumScaling;
    float riposteScaling;

    protected abstract void Init();

    public PlayerClass()
    {
        Init();
    }

    public float GetMaxHealth(){return maxHealth;}
    public float GetMomentumScaling(){return momentumScaling;}
    public float GetRiposteScaling(){return riposteScaling;}
}
