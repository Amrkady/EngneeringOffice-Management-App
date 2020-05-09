package com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;

import com.entities.Transaction;
import com.entities.Users;
import com.services.MailsService;
import com.services.TransactionService;
import com.services.UserService;

@ManagedBean(name = "mailBean")
@ViewScoped
public class MailBean {
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;

	@ManagedProperty(value = "#{mailsServiceImpl}")
	private MailsService mailsServiceImpl;

	@ManagedProperty(value = "#{transactionServiceImpl}")
	private TransactionService transactionServiceImpl;

	private List<Transaction> mailsIn;
	private List<Transaction> mailsOut;
	private Transaction selectedMail;

	@PostConstruct
	public void init() {
		selectedMail = new Transaction();
		mailsIn = new ArrayList<Transaction>();
		mailsOut = new ArrayList<Transaction>();
		FacesContext context = FacesContext.getCurrentInstance();
		Users uss = (Users) context.getExternalContext().getSessionMap().get("user");
		// uss.getUserId();
		if (uss != null) {
			// get in mails
			mailsIn = mailsServiceImpl.getAllMailsIn(uss.getUserId());
			// get out mails
			mailsOut = mailsServiceImpl.getAllMailsOut(uss.getUserId());
		}
	}

	public String getUserById(Integer usrId) {
		if (usrId != null) {
			Users us = userServiceImpl.findUserById(usrId);
			return us.getName();
		}
		return "";
	}

	public String viewRowSelected(Transaction mail) {
		selectedMail = mail;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("selectedMail", selectedMail);
		return "mailView";
	}

	public String viewRowSelectedIn(Transaction mail) {
		selectedMail = mail;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("selectedMail", selectedMail);
		selectedMail.setMarkRead(1);
		transactionServiceImpl.updateTransaction(selectedMail);
		return "mailView";
	}

	public void onRowUnselect(SelectEvent event) {
	}

	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public List<Transaction> getMailsIn() {
		return mailsIn;
	}

	public void setMailsIn(List<Transaction> mailsIn) {
		this.mailsIn = mailsIn;
	}

	public List<Transaction> getMailsOut() {
		return mailsOut;
	}

	public void setMailsOut(List<Transaction> mailsOut) {
		this.mailsOut = mailsOut;
	}

	public Transaction getSelectedMail() {
		return selectedMail;
	}

	public void setSelectedMail(Transaction selectedMail) {
		this.selectedMail = selectedMail;
	}

	public MailsService getMailsServiceImpl() {
		return mailsServiceImpl;
	}

	public void setMailsServiceImpl(MailsService mailsServiceImpl) {
		this.mailsServiceImpl = mailsServiceImpl;
	}

	public TransactionService getTransactionServiceImpl() {
		return transactionServiceImpl;
	}

	public void setTransactionServiceImpl(TransactionService transactionServiceImpl) {
		this.transactionServiceImpl = transactionServiceImpl;
	}

}
