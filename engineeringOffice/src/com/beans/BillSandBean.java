package com.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.entities.Bills;
import com.services.SandService;

import common.util.Utils;

@ManagedBean(name = "sandBean")
@ViewScoped
public class BillSandBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	private Bills billSnad;
	private Date sandDate;

	@PostConstruct
	public void init() {
		billSnad = new Bills();
	}

	public void addBillOrSnad() {
		try {
			System.out.println(sandServiceImpl.addSand(billSnad));
			//sandServiceImpl.addSand(billSnad);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String save() {
		addBillOrSnad();
//		String reportName = "/reports/protection_A3ohad.jasper";
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("artId", -1);
//		//parameters.put("userId", employerId);
//		Utils.printPdfReport(reportName, parameters);
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

}
