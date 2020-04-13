package com.services;

import java.util.List;

import com.common.CommonDao;
import com.entities.Attachment;
import com.entities.Transaction;

public class TransactionServiceImpl implements TransactionService {
	CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public Integer addAttachment(Attachment attach) {
		return commonDao.saveAttachment(attach);
	}

	@Override
	public Integer addTransaction(Transaction transaction) {
		return commonDao.saveTransaction(transaction);
	}

	@Override
	public boolean updateTransaction(Transaction transaction) {
		return commonDao.updateObject(transaction);
	}

	@Override
	public List<Attachment> findAttachmentByTransId(Integer transId) {
		List<Attachment> att = commonDao.findAttachmentsByTransId(transId);
		return att;
	}

}
