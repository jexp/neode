package org.neo4j.datasetbuilder.randomnumbers;

import java.util.Random;

public class NormalDistributionUniqueRandomNumberGenerator extends BaseUniqueRandomNumberGenerator
{
    public static RandomNumberGenerator normalDistribution()
    {
        return new NormalDistributionUniqueRandomNumberGenerator( );
    }

    protected int getNextNumber( int min, int upTo, Random random )
    {
        double gaussian = random.nextGaussian();
        int standardDeviation = upTo / 2;
        return (int) (min + standardDeviation + (gaussian * standardDeviation));
    }
}