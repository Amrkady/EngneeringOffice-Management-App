package com.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bill_pay")
public class BillsPay {

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
	private BigDecimal amountPay;

	@Column(name = "amount_rest")
	private BigDecimal amountRest;

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

	@Column(name = "tax")
	private Integer tax;

	@Column(name = "sysdate")
	private Date date;

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

	public BigDecimal getAmountPay() {
		return amountPay;
	}

	public void setAmountPay(BigDecimal amountPay) {
		this.amountPay = amountPay;
	}

	public BigDecimal getAmountRest() {
		return amountRest;
	}

	public void setAmountRest(BigDecimal amountRest) {
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

	public Integer getTax() {
		return tax;
	}

	public void setTax(Integer tax) {
		this.tax = tax;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
