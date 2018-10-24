package com.yven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yven.dao.LoginLogDao;
import com.yven.dao.UserDao;
import com.yven.domain.LoginLog;
import com.yven.domain.User;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
	@Autowired
	private UserDao userdao;
	@Autowired
	private LoginLogDao loginLogDao;

	public boolean hasMatchUser(String name, String password) {
		int matchCount = userdao.getMatchCount(name, password);
		return matchCount > 0;
	}

	public User findUserByUsername(String username) {
		return userdao.findUserByName(username);
	}
	
	public void loginSuccess(User user) {
		user.setCredits(user.getCredits() + 5);
		LoginLog loginLog = new LoginLog();
		loginLog.setUserId(user.getUserId());
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(user.getLastVisit());
		userdao.updateLoginInfo(user);
		loginLogDao.insertLoginLog(loginLog);
	}

	public List<String> findListByUsername(String username){
		return userdao.findUserList(username);
	}

	public List<String> findListByUsername2(String username){
		return userdao.findUserList2(username);
	}

	public List<User> findListByUsername3(String username){
		return userdao.findUserList3(username);
	}
	public List<User> findListByUsername4(String username){
		return userdao.findUserList4(username);
	}
}
