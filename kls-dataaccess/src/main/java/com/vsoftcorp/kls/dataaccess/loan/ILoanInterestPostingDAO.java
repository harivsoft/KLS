package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.time.Date;

/**
 * @author sponnam
 * 
 */
public interface ILoanInterestPostingDAO {

	///public void postInterest(Date theBusinessDate);

	public List<LineOfCredit> getLineOfCreditListForInterestPosting();

	public List fetchLocs(Date theBusinessDate, String eventType,List<Integer> pacsIdsList);

	public List getOverDueLocs(Date currentDate);

}
