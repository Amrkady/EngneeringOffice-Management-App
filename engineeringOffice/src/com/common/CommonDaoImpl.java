package com.common;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

public class CommonDaoImpl extends HibernateTemplate implements CommonDao {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

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

}
