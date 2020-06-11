package com.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.Constant;
import com.entities.Contracts;
import com.entities.Customers;
import com.services.CustomerService;
import com.services.SandService;

import common.util.Utils;

@ManagedBean
@ViewScoped
public class ContractsViewBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	@ManagedProperty(value = "#{customerServiceImpl}")
	private CustomerService customerServiceImpl;
	List<Contracts> contracts;
	private List<Contracts> filtredContracts;

	@PostConstruct
	public void init() {
		if (Utils.findCurrentUser().getRoleId() == Constant.ROLE_MANAGER) {
			contracts = sandServiceImpl.getContractsByDept(Utils.findCurrentUser().getDeptId());
		} else if (Utils.findCurrentUser().getRoleId() == Constant.ROLE_ADMIN) {
			contracts = sandServiceImpl.getAllContracts();
		}
	}

	public String transferContract(Integer contractNo) {
//		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
//		flash.put("contractNo", contractNo);
//		
		HttpServletRequest httprequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession httpSession = httprequest.getSession(false);
		httpSession.setAttribute("contractNo", contractNo);
		return "trans";

	}

	public String save(Contracts contract) {
		if (contract == null) {
			return "";
		}
		Customers customer = customerServiceImpl.findCustomerById(contract.getCustomerId());
		String reportName = "/reports/contractReport.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		String reportSizes = "";
		if (contract.getPlan() == 1) {
			if (contract.getLight() == 1) {
				reportSizes = Utils.loadMessagesFromFile("base") + Utils.loadMessagesFromFile("light")
						+ Utils.loadMessagesFromFile("base2") + Utils.loadMessagesFromFile("one-complete");
			} else {
				reportSizes = Utils.loadMessagesFromFile("base") + Utils.loadMessagesFromFile("base2")
						+ Utils.loadMessagesFromFile("one-complete");
			}
			parameters.put("sizes", reportSizes);
		} else if (contract.getPlan() == 2) {
			if (contract.getLight() == 1) {
				reportSizes = Utils.loadMessagesFromFile("base") + Utils.loadMessagesFromFile("light")
						+ Utils.loadMessagesFromFile("base2") + Utils.loadMessagesFromFile("two-complete");
			} else {
				reportSizes = Utils.loadMessagesFromFile("base") + Utils.loadMessagesFromFile("base2")
						+ Utils.loadMessagesFromFile("two-complete");
			}
			parameters.put("sizes", reportSizes);

		} else if (contract.getPlan() == 3) {
			String value = Utils.loadMessagesFromFile("three-start");

			if (contract.getArch() == 1) {
				value = value + Utils.loadMessagesFromFile("arc") + " - ";
			}
			if (contract.getBuild() == 1) {
				value = value + Utils.loadMessagesFromFile("building") + " - ";
			}
			if (contract.getSbaka() == 1) {
				value = value + Utils.loadMessagesFromFile("health") + " - ";
			}
			if (contract.getElectric() == 1) {
				value = value + Utils.loadMessagesFromFile("elc") + " - ";
			}
			if (contract.getLightPlan() == 1) {
				value = value + Utils.loadMessagesFromFile("light2") + " - ";

			}
			if (contract.getView() == 1) {
				value = value + Utils.loadMessagesFromFile("view") + " - ";
			}
			if (contract.getPark() == 1) {
				value = value + Utils.loadMessagesFromFile("garden") + " - ";
			}

			value = value.substring(0, value.length() - 2);
			value = value + ".";
			parameters.put("sizes", value);
		}

		// System.out.println("reportSizes" + reportSizes);

		parameters.put("day", contract.getDayLetter());
		parameters.put("date", contract.getContractDate());
		if (customer != null) {
			parameters.put("recordNo", customer.getNatNo().toString());
			parameters.put("custName", customer.getCustomerName());
			parameters.put("address", customer.getAddress());
			parameters.put("phone", customer.getPhone());
		}
		parameters.put("onwerNo", contract.getOwnerNo());
		parameters.put("buildDetails", contract.getLicenseType());
		parameters.put("from", contract.getOutFrom());
		parameters.put("outDate", contract.getOutHijridate());
		parameters.put("cost", contract.getAmount().toString());
		parameters.put("licence", contract.getLicOut()); // 0
		parameters.put("torba", contract.getTrba()); // 0 for no or 1 for yes
		parameters.put("airCon", contract.getAirCond()); // 0 for no or 1 for yes

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

	public List<Contracts> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contracts> contracts) {
		this.contracts = contracts;
	}

	public List<Contracts> getFiltredContracts() {
		return filtredContracts;
	}

	public void setFiltredContracts(List<Contracts> filtredContracts) {
		this.filtredContracts = filtredContracts;
	}

	public CustomerService getCustomerServiceImpl() {
		return customerServiceImpl;
	}

	public void setCustomerServiceImpl(CustomerService customerServiceImpl) {
		this.customerServiceImpl = customerServiceImpl;
	}
}
