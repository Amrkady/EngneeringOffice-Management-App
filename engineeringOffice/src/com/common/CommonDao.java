
package com.common;

import java.util.Date;
import java.util.List;

import com.entities.Attachment;
import com.entities.BankDeposit;
import com.entities.Bills;
import com.entities.BillsPay;
import com.entities.Contracts;
import com.entities.Customers;
import com.entities.Transaction;
import com.entities.Users;

public interface CommonDao {

	public boolean saveObject(Object object);

	List<Object> findAll(Class object);

	Object findEntityById(Class entityClass, Integer EntityId);

	boolean deleteObject(Object object);

	boolean updateObject(Object myObject);

	public Integer saveCustomer(Customers customer);
	public Integer saveAttachment(Attachment attachment);

	public Users loadUser(final String username, final String password);

	Integer findContractNo();

	List<Transaction> findMailsIN(Integer userId);

	List<Transaction> findMailsOut(Integer userId);
public List<Contracts> findContractsByDept(Integer depId);
	public Contracts loadContractByContNo(Integer contractNo);
	List<Users> findUsersByDept(Integer depId);

	public Integer saveTransaction(Transaction transaction);

	Integer findSandNo();

	public List<Bills> findBillsReceiveByDeptDate(Integer depId, Date dateFrom, Date dateTo);

	public List<Bills> findBillsByDate(Date dateFrom, Date dateTo);

	public List<BillsPay> findBillsPayByDeptDate(Integer depId, Date dateFrom, Date dateTo);

	List<Attachment> findAttachmentsByTransId(Integer transId);

	List<BillsPay> findBillsPayByDept(Integer depId);

	Integer findBillsSandNo();

	public List<BillsPay> findBillsPayByDate(Date dateFrom, Date dateTo);

	List<BankDeposit> findBankDepositByYear(Date dateFrom, Date dateTo);

	public List<Customers> getCustomersByDept(Integer deptId);


}
