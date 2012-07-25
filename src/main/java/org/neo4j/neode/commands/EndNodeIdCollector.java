package org.neo4j.neode.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EndNodeIdCollector implements NodeIdCollector
{
    private final Set<Long> ids;

    public EndNodeIdCollector( )
    {
        ids = new HashSet<Long>(  );
    }

    @Override
    public void add( Long nodeId )
    {
        ids.add( nodeId );
    }

    @Override
    public List<Long> nodeIds()
    {
        return new ArrayList<Long>( ids );
    }
}
