package com.beans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.entities.Bills;
import com.models.CustomerModel;
import com.services.SandService;

import common.util.Utils;

@ManagedBean(name = "sandBean")
@ViewScoped
public class BillSandBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	private Bills billSnad;
	private Date sandDate;
	private boolean enablePrint = false;
	boolean status = false;
	private Integer sandNo = new Integer(0);
	private boolean vat;
	private boolean taxs;
	private double total;

	@PostConstruct
	public void init() {
		// sandDate = GregorianCalendar.getInstance().getTime();
		// System.out.println(">>>>.." + sandDate);
		billSnad = new Bills();
		HttpServletRequest httprequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession httpSession = httprequest.getSession(false);
		CustomerModel cm = (CustomerModel) httpSession.getAttribute("CustomerObject");
		if (cm != null) {
			billSnad.setCustomerId(cm.getCustomerId());
			billSnad.setDeptName(cm.getDeptName());
			billSnad.setDeptId(cm.getDeptId());
			billSnad.setCustomerName(cm.getCustomerName());
			// billSnad.setSanadNo(sandServiceImpl.getSandNo() + 1);
			sandNo = sandServiceImpl.getSandNo() + 1;
		}
	}

	public String addBillOrSnad() {
		try {
			String strDate = "";
			// System.out.println(sandServiceImpl.addSand(billSnad));
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/mm/yyyy");
			if (sandDate != null) {
				strDate = sdfDate.format(sandDate);
			}
			billSnad.setBillDate(strDate);
			billSnad.setDate(new Date());
			billSnad.setSanadNo(sandNo);
			billSnad.setAmountPay(new BigDecimal(total));
			if (taxs == true) {
				billSnad.setTax(1);
			}
			status = sandServiceImpl.addSand(billSnad);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, " „ «·Õ›Ÿ", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			sandNo++;
			enablePrint = true;
			Utils.updateUIComponent("form:print");
			Utils.updateUIComponent("form:sndNo");

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "·„ Ì „ «·Õ›Ÿ «⁄œ «·„Õ«Ê·…", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
			status = false;

		}
		return "";

	}

	public String save() {
		try {
			String reportName = "/reports/Bills_snad.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sndNo", billSnad.getSanadNo());
			parameters.put("custName", billSnad.getCustomerName());

			String reyal = String.valueOf(billSnad.getAmountPay());
			reyal = reyal.substring(0, reyal.indexOf("."));
			parameters.put("reyal", Integer.parseInt(reyal));

			parameters.put("costRest", billSnad.getAmountRest());
			parameters.put("for", billSnad.getBillReason());
			parameters.put("payType", billSnad.getBillType());
			parameters.put("dept", billSnad.getDeptName());
			parameters.put("date", billSnad.getBillDate());
			parameters.put("costByLet", billSnad.getAmountPay());
			parameters.put("tax", billSnad.getTax());

			String hall = String.valueOf(billSnad.getAmountPay());
			hall = hall.substring(hall.indexOf(".") + 1);
			parameters.put("halaa", Integer.parseInt(hall));

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

	public void updateCom() {
		if (billSnad.getAmountPay() != null && vat == true) {
			double taxCal = (5 / 100.0);
			total = billSnad.getAmountPay().doubleValue() + billSnad.getAmountPay().doubleValue() * taxCal;
			total = Math.round(total * 100) / 100.0d;
			// billSnad.setAmountPay(new BigDecimal(total));
			Utils.updateUIComponent("form:d4");
			System.out.println(">>>>>>>>1111111111111");
		} else {
			if (billSnad.getAmountPay() != null) {
				total = billSnad.getAmountPay().doubleValue();
				total = Math.round(total * 100) / 100.0d;
			} else {
				total = 0.0;
			}
		}

	};

	public void updateTotal() {
		if (billSnad.getAmountPay() != null && vat == true && taxs == true) {
			total = billSnad.getAmountPay().doubleValue();
			total = Math.round(total * 100) / 100.0d;
			Utils.updateUIComponent("form:d4");
			System.out.println(">>>>>>>>222222");
		} else {
			if (billSnad.getAmountPay() != null) {
				double taxCal = (5 / 100.0);
				total = billSnad.getAmountPay().doubleValue() + billSnad.getAmountPay().doubleValue() * taxCal;
				total = Math.round(total * 100) / 100.0d;
			} else {
				total = 0.0;
			}
		}

	};

	public SandService getSandServiceImpl() {
		return sandServiceImpl;
	}

	public void setSandServiceImpl(SandService sandServiceImpl) {
		this.sandServiceImpl = sandServiceImpl;
	}

	public Bills getBillSnad() {
		return billSnad;
	}

	public void setBillSnad(Bills billSnad) {
		this.billSnad = billSnad;
	}

	public Date getSandDate() {
		return sandDate;
	}

	public void setSandDate(Date sandDate) {
		this.sandDate = sandDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(boolean enablePrint) {
		this.enablePrint = enablePrint;
	}

	public Integer getSandNo() {
		return sandNo;
	}

	public void setSandNo(Integer sandNo) {
		this.sandNo = sandNo;
	}

	public boolean isVat() {
		return vat;
	}

	public void setVat(boolean vat) {
		this.vat = vat;
	}

	public boolean isTaxs() {
		return taxs;
	}

	public void setTaxs(boolean taxs) {
		this.taxs = taxs;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
