package com.beans;

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

	boolean status = false;

	@PostConstruct
	public void init() {

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
			billSnad.setSanadNo(sandServiceImpl.getSandNo() + 1);
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
			status = sandServiceImpl.addSand(billSnad);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "�� �����", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			billSnad.setSanadNo(sandServiceImpl.getSandNo() + 1);
//			status = true;
//			Utils.updateUIComponent("form:print");

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "��� ����� ����� ��� ��� ����", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
			status = false;

		}
		return "";

	}

	public String save() {
		try {
		addBillOrSnad();
		String reportName = "/reports/Bills_snad.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("sndNo", billSnad.getSanadNo());
		parameters.put("custName", billSnad.getCustomerName());
		parameters.put("reyal", billSnad.getAmountPay());
		parameters.put("costRest", billSnad.getAmountRest());
		parameters.put("for", billSnad.getBillReason());
		parameters.put("payType", billSnad.getBillType());
		parameters.put("dept", billSnad.getDeptName());
		parameters.put("date", billSnad.getBillDate());
		parameters.put("costByLet", billSnad.getAmountLetters());
		parameters.put("halaa", 00);
		String footerPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/footer.png");
		parameters.put("footer", footerPath);
		String headerPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/header.png");
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

}
