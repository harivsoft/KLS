//package com.vsoftcorp.kls.service.helper;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import com.vsoftcorp.finance.Currency;
//import com.vsoftcorp.finance.Money;
//import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
//import com.vsoftcorp.kls.business.entity.loan.ChargesDebit;
//import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
//import com.vsoftcorp.kls.data.ChargesDebitData;
//import com.vsoftcorp.kls.data.ChargesMasterData;
//
//public class ChargesDebitHelper {
//	
//	public static ChargesDebit getChargesDebit(ChargesDebitData chargesDebitData) {
//		ChargesDebit chargesDebit = new ChargesDebit();
//		if (chargesDebitData.getId() != null)
//			chargesDebit.setId(chargesDebitData.getId());		
//		if (chargesDebitData.getLineOfCreditId() != null){			
//			LineOfCredit lineOfCredit=new LineOfCredit();
//			lineOfCredit.setId(chargesDebitData.getLineOfCreditId());
//			chargesDebit.setLineOfCredit(lineOfCredit);
//			}
//		if (chargesDebitData.getChargesMasterDataList() != null){
//			List<ChargesMasterData> chargesMasterDataList=chargesDebitData.getChargesMasterDataList();
//			Iterator<ChargesMasterData> chargesMasterDataListIterator=chargesMasterDataList.iterator();
//			ChargesMasterData chargesMasterData=null;
//			if(chargesMasterDataListIterator.hasNext()){
//				chargesMasterData=(ChargesMasterData) chargesMasterDataListIterator.next();
//				ChargesMaster chargesMaster=new ChargesMaster();
//				chargesMaster.setChargesCode(chargesMasterData.getChargesCode());
//				chargesMaster.setChargesDescription(chargesMasterData.getChargesDescription());
//				chargesDebit.setChargesMaster(chargesMaster);
//				if (chargesMasterData.getAmount() != null)
//					chargesDebit.setAmount(Money.valueOf(Currency.getInstance("INR"), chargesMasterData.getAmount()));
//				if (chargesMasterData.getRemarks() != null)
//					chargesDebit.setRemarks(chargesMasterData.getRemarks());
//				}
//			}
//		return chargesDebit;
//	}
//	public static ChargesDebitData getChargesDebitData(ChargesDebit chargesDebit) {
//		ChargesDebitData chargesDebitData = new ChargesDebitData();
//		if (chargesDebit.getId() != null)
//			chargesDebitData.setId(chargesDebit.getId());
//		if (chargesDebit.getLineOfCredit() != null){
//			LineOfCredit lineOfCredit=chargesDebit.getLineOfCredit();
//			chargesDebitData.setLineOfCreditId(lineOfCredit.getId());
//		}
//		if (chargesDebit.getChargesMaster() != null){
//			List<ChargesMasterData> chargesMasterDataList=new ArrayList<ChargesMasterData>();
//			ChargesMaster chargesMaster=chargesDebit.getChargesMaster();
//			ChargesMasterData chargesMasterData=new ChargesMasterData();
//			chargesMasterData.setChargesCode(chargesMaster.getChargesCode());
//			chargesMasterData.setChargesDescription(chargesMaster.getChargesDescription());
//			if (chargesDebit.getAmount() != null)
//				chargesMasterData.setAmount(chargesDebit.getAmount().getAmount().setScale(2).toString());
//			if (chargesDebit.getRemarks() != null)
//				chargesMasterData.setRemarks(chargesDebit.getRemarks());
//			chargesMasterDataList.add(chargesMasterData);
//			chargesDebitData.setChargesMasterDataList(chargesMasterDataList);
//			
//				
//		}
//		
//		return chargesDebitData;
//	}
//}
