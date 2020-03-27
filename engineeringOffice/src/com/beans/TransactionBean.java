package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.model.UploadedFile;

import com.entities.Contracts;
import com.entities.Users;
import com.services.SandService;

@ManagedBean
@ViewScoped
public class TransactionBean {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	private Contracts contract;
	private UploadedFile file;
	private List<Users> users;
	
	@PostConstruct
	public void init() {
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		Integer contractNo= Integer.parseInt(flash.get("contractNo").toString());
		contract=sandServiceImpl.loadContractByContNo(contractNo);
		
	}

	public SandService getSandServiceImpl() {
		return sandServiceImpl;
	}

	public void setSandServiceImpl(SandService sandServiceImpl) {
		this.sandServiceImpl = sandServiceImpl;
	}

	public Contracts getContract() {
		return contract;
	}

	public void setContract(Contracts contract) {
		this.contract = contract;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
