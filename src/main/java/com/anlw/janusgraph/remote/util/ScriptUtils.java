package com.anlw.janusgraph.remote.util;

import java.util.stream.Stream;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScriptUtils.class);

	public static void submitScript(Client client, String script) {
		LOGGER.info("submit script...");
		// submit the request to the server
		final ResultSet resultSet = client.submit(script);
		// drain the results completely
		Stream<Result> futureList = resultSet.stream();
		futureList.map(Result::toString).forEach(LOGGER::info);
	}

}
