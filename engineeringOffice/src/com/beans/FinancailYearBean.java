package com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "financailBean")
@ViewScoped
public class FinancailYearBean {
//	@ManagedProperty(value = "#{sandServiceImpl}")
//	private SandService sandServiceImpl;
//	@ManagedProperty(value = "#{departmentServiceImpl}")
//	private DepartmentService departmentServiceImpl;
//	private List<Departments> depts;
//	private List<BillsPay> bills;
//
//	private boolean taxFlag;
//	private double totalWithoutTax;
//	private double totalRest;
//	private double taxValue;
//	private double totalAfterTax;
//	private BillsPay billPay;
//	private double billHasTax;

	private List<Integer> years;
	private Integer year;

	@PostConstruct
	public void init() {
//
//		Users curUser = Utils.findCurrentUser();
//		if (curUser != null) {
//			if (curUser.getRoleId() == Constant.ROLE_MANAGER) {
//
//				// departmentId = Utils.findCurrentUser().getDeptId();
//			} else if (curUser.getRoleId() == Constant.ROLE_ADMIN || curUser.getRoleId() == Constant.ROLE_ACCOUNTANT) {
//				depts = departmentServiceImpl.loadDepartments();
//				System.out.println("alllldepts");
//			}
//		}

	}

	public void loadData() {
		System.out.println(year);
	}
//	public void getBillsByDeptId() {
//
//			totalWithoutTax = 0;
//			totalRest = 0;
//			taxValue = 0;
//			billHasTax = 0;
//			totalAfterTax = 0;
//			// bills = sandServiceImpl.getBillsPayByDeptDate(departmentId, dateFrom,
//			// dateTo);
//			for (int i = 0; i < bills.size(); i++) {
//				billPay = bills.get(i);
//				totalWithoutTax += billPay.getAmountPay().doubleValue();
//				totalRest += billPay.getAmountRest().doubleValue();
//				if (billPay.getTax() == 1) {
//					taxValue += (billPay.getAmountPay().doubleValue() / 1.05) * 0.05;
//					billHasTax += billPay.getAmountPay().doubleValue();
//
//				}
//
//
//			taxValue = Math.round(taxValue * 100) / 100.00d;
//			totalAfterTax = billHasTax - taxValue;
//			totalAfterTax = Math.round(totalAfterTax * 100) / 100.00d;
//		}
//	}
//
//	public String print(BillsPay selectedBill) {
//		System.out.print("print >>>>>>>.");
//		try {
//			String reportName = "/reports/Bills_Pay.jasper";
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("sndNo", selectedBill.getSanadNo());
//			parameters.put("custName", selectedBill.getCustomerName());
//			String reyal = String.valueOf(selectedBill.getAmountPay());
//			reyal = reyal.substring(0, reyal.indexOf("."));
//			parameters.put("reyal", Integer.parseInt(reyal));
//			parameters.put("costByLet", selectedBill.getAmountPay());
//			parameters.put("costRest", selectedBill.getAmountRest());
//			parameters.put("for", selectedBill.getBillReason());
//			parameters.put("payType", selectedBill.getBillType());
//			parameters.put("dept", selectedBill.getDeptName());
//			parameters.put("date", selectedBill.getBillDate());
//			String hall = String.valueOf(selectedBill.getAmountPay());
//			hall = hall.substring(hall.indexOf(".") + 1);
//			parameters.put("halaa", Integer.parseInt(hall));
//			String footerPath = FacesContext.getCurrentInstance().getExternalContext()
//					.getRealPath("/reports/footer.png");
//			parameters.put("footer", footerPath);
//			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
//					.getRealPath("/reports/header.png");
//			parameters.put("header", headerPath);
//			Utils.printPdfReport(reportName, parameters);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return "";
//	}
//
//	public SandService getSandServiceImpl() {
//		return sandServiceImpl;
//	}
//
//	public void setSandServiceImpl(SandService sandServiceImpl) {
//		this.sandServiceImpl = sandServiceImpl;
//	}
//
//
//	public DepartmentService getDepartmentServiceImpl() {
//		return departmentServiceImpl;
//	}
//
//	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
//		this.departmentServiceImpl = departmentServiceImpl;
//	}
//
//	public List<Departments> getDepts() {
//		return depts;
//	}
//
//	public void setDepts(List<Departments> depts) {
//		this.depts = depts;
//	}
//
//
//	public List<BillsPay> getBills() {
//		return bills;
//	}
//
//	public void setBills(List<BillsPay> bills) {
//		this.bills = bills;
//	}
//
//
//	public boolean isTaxFlag() {
//		return taxFlag;
//	}
//
//	public void setTaxFlag(boolean taxFlag) {
//		this.taxFlag = taxFlag;
//	}
//
//	public double getTotalWithoutTax() {
//		return totalWithoutTax;
//	}
//
//	public double getTotalRest() {
//		return totalRest;
//	}
//
//	public double getTaxValue() {
//		return taxValue;
//	}
//
//	public double getTotalAfterTax() {
//		return totalAfterTax;
//	}
//
	public List<Integer> getYears() {
		years = new ArrayList<Integer>();
		for (int i = 2020; i < 2121; i++) {
			years.add(i);
		}
		return years;
	}

	public void setYears(List<Integer> years) {

		this.years = years;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
