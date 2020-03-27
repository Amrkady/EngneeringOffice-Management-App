package com.services;

import java.util.List;

import com.common.CommonDao;
import com.entities.Transaction;

public class MailsServiceImpl implements MailsService {
	CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public List<Transaction> getAllMailsIn(Integer userId) {
		return commonDao.findMailsIN(userId);
	}

	@Override
	public List<Transaction> getAllMailsOut(Integer userId) {
		return commonDao.findMailsOut(userId);
	}

}
