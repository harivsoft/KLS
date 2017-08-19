package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.loan.ChargesDebit;
import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
import com.vsoftcorp.kls.business.entity.loan.ChargesRecovery;
import com.vsoftcorp.time.Date;

public interface IChargesDebitDAO {

	public void saveChargesDebit(ChargesDebit master);

	public ChargesDebit getChargesDebit(ChargesDebit master, boolean isCloseSession);

	public List<ChargesDebit> getAllChargesDebit(Long lineOfCreditID);

	public void deleteChargesDebit(Long chargesDebitID, Long lineOfCreditID);

	public ChargesMaster getChargesDebitByVoucher(String voucherNumber);
	
	public ChargesMaster getChargesDebitByVoucher(String voucherNumber, Date bussinessDate);
	
	public void saveChargesRecovery(ChargesRecovery chargesRecovery);
	
	public List<ChargesRecovery> getChargesMasterListByVoucher(String voucherNumber, Date bussinessDate);
	
	public void updateChargesDebit(ChargesDebit chargesDebit);


}
