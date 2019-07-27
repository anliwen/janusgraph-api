package com.anlw.janusgraph.api.remote.connect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

public class Test {
	public static void main(String[] args) throws Exception {
		GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
		//g.addV("lll").property("aa", "dd").next();
		 GraphTraversal<Vertex, Vertex> traversal = g.addV("lll");
		 Map<String, Object> values = new HashMap<>();
		 values.put("aa","dddddddddddddddd");
		 values.forEach((k, v) -> {
	            if (v instanceof List) {
	                traversal.property(VertexProperty.Cardinality.list, k, v);
	            } else if (v instanceof Set) {
	                traversal.property(VertexProperty.Cardinality.set, k, v);
	            } else {
	                traversal.property(VertexProperty.Cardinality.single, k, v);
	            }
	        });
	        traversal.next();
		 
	}

}
