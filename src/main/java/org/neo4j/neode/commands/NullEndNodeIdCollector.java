package org.neo4j.neode.commands;

import java.util.Collections;
import java.util.List;

public class NullEndNodeIdCollector implements NodeIdCollector
{
    public static NodeIdCollector INSTANCE = new NullEndNodeIdCollector();

    private NullEndNodeIdCollector(){}

    @Override
    public void add( Long nodeId )
    {
        // Do nothing
    }

    @Override
    public List<Long> nodeIds()
    {
        return Collections.emptyList();
    }
}
