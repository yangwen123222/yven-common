package com.yven.mongodb.config;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class MongoConfig {

	private Log log = LogFactory.getLog(MongoConfig.class);
	private static MongoConfig instance = null;

	private ResourceBundle rb;

	private MongoConfig() {
		rb = ResourceBundle.getBundle("conf/mongodb", Locale.getDefault());
	}

	public static MongoConfig getInstance() {
		if (instance == null) {
			instance = new MongoConfig();
		}
		return instance;
	}

	public void changeBundle(String proPath) {
		rb = ResourceBundle.getBundle(proPath, Locale.getDefault());
	}

	public String getHost() {
		return rb.getString("host");
	}
	
	public String[] getHosts(){
		String[] hosts = StringUtils.split(rb.getString("host"), ",");
		return hosts;
	}

	public int getPort() {
		return getInt("port", 27017);
	}
	
	public String[] getPorts(){
		String[] ports = StringUtils.split(rb.getString("port"), ",");
		return ports;
	}

	public boolean needAuth() {
		return getBoolean("auth", true);
	}

	public String getUserName() {
		return rb.getString("username");
	}

	public String getPassword() {
		return rb.getString("password");
	}

	public String getDbName() {
		return rb.getString("dbname");
	}

	public String getString(String key) {
		return rb.getString(key);
	}

	public String getString(String key, String _default) {
		String temp = rb.getString(key);
		if (temp == null || "".equals(temp.trim())) {
			return _default;
		}
		return temp;
	}

	public boolean getBoolean(String key) {
		return Boolean.valueOf(rb.getString(key));
	}

	public boolean getBoolean(String key, boolean _default) {
		try {
			return Boolean.valueOf(rb.getString(key));
		} catch (Exception e) {
			log.warn("解析" + key + "异常，使用默认值" + _default);
			return _default;
		}
	}

	public int getInt(String key) {
		return Integer.valueOf(rb.getString(key));
	}

	public int getInt(String key, int _default) {
		try {
			return Integer.valueOf(rb.getString(key));
		} catch (Exception e) {
			log.warn("解析" + key + "异常，使用默认值" + _default);
			return _default;
		}
	}

}
