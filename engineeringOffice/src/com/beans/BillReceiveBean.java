package com.beans;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
	private Bills billReceive;
	private Date billDate;
	private double taxVal;
	private double amountWithoutTax;
	private boolean flag;
	private double totalVal;
	private double amountPay;

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
					totalWithoutTax += billsRecieve.get(i).getAmountPay().doubleValue();
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
		commision = Math.round(commision * 100) / 100.00d;
		total = Math.round(total * 100) / 100.00d;
		totalWithoutTax = Math.round(totalWithoutTax * 100) / 100.00d;
		deptIncome = Math.round(deptIncome * 100) / 100.00d;
		mngCommision = Math.round(mngCommision * 100) / 100.00d;
		totalBillsPay = Math.round(totalBillsPay * 100) / 100.00d;
	}

	private void findTotalPay() {

		for (int i = 0; i < billsPay.size(); i++) {

			billPay = billsPay.get(i);
			if (billPay.getTax() == 0) {
				totalBillsPay += billPay.getAmountPay().doubleValue();
			} else if (billPay.getTax() == 1) {
				totalBillsPay += (billPay.getAmountPay()).doubleValue()
						- ((billPay.getAmountPay().doubleValue() / 1.05) * 0.05);
			}
		}

	}

	private void findTotalReceive() {

		for (int i = 0; i < billsRecieve.size(); i++) {
			bill = billsRecieve.get(i);
			if (bill.getTax() == 0) {
				total += bill.getAmountPay().doubleValue();
			} else if (bill.getTax() == 1) {
				total += (bill.getAmountPay().doubleValue()) - ((bill.getAmountPay().doubleValue() * 0.05) / 1.05);
			}

			totalRest += billsRecieve.get(i).getAmountRest().doubleValue();
		}

	}

	public String print(Bills selectedBill) {
		System.out.print("print >>>>>>>.");
		try {
			String reportName = "/reports/Bills_snad.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sndNo", selectedBill.getSanadNo());
			parameters.put("custName", selectedBill.getCustomerName());

			String reyal = String.valueOf(selectedBill.getAmountPay());
			if (reyal.contains(".")) {
				reyal = reyal.substring(0, reyal.indexOf("."));
				parameters.put("reyal", Integer.parseInt(reyal));
				String hall = String.valueOf(selectedBill.getAmountPay());
				hall = hall.substring(hall.indexOf(".") + 1);
				parameters.put("halaa", Integer.parseInt(hall));
			} else {
				parameters.put("reyal", selectedBill.getAmountPay().intValue());
				parameters.put("halaa", 00);
			}

			parameters.put("costRest", selectedBill.getAmountRest());
			parameters.put("for", selectedBill.getBillReason());
			parameters.put("payType", selectedBill.getBillType());
			parameters.put("dept", selectedBill.getDeptName());
			parameters.put("date", selectedBill.getBillDate());
			parameters.put("costByLet", selectedBill.getAmountPay());
			if (selectedBill.getTax() == 0) {
				parameters.put("tax", selectedBill.getAmountPay().doubleValue());
				parameters.put("taxValue", 0.0);
			} else {
				parameters.put("taxValue", (selectedBill.getAmountPay().doubleValue() / 1.05) * 0.05);
				parameters.put("tax", selectedBill.getAmountPay().doubleValue()
						- (selectedBill.getAmountPay().doubleValue() / 1.05) * 0.05);

			}

			String footerPath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/footer.png");
			parameters.put("footer", footerPath);
			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/header.png");
			parameters.put("header", headerPath);
//		//parameters.put("userId", employerId);
			Utils.printPdfReport(reportName, parameters);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public void edit(Bills selectedBill) {
		if (selectedBill != null) {
			// billsPay = new BillsPay();

			billReceive = selectedBill;
			if (billReceive.getTax() == 1) {
				flag = true;
				taxVal = (billReceive.getAmountPay().doubleValue() / 1.05) * 0.05;
				taxVal = Math.round(taxVal * 100) / 100.00d;
				totalVal = billReceive.getAmountPay().doubleValue();
				totalVal = Math.round(totalVal * 100) / 100.00d;
				amountPay = billReceive.getAmountPay().doubleValue()
						- (billReceive.getAmountPay().doubleValue() / 1.05) * 0.05;
				billReceive.setAmountPay(new BigDecimal(amountPay));

			} else {
				flag = false;
				taxVal = 0;
				totalVal = billReceive.getAmountPay().doubleValue();
				amountWithoutTax = billReceive.getAmountPay().doubleValue();
			}
			billReceive.setAmountPay(selectedBill.getAmountPay());
			System.out.println("update>>>>>>>>>>" + billReceive.getSanadNo());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {
				billDate = dateFormat.parse(selectedBill.getBillDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Utils.openDialog("whsdlAdd");
		}
		Utils.openDialog("whsdlAdd");
	}

	public void updateComm() {
		if (flag == true) {
			taxVal = billReceive.getAmountPay().doubleValue() * 0.05;

			totalVal = billReceive.getAmountPay().doubleValue() + taxVal;
			taxVal = Math.round(taxVal * 100) / 100.00d;

			totalVal = Math.round(totalVal * 100) / 100.00d;
		} else {
			taxVal = 0;
			totalVal = 0;
		}
	}

	public String save() {

		try {
			String strDate = "";
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			if (billDate != null) {
				strDate = sdfDate.format(billDate);
			}
			if (flag == true) {
				billReceive.setAmountPay(new BigDecimal(totalVal));
				billReceive.setTax(1);
			} else {
				billReceive.setTax(0);
			}
			billReceive.setTax(flag == true ? 1 : 0);
			billReceive.setBillDate(strDate);
			billReceive.setDate(new Date());

			sandServiceImpl.updateBillsReceive(billReceive);
			billReceive = new Bills();
			billDate = null;

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					Utils.loadMessagesFromFile("success.operation"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			init();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("error.operation"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
		return "";
	}

	public void back() {
		if (billReceive.getTax() == 1) {
			billReceive.setAmountPay(new BigDecimal(totalVal));
			totalVal = 0;

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

	public double getTaxVal() {
		return taxVal;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Bills getBillReceive() {
		return billReceive;
	}

	public void setBillReceive(Bills billReceive) {
		this.billReceive = billReceive;
	}

	public double getAmountWithoutTax() {
		return amountWithoutTax;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Bills getB() {
		return b;
	}

	public void setB(Bills b) {
		this.b = b;
	}

	public List<BillsPay> getBillsPay() {
		return billsPay;
	}

	public void setBillsPay(List<BillsPay> billsPay) {
		this.billsPay = billsPay;
	}

	public Bills getBill() {
		return bill;
	}

	public void setBill(Bills bill) {
		this.bill = bill;
	}

	public BillsPay getBillPay() {
		return billPay;
	}

	public void setBillPay(BillsPay billPay) {
		this.billPay = billPay;
	}

	public DecimalFormat getDf2() {
		return df2;
	}

	public void setDf2(DecimalFormat df2) {
		this.df2 = df2;
	}

	public double getTotalVal() {
		return totalVal;
	}

	public void setTotalVal(double totalVal) {
		this.totalVal = totalVal;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setTotalBillsPay(double totalBillsPay) {
		this.totalBillsPay = totalBillsPay;
	}

	public void setCommision(double commision) {
		this.commision = commision;
	}

	public void setDeptIncome(double deptIncome) {
		this.deptIncome = deptIncome;
	}

	public void setMngCommision(double mngCommision) {
		this.mngCommision = mngCommision;
	}

	public void setTotalRest(double totalRest) {
		this.totalRest = totalRest;
	}

	public void setTotalWithoutTax(double totalWithoutTax) {
		this.totalWithoutTax = totalWithoutTax;
	}

	public void setTaxVal(double taxVal) {
		this.taxVal = taxVal;
	}

	public void setAmountWithoutTax(double amountWithoutTax) {
		this.amountWithoutTax = amountWithoutTax;
	}

	public double getAmountPay() {
		return amountPay;
	}

	public void setAmountPay(double amountPay) {
		this.amountPay = amountPay;
	}

}
