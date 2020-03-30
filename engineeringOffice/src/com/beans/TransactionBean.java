package com.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
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

import common.util.HijriCalendarUtil;
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
		
		
		
//		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
//		Integer contractNo= Integer.parseInt(flash.get("contractNo").toString());
		
		HttpServletRequest httprequest=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession httpSession=httprequest.getSession(false);
		Integer contractNo=Integer.parseInt(httpSession.getAttribute("contractNo").toString());
		
		contract=sandServiceImpl.loadContractByContNo(contractNo);
		users=userServiceImpl.findUsersByDept(Utils.findCurrentUser().getDeptId());
	}
	
	public void NewRecordupload(FileUploadEvent event) {

		try {
//			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attach.setRealName(Utils.generateRandomName()+"."+attach.getAttachExt());
			
			

		} catch (Exception e) {

		}
	}
	public void save() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		
		try {
			trans.sethDate(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(dateFormat.format(cal.getTime()).toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		attachment = Utils.SaveAttachementsToFtpServer(attach);
		Integer attachId=transactionServiceImpl.addAttachment(attachment);
		trans.setAttachId(attachId);
		trans.setContractId(contract.getId());
		trans.setTrFrom(Utils.findCurrentUser().getUserId());
		trans.setMarkRead(0);
		
		transactionServiceImpl.addTransaction(trans);
		contract.setSent(1);
		sandServiceImpl.updateContract(contract);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "تم الارسال بنجاح", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

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
