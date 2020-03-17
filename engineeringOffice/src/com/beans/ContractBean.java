package com.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.entities.Bills;
import com.entities.Contracts;
import com.services.SandService;

import common.util.Utils;

@ManagedBean(name = "contractBean")
@ViewScoped
public class ContractBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	private Contracts contract;
	private Date contractDate;

	@PostConstruct
	public void init() {
		contract = new Contracts();
	}

	public void addContract() {
		try {
			String strDate = "";
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/mm/yyyy");
			if (contractDate != null) {
				strDate = sdfDate.format(contractDate);
			}
			contract.setContractDate(strDate);
			sandServiceImpl.addContract(contract);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String save() {
//		addContract();
//		String strDate = "";
//		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/mm/yyyy");
//		if (contractDate != null) {
//			strDate = sdfDate.format(contractDate);
//		}
		// billSnad.setBillDate(strDate);
		String reportName = "/reports/contractReport.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("sndNo", billSnad.getSanadNo());
//		parameters.put("custName", billSnad.getCustomerName());
//		parameters.put("reyal", billSnad.getAmountPay());
//		parameters.put("costRest", billSnad.getAmountRest());
//		parameters.put("for", billSnad.getBillReason());
//		parameters.put("payType", billSnad.getBillType());
//		parameters.put("dept", billSnad.getDeptName());
//		parameters.put("date", billSnad.getBillDate());
//		parameters.put("costByLet", "„«∆…");
//		parameters.put("halaa", 00);
//		//parameters.put("userId", employerId);
	
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

}
