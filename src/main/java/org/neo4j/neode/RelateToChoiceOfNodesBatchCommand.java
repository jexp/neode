package org.neo4j.neode;

import java.util.List;
import java.util.Random;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.neode.logging.Log;

class RelateToChoiceOfNodesBatchCommand implements BatchCommand<List<NodeIdCollection>>
{
    private final NodeIdCollection startNodeIds;
    private final Commands commands;
    private final int batchSize;

    RelateToChoiceOfNodesBatchCommand( NodeIdCollection startNodeIds, Commands commands, int batchSize )
    {
        this.startNodeIds = startNodeIds;
        this.commands = commands;
        this.batchSize = batchSize;
    }

    @Override
    public int numberOfIterations()
    {
        return startNodeIds.size();
    }

    @Override
    public int batchSize()
    {
        return batchSize;
    }

    @Override
    public void execute( GraphDatabaseService db, int iteration, Random random )
    {
        Node currentNode = db.getNodeById( startNodeIds.getIdByPosition( iteration ) );
        execute( currentNode, db, iteration, random );
    }

    @Override
    public void execute( Node currentNode, GraphDatabaseService db, int iteration, Random random )
    {
        BatchCommand<NodeIdCollection> nextCommand = commands.nextCommand( currentNode, random );
        nextCommand.execute( currentNode, db, iteration, random );
    }

    @Override
    public String description()
    {
        return "Creating choice of relationships.";
    }

    @Override
    public String shortDescription()
    {
        return "Create choice of relationships";
    }

    @Override
    public void onBegin( Log log )
    {
        commands.onBegin(log);
    }

    @Override
    public void onEnd( Log log )
    {
        commands.onEnd(log);
    }

    @Override
    public List<NodeIdCollection> results()
    {
        return commands.results();
    }
}
