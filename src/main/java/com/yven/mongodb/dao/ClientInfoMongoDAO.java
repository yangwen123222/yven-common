package com.yven.mongodb.dao;

import org.mongodb.morphia.dao.DAO;

import java.util.List;
import java.util.Map;

@SuppressWarnings("hiding")
public interface ClientInfoMongoDAO<ClientInfoEntity, String> extends DAO<ClientInfoEntity, String> {

	/**
	 * 
	 * @Title: getByUserId @Description:
	 *         根据userId获取终端信息(只能查在线用户(设备),因为下线后,nsName字段被清除了) @param
	 *         userId @return 设定文件 List<ClientInfoEntity> 返回类型 @throws
	 */
	public List<ClientInfoEntity> getByUserId(String userId, List<String> nsNames);
	
	public List<ClientInfoEntity> getByUserId(String userId, List<String> nsNames, String appid);

	/**
	 * @Description 根据userId获取终端信息（无论在不在线,)
	 * @param userId
	 * @return
	 */
	public List<ClientInfoEntity> getbyUserId(String userId);

	/**
	 * @Title: getOnlineOnAsNodes @Description: 查询好友或陌生人在各as节点上的分布情况 @param
	 *         list @return 设定文件 Map<String,Object> 返回类型 @throws
	 */
//	public Map<String, Object> getOnlineOnAsNodes(List<String> userIds);
	
	/**
	 * 根据jid获取用户终端信息
	 * @param jid
	 * @return
	 */
	public List<ClientInfoEntity> getUserByJid(String jid);
}
