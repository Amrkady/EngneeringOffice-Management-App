package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import common.util.Utils;
import com.entities.Departments;
import com.entities.Users;
import com.services.DepartmentService;
import com.services.UserService;

@ManagedBean(name = "deptBean")
@ViewScoped
public class deptsBean {
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Departments> depts;
	private List<Users> users;
	private Departments dept;

	@PostConstruct
	public void init() {
		dept = new Departments();
		depts = departmentServiceImpl.loadDepartments();
		users = userServiceImpl.getAllUser();
	}

	public String getUserById(Integer usrId) {
		if (usrId != null) {
			Users us = userServiceImpl.findUserById(usrId);
			return us.getName();
		}
		return "";
	}

	public String addDept() {
		try {
			departmentServiceImpl.addDepartment(dept);
			FacesMessage msg = new FacesMessage(" „  «·«÷«›…", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			depts = departmentServiceImpl.loadDepartments();
			// update manager user = 1
			Users us = userServiceImpl.findUserById(dept.getDeptManager());
			us.setManager(1);
			userServiceImpl.updateUser(us);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("·„   „ «·«÷«›…", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
		return "";
	}

	public String deleteDepartment(Departments deptD) {
		if (deptD != null) {
			try {
				departmentServiceImpl.deleteDepartment(deptD);
				FacesMessage msg = new FacesMessage(" „ «·Õ–›", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				depts = departmentServiceImpl.loadDepartments();
				// update manager user = 0
				Users us = userServiceImpl.findUserById(dept.getDeptManager());
				us.setManager(0);
				userServiceImpl.updateUser(us);
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
			dept = (Departments) event.getObject();

			departmentServiceImpl.updateDepartment(dept);
			FacesMessage msg = new FacesMessage(" „ Õ›Ÿ «· ⁄œÌ·", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// update manager user = 1
			Users us = userServiceImpl.findUserById(dept.getDeptManager());
			us.setManager(1);
			userServiceImpl.updateUser(us);
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

	public Departments getDept() {
		return dept;
	}

	public void setDept(Departments dept) {
		this.dept = dept;
	}

}
