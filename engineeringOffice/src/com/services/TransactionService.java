package com.services;

import com.entities.Attachment;
import com.entities.Transaction;

public interface TransactionService {
	
	Integer addAttachment(Attachment attach);
	
	boolean addTransaction(Transaction transaction);

	boolean updateTransaction(Transaction transaction);

	Attachment findAttachmentById(Integer attachId);

}
