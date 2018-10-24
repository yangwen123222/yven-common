package com.yven.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yven.domain.User;

@Repository
public class UserDao {
	private static Logger logger = Logger.getLogger(UserDao.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	public int getMatchCount(String name, String password) {
		String sql = "SELECT count(*) FROM t_user WHERE user_name = ? and password = ?";
		return jdbcTemplate.queryForInt(sql, new Object[]{name, password});
	}

	public User findUserByName(final String name) {
		String sql = "select user_id,user_name,credits from t_user where user_name = ?";
		final User user = new User();
		jdbcTemplate.query(sql, new Object[]{name}, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(name);
				user.setCredits(rs.getInt("credits"));
			}
		});
		return user;
	}

	public void updateLoginInfo(User user) {
		String sql = "update t_user set last_visit = ?,last_ip = ?, credits = ? where user_id = ?";
		jdbcTemplate.update(sql,
				new Object[]{user.getLastVisit(), user.getLastIp(), user.getCredits(), user.getUserId()});
	}


	private final static String THIRD_CTRLOR_SQL = "SELECT t.user_id FROM t_user t WHERE EXISTS (SELECT user_id FROM t_user t0 WHERE t.user_id=t0.user_id AND user_name= ? )";


	private final static String THIRD_CTRLOR_SQL2 = "SELECT t.user_id FROM t_user t inner join t_user t0 on t.user_id=t0.user_id AND t0.user_name= ?  ";

	private final static String THIRD_CTRLOR_SQL3 = "SELECT * FROM t_user t inner join t_user t0 on t.user_id=t0.user_id AND t0.user_name= ?  ";


	/**
	 * 返回一个简单属性列表
	 * @param username
	 * @return
	 */
	public List<String> findUserList(String username) {
		List<String> userId = jdbcTemplate.queryForList(THIRD_CTRLOR_SQL, new Object[]{username}, String.class);
		return userId;
	}

	/**
	 * 返回一个简单属性列表
	 * @param username
	 * @return
	 */
	public List<String> findUserList2(String username) {
		List<String> userId = jdbcTemplate.queryForList(THIRD_CTRLOR_SQL2, new Object[]{username}, String.class);
		return userId;
	}


	/**
	 * 查询user 列表,方式一：User类实现RowMapper接口，实现mapRow()方法
	 * @param username
	 * @return
	 */
	public List<User> findUserList3(String username) {
		List<User> users = jdbcTemplate.query(THIRD_CTRLOR_SQL3, new Object[]{username}, new User());
		return users;
	}

	/**
	 * 查询user列表，方式二：将查询道的Map集合转化为user列表
	 * @param username
	 * @return
	 */
	public List<User> findUserList4(String username){
		List<Map<String, Object>> users = jdbcTemplate.queryForList(THIRD_CTRLOR_SQL3, new Object[]{username});
		return this.toUserList(users);
	}

	/**
	 *  map --> user
	 * @param map
	 * @return
	 */
	private User toUser(Map<String, Object> map){
		User user = new User();
		user.setUserId((Integer) map.get("user_id"));
		user.setUserName((String) map.get("user_name"));
		user.setCredits((Integer) map.get("credits"));
		user.setPassword((String) map.get("password"));
		user.setLastIp((String) map.get("last_ip"));
		user.setLastVisit((Date) map.get("last_visit"));
		return user;
	}

	/**
	 * list --> map
	 * @param lists
	 * @return
	 */
	private List<User> toUserList(List<Map<String, Object>> lists){
		List<User> users = new ArrayList<User>();
		for (Map<String, Object> map : lists) {
			User user =  this.toUser(map);
			if (user != null) {
                users.add(user);
			}
		}
		return users;

	}


}