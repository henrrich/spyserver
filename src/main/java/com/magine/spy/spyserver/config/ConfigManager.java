package com.magine.spy.spyserver.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Util class for loading xml configuration into memory. By default, the file
 * spyserver_config.xml file in classpath will be used. -Dconfig vm argument can
 * be used to specify another xml config file.
 */
public class ConfigManager {

	private static final String CONFIG_FILENAME = "spyserver_config.xml";
	private static final String PROPERTY_CONFIG = "config";

	private static final String CONFIG_SERVER_PORT = "server_port";
	private static final String CONFIG_WORKSPACE = "workspace";

	private static ConfigManager instance;

	private int serverPort;
	private String workspace;

	private ConfigManager() {
		try {
			load();
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.err.println("Failed to load configuration.");
			System.exit(1);
		}
	}

	/**
	 * Get singleton instance of ConfigManager class
	 * 
	 * @return ConfigManager singleton instance of ConfigManager class
	 */
	public static ConfigManager getInstance() {
		if (instance == null) {
			synchronized (ConfigManager.class) {
				if (instance == null) {
					instance = new ConfigManager();
				}
			}
		}

		return instance;
	}

	private void load() throws ConfigurationException {
		String configFilename = System.getProperty(PROPERTY_CONFIG);
		XMLConfiguration config = null;
		if (configFilename != null) {
			config = new XMLConfiguration(configFilename);
		} else {
			config = new XMLConfiguration(CONFIG_FILENAME);
		}

		this.serverPort = config.getInt(CONFIG_SERVER_PORT);
		this.workspace = config.getString(CONFIG_WORKSPACE);

	}

	/**
	 * Getter for server port
	 * 
	 * @return String server port from xml configuration file
	 */
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 * Getter for workspace path
	 * 
	 * @return String server workspace path where collected logs are transferred
	 *         to.
	 */
	public String getWorkspace() {
		return this.workspace;
	}

}
