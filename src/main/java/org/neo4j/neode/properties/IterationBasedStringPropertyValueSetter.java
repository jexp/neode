package org.neo4j.neode.properties;

import java.util.Random;

import org.neo4j.graphdb.PropertyContainer;

class IterationBasedStringPropertyValueSetter extends PropertyValueSetter
{
    @Override
    public Object generateValue( PropertyContainer propertyContainer, String nodeLabel, int iteration, Random random )
    {
        return String.format( "%s-%s", nodeLabel, iteration + 1 );
    }
}
