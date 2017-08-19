package com.vsoftcorp.kls.business.entity.account;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * This Entity indicates line of credits for borrowing account
 * 
 * @author a1605
 * 
 */

@Entity
@DiscriminatorValue("B")
// "B" for Borrowing loans
public class BorrowingsLineOfCredit extends LoanLineOfCredit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6397339139914203756L;
	
	@Override
	public String getLoanType() {
		return "B";
	}

}
