package com.anlw.janusgraph.remote.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.Bindings;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anlw.janusgraph.model.VertexDO;
import com.google.common.base.Strings;

public class VertexUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(VertexUtils.class);

	// 添加单个节点
	public static Vertex addVertex(GraphTraversalSource g, VertexDO v) {
		try {
			LOGGER.info("开始添加节点...");
			String label = v.getLabel();
			boolean checkResult = Strings.isNullOrEmpty(label);
			GraphTraversal<Vertex, Vertex> graphTraversal;
			if (checkResult) {
				graphTraversal = g.addV();
			} else {
				graphTraversal = g.addV(label);
			}

			Map<String, Object> map = v.getProperties();
			if (null == map || map.size() == 0) {
				return graphTraversal.next();
			}
			final Bindings b = Bindings.instance();
			map.forEach((key, value) -> {
				graphTraversal.property(key, b.of(key, value));
			});
			return graphTraversal.next();
		} catch (Exception e) {
			LOGGER.info("添加节点异常，数据回滚...");
			g.tx().rollback();
			e.printStackTrace();
		}
		return null;
	}

	// 批量添加节点
	public static List<Vertex> addBatchVertexs(GraphTraversalSource g, List<VertexDO> list) {
		if (null == list || list.isEmpty()) {
			LOGGER.info("需要添加的节点个数为0");
			return null;
		}
		try {
			List<Vertex> resultList = new ArrayList<>();
			LOGGER.info("开始批量添加节点...");
			list.forEach((v) -> {
				String label = v.getLabel();
				boolean checkResult = Strings.isNullOrEmpty(label);
				GraphTraversal<Vertex, Vertex> graphTraversal;
				if (checkResult) {
					graphTraversal = g.addV();
				} else {
					graphTraversal = g.addV(label);
				}

				Map<String, Object> map = v.getProperties();
				if (null == map || map.size() == 0) {
					resultList.add(graphTraversal.next());
				}
				final Bindings b = Bindings.instance();
				map.forEach((key, value) -> {
					graphTraversal.property(key, b.of(key, value));
				});
				resultList.add(graphTraversal.next());
			});
			return resultList;
		} catch (Exception e) {
			LOGGER.info("批量添加节点异常，数据回滚...");
			g.tx().rollback();
			e.printStackTrace();
		}
		return null;
	}

	// 删除全部节点与边
	public static void deleteAllVertex(GraphTraversalSource g) {
		try {
			g.V().drop().iterate();
			LOGGER.info("节点全部删除完毕");
		} catch (Exception e) {
			LOGGER.info("删除节点异常，数据回滚...");
			g.tx().rollback();
			e.printStackTrace();
		}
	}

}
