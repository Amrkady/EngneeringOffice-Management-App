package com.services;

import java.util.Date;
import java.util.List;

import com.entities.BankDeposit;
import com.entities.Bills;
import com.entities.BillsPay;
import com.entities.Contracts;

public interface SandService {

	boolean addSand(Bills bill);

	boolean addContract(Contracts contract);

	Integer getContractNo();

	public List<Contracts> getContractsByDept(Integer deptId);

//	public Contracts getContractById(Integer id);
	public Contracts loadContractByContNo(Integer contractNo);

	public boolean updateContract(Contracts contract);

	public Integer getSandNo();

	public List<Bills> getBillsReceiveByDeptDate(Integer deptId, Date dateFrom, Date dateTo);

	public List<Bills> getBillsByDate(Date dateFrom, Date dateTo);

	public List<Bills> getAllBillsReceive();

	public List<BillsPay> getBillsPayByDeptDate(Integer deptId, Date dateFrom, Date dateTo);

	public List<BillsPay> getAllBillsPay();

	public List<BillsPay> getBillsPayByDept(Integer deptId);

	boolean addBillsPay(BillsPay bill);

	boolean updateBillsPay(BillsPay bill);

	Integer findBillsSandNo();

	public List<BillsPay> getBillsPayByDate(Date dateFrom, Date dateTo);

	public boolean addBankDeposit(BankDeposit bnkDeposit);

}
