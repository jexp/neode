/*
 * Copyright (C) 2012 Neo Technology
 * All rights reserved
 */
package org.neo4j.neode.properties;

import java.util.Random;

import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.neode.Range;
import org.neo4j.neode.probabilities.ProbabilityDistribution;

public abstract class PropertyValueSetter
{
    public static PropertyValueSetter counterBased()
    {
        return new CounterBasedStringPropertyValueSetter();
    }

    public static PropertyValueSetter iterationBased()
    {
        return new IterationBasedStringPropertyValueSetter();
    }

    public static PropertyValueSetter idBased()
    {
        return new IdBasedStringPropertyValueSetter();
    }

    public static PropertyValueSetter integerRange( int min, int max, ProbabilityDistribution probabilityDistribution )
    {
        return new RangeBasedIntegerPropertyValueSetter( Range.minMax( min, max ), probabilityDistribution );
    }

    public static PropertyValueSetter integerRange( int min, int max )
    {
        return integerRange( min, max, ProbabilityDistribution.flatDistribution() );
    }

    public abstract Object generateValue( PropertyContainer propertyContainer, String nodeLabel, int iteration,
                                          Random random );
}
