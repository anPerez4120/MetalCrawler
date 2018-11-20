package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Random;

public abstract class SpawnChance {
    float minFrequency;
    float maxFrequency;

    float minCount;
    float maxCount;

    Random random = new Random();

    public float GetFrequency()
    {
        return minFrequency / maxFrequency;
    }

    public float GetCount()
    {
        return minCount / maxCount;
    }

    public float GetRandom(int depth) {return Math.round(minCount + ((CalculateCount(depth) - minCount) * random.nextFloat()));}

    public abstract float CalculateFrequency(int level);
    public abstract float CalculateCount(int level);
}
