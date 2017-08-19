package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
import com.vsoftcorp.kls.data.ChargesMasterData;
import com.vsoftcorp.kls.valuetypes.charges.ChargesType;

public class ChargesMasterHelper {
	public static ChargesMaster getChargesMaster(ChargesMasterData chargesMasterData) {
		ChargesMaster chargesMaster = new ChargesMaster();
		if (chargesMasterData.getId() != null)
			chargesMaster.setId(chargesMasterData.getId());
		if (chargesMasterData.getChargesCode() != null)
			chargesMaster.setChargesCode(chargesMasterData.getChargesCode());
		if (chargesMasterData.getChargesDescription() != null)
			chargesMaster.setChargesDescription(chargesMasterData.getChargesDescription());

		if (chargesMasterData.getChargesType() != null)
			if (chargesMasterData.getChargesType() != null)
				chargesMaster.setChargesType(ChargesType.getType(chargesMasterData.getChargesType()));
		if (chargesMasterData.getMaxAmount() != null)
			chargesMaster.setMaxAmount(Money.valueOf(Currency.getInstance("INR"), chargesMasterData.getMaxAmount()));

		if (chargesMasterData.getMinAmount() != null)
			chargesMaster.setMinAmount(Money.valueOf(Currency.getInstance("INR"), chargesMasterData.getMinAmount()));
		if (chargesMasterData.getPacsId() != null) {
			Pacs pacs = new Pacs();
			pacs.setId(chargesMasterData.getPacsId());
			chargesMaster.setPacs(pacs);
		}
		if (chargesMasterData.getChargesReceivableGL() != null)
			chargesMaster.setChargesReceivableGL(chargesMasterData.getChargesReceivableGL());
		if (chargesMasterData.getChargesReceivedGL() != null)
			chargesMaster.setChargesReceivedGL(chargesMasterData.getChargesReceivedGL());
		if (chargesMasterData.getBankChargesReceivableGL() != null)
			chargesMaster.setBankChargesReceivableGL(chargesMasterData.getBankChargesReceivableGL());
		if (chargesMasterData.getBankChargesReceivedGL() != null)
			chargesMaster.setBankChargesReceivedGL(chargesMasterData.getBankChargesReceivedGL());
		return chargesMaster;
	}

	public static ChargesMasterData getChargesMasterData(ChargesMaster chargesMaster) {
		ChargesMasterData chargesMasterData = new ChargesMasterData();
		if (chargesMaster.getChargesCode() != null)
			chargesMasterData.setChargesCode(chargesMaster.getChargesCode());
		if (chargesMaster.getChargesDescription() != null)
			chargesMasterData.setChargesDescription(chargesMaster.getChargesDescription());
		if (chargesMaster.getId() != null)
			chargesMasterData.setId(chargesMaster.getId());

		if (chargesMaster.getChargesType() != null)
			chargesMasterData.setChargesType(chargesMaster.getChargesType().getValue());
		if (chargesMaster.getMaxAmount() != null)
			chargesMasterData.setMaxAmount(chargesMaster.getMaxAmount().getAmount().setScale(2).toString());
		if (chargesMaster.getMinAmount() != null)
			chargesMasterData.setMinAmount(chargesMaster.getMinAmount().getAmount().setScale(2).toString());
		if (chargesMaster.getPacs() != null)
			chargesMasterData.setPacsId(chargesMaster.getPacs().getId());
		if (chargesMaster.getChargesReceivableGL() != null)
			chargesMasterData.setChargesReceivableGL(chargesMaster.getChargesReceivableGL());
		if (chargesMaster.getChargesReceivedGL() != null)
			chargesMasterData.setChargesReceivedGL(chargesMaster.getChargesReceivedGL());
		if (chargesMaster.getBankChargesReceivableGL() != null)
			chargesMasterData.setBankChargesReceivableGL(chargesMaster.getBankChargesReceivableGL());
		if (chargesMaster.getBankChargesReceivedGL() != null)
			chargesMasterData.setBankChargesReceivedGL(chargesMaster.getBankChargesReceivedGL());
		
		return chargesMasterData;
	}

}
