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
	
	@Column(name="date")
	private Date date;
	
	@Column(name="contract_id")
	private Integer contractId;
	
	@Column(name="attach_id")
	private Integer attachId;
	
	@Column(name="tr_from")
	private Integer trFrom;
	
	@Column(name="tr_to")
	private Integer trTo;

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

	public Integer getAttachId() {
		return attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
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
}
