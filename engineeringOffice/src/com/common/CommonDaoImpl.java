package com.common;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import com.entities.Attachment;
import com.entities.Bills;
import com.entities.BillsPay;
import com.entities.Contracts;
import com.entities.Customers;
import com.entities.Transaction;
import com.entities.Users;

public class CommonDaoImpl extends HibernateTemplate implements CommonDao {
	private SessionFactory sessionFactory;

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean saveObject(Object object) {
		try {
			sessionFactory.getCurrentSession().save(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	@Transactional
	public Integer saveCustomer(Customers customer) {
		try {
			Integer id = (Integer) sessionFactory.getCurrentSession().save(customer);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> findAll(Class typeClass) {
		try {
			return loadAll(typeClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object findEntityById(Class entityClass, Integer EntityId) {
		return get(entityClass, EntityId);
	}

	@Override
	@Transactional
	public boolean deleteObject(Object object) {
		try {
			sessionFactory.getCurrentSession().delete(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	@Transactional
	public boolean updateObject(Object myObject) {
		try {
			sessionFactory.getCurrentSession().update(myObject);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Users loadUser(final String username, final String password) throws AuthenticationException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Users.class);
		criteria.add(Restrictions.eq("loginName", username));
		if (password != null) {

			criteria.add(Restrictions.eq("password", password));

		}
		Users result = (Users) criteria.uniqueResult();
		if (result == null) {
			throw new BadCredentialsException("bad credentials");
		}
		Hibernate.initialize(result.getRole());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer findContractNo() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Contracts.class);
		criteria.setProjection(Projections.count("id"));
		Long id = (Long) criteria.uniqueResult();
		return id.intValue();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Transaction> findMailsIN(Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Transaction.class);
		criteria.add(Restrictions.eq("trTo", userId));
		criteria.addOrder(Order.desc("date"));
		return criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Transaction> findMailsOut(Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Transaction.class);
		criteria.add(Restrictions.eq("trFrom", userId));
		criteria.addOrder(Order.desc("date"));
		return criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Contracts> findContractsByDept(Integer depId) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Contracts.class);
		criteria.add(Restrictions.eq("deptId", depId));
		List<Contracts> contracts = criteria.list();
		return contracts;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Bills> findBillsReceiveByDeptDate(Integer depId, Date dateFrom, Date dateTo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bills.class);
		criteria.add(Restrictions.eq("deptId", depId));
		criteria.add(Restrictions.ge("date", dateFrom));
		criteria.add(Restrictions.le("date", dateTo));
		List<Bills> billsReceive = criteria.list();
		return billsReceive;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Contracts loadContractByContNo(Integer contractNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Contracts.class);
		criteria.add(Restrictions.eq("conNo", contractNo));
		Contracts contract = (Contracts) criteria.uniqueResult();
		return contract;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Users> findUsersByDept(Integer depId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Users.class);
		criteria.add(Restrictions.eq("deptId", depId));
		criteria.add(Restrictions.eq("manager", 0));
		List<Users> users = criteria.list();
		return users;
	}

	@Override
	@Transactional
	public Integer saveAttachment(Attachment attach) {
		try {
			Integer id = (Integer) sessionFactory.getCurrentSession().save(attach);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	@Transactional
	public Integer saveTransaction(Transaction transaction) {
		try {
			Integer id = (Integer) sessionFactory.getCurrentSession().save(transaction);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer findSandNo() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bills.class);
		criteria.setProjection(Projections.count("id"));
		Long id = (Long) criteria.uniqueResult();
		return id.intValue();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<BillsPay> findBillsPayByDeptDate(Integer depId, Date dateFrom, Date dateTo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillsPay.class);
		criteria.add(Restrictions.eq("deptId", depId));
		criteria.add(Restrictions.ge("date", dateFrom));
		criteria.add(Restrictions.le("date", dateTo));
		List<BillsPay> billsPay = criteria.list();
		return billsPay;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Attachment> findAttachmentsByTransId(Integer transId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Attachment.class);
		criteria.add(Restrictions.eq("transId", transId));
		List<Attachment> attachments = criteria.list();
		return attachments;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<BillsPay> findBillsPayByDept(Integer depId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillsPay.class);
		criteria.add(Restrictions.eq("deptId", depId));
		List<BillsPay> billsPay = criteria.list();
		return billsPay;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer findBillsSandNo() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillsPay.class);
		criteria.setProjection(Projections.count("id"));
		Long id = (Long) criteria.uniqueResult();
		return id.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Bills> findBillsByDate(Date dateFrom, Date dateTo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bills.class);
		criteria.add(Restrictions.ge("date", dateFrom));
		criteria.add(Restrictions.le("date", dateTo));
		List<Bills> bills = criteria.list();
		return bills;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional

	public List<BillsPay> findBillsPayByDate(Date dateFrom, Date dateTo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillsPay.class);
		criteria.add(Restrictions.ge("date", dateFrom));
		criteria.add(Restrictions.le("date", dateTo));
		List<BillsPay> billsPay = criteria.list();
		return billsPay;

	}

}
