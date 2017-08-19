package com.vsoftcorp.kls.service.account;

import java.util.List;

import com.vsoftcorp.kls.data.ChargesEnumData;
import com.vsoftcorp.kls.data.ChargesMasterData;

public interface IChargesMasterService {

	public void saveChargesMaster(ChargesMasterData data);

	public List<ChargesMasterData> getAllCharges();

	public ChargesEnumData getAllChargesTypeEnums();

	public void updateChargesMaster(ChargesMasterData data);

}
