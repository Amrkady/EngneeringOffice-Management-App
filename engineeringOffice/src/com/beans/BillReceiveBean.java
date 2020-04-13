package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.common.Constant;
import com.entities.Bills;
import com.entities.Departments;
import com.services.DepartmentService;
import com.services.SandService;

import common.util.Utils;

@ManagedBean
@ViewScoped
public class BillReceiveBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Departments> depts;
	private Integer departmentId;

	List<Bills> billsRecieve;
	private List<Bills> filtredBills;
	private int total;
	Bills b;
	@PostConstruct
	public void init() {
		depts = departmentServiceImpl.loadDepartments();

		if (Utils.findCurrentUser().getRoleId() == Constant.ROLE_MANAGER) {
			billsRecieve = sandServiceImpl.getBillsReceiveByDept(Utils.findCurrentUser().getDeptId());
			findTotal();
		} else if (Utils.findCurrentUser().getRoleId() == Constant.ROLE_ADMIN) {
			billsRecieve = sandServiceImpl.getAllBillsReceive();
			findTotal();
		}
	}

	public void update() {
		if (departmentId != 0) {
			billsRecieve = sandServiceImpl.getBillsReceiveByDept(departmentId);
			findTotal();
		} else if (departmentId == 0) {
			billsRecieve = sandServiceImpl.getAllBillsReceive();
			findTotal();
		}
	}

	private void findTotal() {
		total = 0;

		for (int i = 0; i < billsRecieve.size(); i++) {
			total += billsRecieve.get(i).getAmountPay();
		}


	}

	public SandService getSandServiceImpl() {
		return sandServiceImpl;
	}

	public void setSandServiceImpl(SandService sandServiceImpl) {
		this.sandServiceImpl = sandServiceImpl;
	}

	public List<Bills> getBillsRecieve() {
		return billsRecieve;
	}

	public void setBillsRecieve(List<Bills> billsRecieve) {
		this.billsRecieve = billsRecieve;
	}

	public List<Bills> getFiltredBills() {
		return filtredBills;
	}

	public void setFiltredBills(List<Bills> filtredBills) {
		this.filtredBills = filtredBills;
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

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
