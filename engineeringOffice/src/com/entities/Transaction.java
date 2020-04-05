package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="transactions")
public class Transaction {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name="trans_id")
	private Integer transId;
	
	@Column(name="mdate")
	private Date date;
	
	@Column(name="contract_id")
	private Integer contractId;
	
	
	
	@Column(name="tr_from")
	private Integer trFrom;
	
	@Column(name="tr_to")
	private Integer trTo;

	@Column(name="mark_read")
	private Integer markRead;
	
	@Column(name="Hdate")
	private String hDate;
	
	@Column(name="subject")
	private String subject;
	
	
	public Integer getTransId() {
		return transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getTrFrom() {
		return trFrom;
	}

	public void setTrFrom(Integer trFrom) {
		this.trFrom = trFrom;
	}

	public Integer getTrTo() {
		return trTo;
	}

	public void setTrTo(Integer trTo) {
		this.trTo = trTo;
	}

	public Integer getMarkRead() {
		return markRead;
	}

	public void setMarkRead(Integer markRead) {
		this.markRead = markRead;
	}

	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String gethDate() {
		return hDate;
	}

	public void sethDate(String hDate) {
		this.hDate = hDate;
	}
}
