package org.neo4j.neode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.neo4j.graphdb.Node;
import org.neo4j.neode.logging.Log;

class Commands
{
    private final List<BatchCommand<NodeIdCollection>> commands;
    private final CommandSelectionStrategy commandSelectionStrategy;

    Commands( List<BatchCommand<NodeIdCollection>> commands, CommandSelectionStrategy commandSelectionStrategy )
    {
        this.commands = commands;
        this.commandSelectionStrategy = commandSelectionStrategy;
    }

    public BatchCommand<NodeIdCollection> nextCommand( Node currentNode, Random random )
    {
        return commandSelectionStrategy.nextCommand( commands, currentNode, random );
    }

    public List<NodeIdCollection> results()
    {
        List<NodeIdCollection> results = new ArrayList<NodeIdCollection>();
        for ( BatchCommand<NodeIdCollection> command : commands )
        {
            results.add( command.results() );
        }
        return results;
    }

    public void onBegin( Log log )
    {
        for ( BatchCommand<NodeIdCollection> command : commands )
        {
            log.write( String.format( "      [%s]", command.shortDescription() ) );
            command.onBegin( log );
        }
    }

    public void onEnd( Log log )
    {
        for ( BatchCommand<NodeIdCollection> command : commands )
        {
            log.write( String.format( "      [%s]", command.shortDescription() ) );
            command.onEnd( log );
        }

    }
}
