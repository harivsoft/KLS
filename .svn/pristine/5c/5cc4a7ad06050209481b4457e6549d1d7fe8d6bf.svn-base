package com.vsoftcorp.kls.report.util;

import com.vsoftcorp.time.Date;
import com.vsoftcorp.time.DurationFactory;

public class DCBReportUtil {

	public static Date getDateByFreequncyPeroid(Date fromDate, Integer freequency) {

		Date toDate = null;

		toDate = fromDate.shift(DurationFactory.valueOf(freequency == 0 ? "MONTH:1" : freequency == 1 ? "MONTH:3" : freequency == 2 ? "MONTH:6" : "YEAR:1"));

		return toDate;

	}
}
