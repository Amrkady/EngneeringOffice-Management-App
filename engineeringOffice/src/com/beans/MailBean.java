package com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;

import com.entities.Transaction;
import com.entities.Users;
import com.services.MailsService;
import com.services.UserService;

@ManagedBean(name = "mailBean")
@ViewScoped
public class MailBean {
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;

	@ManagedProperty(value = "#{mailsServiceImpl}")
	private MailsService mailsServiceImpl;

	private List<Transaction> mailsIn;
	private List<Transaction> mailsOut;
	private Transaction selectedMail = new Transaction();

	@PostConstruct
	public void init() {
		mailsIn = new ArrayList<Transaction>();
		mailsOut = new ArrayList<Transaction>();
		FacesContext context = FacesContext.getCurrentInstance();
		Users uss = (Users) context.getExternalContext().getSessionMap().get("user");
		//uss.getUserId();
		//if (uss != null) {
			// get in mails
			mailsIn = mailsServiceImpl.getAllMailsIn(1);
			// get out mails
			mailsOut = mailsServiceImpl.getAllMailsOut(1);
		//}
	}

	public String getUserById(Integer usrId) {
		if (usrId != null) {
			Users us = userServiceImpl.findUserById(usrId);
			return us.getName();
		}
		return "";
	}

	public void viewInboxRow(SelectEvent event) {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
//		session.setAttribute("arcRecord", selectedInbox.AppId);
//		session.setAttribute("type", selectedInbox.getWrkType());
//		session.setAttribute("selectedMail", selectedInbox);
//		String page = mainManagedBean.editMail(selectedInbox);
//		dataAccessService.MakeItRead(String.valueOf(selectedInbox.StepId), selectedInbox.WrkId);
//		flash.put("selectedMail", selectedInbox);
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void viewOutBoxRow(SelectEvent event) {
//		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedOutBoxMail", selectedInbox);
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect("models/outBox_definition.xhtml");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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

}
