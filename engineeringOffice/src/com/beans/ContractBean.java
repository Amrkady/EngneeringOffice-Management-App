package com.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.entities.Contracts;
import com.models.CustomerModel;
import com.services.SandService;

import common.util.Utils;

@ManagedBean(name = "contractBean")
@ViewScoped
public class ContractBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	private Contracts contract;
	private Date contractDate;
	private CustomerModel cm = new CustomerModel();
	private boolean enablePrint = false;
	private Integer conNo = new Integer(0);

	@PostConstruct
	public void init() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyyy");
		Calendar cal = Calendar.getInstance();
		GregorianCalendar.getInstance().getTime();
		contractDate = cal.getTime();

		// System.out.println(">>>>>>" + contractDate);
		contract = new Contracts();
		HttpServletRequest httprequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession httpSession = httprequest.getSession(false);
		cm = (CustomerModel) httpSession.getAttribute("CustomerObject");
		if (cm != null) {
			contract.setCustomerId(cm.getCustomerId());
			contract.setDeptId(cm.getDeptId());
			contract.setNat_no(cm.getNatNo());

		}

		conNo = sandServiceImpl.getContractNo() + 1;

	}

	public String addContract() {
		try {
			String strDate = "";
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			if (contractDate != null) {
				strDate = sdfDate.format(contractDate);
			}
			contract.setContractDate(strDate);
			contract.setDayLetter(Utils.getDayDate(strDate));
			contract.setConNo(conNo);
			contract.setSent(0);
			sandServiceImpl.addContract(contract);
			conNo++;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "�� �������", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			enablePrint = true;
			Utils.updateUIComponent("form:print");

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "�� ��� �������", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();

		}
		return "";
	}

	public String save() {
		String reportName = "/reports/contractReport.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("day", contract.getDayLetter());
		parameters.put("date", contract.getContractDate());
		parameters.put("recordNo", cm.getNatNo().toString());
		parameters.put("custName", cm.getCustomerName());
		parameters.put("address", cm.getAddress());
		parameters.put("phone", cm.getPhone());
		parameters.put("onwerNo", contract.getOwnerNo());
		parameters.put("buildDetails", contract.getLicenseType());
		parameters.put("from", contract.getOutFrom());
		parameters.put("outDate", contract.getOutHijridate());
		parameters.put("cost", contract.getAmount().toString());
		parameters.put("licence", contract.isLicence() == true ? 1 : 0); // 0 for no or 1 for yes
		parameters.put("torba", contract.isTorba() == true ? 1 : 0); // 0 for no or 1 for yes
		parameters.put("airCon", contract.isAirCon() == true ? 1 : 0); // 0 for no or 1 for yes

		String footerPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/footer.png");
		parameters.put("footer", footerPath);
		String headerPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/header.png");
		parameters.put("header", headerPath);
		Utils.printPdfReport(reportName, parameters);

		return "";
	}

	public SandService getSandServiceImpl() {
		return sandServiceImpl;
	}

	public void setSandServiceImpl(SandService sandServiceImpl) {
		this.sandServiceImpl = sandServiceImpl;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Contracts getContract() {
		return contract;
	}

	public void setContract(Contracts contract) {
		this.contract = contract;
	}

	public CustomerModel getCm() {
		return cm;
	}

	public void setCm(CustomerModel cm) {
		this.cm = cm;
	}

	public Integer getConNo() {
		return conNo;
	}

	public void setConNo(Integer conNo) {
		this.conNo = conNo;
	}

	public boolean isEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(boolean enablePrint) {
		this.enablePrint = enablePrint;
	}

}
