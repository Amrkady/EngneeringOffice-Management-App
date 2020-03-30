package com.services;

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
	public boolean addTransaction(Transaction transaction) {
		return commonDao.saveObject(transaction);
	}

	@Override
	public boolean updateTransaction(Transaction transaction) {
		return commonDao.updateObject(transaction);
	}

	@Override
	public Attachment findAttachmentById(Integer attachId) {
		Attachment att = (Attachment) commonDao.findEntityById(Attachment.class, attachId);
		return att;
	}

}
