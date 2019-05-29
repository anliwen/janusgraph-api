package com.anlw.janusgraph.remote.connect;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteScriptConnection {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteScriptConnection.class);

	private static volatile Client client = null;

	private static volatile Cluster cluster = null;

	private static final Class<RemoteScriptConnection> CLASS_LOCL = RemoteScriptConnection.class;

	private static final String INVOKE_CLASS = "gremlin.remote.driver.clusterFile";

	private RemoteScriptConnection() {
	}

	// using the remote driver for schema
	public static Client getClient() throws Exception {
		LOGGER.info("open client...");
		synchronized (CLASS_LOCL) {
			if (client == null) {
				Configuration conf = new PropertiesConfiguration("config/client/remote-graph.properties");
				cluster = Cluster.open(conf.getString(INVOKE_CLASS));
				return cluster.connect();
			}
		}
		return client;
	}

	public static void closeClient() throws Exception {
		LOGGER.info("closing client");
		try {
			if (cluster != null) {
				// the cluster closes all of its clients
				cluster.close();
			}
		} finally {
			client = null;
			cluster = null;
		}
	}
}
