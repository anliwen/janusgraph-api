package com.anlw.janusgraph.api;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.DefaultGraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * 
 * @author Anlw
 *
 * @date Jun 4, 2019
 */
public class RemoteTraversalAPI {
	// 获取远程Server连接
	public static GraphTraversalSource getConnection() throws Exception {
		GraphTraversalSource g = AnonymousTraversalSource.traversal()
				.withRemote("config/client/remote-graph.properties");
		return g;
	}

	public static void main(String[] args) throws Exception {
		GraphTraversalSource g = getConnection();
		// DefaultGraphTraversal<Vertex, Vertex> g1 = (DefaultGraphTraversal<Vertex,
		// Vertex>) g.addV();
		// g1.next();
		g.V().drop().iterate();
		g.addV().property("name","1").next();
		g.addV().property("name","2").next();
		g.addV().property("name","3").next();
		g.addV().property("name","4").next();
		g.addV().property("name","5").next();//35142
		List<Vertex> list = g.V().toList();
		list.forEach((v)->{
			System.out.println(v.id());
		});
		System.exit(0);
	}
}
