package com.anlw.janusgraph.api.remote.traversal;

import java.util.List;

import static org.apache.tinkerpop.gremlin.process.traversal.P.*;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.anlw.janusgraph.api.remote.connect.RemoteGraphConnection;

public class ANoteOnPredicates {
	public static void main(String[] args) throws Exception {
		GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
		//g.V().drop().iterate();
		//preData(g);
		Byte s = new Byte("6");
		Vertex v1 = g.addV().property("name", "marko1").property("age", s).next();
		//Vertex v2 = g.addV().property("name", "josh").property("age", 32).next();
		List<Vertex> list = g.V().has("name","marko1").toList();
		//System.out.println(list.size());
		System.out.println(g.V().values("age").next().getClass());
		list.forEach((v)->{
			//System.out.println(v);
		});
		
		
	}

	// 测试数据
	private static void preData(GraphTraversalSource g) {
		Vertex v1 = g.addV().property("name", "marko").property("age", 29).next();
		Vertex v2 = g.addV().property("name", "josh").property("age", 32).next();
		Vertex v3 = g.addV().property("name", "peter").property("age", 35).next();
		Vertex v4 = g.addV().property("name", "lop").property("lang", "java").next();
		g.addE("created").from(v1).to(v4).next();
		g.addE("created").from(v2).to(v4).next();
		g.addE("created").from(v3).to(v4).next();
	}

}
