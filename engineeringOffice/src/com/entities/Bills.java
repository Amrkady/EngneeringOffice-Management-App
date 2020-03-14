package com.entities;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bills")
public class Bills {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "sanad_no")
	private Integer sanadNo;

	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@Column(name = "amount_letters", nullable = false)
	private String amountLetters;

	@Column(name = "amount")
	private Integer amountPay;

	@Column(name = "amount_rest")
	private Integer amountRest;

	@Column(name = "bill_reason")
	private String billReason;
	
	@Column(name = "bill_type")
	private String billType;
	
	
	@Column(name = "dept_name")
	private String deptName;

	@Column(name = "date")
	private String billDate;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "dept_id")
	private Integer deptId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSanadNo() {
		return sanadNo;
	}

	public void setSanadNo(Integer sanadNo) {
		this.sanadNo = sanadNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAmountLetters() {
		return amountLetters;
	}

	public void setAmountLetters(String amountLetters) {
		this.amountLetters = amountLetters;
	}

	public Integer getAmountPay() {
		return amountPay;
	}

	public void setAmountPay(Integer amountPay) {
		this.amountPay = amountPay;
	}

	public Integer getAmountRest() {
		return amountRest;
	}

	public void setAmountRest(Integer amountRest) {
		this.amountRest = amountRest;
	}

	public String getBillReason() {
		return billReason;
	}

	public void setBillReason(String billReason) {
		this.billReason = billReason;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	
}
