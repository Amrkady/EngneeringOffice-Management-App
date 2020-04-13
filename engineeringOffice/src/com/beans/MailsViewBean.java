package com.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.entities.Attachment;
import com.entities.Transaction;
import com.services.TransactionService;
import com.services.UserService;

import common.util.FtpTransferFile;

@ManagedBean(name = "mailsViewBean")
@ViewScoped
public class MailsViewBean {
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;

	@ManagedProperty(value = "#{transactionServiceImpl}")
	private TransactionService transactionServiceImpl;

	private Transaction selectedMail = new Transaction();
	Attachment attach = new Attachment();
	List<Attachment> attachs = new ArrayList<Attachment>();
	private StreamedContent file;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		selectedMail = (Transaction) session.getAttribute("selectedMail");
		if (selectedMail != null) {
			attachs = transactionServiceImpl.findAttachmentByTransId(selectedMail.getTransId());
		}
	}

	public StreamedContent getFile(Attachment attach) {
		InputStream stream = null;
		StreamedContent file = null;
		try {
			stream = FtpTransferFile.getFileStream(attach.getAttName());
			file = new DefaultStreamedContent(stream, "application/pdf", attach.getRealName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public TransactionService getTransactionServiceImpl() {
		return transactionServiceImpl;
	}

	public void setTransactionServiceImpl(TransactionService transactionServiceImpl) {
		this.transactionServiceImpl = transactionServiceImpl;
	}

	public Transaction getSelectedMail() {
		return selectedMail;
	}

	public void setSelectedMail(Transaction selectedMail) {
		this.selectedMail = selectedMail;
	}

	public Attachment getAttach() {
		return attach;
	}

	public void setAttach(Attachment attach) {
		this.attach = attach;
	}

	public StreamedContent getFile() {
		// file = findFile();
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public List<Attachment> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<Attachment> attachs) {
		this.attachs = attachs;
	}

}
