package csc472.depaul.edu.metalcrawler.GameComponents;

public class JunkieSpawnChance extends SpawnChance {
    public JunkieSpawnChance()
    {
        minCount = 1;
        maxCount = 4;
        minFrequency = 0.5f;
        maxFrequency = 0.75f;
    }

    @Override
    public float CalculateFrequency(int level) {
        return 0;
    }

    @Override
    public float CalculateCount(int level) {
        if (level > 50)
            return 5;
        return minCount + Math.min(1,Math.round(1.0f - (float)level/20)) * (maxCount-minCount);
    }
}
