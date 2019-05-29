package com.anlw.janusgraph;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import com.anlw.janusgraph.remote.connect.RemoteGraphConnection;
import com.anlw.janusgraph.remote.connect.RemoteScriptConnection;
import com.anlw.janusgraph.remote.util.ScriptUtils;

public class Test {
	public static void main(String[] args) throws Exception {
		GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
		// AddVertexTest.createElements(g);
		//VertexUtils.deleteAllVertex(g);
		Client c = RemoteScriptConnection.getClient();
		ScriptUtils.submitScript(c, "g.addV(\"dd6\");");
	}
}
