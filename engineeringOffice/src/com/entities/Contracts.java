package com.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "contract")
public class Contracts {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "day_letter")
	private String dayLetter;

	@Column(name = "date")
	private String contractDate;

	@Column(name = "owner_no")
	private String ownerNo;

	// ����� ����� �����
	@Column(name = "out_hijri_date")
	private String outHijridate;
	// ���� �� ��� �
	@Column(name = "out_from")
	private String outFrom;
	// ��� �������
	@Column(name = "license_type")
	private String licenseType;

	@Column(name = "amount_letter")
	private String amountByLetter;
	// ��� ����� ������ ������
	@Column(name = "nat_no")
	private Integer nat_no;
	// ������
	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "dept_id")
	private Integer deptId;

	@Column(name = "CONTRACT_NO")
	private Integer conNo;

	@Column(name = "sent")
	private Integer sent;

	@Column(name = "lic_out")
	private Integer licOut = new Integer(0);

	@Column(name = "plan")
	private Integer plan = new Integer(0);

	@Column(name = "torba")
	private Integer trba = new Integer(0);

	@Column(name = "air_cond")
	private Integer airCond = new Integer(0);

	@Column(name = "light")
	private Integer light = new Integer(0);

	@Column(name = "arch")
	private Integer arch = new Integer(0);
	@Column(name = "sbaka")
	private Integer sbaka = new Integer(0);

	@Column(name = "electric")
	private Integer electric = new Integer(0);

	@Column(name = "view")
	private Integer view = new Integer(0);

	@Column(name = "park")
	private Integer park = new Integer(0);

	@Column(name = "build")
	private Integer build = new Integer(0);

	@Column(name = "light_plan")
	private Integer lightPlan = new Integer(0);

	@Formula("(select c.customer_name from customers c where c.id = customer_id)")
	private String customerName;

	@Transient
	private boolean licence;

	@Transient
	private boolean torba;

	@Transient
	private boolean airCon;

	@Formula("(select d.dept_name from departments d where d.id = dept_id)")
	private String deptName;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDayLetter() {
		return dayLetter;
	}

	public void setDayLetter(String dayLetter) {
		this.dayLetter = dayLetter;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public String getOutHijridate() {
		return outHijridate;
	}

	public void setOutHijridate(String outHijridate) {
		this.outHijridate = outHijridate;
	}

	public String getOutFrom() {
		return outFrom;
	}

	public void setOutFrom(String outFrom) {
		this.outFrom = outFrom;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getAmountByLetter() {
		return amountByLetter;
	}

	public void setAmountByLetter(String amountByLetter) {
		this.amountByLetter = amountByLetter;
	}

	public Integer getNat_no() {
		return nat_no;
	}

	public void setNat_no(Integer nat_no) {
		this.nat_no = nat_no;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public Integer getConNo() {
		return this.conNo;
	}

	public void setConNo(Integer conNo) {
		this.conNo = conNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getSent() {
		return sent;
	}

	public void setSent(Integer sent) {
		this.sent = sent;
	}

	public boolean isLicence() {
		return licence;
	}

	public void setLicence(boolean licence) {
		this.licence = licence;
	}

	public boolean isTorba() {
		return torba;
	}

	public void setTorba(boolean torba) {
		this.torba = torba;
	}

	public boolean isAirCon() {
		return airCon;
	}

	public void setAirCon(boolean airCon) {
		this.airCon = airCon;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getLicOut() {
		return licOut;
	}

	public void setLicOut(Integer licOut) {
		this.licOut = licOut;
	}

	public Integer getPlan() {
		return plan;
	}

	public void setPlan(Integer plan) {
		this.plan = plan;
	}

	public Integer getTrba() {
		return trba;
	}

	public void setTrba(Integer trba) {
		this.trba = trba;
	}

	public Integer getAirCond() {
		return airCond;
	}

	public void setAirCond(Integer airCond) {
		this.airCond = airCond;
	}

	public Integer getLight() {
		return light;
	}

	public void setLight(Integer light) {
		this.light = light;
	}

	public Integer getArch() {
		return arch;
	}

	public void setArch(Integer arch) {
		this.arch = arch;
	}

	public Integer getSbaka() {
		return sbaka;
	}

	public void setSbaka(Integer sbaka) {
		this.sbaka = sbaka;
	}

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public Integer getPark() {
		return park;
	}

	public void setPark(Integer park) {
		this.park = park;
	}

	public Integer getBuild() {
		return build;
	}

	public void setBuild(Integer build) {
		this.build = build;
	}

	public Integer getLightPlan() {
		return lightPlan;
	}

	public void setLightPlan(Integer lightPlan) {
		this.lightPlan = lightPlan;
	}

	public Integer getElectric() {
		return electric;
	}

	public void setElectric(Integer electric) {
		this.electric = electric;
	}

}
