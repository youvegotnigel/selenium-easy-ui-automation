package com.youvegotnigel.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import com.youvegotnigel.automation.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Read the property file and store it in a HashMap for faster processing.
 * Users can prefer to use json instead of property file based on their requirement.
 *
 * Dec 29, 2022
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public final class PropertyUtils {

	private static Properties property = new Properties();
	private static final Map<String, String> CONFIGMAP = new HashMap<>();
	private static final Logger log = LogManager.getLogger(PropertyUtils.class.getName());


	/**
	 * Private constructor to avoid external instantiation
	 */
	private PropertyUtils() {}

	static {
		try(FileInputStream file = new FileInputStream(FrameworkConstants.getConfigFilePath())) {
			property.load(file);
			for (Map.Entry<Object, Object> entry : property.entrySet()) {
				CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()).trim()); //remove the trailing and leading spaces
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		} 
	}

	/**
	 * Receives the property, converts to lowercase, return the corresponding value for the key from the HashMap
	 * @param key To be fetched from property file
	 * @return corresponding value for the requested key if found else throws Exception
	 */
	public static String get(String key){
		if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key))) {
			log.error(String.format("Property named '%s' was NOT found. Please check in file='%s'", key, FrameworkConstants.getConfigFilePath()));
		}
		return CONFIGMAP.get(key);
	}

}
