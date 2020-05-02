package com.beans;

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
import com.entities.BankDeposit;
import com.entities.Bills;
import com.entities.BillsPay;
import com.services.DepartmentService;
import com.services.SandService;

import common.util.Utils;

@ManagedBean
@ViewScoped
public class RevenueBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;

	private Date dateFrom;

	private Date dateTo;

	private List<Bills> bills;
	private List<BillsPay> billsPay;
	private double totalReceive;
	private double totalRest;
	private double partnerCommision;
	private double totalAfterTaxComm;
	private double totalPayAfterTax;
	private Bills bill;
	private BillsPay billPay;
	private double archDept;
	private double spaceDept;
	private double officeDept;
	private double netProfitMonth;
	private double ownerComm;
	private double cash;
	private double transfer;
	private double visa;
	private double visaCommision;
	private double taxValue;
	private BankDeposit bnkDeposit = new BankDeposit();
	
	@PostConstruct
	public void init() {

	}

	public void load() {
		cash = 0;
		visa = 0;
		transfer = 0;
		totalReceive = 0;
		totalRest = 0;
		partnerCommision = 0;
		totalAfterTaxComm = 0;
		archDept = 0;
		spaceDept = 0;
		officeDept = 0;
		totalPayAfterTax = 0;
		netProfitMonth = 0;
		ownerComm = 0;
		taxValue = 0;
		Bills bill;
		bills = sandServiceImpl.getBillsByDate(dateFrom, dateTo);
		billsPay = sandServiceImpl.getBillsPayByDate(dateFrom, dateTo);
		for (int i = 0; i < bills.size(); i++) {
			bill = bills.get(i);
			totalReceive += bill.getAmountPay().doubleValue();
			totalRest += bill.getAmountRest().doubleValue();
			if (bill.getTax() == 1) {
				totalAfterTaxComm += bill.getAmountPay().doubleValue()
						- ((bill.getAmountPay().doubleValue() / 1.05) * 0.05);
				taxValue += (bill.getAmountPay().doubleValue() / 1.05) * 0.05;
			} else if (bill.getTax() == 0) {
				totalAfterTaxComm += bill.getAmountPay().doubleValue();
			}
			if (bill.getBillType().equals("äÞÏí")) {
				cash += bill.getAmountPay().doubleValue();
			} else if (bill.getBillType().equals("ÔÈßÉ")) {
				visa += bill.getAmountPay().doubleValue();
				visaCommision += bill.getAmountPay().doubleValue() * 0.0084;

			} else if (bill.getBillType().equals("ÊÍæíá")) {
				transfer+=bill.getAmountPay().doubleValue();
			}


			if (bill.getDeptId() == Constant.arch_dept) {
				archDept += bill.getAmountPay().doubleValue();
			} else if (bill.getDeptId() == Constant.space_dept) {
				spaceDept += bill.getAmountPay().doubleValue();
			} else if (bill.getDeptId() == Constant.office_dept) {
				officeDept += bill.getAmountPay().doubleValue();
			}

		}
		partnerCommision = totalReceive * 5 / 100;
		totalAfterTaxComm = totalAfterTaxComm - partnerCommision;

		for (int i = 0; i < billsPay.size(); i++) {
			billPay = billsPay.get(i);
			if (billPay.getTax() == 1) {
				totalPayAfterTax += billPay.getAmountPay().doubleValue()
						- ((billPay.getAmountPay().doubleValue() / 1.05) * 0.05);
			} else if (billPay.getTax() == 0) {
				totalPayAfterTax += billPay.getAmountPay().doubleValue();
			}
		}
		netProfitMonth = totalAfterTaxComm - totalPayAfterTax;
		ownerComm = netProfitMonth / 2;

		totalReceive = Math.round(totalReceive * 100) / 100.00d;
		totalRest = Math.round(totalRest * 100) / 100.00d;
		partnerCommision = Math.round(partnerCommision * 100) / 100.00d;
		totalAfterTaxComm = Math.round(totalAfterTaxComm * 100) / 100.00d;
		archDept = Math.round(archDept * 100) / 100.00d;
		spaceDept = Math.round(spaceDept * 100) / 100.00d;
		officeDept = Math.round(officeDept * 100) / 100.00d;

		totalPayAfterTax = Math.round(totalPayAfterTax * 100) / 100.00d;
		netProfitMonth = Math.round(netProfitMonth * 100) / 100.00d;
		ownerComm = Math.round(ownerComm * 100) / 100.00d;
		visaCommision = Math.round(visaCommision * 100) / 100.00d;
		taxValue = Math.round(taxValue * 100) / 100.00d;

	}

	public void addDeposit() {

		bnkDeposit.setDate(new Date());
		sandServiceImpl.addBankDeposit(bnkDeposit);

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ØªÙ…Øª Ø§Ù„Ø§Ø¶Ø§ÙØ© Ø¨Ù†Ø¬Ø§Ø­", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public SandService getSandServiceImpl() {
		return sandServiceImpl;
	}

	public void setSandServiceImpl(SandService sandServiceImpl) {
		this.sandServiceImpl = sandServiceImpl;
	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
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

	public List<Bills> getBills() {
		return bills;
	}

	public void setBills(List<Bills> bills) {
		this.bills = bills;
	}

	public double getTotalPay() {
		return totalReceive;
	}

	public double getTotalRest() {
		return totalRest;
	}

	public double getPartnerCommision() {
		return partnerCommision;
	}

	public double getTotalAfterTax() {
		return totalAfterTaxComm;
	}

	public List<BillsPay> getBillsPay() {
		return billsPay;
	}

	public void setBillsPay(List<BillsPay> billsPay) {
		this.billsPay = billsPay;
	}

	public double getTotalReceive() {
		return totalReceive;
	}

	public void setTotalReceive(double totalReceive) {
		this.totalReceive = totalReceive;
	}

	public double getTotalAfterTaxComm() {
		return totalAfterTaxComm;
	}

	public void setTotalAfterTaxComm(double totalAfterTaxComm) {
		this.totalAfterTaxComm = totalAfterTaxComm;
	}

	public double getTotalPayAfterTax() {
		return totalPayAfterTax;
	}

	public void setTotalPayAfterTax(double totalPayAfterTax) {
		this.totalPayAfterTax = totalPayAfterTax;
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

	public double getArchDept() {
		return archDept;
	}

	public void setArchDept(double archDept) {
		this.archDept = archDept;
	}

	public double getSpaceDept() {
		return spaceDept;
	}

	public void setSpaceDept(double spaceDept) {
		this.spaceDept = spaceDept;
	}

	public double getOfficeDept() {
		return officeDept;
	}

	public void setOfficeDept(double officeDept) {
		this.officeDept = officeDept;
	}

	public double getNetProfitMonth() {
		return netProfitMonth;
	}

	public void setNetProfitMonth(double netProfitMonth) {
		this.netProfitMonth = netProfitMonth;
	}

	public double getOwnerComm() {
		return ownerComm;
	}

	public void setOwnerComm(double ownerComm) {
		this.ownerComm = ownerComm;
	}

	public void setTotalRest(double totalRest) {
		this.totalRest = totalRest;
	}

	public void setPartnerCommision(double partnerCommision) {
		this.partnerCommision = partnerCommision;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public double getTransfer() {
		return transfer;
	}

	public void setTransfer(double transfer) {
		this.transfer = transfer;
	}

	public double getVisa() {
		return visa;
	}

	public void setVisa(double visa) {
		this.visa = visa;
	}

	public double getVisaCommision() {
		return visaCommision;
	}

	public void setVisaCommision(double visaCommision) {
		this.visaCommision = visaCommision;
	}

	public double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(double taxValue) {
		this.taxValue = taxValue;
	}

	public BankDeposit getBnkDeposit() {
		return bnkDeposit;
	}

	public void setBnkDeposit(BankDeposit bnkDeposit) {
		this.bnkDeposit = bnkDeposit;
	}

}
