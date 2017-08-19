package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.BorrowingsLineOfCreditData;

public class BorrowingsLineOfCreditHelper {
	
	public static BorrowingsLineOfCreditData getBorrowingsLineOfCredit(BorrowingsLineOfCredit data) {
		BorrowingsLineOfCreditData master = new BorrowingsLineOfCreditData();
		master.setId(data.getId());
		master.setCurrentBalance(data.getCurrentBalance().getMoney().getAmount());
		master.setInterestReceivable(data.getInterestReceivable());
		master.setPenalInterestReceivable(data.getPenalInterestReceivable());
		master.setOverDueDate(DateUtil.getDateString(DateUtil.getNextDateByFreequncyPeroid(data.getSanctionedDate(),12)));
		master.setLoanProductId(data.getLoanAccountProperty().getProduct().getId());
		master.setRecoverySequence(data.getLoanAccountProperty().getProduct().getRecoverySequence().getId());
		
		return master;
	}
	
	
	public static List<BorrowingsLineOfCreditData> getBorrowingsLineOfCreditList(List<BorrowingsLineOfCredit> data) {
		List<BorrowingsLineOfCreditData> master = new ArrayList<BorrowingsLineOfCreditData>();
		for (BorrowingsLineOfCredit borrowingsLineOfCredit : data) {
			master.add(getBorrowingsLineOfCredit(borrowingsLineOfCredit));
		}
		return master;
	}

}
