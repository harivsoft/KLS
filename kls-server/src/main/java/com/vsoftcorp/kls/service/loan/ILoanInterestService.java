package com.vsoftcorp.kls.service.loan;

import java.util.List;

import com.vsoftcorp.time.Date;

public interface ILoanInterestService {

	public void calculateInterest(Date theBusinessDate,List<Integer> pacsList);

	public void postInterest(Date theBusinessDate, String eventType,List<Integer> pacsIdsList);

}
