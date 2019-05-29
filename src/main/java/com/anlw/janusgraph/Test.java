package com.anlw.janusgraph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import com.anlw.janusgraph.remote.connect.RemoteGraphConnection;

public class Test {
	public static void main(String[] args) throws Exception {
		GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
		// AddVertexTest.createElements(g);
		//VertexUtils.deleteAllVertex(g);
		g.V().has("name", "jupiter").property("ts", 1).iterate();
	}
}
