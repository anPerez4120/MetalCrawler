package csc472.depaul.edu.metalcrawler.GameComponents;

public class AlchemistSpawnChance extends SpawnChance {
    public AlchemistSpawnChance()
    {
        minCount = 1;
        maxCount = 3;
        minFrequency = 0.5f;
        maxFrequency = 0.75f;
    }

    @Override
    public float CalculateFrequency(int level) {
        return 0;
    }

    int maxLevel = 45;
    @Override
    public float CalculateCount(int level) {
        if (level < 5)
            return 0;

        return minCount + Math.min(1,Math.round((float)level/maxLevel)) * (maxCount-minCount);
    }
}
