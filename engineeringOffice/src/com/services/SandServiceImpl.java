package com.services;

import java.util.Date;
import java.util.List;

import com.common.CommonDao;
import com.entities.BankDeposit;
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
	public List<Contracts> getContractsByDept(Integer deptId) {
		return commonDao.findContractsByDept(deptId);
	}

	@Override
	public List<Bills> getBillsReceiveByDeptDate(Integer deptId, Date dateFrom, Date dateTo) {
		return commonDao.findBillsReceiveByDeptDate(deptId, dateFrom, dateTo);
	}

	@Override
	public List<Bills> getAllBillsReceive() {
		List billsRecieve = commonDao.findAll(Bills.class);
		return billsRecieve;
	}

	@Override
	public Contracts loadContractByContNo(Integer contractNo) {
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
	public List<BillsPay> getBillsPayByDeptDate(Integer deptId, Date dateFrom, Date dateTo) {
		return commonDao.findBillsPayByDeptDate(deptId, dateFrom, dateTo);
	}

	@Override
	public List<BillsPay> getAllBillsPay() {
		List billsPay = commonDao.findAll(BillsPay.class);
		return billsPay;
	}

	@Override
	public List<BillsPay> getBillsPayByDept(Integer deptId) {
		return commonDao.findBillsPayByDept(deptId);
	}

	@Override
	public boolean addBillsPay(BillsPay bill) {
		return commonDao.saveObject(bill);
	}

	@Override
	public boolean updateBillsPay(BillsPay bill) {
		return commonDao.updateObject(bill);
	}

	@Override
	public Integer findBillsSandNo() {
		return commonDao.findBillsSandNo();
	}

	@Override
	public List<Bills> getBillsByDate(Date dateFrom, Date dateTo) {
		return commonDao.findBillsByDate(dateFrom, dateTo);

	}

	@Override
	public List<BillsPay> getBillsPayByDate(Date dateFrom, Date dateTo) {
		return commonDao.findBillsPayByDate(dateFrom, dateTo);

	}

	@Override
	public boolean addBankDeposit(BankDeposit bnkDeposit) {
		return commonDao.saveObject(bnkDeposit);
	}


	@Override
	public List<BankDeposit> findBankDepositByYear(Date dateFrom, Date dateTo) {
		return commonDao.findBankDepositByYear(dateFrom, dateTo);
	}

	@Override
	public List<Contracts> getAllContracts() {
		List contracts = commonDao.findAll(Contracts.class);
		return contracts;
	}

	@Override
	public boolean updateBillsReceive(Bills billReceive) {
		return commonDao.updateObject(billReceive);
	}
}
