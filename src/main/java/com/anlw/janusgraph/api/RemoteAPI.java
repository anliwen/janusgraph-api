package com.anlw.janusgraph.api;

import static com.anlw.janusgraph.remote.constant.Constants.IN_V;
import static com.anlw.janusgraph.remote.constant.Constants.LABEL;
import static com.anlw.janusgraph.remote.constant.Constants.OUT_V;
import static com.anlw.janusgraph.remote.constant.Constants.REASON;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.Bindings;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class RemoteAPI {

	// 获取远程Server连接
	public static GraphTraversalSource getConnection() throws Exception {
		GraphTraversalSource g = AnonymousTraversalSource.traversal()
				.withRemote("config/client/remote-graph.properties");
		return g;
	}

	// 创建顶点
	public static void addVertex(GraphTraversalSource g) {
		// 创建无标签的顶点，默认标签vertex
		Vertex v1 = g.addV().next();
		System.out.println(v1.id());

		// 创建有标签的顶点，标签一旦创建，不可更改
		Vertex v2 = g.addV("student").next();
		System.out.println(v2.id());

		// 创建无标签单属性顶点
		Vertex v3 = g.addV().property("name", "kobe").next();
		System.out.println(v3.id());

		// 创建无标签多属性顶点
		Vertex v4 = g.addV().property("name", "Iverson").property("age", 37).next();
		System.out.println(v4.id());

		// 创建有标签单属性顶点
		Vertex v5 = g.addV("teacher").property("name", "zhangsan").next();
		System.out.println(v5.id());

		// 创建有标签多属性顶点
		Vertex v6 = g.addV("docter").property("name", "lisi").property("age", 14).next();
		System.out.println(v6.id());
	}

	// 删除所有顶点，边
	public static void dropAllVertex(GraphTraversalSource g) {
		g.V().drop().iterate();
	}

	// 查询顶点，就是根据顶点的ID，标签，key，value来回相互查询
	public static void queryVertex(GraphTraversalSource g) {
		// 查询所有顶点，这个方法不推荐使用，数据量大的时候会卡死，推荐遍历
		List<Vertex> list1 = g.V().toList();
		list1.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据标签查询顶点，这个方法不推荐使用，数据量大的时候会卡死，推荐遍历
		List<Vertex> list2 = g.V().hasLabel("teacher").toList();
		list2.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据单属性查看顶点
		List<Vertex> list3 = g.V().has("name", "zhangsan").toList();
		list3.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据多属性查看顶点
		List<Vertex> list4 = g.V().has("name", "Iverson").has("age", 37).toList();
		list4.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据属性key的不包含查询，属性中key不包含name的所有顶点
		List<Vertex> list5 = g.V().hasNot("name").toList();
		list5.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据单个ID查看顶点
		Map<Object, Object> map1 = g.V(20656).valueMap().next();
		map1.forEach((key, value) -> {
			System.out.println(key + "---" + value);
		});

		// 根据多个ID查看顶点
		List<Map<Object, Object>> list7 = g.V(20656, 24752).valueMap().toList();
		list7.forEach((map) -> {
			map.forEach((key, value) -> {
				System.out.println(key + "---" + value);
			});
		});

		// 根据属性查看顶点的标签
		List<String> list8 = g.V().has("name", "zhangsan").label().toList();
		list8.forEach((v) -> {
			System.out.println(v);
		});

		// 根据属性查看顶点的key-value
		List<Map<Object, Object>> list9 = g.V().has("name", "lisi").valueMap().toList();
		list9.forEach((map) -> {
			map.forEach((key, value) -> {
				System.out.println(key + "---" + value);
			});
		});

		// 查看所有顶点的标签，toSet为去重，如果不去重请使用toList
		Set<String> list10 = g.V().label().toSet();
		list10.forEach((v) -> {
			System.out.println(v);
		});

		// 根据标签和属性一起查
		List<Vertex> list11 = g.V().hasLabel("teacher").has("name", "zhangsan").toList();
		list11.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据ID查询顶点
		Vertex v = g.V(32896).next();
		System.out.println(v.label());
	}

	// 创建边
	public static void addEdge(GraphTraversalSource g) {
		// first method
		Vertex v1 = g.addV().property("name", "Iverson").property("age", 36).next();
		Vertex v2 = g.addV().property("name", "Cluo").property("age", 35).next();
		Edge e1 = g.addE("friend").property("name", "relationship").from(v1).to(v2).next();
		System.out.println(e1.id());

		// second method
		// 推荐采用这种方式
		Vertex v3 = g.addV().property("name", "Iverson").property("age", 37).next();
		Vertex v4 = g.addV().property("name", "Cluo").property("age", 34).next();

		// Use bindings to allow the Gremlin Server to cache traversals that
		// will be reused with different parameters. This minimizes the
		// number of scripts that need to be compiled and cached on the server.
		// https://tinkerpop.apache.org/docs/3.2.6/reference/#parameterized-scripts
		final Bindings b = Bindings.instance();
		Edge e2 = g.V(b.of(OUT_V, v3)).as("a").V(b.of(IN_V, v4)).addE(b.of(LABEL, "friends"))
				.property(REASON, b.of(REASON, "ball")).from("a").next();
		System.out.println(e2.id());
	}

	// 查看边
	public static void queryEdge(GraphTraversalSource g) {
		System.out.println(g.V().has("name", "Iverson").as("a").outE().as("b").inV().has("age",35).as("c").select("a").next());
		System.out.println(g.V().has("name", "Iverson").as("a").outE().as("b").inV().has("age",35).as("c").select("b").next());
		System.out.println(g.V().has("name", "Iverson").as("a").outE().as("b").inV().has("age",35).as("c").select("c").next());
		//System.out.println(g.V(40992920).outE().label().next());
	}

	public static void main(String[] args) throws Exception {
		GraphTraversalSource g = getConnection();
		//dropAllVertex(getConnection());
		// addVertex(g);
		// queryVertex(g);
		// addEdge(g);
		//queryEdge(g);
		System.out.println(g.V(53424).outE().label().next());
	}

}
