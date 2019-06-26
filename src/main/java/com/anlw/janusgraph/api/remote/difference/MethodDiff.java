package com.anlw.janusgraph.api.remote.difference;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * 
 * @author Anlw
 *
 * @date Jun 26, 2019
 */
public class MethodDiff {

	// 获取远程Server连接
	public static GraphTraversalSource getConnection() throws Exception {
		GraphTraversalSource g = AnonymousTraversalSource.traversal()
				.withRemote("config/client/remote-graph.properties");
		return g;
	}

	// in(),in(String... edgeLabels),inE(),inE(String...
	// edgeLabels),out(),out(String...
	// edgeLabels),outE(),outE(String... edgeLabels),both(),both(String...
	// edgeLabels),
	// bothE(),bothE(String... edgeLabels),inV(),outV,bothV(),otherV()
	public static void test1() throws Exception {
		GraphTraversalSource g = getConnection();
		dataForTest1(g);
		// in():以顶点为基准，获取所有指向该顶点的邻顶点
		List<Vertex> list1 = g.V().hasLabel("label-c").in().toList();
		list1.forEach((v) -> {
			System.out.println(v.toString());
		});
		// in(String...
		// edgeLabels):以顶点为基准，获取所有指向该顶点的邻顶点，然后根据该顶点与所有邻顶点的边的EdgeLabel进行过滤，获取指定label邻顶点
		List<Vertex> list2 = g.V().hasLabel("label-c").in("friend").toList();
		list2.forEach((v) -> {
			System.out.println(v.toString());
		});
		// inE():以顶点为基准，获取所有指向该顶点的边
		List<Edge> list3 = g.V().hasLabel("label-c").inE().toList();
		list3.forEach((v) -> {
			System.out.println(v.toString());
		});
		// inE(String... edgeLabels):以顶点为基准，获取所有指向该顶点的边，然后根据该边的EdgeLabel进行过滤
		List<Edge> list4 = g.V().hasLabel("label-c").inE("brother").toList();
		list4.forEach((v) -> {
			System.out.println(v.toString());
		});

		// out():以顶点为基准，获取所有该顶点指向的邻顶点
		List<Vertex> list5 = g.V().hasLabel("label-c").out().toList();
		list5.forEach((v) -> {
			System.out.println(v.toString());
		});
		// out(String...
		// edgeLabels):以顶点为基准，获取所有该顶点指向的邻顶点，然后根据该顶点与所有邻顶点的边的EdgeLabel进行过滤，获取指定label邻顶点
		List<Vertex> list6 = g.V().hasLabel("label-c").out("brother", "friend").toList();
		list6.forEach((v) -> {
			System.out.println(v.toString());
		});

		// outE():以顶点为基准，获取所有该顶点指向其他顶点的边
		List<Edge> list7 = g.V().hasLabel("label-c").outE().toList();
		list7.forEach((v) -> {
			System.out.println(v.toString());
		});

		// outE(String...
		// edgeLabels):以顶点为基准，获取所有该顶点指向其他顶点的边，然后根据该顶点与所有邻顶点的边的EdgeLabel进行过滤，获取指定label邻顶点
		List<Edge> list8 = g.V().hasLabel("label-c").outE("brother").toList();
		list8.forEach((v) -> {
			System.out.println(v.toString());
		});

		// both():以顶点为基准，获取所有邻顶点(双向)
		List<Vertex> list9 = g.V().hasLabel("label-c").both().toList();
		list9.forEach((v) -> {
			System.out.println(v.toString());
		});
		// both(String...
		// edgeLabels):以顶点为基准，获取所有邻顶点(双向)，然后根据该顶点与所有邻顶点的边的EdgeLabel进行过滤，获取指定label邻顶点
		List<Vertex> list10 = g.V().hasLabel("label-c").both("friend").toList();
		list10.forEach((v) -> {
			System.out.println(v.toString());
		});

		// bothE():以顶点为基准，获取该顶点所有边(双向)
		List<Edge> list11 = g.V().hasLabel("label-c").bothE().toList();
		list11.forEach((v) -> {
			System.out.println(v.toString());
		});
		// bothE(String...
		// edgeLabels):以顶点为基准，获取该顶点所有边(双向)，然后根据该顶点与所有邻顶点的边的EdgeLabel进行过滤，获取指定label邻顶点
		List<Edge> list12 = g.V().hasLabel("label-c").bothE("friend").toList();
		list12.forEach((v) -> {
			System.out.println(v.toString());
		});
		// outV():以边为基准，获取所有边的所有起始顶点
		List<Vertex> list13 = g.V().hasLabel("label-c").inE().outV().toList();
		list13.forEach((v) -> {
			System.out.println(v.toString());
		});
		// inV():以边为基准，获取所有边的所有到达顶点
		List<Vertex> list14 = g.V().hasLabel("label-c").inE().inV().toList();
		list14.forEach((v) -> {
			System.out.println(v.toString());
		});
		// bothV():以边为基准，获取所有边的所有顶点(双向)
		List<Vertex> list15 = g.V().hasLabel("label-c").inE().bothV().toList();
		list15.forEach((v) -> {
			System.out.println(v.toString());
		});
		// otherV():以边为基准，访问边的伙伴顶点，即相对于基准顶点而言的另一端的顶点
		List<Vertex> list16 = g.V().hasLabel("label-c").outE().otherV().toList();
		list16.forEach((v) -> {
			System.out.println(v.toString());
		});
	}

	public static void main(String[] args) throws Exception {
		test1();
	}

	// 为test1()制造测试数据
	private static void dataForTest1(GraphTraversalSource g) {
		Vertex v1 = g.addV("label-a").property("name", "a").property("age", 1).next();
		Vertex v2 = g.addV("label-b").property("name", "b").property("age", 2).next();
		Vertex v3 = g.addV("label-c").property("name", "c").property("age", 3).next();
		Vertex v4 = g.addV("label-d").property("name", "d").property("age", 4).next();
		Vertex v5 = g.addV("label-e").property("name", "e").property("age", 5).next();
		Vertex v6 = g.addV("label-f").property("name", "f").property("age", 6).next();
		g.addE("friend").from(v1).to(v3).next();
		g.addE("brother").from(v2).to(v3).next();
		g.addE("friend").from(v3).to(v4).next();
		g.addE("brother").from(v3).to(v5).next();
		g.addE("brother").from(v3).to(v6).next();
	}

}
