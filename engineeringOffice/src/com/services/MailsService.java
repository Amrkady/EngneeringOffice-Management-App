package com.services;

import java.util.List;

import com.entities.Transaction;

public interface MailsService {

	List<Transaction> getAllMailsIn(Integer userId);
	
	List<Transaction> getAllMailsOut(Integer userId);
}
