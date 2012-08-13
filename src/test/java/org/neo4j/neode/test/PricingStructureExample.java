package org.neo4j.neode.test;

import static org.neo4j.neode.TargetNodesSpecificationsChoices.randomChoice;
import static org.neo4j.neode.TargetNodesSpecification.getOrCreate;
import static org.neo4j.neode.Range.minMax;
import static org.neo4j.neode.properties.Property.indexableProperty;
import static org.neo4j.neode.properties.Property.property;
import static org.neo4j.neode.properties.PropertyValueGenerator.integerRange;

import java.util.List;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.neode.DatasetManager;
import org.neo4j.neode.NodeCollection;
import org.neo4j.neode.Dataset;
import org.neo4j.neode.NodeSpecification;
import org.neo4j.neode.logging.SysOutLog;

public class PricingStructureExample
{
    @Test
    public void buildPricingStructure()
    {
        GraphDatabaseService db = Db.tempDb();
        DatasetManager datasetManager = new DatasetManager( db, SysOutLog.INSTANCE );
        Dataset dataset = datasetManager.newDataset( "Pricing tree" );

        NodeSpecification root = datasetManager.newNodeSpecification( "root", indexableProperty( "name" ) );
        NodeSpecification intermediate = datasetManager.newNodeSpecification( "intermediate" );
        NodeSpecification leaf = datasetManager.newNodeSpecification( "leaf", property( "price", integerRange( 1, 10 ) ) );

        NodeCollection roots = root.create( 10 ).update( dataset );

        List<NodeCollection> subnodes = roots.createRelationshipsTo(
                randomChoice(
                        getOrCreate( intermediate, 20 )
                                .relationship( "CONNECTED_TO",
                                        property( "quantity", integerRange( 1, 5 ) ) )
                                .relationshipConstraints( minMax( 1, 3 ) ),
                        getOrCreate( leaf, 100 )
                                .relationship( "CONNECTED_TO",
                                        property( "quantity", integerRange( 1, 5 ) ) )
                                .relationshipConstraints( minMax( 1, 3 ) ) ) )
                .update( dataset );

        for ( NodeCollection subnode : subnodes )
        {
            if ( subnode.label().equals( "intermediate" ) )
            {
                subnode.createRelationshipsTo(
                        getOrCreate( leaf, 100 )
                                .relationship( "CONNECTED_TO",
                                        property( "quantity", integerRange( 1, 5 ) ) )
                                .relationshipConstraints( minMax( 1, 3 ) ) )
                        .updateNoReturn( dataset );

            }
        }

        dataset.end();

        db.shutdown();

    }

}