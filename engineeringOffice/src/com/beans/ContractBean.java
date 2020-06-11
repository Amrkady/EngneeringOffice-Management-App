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
	private boolean hide = false;
	private Integer conNo = new Integer(0);
	private Integer second = new Integer(0);
	private String sizes;
	private String reportSizes;
	private String[] selected;
	private boolean light;

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
			check();
			sandServiceImpl.addContract(contract);
			conNo++;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					Utils.loadMessagesFromFile("success.operation"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			enablePrint = true;
			Utils.updateUIComponent("form:print");

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("error.operation"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();

		}
		return "";
	}

	public String save() {
		String reportName = "/reports/contractReport.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();

		if (sizes.equalsIgnoreCase("1") || sizes.equalsIgnoreCase("2")) {
			parameters.put("sizes", reportSizes);
		} else if (sizes.equalsIgnoreCase("3")) {
			String value = Utils.loadMessagesFromFile("three-start");
			for (int i = 0; i < selected.length; i++) {
				if (i + 1 == selected.length) {
					value = value + selected[i];
				} else {
					value = value + selected[i] + " - ";
				}
			}
			value = value + ".";
			parameters.put("sizes", value);
		}

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

	public void check() {
		planSet();
		if (sizes.equalsIgnoreCase("3")) {
			contract.setPlan(3);
			for (int i = 0; i < selected.length; i++) {
				if (selected[i].equalsIgnoreCase(Utils.loadMessagesFromFile("elc"))) {
					contract.setElectric(1);
				}
				if (selected[i].trim().equalsIgnoreCase(Utils.loadMessagesFromFile("light2"))) {
					contract.setLightPlan(1);
				}
				if (selected[i].equalsIgnoreCase(Utils.loadMessagesFromFile("view"))) {
					contract.setView(1);
				}
				if (selected[i].equalsIgnoreCase(Utils.loadMessagesFromFile("garden"))) {
					contract.setPark(1);
				}
				if (selected[i].equalsIgnoreCase(Utils.loadMessagesFromFile("arc"))) {
					contract.setArch(1);
				}
				if (selected[i].equalsIgnoreCase(Utils.loadMessagesFromFile("building"))) {
					contract.setBuild(1);
				}
				if (selected[i].equalsIgnoreCase(Utils.loadMessagesFromFile("health"))) {
					contract.setSbaka(1);
				}

			}
		} else if (sizes.equalsIgnoreCase("2")) {
			contract.setPlan(2);

			contract.setLight(light == true ? 1 : 0);
		} else if (sizes.equalsIgnoreCase("1")) {
			contract.setPlan(1);
			contract.setLight(light == true ? 1 : 0);

		}
		contract.setLicOut(contract.isLicence() == true ? 1 : 0);
		contract.setTrba(contract.isTorba() == true ? 1 : 0); // 0 for no or 1 for yes
		contract.setAirCond(contract.isAirCon() == true ? 1 : 0); // 0 for no or 1 for yes
	}

	public String checkSize() {
		reportSizes = "";
		if (sizes.equalsIgnoreCase("1")) {
			contract.setTrba(0); // 0 for no or 1 for yes
			contract.setAirCond(0); // 0 for no or 1 for yes
			contract.setLight(0);
			contract.setTorba(false);
			contract.setAirCon(false);
			light = false;
			hide = false;
		} else if (sizes.equalsIgnoreCase("2")) {
			contract.setTrba(0); // 0 for no or 1 for yes
			contract.setAirCond(0); // 0 for no or 1 for yes
			contract.setLight(0);
			contract.setTorba(false);
			contract.setAirCon(false);
			light = false;
			hide = false;
		} else if (sizes.equalsIgnoreCase("3")) {
			selected = null;
			contract.setElectric(0);
			contract.setLightPlan(0);
			contract.setView(0);
			contract.setPark(0);
			contract.setArch(0);
			contract.setBuild(0);
			contract.setSbaka(0);
			hide = true;
			Utils.updateUIComponent("form:grid");

		}

		return "";
	}

	public void planSet() {
		reportSizes = "";
		if (sizes.equalsIgnoreCase("1")) {
			if (light) {
				reportSizes = Utils.loadMessagesFromFile("base") + Utils.loadMessagesFromFile("light")
						+ Utils.loadMessagesFromFile("base2") + Utils.loadMessagesFromFile("one-complete");
			} else {
				reportSizes = Utils.loadMessagesFromFile("base") + Utils.loadMessagesFromFile("base2")
						+ Utils.loadMessagesFromFile("one-complete");
			}
			System.out.println("reportSizes" + reportSizes);

		} else if (sizes.equalsIgnoreCase("2")) {
			if (light) {
				reportSizes = Utils.loadMessagesFromFile("base") + Utils.loadMessagesFromFile("light")
						+ Utils.loadMessagesFromFile("base2") + Utils.loadMessagesFromFile("two-complete");
			} else {
				reportSizes = Utils.loadMessagesFromFile("base") + Utils.loadMessagesFromFile("base2")
						+ Utils.loadMessagesFromFile("two-complete");
			}
		}
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

	public String getSizes() {
		return sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	public String getReportSizes() {
		return reportSizes;
	}

	public void setReportSizes(String reportSizes) {
		this.reportSizes = reportSizes;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public String[] getSelected() {
		return selected;
	}

	public void setSelected(String[] selected) {
		this.selected = selected;
	}

	public boolean isLight() {
		return light;
	}

	public void setLight(boolean light) {
		this.light = light;
	}

}
