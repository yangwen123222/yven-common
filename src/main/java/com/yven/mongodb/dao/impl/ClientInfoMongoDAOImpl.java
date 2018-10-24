package com.yven.mongodb.dao.impl;


import com.yven.mongodb.entity.ClientInfoEntity;
import com.yven.mongodb.dao.ClientInfoMongoDAO;
import com.yven.mongodb.dao.TBaseDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class ClientInfoMongoDAOImpl extends TBaseDAO<ClientInfoEntity, String>
		implements ClientInfoMongoDAO<ClientInfoEntity, String> {

	@Override
	public List<ClientInfoEntity> getByUserId(String userId, List<String> nsNames) {
		Query<ClientInfoEntity> query = this.createQuery();
		query.criteria("userId").equal(userId);
		//query.criteria("nsName").in(nsNames);
		return query.asList();
	}

	@Override
	public List<ClientInfoEntity> getByUserId(String userId, List<String> nsNames, String appid) {
		Query<ClientInfoEntity> query = this.createQuery();
		query.criteria("userId").equal(userId);
		query.criteria("nsName").in(nsNames);
		query.criteria("joinid").equal(appid);
		return query.asList();
	}
	
//	@Override
//	public Map<String, Object> getOnlineOnAsNodes(List<String> userIds) {
//
//		if (null == userIds || userIds.isEmpty()) {
//			return null;
//		}
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		List<String> asNodes = ASClusterFactory.getInstance().getClusterAdapter().getOtherServer("as");
//
//		Query<ClientInfoEntity> query = this.createQuery();
//		query.field("userId").in(userIds);
//
//		for (String node : asNodes) {
//			query.field("nsName").equal(node);
//			List<ClientInfoEntity> nodeList = query.asList();
//			map.put(node, nodeList);
//		}
//		return map;
//	}

	@Override
	public List<ClientInfoEntity> getbyUserId(String userId) {
		Query<ClientInfoEntity> query = this.createQuery();
		query.criteria("userId").equal(userId);
		return query.asList();
	}

	@Override
	public List<ClientInfoEntity> getUserByJid(String jid) {
		Query<ClientInfoEntity> query = this.createQuery();
		query.criteria("_id").equal(jid);
		return query.asList();
	}

}
