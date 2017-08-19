package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.dao.dcb.ISTMTLTDcbReportDAO;
import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.STMTLTDcbReportData;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class STMTLTDCBReportHelper {

	private static final Logger logger = Logger.getLogger(STMTLTDCBReportHelper.class);

	private static ISTMTLTDcbReportDAO dao = KLSReportDataAccessFactory.getstmtltDcbReportDAO();

	private static BigDecimal zero = BigDecimal.ZERO;

	private static AccountingMoney ZERO = AccountingMoney.ZERO;

	public static List<STMTLTDcbReportData> getSTMTLTDcbReportData(Integer pacsId, Date asOnDate,Integer branchId,String branchName) {

		logger.info("Start: Inside getSTMTLTDcbReportData() of STMTLTDCBReportHelper");
		List<STMTLTDcbReportData> result = new ArrayList<STMTLTDcbReportData>();

		STMTLTDcbReportData data = new STMTLTDcbReportData();

		AccountingMoney stAgriPrincipleReceivable = dao.getSTAgriPricipleReceivable(pacsId, asOnDate,branchId);
		AccountingMoney stAgriPrincipleReceivableTillDate = dao.getSTAgriPricipleReceivableTillDate(pacsId, asOnDate,branchId);
		AccountingMoney stAgriPrinciplePaid = dao.getSTAgriPrincipleCollectionTotal(pacsId, asOnDate,branchId);
		BigDecimal stAgriIntReceivale = dao.getSTAgriInterestReceivable(pacsId, asOnDate,branchId).abs();
		AccountingMoney stAgriIntPaid = dao.getSTAgriIntCollectionTotal(pacsId, asOnDate,branchId);

		Money mtltAgriPrincipleReceivale = dao.getMTLTAgriPricipleReceivable(pacsId, asOnDate,branchId);
		Money mtltAgriPrincipleReceivaleTillDate = dao.getMTLTAgriPricipleReceivableTillDate(pacsId, asOnDate,branchId);

		BigDecimal mtltAgriPrinciplePaid = dao.getMTLTAgriPriciplePaid(pacsId, asOnDate,branchId);
		BigDecimal mtltAgriIntReceivale = dao.getMTLTAgriInterestReceivable(pacsId, asOnDate,branchId);
		BigDecimal mtltAgriIntPaid = dao.getMTLTAgriInterestPaid(pacsId, asOnDate,branchId);

		AccountingMoney stAgriPricipleDmdArrears = stAgriPrincipleReceivable.subtract(stAgriPrinciplePaid);
		if (stAgriPricipleDmdArrears.isDebit())
			stAgriPricipleDmdArrears = ZERO;

		AccountingMoney stAgriPrincipleDmdCurrentArrears = stAgriPrincipleReceivableTillDate.subtract(stAgriPrinciplePaid);
		if (stAgriPrincipleDmdCurrentArrears.isDebit())
			stAgriPrincipleDmdCurrentArrears = ZERO;
		AccountingMoney stAgriPrincipleDmdCurrent = stAgriPrincipleDmdCurrentArrears.subtract(stAgriPricipleDmdArrears);
		if (stAgriPrincipleDmdCurrent.isDebit())
			stAgriPrincipleDmdCurrent = ZERO;

		BigDecimal stAgriPrincipleCollectionTotal = stAgriPrinciplePaid.getMoney().getAmount();

		BigDecimal stAgriPrinciplaDmdTotal = stAgriPricipleDmdArrears.add(stAgriPrincipleDmdCurrent).getMoney().getAmount();

		BigDecimal stAgriPrincipleCollectionInAdvance = BigDecimal.ZERO;

		if (stAgriPrincipleCollectionTotal.compareTo(stAgriPrinciplaDmdTotal) == 1)
			stAgriPrincipleCollectionInAdvance = stAgriPrincipleCollectionTotal.subtract(stAgriPrinciplaDmdTotal);

		BigDecimal stAgriIntDmdArrears = stAgriIntReceivale.subtract(stAgriIntPaid.getMoney().getAmount());
		if (stAgriIntDmdArrears.compareTo(BigDecimal.ZERO) == -1)
			stAgriIntDmdArrears = zero;

		BigDecimal stAgriIntDmdCurrent = stAgriIntReceivale.subtract(stAgriIntDmdArrears);
		if (stAgriIntDmdCurrent.compareTo(BigDecimal.ZERO) == -1)
			stAgriIntDmdCurrent = zero;

		BigDecimal stAgriIntDmdTotal = stAgriIntDmdArrears.add(stAgriIntDmdCurrent);

		BigDecimal stAgriIntCollectionTotal = stAgriIntPaid.getMoney().getAmount();

		BigDecimal stAgriIntCollectionInAdvance = BigDecimal.ZERO;

		if (stAgriIntCollectionTotal.compareTo(stAgriIntDmdTotal) == 1)
			stAgriIntCollectionInAdvance = stAgriIntCollectionTotal.subtract(stAgriIntDmdTotal);

		BigDecimal stAgriPercentage = zero;
		try {
			stAgriPercentage = stAgriPrincipleCollectionTotal.divide(
					stAgriPricipleDmdArrears.getMoney().getAmount().add(stAgriPrincipleDmdCurrent.getMoney().getAmount()), 2, RoundingMode.HALF_UP)
					.multiply(new BigDecimal("100"));
			logger.info("stAgriPercentage: " + stAgriPercentage);
		} catch (Exception e) {
			e.printStackTrace();
			stAgriPercentage = zero;
			logger.info("stAgriPercentage: " + stAgriPercentage);
		}

		BigDecimal mtltAgriPricipleDmdArrears = mtltAgriPrincipleReceivale.getAmount().subtract(mtltAgriPrinciplePaid);
		if (mtltAgriPricipleDmdArrears.compareTo(BigDecimal.ZERO) == -1)
			mtltAgriPricipleDmdArrears = zero;

		BigDecimal mtltAgriPrincipleDmdCurrent = mtltAgriPrincipleReceivaleTillDate.getAmount().subtract(mtltAgriPrinciplePaid)
				.subtract(mtltAgriPricipleDmdArrears);
		if (mtltAgriPrincipleDmdCurrent.compareTo(BigDecimal.ZERO) == -1)
			mtltAgriPrincipleDmdCurrent = zero;

		BigDecimal mtltAgriPrincipalDmdTotal = mtltAgriPricipleDmdArrears.add(mtltAgriPrincipleDmdCurrent);

		BigDecimal mtltAgriPrincipleCollectionTotal = mtltAgriPrinciplePaid;

		BigDecimal mtltAgriPrincipleCollectionInAdvance = BigDecimal.ZERO;

		if (mtltAgriPrincipleCollectionTotal.compareTo(mtltAgriPrincipalDmdTotal) == 1)
			mtltAgriPrincipleCollectionInAdvance = mtltAgriPrincipleCollectionTotal.subtract(mtltAgriPrincipalDmdTotal);

		BigDecimal mtltAgriIntDmdArrears = mtltAgriIntReceivale.subtract(mtltAgriIntPaid);
		if (mtltAgriIntDmdArrears.compareTo(BigDecimal.ZERO) == -1)
			mtltAgriIntDmdArrears = zero;

		BigDecimal mtltAgriIntDmdCurrent = mtltAgriIntReceivale.subtract(mtltAgriIntDmdArrears);
		if (mtltAgriIntDmdCurrent.compareTo(BigDecimal.ZERO) == -1)
			mtltAgriIntDmdCurrent = zero;

		BigDecimal mtltAgriIntDmdTotal = mtltAgriIntDmdArrears.add(mtltAgriIntDmdCurrent);

		BigDecimal mtltAgriIntCollectionTotal = mtltAgriIntPaid;

		BigDecimal mtltAgriIntCollectionInAdvance = BigDecimal.ZERO;

		if (mtltAgriIntCollectionTotal.compareTo(mtltAgriIntDmdTotal) == 1)
			mtltAgriIntCollectionInAdvance = mtltAgriIntCollectionTotal.subtract(mtltAgriIntDmdTotal);

		BigDecimal mtltAgriPercentage;
		try {
			mtltAgriPercentage = (mtltAgriPrincipleCollectionTotal.divide(mtltAgriPricipleDmdArrears.add(mtltAgriPrincipleDmdCurrent), 2,
					RoundingMode.HALF_UP)).multiply(new BigDecimal("100"));
			logger.info("mtltAgriPercentage: " + mtltAgriPercentage);
		} catch (Exception e) {
			e.printStackTrace();
			mtltAgriPercentage = zero;
			logger.info("mtltAgriPercentage: " + mtltAgriPercentage);
		}

		if (pacsId != 0) {
			Pacs pacs = KLSDataAccessFactory.getPacsDAO().getPacs(pacsId);

			data.setPacsId(pacsId);
			data.setPacsName(pacs.getName());
			data.setBranchId(pacs.getBranch().getId());
			data.setBranchName(pacs.getBranch().getName());
		} else {
			data.setPacsName("All");
			data.setBranchName(branchName);
		}
		data.setMtltAgriIntCollectionInAdvance(mtltAgriIntCollectionInAdvance.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setMtltAgriIntCollectionTotal(mtltAgriIntCollectionTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setMtltAgriIntDmdArrears(mtltAgriIntDmdArrears.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setMtltAgriPricipleDmdArrears(mtltAgriPricipleDmdArrears.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setMtltAgriIntDmdCurrent(mtltAgriIntDmdCurrent.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setMtltAgriPrincipleCollectionInAdvance(mtltAgriPrincipleCollectionInAdvance.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setMtltAgriPrincipleDmdCurrent(mtltAgriPrincipleDmdCurrent.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setMtltAgriPrincipleCollectionTotal(mtltAgriPrincipleCollectionTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setMtltAgriPercentage(mtltAgriPercentage.setScale(2, BigDecimal.ROUND_HALF_UP));

		data.setStAgriIntCollectionInAdvance(stAgriIntCollectionInAdvance.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setStAgriIntCollectionTotal(stAgriIntCollectionTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setStAgriIntDmdArrears(stAgriIntDmdArrears.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setStAgriIntDmdCurrent(stAgriIntDmdCurrent.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setStAgriPricipleDmdArrears(stAgriPricipleDmdArrears.getMoney().getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setStAgriPrincipleCollectionInAdvance(stAgriPrincipleCollectionInAdvance.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setStAgriPrincipleCollectionTotal(stAgriPrincipleCollectionTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setStAgriPrincipleDmdCurrent(stAgriPrincipleDmdCurrent.getMoney().getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
		data.setStAgriPercentage(stAgriPercentage.setScale(2, BigDecimal.ROUND_HALF_UP));

		result.add(data);
		logger.info("END: Inside getSTMTLTDcbReportData() of STMTLTDCBReportHelper");
		return result;

	}
}
