package com.services;

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

	public List<Bills> getBillsReceiveByDept(Integer deptId);

	public List<Bills> getAllBillsReceive();
	

	public List<BillsPay> getBillsPayByDept(Integer deptId);

	public List<BillsPay> getAllBillsPay();

}
