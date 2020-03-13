package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.entities.Users;
import com.services.UserService;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean {
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;

	List<Users> users;
	Users user;

	@PostConstruct
	public void init() {
		Users us = new Users();
		us.setLoginName("ugf");
		us.setName("asff");
		us.setRoleId(2);
		us.setPassword("22334");
		userServiceImpl.addUser(us);
		users = userServiceImpl.getAllUser();
	}

	public void addUser() {
		try {
			userServiceImpl.addUser(user);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void updateUser() {
		try {
			userServiceImpl.updateUser(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void deleteUser() {
		try {
			userServiceImpl.deleteUser(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

}
