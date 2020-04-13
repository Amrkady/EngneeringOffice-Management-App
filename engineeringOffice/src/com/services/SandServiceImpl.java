package com.services;

import java.util.List;

import com.common.CommonDao;
import com.entities.Bills;
import com.entities.BillsPay;
import com.entities.Contracts;

public class SandServiceImpl implements SandService {
	private CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public boolean addSand(Bills bill) {
		return commonDao.saveObject(bill);

	}

	@Override
	public boolean addContract(Contracts contract) {
		return commonDao.saveObject(contract);
	}
	
	@Override
	public Integer getContractNo() {
		return commonDao.findContractNo();
	}
	
	@Override
	public List<Contracts> getContractsByDept(Integer deptId){
		 
		return commonDao.findContractsByDept(deptId); 
	}
	@Override
	public List<Bills> getBillsReceiveByDept(Integer deptId) {

		return commonDao.findBillsReceiveByDept(deptId);
	}

	@Override
	public List<Bills> getAllBillsReceive() {

		List billsRecieve = commonDao.findAll(Bills.class);
		return billsRecieve;
	}

	@Override
	public Contracts loadContractByContNo(Integer contractNo)
	{
		return commonDao.loadContractByContNo(contractNo);
	}
	@Override
	public boolean updateContract(Contracts contract) {
		return commonDao.updateObject(contract);
	}

	@Override
	public Integer getSandNo() {
		return commonDao.findSandNo();
	}

	@Override
	public List<BillsPay> getBillsPayByDept(Integer deptId) {

		return commonDao.findBillsPayByDept(deptId);
	}

	@Override
	public List<BillsPay> getAllBillsPay() {

		List billsPay = commonDao.findAll(BillsPay.class);
		return billsPay;
	}

}
