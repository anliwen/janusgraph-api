package com.anlw.janusgraph.model;

import java.util.Map;

import lombok.Data;

@Data
public class VertexDO {
	private String label;
	private Map<String, Object> properties;

}
