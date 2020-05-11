package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.Constant;
import com.entities.Contracts;
import com.services.SandService;

import common.util.Utils;

@ManagedBean
@ViewScoped
public class ContractsViewBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	List<Contracts> contracts;
	private List<Contracts> filtredContracts;


	@PostConstruct
	public void init() {
		if (Utils.findCurrentUser().getRoleId() == Constant.ROLE_MANAGER) {
			contracts=sandServiceImpl.getContractsByDept(Utils.findCurrentUser().getDeptId());
		}
		else if (Utils.findCurrentUser().getRoleId() == Constant.ROLE_ADMIN) {
			contracts=sandServiceImpl.getAllContracts();
		}
	}
	
	public String transferContract(Integer contractNo)
	{
//		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
//		flash.put("contractNo", contractNo);
//		
		HttpServletRequest httprequest=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession httpSession=httprequest.getSession(false);
		httpSession.setAttribute("contractNo", contractNo);
		return "trans";

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
}
