package com.anlw.janusgraph.api.local.traversal;

import org.janusgraph.core.Cardinality;
import org.janusgraph.core.EdgeLabel;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.schema.JanusGraphManagement;

public class EdgeLableMultiplicity {

	public static void main(String[] args) throws InterruptedException {
		JanusGraph janusGraph = JanusGraphFactory.open("config/server/janusgraph-cassandra-es.properties");
		JanusGraphManagement mgmt = janusGraph.openManagement();
		EdgeLabel follow = mgmt.makeEdgeLabel("ss11").multiplicity(Multiplicity.MULTI).make();
		PropertyKey birthDate = mgmt.makePropertyKey("birthDate11").dataType(Long.class).cardinality(Cardinality.SINGLE).make();
		mgmt.addProperties(follow,birthDate);
		System.out.println(mgmt.printSchema());
		mgmt.commit();
		
		
		
	}
}
