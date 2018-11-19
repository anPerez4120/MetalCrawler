package csc472.depaul.edu.metalcrawler.GameComponents;

public abstract class SpawnChance {
    float minFrequency;
    float maxFrequency;

    float minCount;
    float maxCount;

    public float GetFrequency()
    {
        return minFrequency / maxFrequency;
    }

    public float GetCount()
    {
        return minCount / maxCount;
    }

    public abstract float CalculateFrequency(int level);
    public abstract float CalculateCount(int level);
}
