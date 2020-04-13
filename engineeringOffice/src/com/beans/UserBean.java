package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.entities.Departments;
import com.entities.Users;
import com.services.DepartmentService;
import com.services.UserService;

import common.util.Utils;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean {
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Departments> depts;
	private List<Users> users;
	private Users user;
	private String address;
	private String name;
	private String loginName;
	private String password;
	private String phone;
	private Integer deptId;
	
	private boolean MNG = false;

	@PostConstruct
	public void init() {
		user = new Users();
		depts = departmentServiceImpl.loadDepartments();
		users = userServiceImpl.getAllUser();
	}
	
	public String getDeptById(Integer deptId) {
		if(deptId != null)
		{
		   Departments dept= departmentServiceImpl.findDeptById(deptId);
		   return dept.getDeptName();
		}
		return "";
	}

	public String addUser() {
		try {
			if(MNG) {
				user.setRoleId(1);
			}else {
				user.setRoleId(2);
			}
			user.setLoginName(user.getLoginName().toUpperCase());
			userServiceImpl.addUser(user);
			FacesMessage msg = new FacesMessage(" „  «·«÷«›…", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			users = userServiceImpl.getAllUser();
			Utils.closeDialog("whsdlAdd");
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("·„   „ «·«÷«›…", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
		return "";
	}

	public String deleteUser(Users userD) {
		if (user != null) {
			try {
				userServiceImpl.deleteUser(userD);
				FacesMessage msg = new FacesMessage(" „ «·Õ–›", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				users = userServiceImpl.getAllUser();
			
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("·„ Ì „ «·Õ–›", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				e.printStackTrace();
			}
		}
		return "";
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			user = (Users) event.getObject();
			user.setLoginName(user.getLoginName().toUpperCase());
			userServiceImpl.updateUser(user);
			FacesMessage msg = new FacesMessage(" „ Õ›Ÿ «· ⁄œÌ·", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			user = new Users();
			// users = userServiceImpl.getAllUser();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("·„ Ì „ Õ›Ÿ «· ⁄œÌ·", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(" „ «·€«¡ «· ⁄œÌ·", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void showdlAdd() {
		Utils.openDialog("whsdlAdd");

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

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public List<Departments> getDepts() {
		return depts;
	}

	public void setDepts(List<Departments> depts) {
		this.depts = depts;
	}

	public boolean isMNG() {
		return MNG;
	}

	public void setMNG(boolean mNG) {
		MNG = mNG;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}


}
