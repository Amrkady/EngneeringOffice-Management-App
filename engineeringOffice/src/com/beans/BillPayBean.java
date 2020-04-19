package com.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.common.Constant;
import com.entities.BillsPay;
import com.entities.Departments;
import com.entities.Users;
import com.services.DepartmentService;
import com.services.SandService;

import common.util.Utils;

@ManagedBean(name = "billPayBean")
@ViewScoped
public class BillPayBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Departments> depts;
	private List<BillsPay> bills;
	private BillsPay billsPay;
	private Date sandDate;
	private Integer departmentId;

	@PostConstruct
	public void init() {
		billsPay = new BillsPay();
		Users curUser = Utils.findCurrentUser();
		if (curUser != null) {
			if (curUser.getRoleId() == Constant.ROLE_MANAGER) {
				/// GET BY DEPT
				bills = sandServiceImpl.getBillsPayByDept(curUser.getDeptId());
				Departments usrDept = departmentServiceImpl.findDeptById(curUser.getDeptId());
				depts.add(usrDept);
			} else if (curUser.getRoleId() == Constant.ROLE_ADMIN || curUser.getRoleId() == Constant.ROLE_ACCOUNTANT) {
				// GET ALL
				bills = sandServiceImpl.getAllBillsPay();
				depts = departmentServiceImpl.loadDepartments();
				System.out.println("alllldepts");

			}
		}

	}

	public void getBillsByDeptId() {
		if (departmentId != null) {
			bills = sandServiceImpl.getBillsPayByDept(departmentId);
		}
	}

	public String print(BillsPay selectedBill) {
		System.out.print("print >>>>>>>.");
		try {
			String reportName = "/reports/Bills_Pay.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sndNo", selectedBill.getSanadNo());
			parameters.put("custName", selectedBill.getCustomerName());
			String reyal = String.valueOf(selectedBill.getAmountPay());
			reyal = reyal.substring(0, reyal.indexOf("."));
			parameters.put("reyal", Integer.parseInt(reyal));
			parameters.put("costByLet", selectedBill.getAmountPay());
			parameters.put("costRest", selectedBill.getAmountRest());
			parameters.put("for", selectedBill.getBillReason());
			parameters.put("payType", selectedBill.getBillType());
			parameters.put("dept", selectedBill.getDeptName());
			parameters.put("date", selectedBill.getBillDate());
			String hall = String.valueOf(selectedBill.getAmountPay());
			hall = hall.substring(hall.indexOf(".") + 1);
			parameters.put("halaa", Integer.parseInt(hall));
			String footerPath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/footer.png");
			parameters.put("footer", footerPath);
			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/header.png");
			parameters.put("header", headerPath);
			Utils.printPdfReport(reportName, parameters);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public void update(BillsPay selectedBill) {
		if (selectedBill != null) {
			System.out.print("update>>>>>>>>>>");
			// billsPay = new BillsPay();

			billsPay = selectedBill;
			billsPay.setAmountPay(selectedBill.getAmountPay());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {
				sandDate = dateFormat.parse(selectedBill.getBillDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Utils.openDialog("whsdlAdd");
		}

	}

	public void showdlAdd() {
		billsPay = new BillsPay();
		Integer sandNo = sandServiceImpl.findBillsSandNo();
		billsPay.setSanadNo(sandNo == 0 ? 1 : sandNo + 1);
		System.out.print(">>>>>>>>>>" + billsPay.getSanadNo());
		Utils.openDialog("whsdlAdd");

	}

	public String save() {

		if (billsPay.getDeptId() != null) {
			Departments usrDept = departmentServiceImpl.findDeptById(billsPay.getDeptId());
			billsPay.setDeptName(usrDept.getDeptName());
		}
		try {
			String strDate = "";
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/mm/yyyy");
			if (sandDate != null) {
				strDate = sdfDate.format(sandDate);
			}
			billsPay.setBillDate(strDate);
			billsPay.setDate(new Date());
			if (billsPay.getId() == null) {
				sandServiceImpl.addBillsPay(billsPay);
				billsPay = new BillsPay();
			} else {
				sandServiceImpl.updateBillsPay(billsPay);
				billsPay = new BillsPay();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, " „ «·Õ›Ÿ ", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			init();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "·„ Ì „ «·Õ›Ÿ", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
		return "";
	}

	public SandService getSandServiceImpl() {
		return sandServiceImpl;
	}

	public void setSandServiceImpl(SandService sandServiceImpl) {
		this.sandServiceImpl = sandServiceImpl;
	}

	public Date getSandDate() {
		return sandDate;
	}

	public void setSandDate(Date sandDate) {
		this.sandDate = sandDate;
	}

	public BillsPay getBillsPay() {
		return billsPay;
	}

	public void setBillsPay(BillsPay billsPay) {
		this.billsPay = billsPay;
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

	public List<BillsPay> getBills() {
		return bills;
	}

	public void setBills(List<BillsPay> bills) {
		this.bills = bills;
	}

}
