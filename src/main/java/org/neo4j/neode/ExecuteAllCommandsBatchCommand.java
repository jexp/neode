package org.neo4j.neode;

import java.util.Random;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.neode.logging.Log;

public class ExecuteAllCommandsBatchCommand implements BatchCommand<NodeIdCollection>
{
    private final Iterable<BatchCommand<NodeIdCollection>> commands;

    public ExecuteAllCommandsBatchCommand( Iterable<BatchCommand<NodeIdCollection>> commands )
    {
        this.commands = commands;
    }

    @Override
    public int numberOfIterations()
    {
        return 0;
    }

    @Override
    public int batchSize()
    {
        return 0;
    }

    @Override
    public void execute( GraphDatabaseService db, int iteration, Random random )
    {
        for ( BatchCommand<NodeIdCollection> command : commands )
        {
            command.execute( db, iteration, random );
        }
    }

    @Override
    public void execute( Node currentNode, GraphDatabaseService db, int iteration, Random random )
    {
        execute( db, iteration, random );
    }

    @Override
    public String description()
    {
        return null;
    }

    @Override
    public String shortDescription()
    {
        return null;
    }

    @Override
    public void onBegin( Log log )
    {
    }

    @Override
    public void onEnd( Log log )
    {
    }

    @Override
    public NodeIdCollection results()
    {
        return null;
    }
}