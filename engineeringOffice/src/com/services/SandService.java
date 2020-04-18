package com.services;

import java.util.Date;
import java.util.List;

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

	public List<Bills> getAllBillsReceive();
	

	public List<BillsPay> getBillsPayByDeptDate(Integer deptId, Date dateFrom, Date dateTo);

	public List<BillsPay> getAllBillsPay();

}
