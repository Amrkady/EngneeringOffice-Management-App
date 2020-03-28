package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.entities.Attachment;
import com.entities.Contracts;
import com.entities.Transaction;
import com.entities.Users;
import com.models.AttachmentModel;
import com.services.SandService;
import com.services.Scanner;
import com.services.TransactionService;
import com.services.UserService;

import common.util.Utils;

@ManagedBean
@ViewScoped
public class TransactionBean extends Scanner {
	@ManagedProperty(value = "#{sandServiceImpl}")
	private SandService sandServiceImpl;
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;
	@ManagedProperty(value = "#{transactionServiceImpl}")
	private TransactionService transactionServiceImpl;

	private Contracts contract=new Contracts();
	
	private List<Users> users;
	private AttachmentModel attach=new AttachmentModel();
	Attachment attachment=new Attachment();
	
	private Transaction trans=new Transaction();
	
	@PostConstruct
	public void init() {
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		Integer contractNo= Integer.parseInt(flash.get("contractNo").toString());
		contract=sandServiceImpl.loadContractByContNo(contractNo);
		users=userServiceImpl.findUsersByDept(Utils.findCurrentUser().getDeptId());
	}
	
	public void NewRecordupload(FileUploadEvent event) {

		try {
//			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachStream(event.getFile().getInputstream());
	
			attach.setRealName(Utils.generateRandomName()+"."+attach.getAttachExt());
			

		} catch (Exception e) {

		}
	}
	public void save() {
		
		attachment = Utils.SaveAttachementsToFtpServer(attach);
		Integer attachId=transactionServiceImpl.addAttachment(attachment);
		trans.setAttachId(attachId);
		trans.setContractId(contract.getId());
		trans.setTrFrom(Utils.findCurrentUser().getUserId());
		trans.setMarkRead(0);
		
		transactionServiceImpl.addTransaction(trans);
		

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


	

	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public AttachmentModel getAttach() {
		return attach;
	}
	public void setAttach(AttachmentModel attach) {
		this.attach = attach;
	}
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	public TransactionService getTransactionServiceImpl() {
		return transactionServiceImpl;
	}
	public void setTransactionServiceImpl(TransactionService transactionServiceImpl) {
		this.transactionServiceImpl = transactionServiceImpl;
	}
	public Transaction getTrans() {
		return trans;
	}
	public void setTrans(Transaction trans) {
		this.trans = trans;
	}
	

}
