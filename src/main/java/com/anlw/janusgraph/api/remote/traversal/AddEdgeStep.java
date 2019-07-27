package com.anlw.janusgraph.api.remote.traversal;

import static org.apache.tinkerpop.gremlin.process.traversal.P.neq;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.anlw.janusgraph.api.remote.connect.RemoteGraphConnection;

public class AddEdgeStep {

	public static void main(String[] args) throws Exception {
		GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
		//g.V().drop().iterate();
		//preData(g);
		//step 1
		g.V(4344).as("a").out("created").in("created").where(neq("a")).addE("co-developer").from("a").property("year",2009).next();
		//step 2
		//g.V(3,4,5).aggregate("x").has("name","josh").as("a").select("x").unfold().hasLabel("software").addE("createdBy").to("a");
		//step 3
		//g.V().as("a").out("created").addE("createdBy").to("a").property("acl","public");
		//step 4
		//g.V(1).as("a").out("knows").addE("livesNear").from("a").property("year",2009).inV().inE("livesNear").values("year");
		System.exit(1);
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

	// 添加边的多种方式，注意区别
	// 添加边方式1:
	// from.to必须有
	public static void addE1(GraphTraversalSource g) {
		Vertex v1 = g.addV().property("name", "a").property("age", 29).next();
		Vertex v2 = g.addV().property("name", "b").property("age", 32).next();
		g.addE("friend").property("num", "13").from(v1).to(v2).next();
	}

	// 添加边方式2:
	// 虽然addE是由v1发起的，但是依然可以在v2和v3顶点进行建立边
	public static void addE2(GraphTraversalSource g) {
		Vertex v1 = g.addV().property("name", "a").property("age", 29).next();
		Vertex v2 = g.addV().property("name", "b").property("age", 32).next();
		Vertex v3 = g.addV().property("name", "c").property("age", 32).next();
		g.V().has("name", "a").addE("friend").from(v3).to(v2).next();
	}

	// 添加边方式3:
	// 虽然addE是由v1发起的，但是依然可以在v2和v3顶点进行建立边
	public static void addE3(GraphTraversalSource g) {
		Vertex v1 = g.addV().property("name", "a").property("age", 29).next();
		Vertex v2 = g.addV().property("name", "b").property("age", 32).next();
		// v1->v2
		g.V().has("name", "a").addE("friend").from(v1).to(v2).next();
		// v2->v1
		g.V().has("name", "a").addE("friend").from(v2).to(v1).next();
		// v1->v2(省略from，这个是以addE的调用顶点v1为基准，不能单独的from或者to该顶点，需要from或者to另外的顶点)
		g.V().has("name", "a").addE("friend").to(v2).next();
		// v2->v1(省略to，这个是以addE的调用顶点v1为基准，不能单独的from或者to该顶点，需要from或者to另外的顶点)
		g.V().has("name", "a").addE("friend").from(v2).next();
	}

	// 添加边方式4:
	public static void addE4(GraphTraversalSource g) {
		g.addV().property("name", "a").property("age", 29).next();
		g.addV().property("name", "b").property("age", 32).next();
		// v1->v2
		g.V().has("name", "a").as("a").V().has("name", "b").as("b").addE("friend").from("a").to("b").next();
		// v1->v2(省略to，这个是以addE的调用顶点v2为基准，不能单独的from或者to该顶点，需要from或者to另外的顶点)
		g.V().has("name", "a").as("a").V().has("name", "b").as("b").addE("friend").from("a").next();
		// v2->v1(省略from，这个是以addE的调用顶点v2为基准，不能单独的from或者to该顶点，需要from或者to另外的顶点)
		g.V().has("name", "a").as("a").V().has("name", "b").as("b").addE("friend").to("a").next();
	}

}
