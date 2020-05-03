package com.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
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

@ManagedBean(name = "financailBean")
@ViewScoped
public class FinancailYearBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;

	private Date dateFrom;

	private Date dateTo;
	private boolean enablePrint = true;

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
	private double boxValue;
	private double bankValue;
	private double creditor;
	private double debtor;
	private double srfArc = 0.0;
	private double srfSpace = 0.0;
	private double srfOffice = 0.0;
	private double bankdebt = 0.0;
	private double totalBills;
	private double totalPay;
	private double totalProfitAfterTaxs = 0.0;
	private double totalProfitBeforTaxs = 0.0;
	private double zakahTax = 0.0;
	private List<Integer> years;
	private List<BankDeposit> bankDeposits;
	private Integer year;

	@PostConstruct
	public void init() {

	}

	public void getBoxAndBank() {
		// yyyy-MM-dd
		enablePrint = false;
		bankDeposits = new ArrayList<BankDeposit>();
		bankDeposits = sandServiceImpl.findBankDepositByYear(dateFrom, dateTo);
		if (bankDeposits != null) {
			for (BankDeposit bankDeposit : bankDeposits) {
				bankdebt += bankDeposit.getValue().doubleValue();
			}
			boxValue = cash - bankdebt;
			bankValue = (transfer + visa + bankdebt) - visaCommision;
			debtor = boxValue + bankValue + totalPayAfterTax;
			creditor = totalAfterTaxComm;
			debtor = Math.round(debtor * 100) / 100.00d;
			creditor = Math.round(creditor * 100) / 100.00d;
			boxValue = Math.round(boxValue * 100) / 100.00d;
			bankValue = Math.round(bankValue * 100) / 100.00d;
		}

	}

	public void loadData() {
		debtor = 0;
		boxValue = 0;
		creditor = 0;
		bankValue = 0;
		srfArc = 0.0;
		srfSpace = 0.0;
		srfOffice = 0.0;
		totalPay = 0.0;
		totalBills = 0.0;
		totalProfitAfterTaxs = 0.0;
		totalProfitBeforTaxs = 0.0;
		zakahTax = 0.0;
		if (year != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			try {
				dateFrom = dateFormat.parse(year.toString());
				year += 1;
				dateTo = dateFormat.parse(year.toString());
				DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
				dateFrom = dateFormat.parse(dateFormat2.format(dateFrom));
				dateTo = dateFormat.parse(dateFormat2.format(dateTo));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
			bills = new ArrayList<Bills>();
			billsPay = new ArrayList<BillsPay>();
			bills = sandServiceImpl.getBillsByDate(dateFrom, dateTo);
			billsPay = sandServiceImpl.getBillsPayByDate(dateFrom, dateTo);
			if (bills.size() > 0) {
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
						transfer += bill.getAmountPay().doubleValue();
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
					if (billPay.getDeptId() == Constant.arch_dept) {
						srfArc += billPay.getAmountPay().doubleValue();
					} else if (billPay.getDeptId() == Constant.space_dept) {
						srfSpace += billPay.getAmountPay().doubleValue();
					} else if (billPay.getDeptId() == Constant.office_dept) {
						srfOffice += billPay.getAmountPay().doubleValue();
					}

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

				totalPay = srfArc + srfSpace + srfOffice;
				totalBills = archDept + spaceDept + officeDept;
				totalPay = Math.round(totalPay * 100) / 100.00d;
				totalBills = Math.round(totalBills * 100) / 100.00d;

				totalProfitBeforTaxs = totalBills - totalPay;
				totalProfitBeforTaxs = Math.round(totalProfitBeforTaxs * 100) / 100.00d;
				totalProfitAfterTaxs = totalBills - totalPay - taxValue;
				totalProfitAfterTaxs = Math.round(totalProfitAfterTaxs * 100) / 100.00d;

				year--;
				System.out.println(year);
				getBoxAndBank();
			}
		}
	}

	public String addTaxs() {
		totalProfitAfterTaxs = totalProfitAfterTaxs - zakahTax;
		totalProfitAfterTaxs = Math.round(totalProfitAfterTaxs * 100) / 100.00d;
		System.out.println(">>>>>>>>" + totalProfitAfterTaxs);
		Utils.updateUIComponent("form:pro");
		return "";
	}

	public String printReviewSystem() {
		System.out.print("print >>>>>>>.");
		try {
			String reportName = "/reports/review_system.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("box", boxValue);
			parameters.put("bank", bankValue);
			parameters.put("income", totalAfterTaxComm);
			parameters.put("outcome", totalPayAfterTax);
			parameters.put("totalMadeen", debtor);
			parameters.put("year", year.toString());
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

	public String printIcomeMenu() {
		System.out.print("print >>>>>>>.");
		try {
			String reportName = "/reports/Income_menu.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("inArc", archDept);
			parameters.put("inSpace", spaceDept);
			parameters.put("inOffice", officeDept);
			parameters.put("outArc", srfArc);
			parameters.put("outSpace", srfSpace);
			parameters.put("outOffice", srfOffice);
			parameters.put("totalIn", totalBills);
			parameters.put("totalOut", totalPay);
			parameters.put("totalBoforTax", totalProfitBeforTaxs);
			parameters.put("totalAfterTax", totalProfitAfterTaxs);
			parameters.put("tax", taxValue);
			parameters.put("zakahTax", zakahTax);

			parameters.put("year", year.toString());
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

	public String printFinacialCenter() {
		System.out.print("print >>>>>>>.");
		try {
			String reportName = "/reports/financial_center_menu.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("box", boxValue);
			parameters.put("bank", bankValue);
			parameters.put("first", ownerComm);
			parameters.put("second", ownerComm);
			parameters.put("third", partnerCommision);
			parameters.put("year", year.toString());
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

	public double getTotalRest() {
		return totalRest;
	}

	public void setTotalRest(double totalRest) {
		this.totalRest = totalRest;
	}

	public double getPartnerCommision() {
		return partnerCommision;
	}

	public void setPartnerCommision(double partnerCommision) {
		this.partnerCommision = partnerCommision;
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

	public double getBoxValue() {
		return boxValue;
	}

	public void setBoxValue(double boxValue) {
		this.boxValue = boxValue;
	}

	public double getBankValue() {
		return bankValue;
	}

	public void setBankValue(double bankValue) {
		this.bankValue = bankValue;
	}

	public double getCreditor() {
		return creditor;
	}

	public void setCreditor(double creditor) {
		this.creditor = creditor;
	}

	public double getDebtor() {
		return debtor;
	}

	public void setDebtor(double debtor) {
		this.debtor = debtor;
	}

	public List<BankDeposit> getBankDeposits() {
		return bankDeposits;
	}

	public void setBankDeposits(List<BankDeposit> bankDeposits) {
		this.bankDeposits = bankDeposits;
	}

	public double getBankdebt() {
		return bankdebt;
	}

	public void setBankdebt(double bankdebt) {
		this.bankdebt = bankdebt;
	}

	public double getSrfArc() {
		return srfArc;
	}

	public void setSrfArc(double srfArc) {
		this.srfArc = srfArc;
	}

	public double getSrfSpace() {
		return srfSpace;
	}

	public void setSrfSpace(double srfSpace) {
		this.srfSpace = srfSpace;
	}

	public double getSrfOffice() {
		return srfOffice;
	}

	public void setSrfOffice(double srfOffice) {
		this.srfOffice = srfOffice;
	}

	public double getTotalBills() {
		return totalBills;
	}

	public void setTotalBills(double totalBills) {
		this.totalBills = totalBills;
	}

	public double getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}

	public double getTotalProfitAfterTaxs() {
		return totalProfitAfterTaxs;
	}

	public void setTotalProfitAfterTaxs(double totalProfitAfterTaxs) {
		this.totalProfitAfterTaxs = totalProfitAfterTaxs;
	}

	public double getTotalProfitBeforTaxs() {
		return totalProfitBeforTaxs;
	}

	public void setTotalProfitBeforTaxs(double totalProfitBeforTaxs) {
		this.totalProfitBeforTaxs = totalProfitBeforTaxs;
	}

	public double getZakahTax() {
		return zakahTax;
	}

	public void setZakahTax(double zakahTax) {
		this.zakahTax = zakahTax;
	}

	public boolean isEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(boolean enablePrint) {
		this.enablePrint = enablePrint;
	}

}
