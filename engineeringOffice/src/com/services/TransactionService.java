package com.services;

import java.util.List;

import com.entities.Attachment;
import com.entities.Transaction;

public interface TransactionService {

	Integer addAttachment(Attachment attach);

	Integer addTransaction(Transaction transaction);

	boolean updateTransaction(Transaction transaction);

	List<Attachment> findAttachmentByTransId(Integer attachId);

}
