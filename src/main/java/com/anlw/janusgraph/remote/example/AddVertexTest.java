package com.anlw.janusgraph.remote.example;

import org.apache.tinkerpop.gremlin.process.traversal.Bindings;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.attribute.Geoshape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.anlw.janusgraph.remote.constant.Constants.*;

public class AddVertexTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddVertexTest.class);

	public static void createElements(GraphTraversalSource g) {

		LOGGER.info("creating elements");

		// Use bindings to allow the Gremlin Server to cache traversals that
		// will be reused with different parameters. This minimizes the
		// number of scripts that need to be compiled and cached on the server.
		// https://tinkerpop.apache.org/docs/3.2.6/reference/#parameterized-scripts
		final Bindings b = Bindings.instance();

		// see GraphOfTheGodsFactory.java

		Vertex saturn = g.addV(b.of(LABEL, "titan")).property(NAME, b.of(NAME, "saturn"))
				.property(AGE, b.of(AGE, 10000)).next();
		Vertex sky = g.addV(b.of(LABEL, "location")).property(NAME, b.of(NAME, "sky")).next();
		Vertex sea = g.addV(b.of(LABEL, "location")).property(NAME, b.of(NAME, "sea")).next();
		Vertex jupiter = g.addV(b.of(LABEL, "god")).property(NAME, b.of(NAME, "jupiter")).property(AGE, b.of(AGE, 5000))
				.next();
		Vertex neptune = g.addV(b.of(LABEL, "god")).property(NAME, b.of(NAME, "neptune")).property(AGE, b.of(AGE, 4500))
				.next();
		Vertex hercules = g.addV(b.of(LABEL, "demigod")).property(NAME, b.of(NAME, "hercules"))
				.property(AGE, b.of(AGE, 30)).next();
		Vertex alcmene = g.addV(b.of(LABEL, "human")).property(NAME, b.of(NAME, "alcmene")).property(AGE, b.of(AGE, 45))
				.next();
		Vertex pluto = g.addV(b.of(LABEL, "god")).property(NAME, b.of(NAME, "pluto")).property(AGE, b.of(AGE, 4000))
				.next();
		Vertex nemean = g.addV(b.of(LABEL, "monster")).property(NAME, b.of(NAME, "nemean")).next();
		Vertex hydra = g.addV(b.of(LABEL, "monster")).property(NAME, b.of(NAME, "hydra")).next();
		Vertex cerberus = g.addV(b.of(LABEL, "monster")).property(NAME, b.of(NAME, "cerberus")).next();
		Vertex tartarus = g.addV(b.of(LABEL, "location")).property(NAME, b.of(NAME, "tartarus")).next();

		g.V(b.of(OUT_V, jupiter)).as("a").V(b.of(IN_V, saturn)).addE(b.of(LABEL, "father")).from("a").next();
		g.V(b.of(OUT_V, jupiter)).as("a").V(b.of(IN_V, sky)).addE(b.of(LABEL, "lives"))
				.property(REASON, b.of(REASON, "loves fresh breezes")).from("a").next();
		g.V(b.of(OUT_V, jupiter)).as("a").V(b.of(IN_V, neptune)).addE(b.of(LABEL, "brother")).from("a").next();
		g.V(b.of(OUT_V, jupiter)).as("a").V(b.of(IN_V, pluto)).addE(b.of(LABEL, "brother")).from("a").next();

		g.V(b.of(OUT_V, neptune)).as("a").V(b.of(IN_V, sea)).addE(b.of(LABEL, "lives"))
				.property(REASON, b.of(REASON, "loves waves")).from("a").next();
		g.V(b.of(OUT_V, neptune)).as("a").V(b.of(IN_V, jupiter)).addE(b.of(LABEL, "brother")).from("a").next();
		g.V(b.of(OUT_V, neptune)).as("a").V(b.of(IN_V, pluto)).addE(b.of(LABEL, "brother")).from("a").next();

		g.V(b.of(OUT_V, hercules)).as("a").V(b.of(IN_V, jupiter)).addE(b.of(LABEL, "father")).from("a").next();
		g.V(b.of(OUT_V, hercules)).as("a").V(b.of(IN_V, alcmene)).addE(b.of(LABEL, "mother")).from("a").next();

		// 用于提前设定是否支持Geoshape
		boolean supportsGeoshape = true;
		if (supportsGeoshape) {
			g.V(b.of(OUT_V, hercules)).as("a").V(b.of(IN_V, nemean)).addE(b.of(LABEL, "battled"))
					.property(TIME, b.of(TIME, 1)).property(PLACE, b.of(PLACE, Geoshape.point(38.1f, 23.7f))).from("a")
					.next();
			g.V(b.of(OUT_V, hercules)).as("a").V(b.of(IN_V, hydra)).addE(b.of(LABEL, "battled"))
					.property(TIME, b.of(TIME, 2)).property(PLACE, b.of(PLACE, Geoshape.point(37.7f, 23.9f))).from("a")
					.next();
			g.V(b.of(OUT_V, hercules)).as("a").V(b.of(IN_V, cerberus)).addE(b.of(LABEL, "battled"))
					.property(TIME, b.of(TIME, 12)).property(PLACE, b.of(PLACE, Geoshape.point(39f, 22f))).from("a")
					.next();
		} else {
			g.V(b.of(OUT_V, hercules)).as("a").V(b.of(IN_V, nemean)).addE(b.of(LABEL, "battled"))
					.property(TIME, b.of(TIME, 1)).property(PLACE, b.of(PLACE, getGeoFloatArray(38.1f, 23.7f)))
					.from("a").next();
			g.V(b.of(OUT_V, hercules)).as("a").V(b.of(IN_V, hydra)).addE(b.of(LABEL, "battled"))
					.property(TIME, b.of(TIME, 2)).property(PLACE, b.of(PLACE, getGeoFloatArray(37.7f, 23.9f)))
					.from("a").next();
			g.V(b.of(OUT_V, hercules)).as("a").V(b.of(IN_V, cerberus)).addE(b.of(LABEL, "battled"))
					.property(TIME, b.of(TIME, 12)).property(PLACE, b.of(PLACE, getGeoFloatArray(39f, 22f))).from("a")
					.next();
		}

		g.V(b.of(OUT_V, pluto)).as("a").V(b.of(IN_V, jupiter)).addE(b.of(LABEL, "brother")).from("a").next();
		g.V(b.of(OUT_V, pluto)).as("a").V(b.of(IN_V, neptune)).addE(b.of(LABEL, "brother")).from("a").next();
		g.V(b.of(OUT_V, pluto)).as("a").V(b.of(IN_V, tartarus)).addE(b.of(LABEL, "lives"))
				.property(REASON, b.of(REASON, "no fear of death")).from("a").next();
		g.V(b.of(OUT_V, pluto)).as("a").V(b.of(IN_V, cerberus)).addE(b.of(LABEL, "pet")).from("a").next();

		g.V(b.of(OUT_V, cerberus)).as("a").V(b.of(IN_V, tartarus)).addE(b.of(LABEL, "lives")).from("a").next();
	}

	// Returns the geographical coordinates as a float array.
	private static float[] getGeoFloatArray(final float lat, final float lon) {
		final float[] fa = { lat, lon };
		return fa;
	}

}
