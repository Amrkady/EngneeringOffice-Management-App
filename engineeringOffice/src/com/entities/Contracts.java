package com.entities;

import javax.persistence.*;

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

	//  «—ÌŒ «’œ«— «·⁄ﬁœ
	@Column(name = "out_hijri_date")
	private String outHijridate;
	// ’«œ— „‰ ÃÂ… ø
	@Column(name = "out_from")
	private String outFrom;
   // ‰Ê⁄ «· —ŒÌ’
	@Column(name = "license_type")
	private String licenseType;

	@Column(name = "amount_letter")
	private String amountByLetter;
	// —ﬁ„ «·”Ã· «·„œ‰Ì ··⁄„Ì·
	@Column(name = "nat_no")
	private Integer nat_no;
	// «·„»·€
	@Column(name = "amount")
	private Integer amount;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "dept_id")
	private Integer deptId;
	
	@Column(name = "CONTRACT_NO")
	private Integer ConNo;

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
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
		return ConNo;
	}

	public void setConNo(Integer conNo) {
		ConNo = conNo;
	}

}
