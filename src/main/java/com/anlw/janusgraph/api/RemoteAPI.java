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

	// 1.查询顶点，就是根据顶点的ID，Label，Key，Value作为条件查询Vertex
	// 2.next()后指针指向当前节点，用于添加，查询后使用
	// 3.iterate()后指针指向下一个节点，用于删除后使用
	// 4.查询结果如果使用next则返回符合条件的第一个Vertex，所以查询后的结果都应该使用toList()
	// 5.所有的查询都需要适可而止，否则在数据量巨大的情况下容易发生卡死
	// 6.JanusGraph使用fluent api进行多条件查询，查询是逐级递减过滤，而并非'or'和'and'的关系
	public static void queryVertex(GraphTraversalSource g) {
		// 基本查询
		// 查询所有顶点
		List<Vertex> list1 = g.V().toList();
		list1.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据ID查询顶点
		List<Vertex> list2 = g.V("8224").toList();
		list2.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据标签查询顶点
		List<Vertex> list3 = g.V().hasLabel("teacher").toList();
		list3.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据单属性查看顶点
		List<Vertex> list4 = g.V().has("name", "zhangsan").toList();
		list4.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据多属性查看顶点
		List<Vertex> list5 = g.V().has("name", "Iverson").has("age", 37).toList();
		list5.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据属性key的不包含查询，属性中key不包含name的所有顶点
		List<Vertex> list6 = g.V().hasNot("name").toList();
		list6.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据属性key的不包含查询，属性中key不包含name的所有顶点
		List<Vertex> list7 = g.V().has("name").toList();
		list7.forEach((v) -> {
			System.out.println(v.id());
		});

		// 符合查询，包括一些具体的Label/Key等的查询，其实就是根据上述基本查询或者基于fluent
		// api查询出的多个或者一个Vertex，然后在基于该Vertex查询其具体的信息
		// 根据单个ID查看顶点的所有Key-Value
		Map<Object, Object> map1 = g.V(40968384).valueMap().next();
		map1.forEach((key, value) -> {
			System.out.println(key + "---" + value);
		});

		// 根据多个ID查看顶点的所有Key-Value
		List<Map<Object, Object>> list8 = g.V(40968384, 8224).valueMap().toList();
		list8.forEach((map) -> {
			map.forEach((key, value) -> {
				System.out.println(key + "---" + value);
			});
		});

		// 根据属性(Key-Value)查看顶点的标签
		List<String> list9 = g.V().has("name", "zhangsan").label().toList();
		list9.forEach((v) -> {
			System.out.println(v);
		});

		// 根据属性(Key-Value)查看顶点的Key-Value
		List<Map<Object, Object>> list10 = g.V().has("name", "lisi").valueMap().toList();
		list10.forEach((map) -> {
			map.forEach((key, value) -> {
				System.out.println(key + "---" + value);
			});
		});

		// 查看所有顶点的标签，toSet为去重，如果不去重请使用toList
		Set<String> list11 = g.V().label().toSet();
		list11.forEach((v) -> {
			System.out.println(v);
		});

		// 根据标签和属性一起查
		List<Vertex> list12 = g.V().hasLabel("teacher").has("name", "zhangsan").toList();
		list12.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据ID查询顶点
		Vertex v = g.V(40968384).next();
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
		// 选择一个Vertex->选择出去的线outE->进入指定的Vertex inV
		// 选择一个Vertex->选择进来的线inE->选择出自哪个Vertex outV
		Vertex v1 = (Vertex) (g.V().has("name", "Iverson").as("a").outE().as("b").inV().has("age", 34).as("c")
				.select("a").next());
		System.out.println(v1.id());
		Edge e = (Edge) (g.V().has("name", "Iverson").as("a").outE().as("b").inV().has("age", 34).as("c").select("b")
				.next());
		System.out.println(e.id());
		Vertex v2 = (Vertex) (g.V().has("name", "Iverson").as("a").outE().as("b").inV().has("age", 34).as("c")
				.select("c").next());
		System.out.println(v2.id());
		// 选择ID为40980672的顶点所有进来的边的id，可能是一条也可能是多条
		System.out.println(g.V(40980672).inE().id().toList());
	}

	// 操作属性
	// 操作属性也是基于上述查询结果进行操作，返回值等与查询一样，唯一不一样的就是对查询结果做了修改
	public static void operateVertexEdgeProperty(GraphTraversalSource g) {

		// 根据顶点ID对单个顶点增加/修改属性，有则修改，无则增加
		Vertex v1 = g.V(57520).property("name", "kobe").next();
		System.out.println(v1.id());

		// 根据顶点ID对多个顶点增加/修改属性，有则修改，无则增加
		List<Vertex> list1 = g.V(57520, 53424).property("sex", "女").toList();
		list1.forEach((v) -> {
			System.out.println(v.id());
		});

		// 根据顶点ID(多个或者单个顶点)查看所有属性值
		List<Object> list2 = g.V(57520, 53424).values().toList();
		list2.forEach((v) -> {
			System.out.println(v);
		});

		// 根据顶点ID(多个或者单个顶点)查看具体key(单个或多个key)的属性值
		List<Object> list3 = g.V(57520, 53424).values("name").toList();
		list3.forEach((v) -> {
			System.out.println(v);
		});

		// 根据顶点属性(多个或者单个顶点)查看所有属性值
		List<Object> list4 = g.V().has("name", "kobe").values().toList();
		list4.forEach((v) -> {
			System.out.println(v);
		});

		// 根据顶点属性(多个或者单个顶点)查看具体key(单个或多个key)的属性值
		List<Object> list5 = g.V().has("name", "Iverson").values("name", "age").toList();
		list5.forEach((v) -> {
			System.out.println(v);
		});

		// 删除顶点属性
		g.V(53424).properties("name").drop().iterate();

		// 根据边ID增加/修改属性，有则修改，无则增加
		Edge e1 = g.E("hee-1580-2e51-18ds").property("name", "classmates").next();
		System.out.println(e1.id());

		// 查看边属性
		List<Map<Object, Object>> list6 = g.E("hee-1580-2e51-18ds").valueMap().toList();
		list6.forEach((map) -> {
			map.forEach((key, value) -> {
				System.out.println(key + "---" + value);
			});
		});

		// 删除所有边或者具体边
		g.E().drop().iterate();
	}

	public static void main(String[] args) throws Exception {
		GraphTraversalSource g = getConnection();
		System.out.println(g.V(40980672).inE().id().toList());
		System.exit(1);
	}
}
