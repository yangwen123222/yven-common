package com.yven.mongodb.config;

import com.mongodb.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoFactory {

	private static Log log = LogFactory.getLog(MongoFactory.class);

	private static MongoFactory instance;
	
	private static MongoClientOptions options = null;
	private static String[] host = null;
	private static String[] port = null;
	
	private static String[] host2 = null;
	private static String[] port2 = null;
	private static String host2Models = null;
	
	private static String dbName = null;
	private static String readPreference = null;
	private static Boolean auth = null; 
	private static String username = null;
	private static String password = null;
	
	private static Map<String, Datastore> dsMap = new HashMap<String, Datastore>();
	
	public synchronized static MongoFactory getInstance(String className) {
		if (null == instance) {
			instance = new MongoFactory();
		}
		//默认mongodb
		if(null == dsMap.get("host")){
			dsMap.put("host", getDataStore(host, port));
		}
		//其它mongodb
		if(null!=host2){
			if(null == dsMap.get("host2") && StringUtils.contains(host2Models+",", className+",")){
				dsMap.put("host2", getDataStore(host2, port2));
			}
		}
		return instance;
	}

	private MongoFactory() {
		MongoConfig config = MongoConfig.getInstance();
		host = config.getHosts();
		port = config.getPorts();
		
		host2 = StringUtils.split(config.getString("host2"),",");
		port2 = StringUtils.split(config.getString("port2"),",");
		host2Models = config.getString("host2Models");
		
		dbName = config.getString("dbname");
		readPreference = config.getString("readPreference");
		auth = config.needAuth();
		username = config.getUserName();
		password = config.getPassword();
		
		// MongoClient的一些配置项
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.autoConnectRetry(config.getBoolean("autoConnectRetry", true));
		builder.socketKeepAlive(config.getBoolean("socketKeepAlive", true));
		builder.connectionsPerHost(config.getInt("connectionsPerHost", 100));
		builder.connectTimeout(config.getInt("connectTimeout", 10000));
		builder.socketTimeout(config.getInt("socketTimeout", 10000));
		builder.maxWaitTime(config.getInt("maxWaitTime",120000));
		builder.threadsAllowedToBlockForConnectionMultiplier(config.getInt("threadsAllowedToBlockForConnectionMultiplier", 5));
		options = builder.build();
	}
	
	public Datastore getDatastore(String className) {
		if(StringUtils.contains(host2Models+",", className+",")){
			return dsMap.get("host2");
		}else{
			return dsMap.get("host");
		}
	}
	
	private static Datastore getDataStore(String[] _hosts, String[] _ports){
		MongoClient mongo = null;
		DB db = null;
		try {
			//根据配置判断配置方式
			//单一配置
			if(1==_hosts.length){
				ServerAddress dbAddress = new DBAddress(_hosts[0], Integer.valueOf(_ports[0]), dbName);
				mongo = new MongoClient(dbAddress, options);
				db = mongo.getDB(dbName);
			}
			//副本集
			else if(_hosts.length>1){
				List<ServerAddress> addresses = new ArrayList<ServerAddress>();
				for(int i=0; i<_hosts.length; i++){
					ServerAddress dbAddress = new DBAddress(_hosts[i], Integer.valueOf(_ports[i]), dbName);
					addresses.add(dbAddress);
				}
				mongo = new MongoClient(addresses, options);
				db = mongo.getDB(dbName);
				
				//Read Preference to use
				//ReadPreference which reads secondary if available, otherwise from primary.
				if(StringUtils.equals(readPreference, "secondaryPreferred")){
					db.setReadPreference(ReadPreference.secondaryPreferred());
					mongo.setReadPreference(ReadPreference.secondaryPreferred());
				}
				//ReadPreference which reads secondary.
				else if(StringUtils.equals(readPreference, "secondary")){
					db.setReadPreference(ReadPreference.secondary());
					mongo.setReadPreference(ReadPreference.secondary());
				}
				//ReadPreference which reads primary if available.
				else if(StringUtils.equals(readPreference, "primaryPreferred")){
					db.setReadPreference(ReadPreference.primaryPreferred());
					mongo.setReadPreference(ReadPreference.primaryPreferred());
				}
				//ReadPreference which reads from primary only.
				else if(StringUtils.equals(readPreference, "primary")){
					db.setReadPreference(ReadPreference.primary());
					mongo.setReadPreference(ReadPreference.primary());
				}
				//ReadPreference which reads nearest node.
				else if(StringUtils.equals(readPreference, "nearest")){
					db.setReadPreference(ReadPreference.nearest());
					mongo.setReadPreference(ReadPreference.nearest());
				}
			}else{
				throw new Exception("mongodb > host、port配置错误");
			}
			
			//鉴权
			if (auth) {
				boolean rt = db.authenticate(username, password.toCharArray());
				if (!rt){
					throw new Exception("auth failed.");
				}
			}
		} catch (Exception e) {
			log.error("连接MongoDB失败！", e);
		}
		return new Morphia().createDatastore(mongo, dbName);
	}
	
//	public Datastore getDatastore(String dbName) {
//		return morphia.createDatastore(mongo, dbName);
//	}
//
//	public Morphia getMorphia() {
//		return this.morphia;
//	}
}
