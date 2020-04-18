package com.beans;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.common.Constant;
import com.entities.Bills;
import com.entities.BillsPay;
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
	private double total;
	Bills b;
	private Date dateFrom;
	private Date dateTo;
	List<BillsPay> billsPay;
	private double totalBillsPay;
	private double commision;
	private double deptIncome;
	private double mngCommision;
	private double totalRest;
	private Bills bill;
	private BillsPay billPay;
	private DecimalFormat df2 = new DecimalFormat("#.##");
	private double totalWithoutTax;

	@PostConstruct
	public void init() {
		depts = departmentServiceImpl.loadDepartments();
		if (Utils.findCurrentUser().getRoleId() == Constant.ROLE_MANAGER) {
			departmentId = Utils.findCurrentUser().getDeptId();
		}

	}

	public void update() {
		total = 0;
		totalWithoutTax = 0;
		totalBillsPay = 0;
		deptIncome = 0;
		mngCommision = 0;
		totalRest = 0;

		if (departmentId == null || dateFrom == null || dateTo == null) {

		} else {
			try {
				billsRecieve = sandServiceImpl.getBillsReceiveByDeptDate(departmentId, dateFrom, dateTo);
				billsPay = sandServiceImpl.getBillsPayByDeptDate(departmentId, dateFrom, dateTo);

				for (int i = 0; i < billsRecieve.size(); i++) {
					totalWithoutTax += billsRecieve.get(i).getAmountPay();
				}
				commision = totalWithoutTax * 5 / 100.0;

				if (billsRecieve != null) {
					findTotalReceive();
				}
				total = total - commision;
				if (billsPay != null) {
					findTotalPay();
				}

				deptIncome = total - totalBillsPay;
				mngCommision = deptIncome / 2.0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void findTotalPay() {

		for (int i = 0; i < billsPay.size(); i++) {


			billPay = billsPay.get(i);
			if (billPay.getTax() == 0) {
				totalBillsPay += billPay.getAmountPay();
			} else if (billPay.getTax() == 1) {
				totalBillsPay += (billPay.getAmountPay()) - ((billPay.getAmountPay() / 1.05) * 0.05);
			}
		}

	}

	private void findTotalReceive() {


		for (int i = 0; i < billsRecieve.size(); i++) {
			bill = billsRecieve.get(i);
			if (bill.getTax() == 0) {
				total += bill.getAmountPay();
			} else if (bill.getTax() == 1) {
				total += (bill.getAmountPay()) - ((bill.getAmountPay() * 0.05) / 1.05);
			}

			totalRest += billsRecieve.get(i).getAmountRest();
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

	public double getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public double getTotalBillsPay() {
		return totalBillsPay;
	}

	public double getCommision() {
		return commision;
	}

	public double getDeptIncome() {
		return deptIncome;
	}

	public double getMngCommision() {
		return mngCommision;
	}

	public double getTotalRest() {
		return totalRest;
	}

	public double getTotalWithoutTax() {
		return totalWithoutTax;
	}

}
