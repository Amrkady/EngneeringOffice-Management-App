package com.services;

import com.entities.Bills;
import com.entities.Contracts;

public interface SandService {

	boolean addSand(Bills bill);

	boolean addContract(Contracts contract);

	Integer getContractNo();

}
