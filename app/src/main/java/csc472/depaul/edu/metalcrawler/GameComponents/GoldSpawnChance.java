package csc472.depaul.edu.metalcrawler.GameComponents;

public class GoldSpawnChance extends SpawnChance {
    float maxLevel = 10;
    public GoldSpawnChance()
    {
        minCount = 1;
        maxCount = 3;
        minFrequency = 0.5f;
        maxFrequency = 0.75f;
    }

    @Override
    public float CalculateFrequency(int level) {
        return minFrequency + (((float)Math.log(level)) * (maxFrequency - minFrequency));
    }

    @Override
    public float CalculateCount(int level) {
        return minCount + Math.min(1,Math.round((float)level/maxLevel)) * (maxCount-minCount);
    }
}
