/*package com.vsoftcorp.kls.business.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.vsoftcorp.time.Date;
import com.vsoftcorp.time.Duration;
import com.vsoftcorp.time.DurationFactory;
import com.vsoftcorp.time.IDuration;
import com.vsoftcorp.time.Instant.Direction;
import com.vsoftcorp.time.Second;
import com.vsoftcorp.time.Sign;
import com.vsoftcorp.time.TimeSpan;
import com.vsoftcorp.time.TimeUnit;
import com.vsoftcorp.time.Year;

*//**
 * @author sponnam
 *
 *//*
public class DateUtil {

	private static final Logger logger = Logger.getLogger(DateUtil.class);

	private static String dateFormat = "dd MMM yyyy";
	private static String dateInAnotherFormat = "yyyy-MM-dd";
	private static String klsDateFormat = "dd/MM/yyyy";

	public final static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	public final static SimpleDateFormat klssdf = new SimpleDateFormat(klsDateFormat);
	public final static SimpleDateFormat anotherSimpleDateFormat = new SimpleDateFormat(
			dateInAnotherFormat);

	public static Date getVSoftDate(String dateString) {
		Date theDate = null;
		if (dateString != null) {
			java.util.Date utilDate = null;
			try {
				utilDate = getFormattedDate(dateString);
				theDate = new Date(utilDate.getTime());
			} catch (Exception e) {
				logger.error("=Error Occured while parsing date string="
						+ dateString);
				e.printStackTrace();
			}
		}
		return theDate;
	}

	public static Date getDateByAddingNoOfDay(String theDate, int noOfDaysToAdd) {

		Date vsoftDate = getVSoftDate(theDate);

		return vsoftDate.shift(new Duration(Sign.POSITIVE, TimeSpan.valueOf(

		TimeUnit.DAY, (noOfDaysToAdd - 1))));

	}

	public static Date getDateByAddingNoOfDay(Date theDate, int noOfDaysToAdd) {

		return theDate.shift(new Duration(Sign.POSITIVE, TimeSpan.valueOf(

		TimeUnit.DAY, (noOfDaysToAdd))));

	}

	public static Date getDateByAddingNoOfYears(Date theDate, int noOfYearsToAdd) {

		return theDate.shift(new Duration(Sign.POSITIVE, TimeSpan.valueOf(
				TimeUnit.YEAR, (noOfYearsToAdd))));

	}

	public static String getDateByAddingNoOfYears(String theDate,
			int noOfMonthsToAdd) {

		Date vsoftDate = getVSoftDate(theDate);

		vsoftDate = vsoftDate.shift(new Duration(Sign.POSITIVE, TimeSpan
				.valueOf(TimeUnit.YEAR, (noOfMonthsToAdd))));
		vsoftDate = vsoftDate.shift(new Duration(Sign.NEGATIVE, TimeSpan
				.valueOf(TimeUnit.DAY, 1)));
		return sdf.format(vsoftDate.getJavaDate());

	}

	public static String getDateByAddingNoOfMonths(String theDate,
			int noOfMonthsToAdd) {

		Date vsoftDate = getVSoftDate(theDate);
		vsoftDate = vsoftDate.shift(new Duration(Sign.POSITIVE, TimeSpan
				.valueOf(

				TimeUnit.MONTH, (noOfMonthsToAdd))));
		vsoftDate = vsoftDate.shift(new Duration(Sign.NEGATIVE, TimeSpan
				.valueOf(

				TimeUnit.DAY, 1)));
		return sdf.format(vsoftDate.getJavaDate());

	}

	public static String getDateByAddingMonths(String theDate,
			int noOfMonthsToAdd) {

		Date vsoftDate = getVSoftDate(theDate);
		vsoftDate = vsoftDate.shift(new Duration(Sign.POSITIVE, TimeSpan
				.valueOf(TimeUnit.MONTH, (noOfMonthsToAdd))));

		return sdf.format(vsoftDate.getJavaDate());

	}

	public static String getDateStringByAddingNoOfDay(String theDate,
			int noOfDaysToAdd) {

		Date vsoftDate = getDateByAddingNoOfDay(theDate, noOfDaysToAdd);

		return sdf.format(vsoftDate.getJavaDate());
	}

	public static int compareDates(String fromDate, String toDate) {

		Date vsoftFromdate = getVSoftDate(fromDate);
		return (vsoftFromdate.getJavaDate()).compareTo(getVSoftDate(toDate)
				.getJavaDate());

	}

	public static String getDateStringBySubtractingNoOfDay(String theDate,
			int noOfDaysToAdd) {

		Date vsoftDate = getDateBySubtractingNoOfDay(getVSoftDate(theDate),
				noOfDaysToAdd);
		return sdf.format(vsoftDate.getJavaDate());
	}

	public static Date getDateBySubtractingNoOfDay(Date theDate,
			int noOfDaysToAdd) {

		return theDate.shift(new Duration(Sign.NEGATIVE, TimeSpan.valueOf(

		TimeUnit.DAY, (noOfDaysToAdd))));

	}

	public static String getDateString(Date theDate) {

		return klssdf.format(theDate.getJavaDate());
	}

	public static Date getNextDate(Date theDate) {

		return theDate.shift(new Duration(Sign.POSITIVE, TimeSpan
				.valueOf(TimeUnit.MONTH)));

	}

	public static Date getNextDate(Date theDate, int noOfMonths) {
		return theDate.shift(new Duration(Sign.POSITIVE, TimeSpan.valueOf(
				TimeUnit.MONTH, (noOfMonths))));
	}

	public static Date getPrevoiusDate(Date theDate, int noOfMonths) {
		return theDate.shift(new Duration(Sign.NEGATIVE, TimeSpan.valueOf(
				TimeUnit.MONTH, (noOfMonths))));
	}

	public static Date getPrevoiusDate(Date theDate) {

		return theDate.shift(new Duration(Sign.NEGATIVE, TimeSpan
				.valueOf(TimeUnit.MONTH)));

	}

	public static Date getMonthEnd(Date theDate) {

		return theDate.getMonth().getLastDate();

	}

	public static int getYear(Date theDate) {

		return Integer.parseInt(theDate.getYear().toString());

	}

	public static boolean getIsMonthFirstDate(Date theDate) {

		if (theDate.getMonth().getFirstDate().compareTo(theDate) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static int getDifferenceInMonths(Date fromDate, Date toDate) {
		// int noOfMonths = toDate.getMonthOfYear().getValue()
		// - fromDate.getMonthOfYear().getValue();
		int noOfDays = toDate.subtract(fromDate);

		return noOfDays + 1;

	}

	public static int getNoOfMonthsDifference(Date fromDate, Date toDate) {
		int noOfMonths = toDate.getMonthOfYear().getValue()
				- fromDate.getMonthOfYear().getValue();
		// int noOfDays = toDate.subtract(fromDate);

		return noOfMonths;

	}

	public static int getNoOfDaysBetweenDates(Date fromDate, Date toDate) {
		int noOfDays = 0;
		noOfDays = toDate.subtract(fromDate);
		return noOfDays + 1;
	}

	public static Date getQuaterEndDate(Date theDate) {
		int currentYear = theDate.getYearOfEra().getValue();
		Date firstdate = getVSoftDate("31 Mar " + currentYear);
		Date seconddate = getVSoftDate("30 Jun " + currentYear);
		Date thirddate = getVSoftDate("30 Sep " + currentYear);
		Date fourthdate = getVSoftDate("31 Dec " + currentYear);
		Date quateEndDate = null;

		if (theDate.compareTo(firstdate) <= 0) {
			quateEndDate = firstdate;

		} else if (theDate.compareTo(firstdate) > 0
				&& theDate.compareTo(seconddate) <= 0) {
			quateEndDate = seconddate;

		} else if (theDate.compareTo(seconddate) > 0
				&& theDate.compareTo(thirddate) <= 0) {
			quateEndDate = thirddate;
		} else if (theDate.compareTo(thirddate) > 0
				&& theDate.compareTo(fourthdate) <= 0) {
			quateEndDate = fourthdate;
		}

		return quateEndDate;
	}

	public static int getYearOfDate(String fromDate, int noOfYears) {
		Year year;
		Date vsoftDate = getVSoftDate(fromDate);
		vsoftDate = vsoftDate
				.shift(new Duration(new TimeSpan(TimeUnit.YEAR, 1)));
		System.out.println(vsoftDate);
		year = vsoftDate.getLater(vsoftDate).getYear()
				.shift(noOfYears, Direction.PAST);

		return Integer.parseInt(year.toString());
	}

	public static String getDateOfMonFormat(String theDate) {
		String returnDate = "";
		try {
			returnDate = sdf.format(anotherSimpleDateFormat.parse(theDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnDate;
	}

	public static Date getyearlyEndDate(String strDate) {
		Date theDate = getVSoftDate(strDate);
		if (theDate == null)
			throw new RuntimeException(
					"Date should not be null in getQuarterEndDate");

		int currentYear = getYear(theDate);

		currentYear--;
		Date firstdate = getVSoftDate("31 Mar " + currentYear);
		currentYear++;
		Date seconddate = getVSoftDate("31 Mar " + currentYear);
		currentYear++;
		Date thirddate = getVSoftDate("31 Mar " + currentYear);

		Date halfrlyEndDate = null;

		if (theDate.compareTo(firstdate) >= 0
				&& theDate.compareTo(seconddate) <= 0) {

			halfrlyEndDate = seconddate;
		}

		else if (theDate.compareTo(seconddate) >= 0
				&& theDate.compareTo(thirddate) <= 0) {

			halfrlyEndDate = thirddate;
		}

		return halfrlyEndDate;

	}

	public static Date getyearlyPreviousDate(String strDate) {
		Date theDate = getVSoftDate(strDate);
		if (theDate == null)
			throw new RuntimeException(
					"Date should not be null in getQuarterEndDate");

		int currentYear = getYear(theDate);

		currentYear--;
		Date firstdate = getVSoftDate("31 Mar " + currentYear);
		currentYear++;
		Date seconddate = getVSoftDate("31 Mar " + currentYear);
		currentYear++;
		Date thirddate = getVSoftDate("31 Mar " + currentYear);

		Date halfrlyEndDate = null;

		if (theDate.compareTo(firstdate) >= 0
				&& theDate.compareTo(seconddate) <= 0) {

			halfrlyEndDate = firstdate;
		}

		else if (theDate.compareTo(seconddate) >= 0
				&& theDate.compareTo(thirddate) <= 0) {

			halfrlyEndDate = seconddate;
		}

		return halfrlyEndDate;

	}

	*//**
	 * 
	 * @param dateString
	 * @return
	 *//*
	public static Date getVsoftDateFormat(String dateString) {
		Date theDate = null;
		if (dateString != null) {
			java.util.Date utilDate = null;
			try {
				utilDate = anotherSimpleDateFormat.parse(dateString);
				theDate = new Date(utilDate.getTime());
			} catch (ParseException e) {
				logger.error("=Error Occured while parsing date string="
						+ dateString);
				e.printStackTrace();
			}
		}
		return theDate;
	}

	*//**
	 * 
	 * @param fromDate
	 *            of the form dd MMM yyy
	 * @param toDate
	 *            of the form yyyy-MM-dd
	 * @return
	 *//*
	public static int compareDatesofDifferentFormat(String fromDate,
			String toDate) {

		Date vsoftFromDate = DateUtil.getVSoftDate(fromDate);
		Date vsoftToDate = DateUtil.getVsoftDateFormat(toDate);
		return (vsoftFromDate.getJavaDate()).compareTo(vsoftToDate
				.getJavaDate());

	}

	*//**
	 * 
	 * @param fromDate
	 *            of the form dd MMM yyy
	 * @param toDate
	 *            of the form dd MMM yyy
	 * @return
	 *//*
	public static int compareDatesOfSameFormat(String fromDate, String toDate) {

		Date vsoftFromDate = DateUtil.getVSoftDate(fromDate);
		Date vsoftToDate = DateUtil.getVSoftDate(toDate);

		// System.out.println("vsoftFromDate comparision : "+vsoftFromDate);
		// System.out.println("vsoftToDate comparision : "+vsoftToDate);
		return (vsoftFromDate.getJavaDate()).compareTo(vsoftToDate
				.getJavaDate());

	}

	public static int getMonthOfYear(String theDate) {
		Date vsoftDate = DateUtil.getVSoftDate(theDate);
		return vsoftDate.getMonthOfYear().getValue();

	}

	public static int getYear(String theDate) {
		Date vsoftDate = DateUtil.getVSoftDate(theDate);
		return vsoftDate.getYearOfEra().getValue();

	}

	public static int getDateOfMonth(String theDate) {
		Date vsoftDate = DateUtil.getVSoftDate(theDate);
		return vsoftDate.getDayOfMonth().getValue();
	}

	public static String getStringDateByAddingNoOfDay(String theDate,
			int noOfDaysToAdd) {

		Date vsoftDate = getVSoftDate(theDate);
		Date incrementDate = vsoftDate.shift(new Duration(Sign.POSITIVE,
				TimeSpan.valueOf(

				TimeUnit.DAY, (noOfDaysToAdd))));
		return sdf.format(incrementDate.getJavaDate());

	}

	public static Date getFinancialYearBeginDate(Date currentDate) {
		int monthValue = currentDate.getMonthOfYear().getValue();
		int yearValue = currentDate.getYearOfEra().getValue();
		if (monthValue == 1 || monthValue == 2 || monthValue == 3) {
			return getVSoftDate("01 Apr " + (yearValue - 1));
		} else {
			return getVSoftDate("01 Apr " + (yearValue));
		}
	}

	public static Date getFinancialYearEndDate(Date currentDate) {
		int monthValue = currentDate.getMonthOfYear().getValue();
		int yearValue = currentDate.getYearOfEra().getValue();
		if (monthValue == 1 || monthValue == 2 || monthValue == 3) {
			return getVSoftDate("31 Mar " + (yearValue));
		} else {
			return getVSoftDate("31 Mar " + (yearValue + 1));
		}
	}

	public static String getFinancialYear(Date currentDate) {
		return DateUtil.getFinancialYearBeginDate(currentDate).getYearOfEra()
				.getValue()
				+ "-"
				+ DateUtil.getFinancialYearEndDate(currentDate).getYearOfEra()
						.getValue();
	}

	public static Date getFirstDateByWeek(Date startDate, String weekName) {
		for (int i = 1; i <= 7; i++) {
			if (startDate.getDayOfWeek().getName().equalsIgnoreCase(weekName)) {
				return startDate;
			}
			startDate = DateUtil.getDateByAddingNoOfDay(startDate, 1);
		}
		return null;
	}

	// DateUtil.java


	@SuppressWarnings("deprecation")
	public static Second getVsoftSecondByString(String inputDate) {

		return Second.valueOf(new java.util.Date(inputDate));
	}

	public static Date getVsoftDateFromSecond(Second second) {
		return getVsoftDateByString(second.toString("dd MMM yyyy"));
	}

	
	 * public static void main(String[] args) {
	 * 
	 * System.out.println(Second.now().toString("dd MMM yyyy"));
	 * System.out.println(getVsoftDateFromSecond(Second.now())); }
	 
	public static String getMonFormat(Date theDate) {

		if (theDate == null)
			throw new RuntimeException(
					"Date should not be null to convert to String");

		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		strDate = sdf.format(theDate);
		return strDate;
	}

	public static java.util.Date addDays(java.util.Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}

	public static String format(java.util.Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static java.util.Date getDate(String theDate, String format) {
		java.util.Date returnDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			returnDate = sdf.parse(theDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnDate;
	}

	public static long daysBetween(java.util.Date theStartDate,
			java.util.Date theEndDate) {

		// Date startDate = getDate(theStartDate);
		// Date endDate = getDate(theEndDate);
		Calendar date1 = Calendar.getInstance();
		date1.setTime(theStartDate);
		Calendar date2 = Calendar.getInstance();
		date2.setTime(theEndDate);
		long daysBetween = 0;

		// System.out.println(date1+"********* daysBetween"+date2);

		long milis1 = date1.getTimeInMillis();
		long milis2 = date2.getTimeInMillis();
		long diff = milis2 - milis1;
		daysBetween = diff / (24 * 60 * 60 * 1000);
		System.out.println("********* daysBetween" + daysBetween);
		return daysBetween;
	}

	*//**
	 * Desc: dayMonthYear can have value Y for year ,M for Month and D for Days
	 * difference
	 *//*
	public static int getDiffBetweenDates(Date fromDate, Date toDate,
			String dayMonthYear) {
		TimeUnit timeUnit = null;
		if ("Y".equalsIgnoreCase(dayMonthYear)) {
			timeUnit = TimeUnit.YEAR;
		} else if ("M".equalsIgnoreCase(dayMonthYear)) {
			timeUnit = TimeUnit.MONTH;
		} else {
			timeUnit = TimeUnit.DAY;
		}
		IDuration duration = toDate.subtract(fromDate, timeUnit);
		if (Sign.NEGATIVE.equals(duration.getSign())) {
			return -duration.getTimeUnitValue(timeUnit);
		}
		return duration.getTimeUnitValue(timeUnit);
	}

	public static Date addValue(Date date, int value, String dayMonthYear) {
		IDuration duration = null;
		if ("Y".equalsIgnoreCase(dayMonthYear)) {
			duration = DurationFactory.valueOf("YEAR:" + value);
		} else if ("M".equalsIgnoreCase(dayMonthYear)) {
			duration = DurationFactory.valueOf("MONTH:" + value);
		} else {
			duration = DurationFactory.valueOf("DAY:" + value);
		}
		return date.shift(duration);
	}

	public static String currentTime() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(cal.getTime()));
		return sdf.format(cal.getTime());
	}

	// Dateutil.java

	public static String getdateFormat(java.util.Date theDate) {

		if (theDate == null)
			throw new RuntimeException(
					"Date should not be null to convert to String");

		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		strDate = sdf.format(theDate);
		return strDate;
	}

	public static java.util.Date getDateparse(String theDate) {
		java.util.Date returnDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		try {
			returnDate = sdf.parse(theDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnDate;

	}

	public static Calendar toCalendar(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	public static int getYear(java.util.Date theDate) {
		return toCalendar(theDate).get(Calendar.YEAR);
	}

	public static String addmonths(String date, int noofmonths) {
		java.util.Date strdate = getDateparse(date);
		System.out.println("strdate===" + strdate);
		Calendar cal = toCalendar(strdate);
		System.out.println("strdate===" + strdate);
		cal.add(Calendar.MONTH, noofmonths);
		String adddate = getdateFormat(cal.getTime());
		System.out.println("adddate====" + adddate);
		return adddate;
	}

	public static int datecompare(String date1, String date2) {

		java.util.Date date11 = getDateparse(date1);
		java.util.Date date12 = getDateparse(date2);

		int cflag = date11.compareTo(date12);
		return cflag;

	}

	public static String Findfinhalafrlystartdate(String theDate) {
		java.util.Date sDate = getDateparse(theDate);
		int currentYear = getYear(sDate);
		currentYear--;
		java.util.Date firstdate = getDateparse("01 Oct " + currentYear);
		currentYear++;
		java.util.Date seconddate = getDateparse("01 April " + currentYear);
		java.util.Date thirddate = getDateparse("01 Oct " + currentYear);
		currentYear++;
		java.util.Date fourthdate = getDateparse("01 April " + currentYear);
		java.util.Date fromdate = null;

		// java.util.Date todate = null;
		if (sDate.compareTo(firstdate) >= 0 && sDate.compareTo(seconddate) <= 0) {

			fromdate = firstdate;
			// todate = seconddate;
		}

		if (sDate.compareTo(seconddate) >= 0 && sDate.compareTo(thirddate) <= 0) {

			fromdate = seconddate;
			// todate = thirddate;
		}
		if (sDate.compareTo(thirddate) >= 0 && sDate.compareTo(fourthdate) <= 0) {

			fromdate = thirddate;
			// todate = fourthdate;
		}

		String fromdate1 = getdateFormat(fromdate);
		return fromdate1;
	}

	public static String Findfinhalafrlyenddate(String theDate) {
		java.util.Date sDate = getDateparse(theDate);
		int currentYear = getYear(sDate);
		currentYear--;
		java.util.Date firstdate = getDateparse("01 Oct " + currentYear);
		currentYear++;
		java.util.Date seconddate = getDateparse("01 April " + currentYear);
		java.util.Date thirddate = getDateparse("01 Oct " + currentYear);
		currentYear++;
		java.util.Date fourthdate = getDateparse("01 April " + currentYear);
		// java.util.Date fromdate = null;

		java.util.Date todate = null;
		if (sDate.compareTo(firstdate) >= 0 && sDate.compareTo(seconddate) <= 0) {

			// fromdate = firstdate;
			todate = seconddate;
		}

		if (sDate.compareTo(seconddate) >= 0 && sDate.compareTo(thirddate) <= 0) {

			// fromdate = seconddate;
			todate = thirddate;
		}
		if (sDate.compareTo(thirddate) >= 0 && sDate.compareTo(fourthdate) <= 0) {

			// fromdate = thirddate;
			todate = fourthdate;
		}
		String todate1 = getdateFormat(todate);
		return todate1;
	}

	*//**
	 * @Description : FirstDueDate calculation
	 * @param strDate
	 * @return
	 *//*
	public static String getFirstDueDate(String strDate) {
		String firstDueDate = "";
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		Calendar cal = Calendar.getInstance();
		try {
			java.util.Date currentSysDate = DateUtil.getDateparse(strDate);

			cal.setTime(currentSysDate);
			Calendar cal1 = Calendar.getInstance();
			cal1.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 16);
			// logger.info(" cal checking" + cal.getTime());
			// logger.info(" cal1 checking" + cal1.getTime());
			// logger.info(" cal1 AFETER checking" +
			// format.parse(format.format(cal1.getTime())));
			if (cal.getTime().before(
					format.parse(format.format(cal1.getTime())))) {
				cal.add(Calendar.MONTH, 1);
				cal.set(Calendar.DATE, 5);
				// logger.info("The Next Due Date is "+cal.getTime());
				// logger.info("The Date String is:"+format.format(cal.getTime()));
			} else {
				cal.add(Calendar.MONTH, 2);
				cal.set(Calendar.DATE, 5);
				// logger.info("The els Next Due Date is "+cal.getTime());
			}
			firstDueDate = format.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return firstDueDate;

	}

	public static String getFormattedDate(java.util.Date date, String pattren) {
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattren);
		return sdf2.format(date);

	}

	public static String getMonFormat(java.util.Date theDate) {

		if (theDate == null)
			throw new RuntimeException(
					"Date should not be null to convert to String");

		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		strDate = sdf.format(theDate);
		return strDate;
	}

	public static java.util.Date getDate(String theDate) {
		java.util.Date returnDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		try {
			returnDate = sdf.parse(theDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnDate;
	}

	// Calendar.MONTH returns one less than the actual month
	public static int getCurrentMonth(java.util.Date theDate) {
		return toCalendar(theDate).get(Calendar.MONTH);
	}

	public static Calendar toCalendar(String theDate) {
		return toCalendar(getDate(theDate));
	}

	// Need Clarification about parameter
	public static java.util.Date getQuarterEndDate(java.util.Date theDate) {

		if (theDate == null)
			throw new RuntimeException(
					"Date should not be null in getQuarterEndDate");

		int currentYear = getYear(theDate);
		java.util.Date firstQuarterEndDate = getDate("31 Mar " + currentYear);
		java.util.Date secondQuarterEndDate = getDate("30 Jun " + currentYear);
		java.util.Date thirdQuarterEndDate = getDate("30 Sep " + currentYear);
		java.util.Date fourthQuarterEndDate = getDate("31 Dec " + currentYear);

		java.util.Date quarterEndDate = null;
		// System.out.println(theDate+"^^^^^555^^^^^^^^^^^^==="+secondQuarterEndDate);

		if (theDate.compareTo(firstQuarterEndDate) <= 0) {
			quarterEndDate = firstQuarterEndDate;

		} else if (theDate.compareTo(firstQuarterEndDate) > 0
				&& theDate.compareTo(secondQuarterEndDate) < 0) {
			quarterEndDate = secondQuarterEndDate;

		} else if (theDate.compareTo(secondQuarterEndDate) > 0
				&& theDate.compareTo(thirdQuarterEndDate) < 0) {
			quarterEndDate = thirdQuarterEndDate;

		} else if (theDate.compareTo(thirdQuarterEndDate) > 0
				&& theDate.compareTo(fourthQuarterEndDate) <= 0) {
			quarterEndDate = fourthQuarterEndDate;

		}

		return quarterEndDate;

	}

	*//** Using Calendar - THE CORRECT WAY **//*
	// assert: startDate must be before endDate
	public static long daysBetween(String theStartDate, String theEndDate) {

		java.util.Date startDate = DateUtil.getDate(theStartDate);
		java.util.Date endDate = DateUtil.getDate(theEndDate);
		Calendar date1 = Calendar.getInstance();
		date1.setTime(startDate);
		Calendar date2 = Calendar.getInstance();
		date2.setTime(endDate);
		long daysBetween = 0;

		// System.out.println(date1+"********* daysBetween"+date2);

		long milis1 = date1.getTimeInMillis();
		long milis2 = date2.getTimeInMillis();
		long diff = milis2 - milis1;
		daysBetween = diff / (24 * 60 * 60 * 1000);
		System.out.println("********* daysBetween" + daysBetween);
		return daysBetween;
	}

	public static double DifferenceInMonths(java.util.Date date1,
			java.util.Date date2) {
		return DifferenceInYears(date1, date2) * 12;
	}

	// added by santhosh for getting last date of the month on 17 Dec 2010
	public static int lastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	public static java.util.Date getDate(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, date);
		return cal.getTime();
	}

	public static java.util.Date getFormattedDate(String theDate) {

		java.util.Date d = null;
		String formats[] = { "dd MMM yyyy", "dd-MMM-yyyy", "d MMM  yyyy",
				"dd/MM/yyyy", "EEE, MMM d,yyyy", "yyyy-MM-dd H:m:s",
				"dd-MM-yyyy HH:mm:ss" };
		for (int i = 0; i < formats.length; i++) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(formats[i]);
				d = sdf.parse(theDate);
				break;
			} catch (Exception e) {
				// e.printStackTrace();
				// This part is commented intentionally
			}
		}
		return d;
	}

	public static int getFinancialStartYear(java.util.Date theCurrentDate) {
		if (theCurrentDate == null)
			throw new RuntimeException(
					"Date should not be null in getFinancialYearEndDate");
		int currentYear = getYear(theCurrentDate);
		int currentMonth = getCurrentMonth(theCurrentDate);
		if (currentMonth >= 0 && currentMonth < 3) {
			currentYear = currentYear - 1;
		}
		return currentYear;
	}

	*//**
	 * this method is used for get number of days for particular given month
	 * when given date is string type
	 * 
	 * @param strDate
	 * @return No of days for the month
	 * @throws ParseException
	 *//*
	public static int getDaysForMonth(String strDate) throws ParseException {
		DateFormat formatter;
		java.util.Date date;
		formatter = new SimpleDateFormat("dd MMM yyyy");
		date = (java.util.Date) formatter.parse(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	*//**
	 * This method is used for get Date String Type when Given date is Date Type
	 * 
	 * @param date
	 * @return
	 *//*
	public static String getStrDateByDate(java.util.Date date) {
		String strDate;
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MMM-yy");
		strDate = formatter.format(date);
		return strDate;

	}

	*//**
	 * this method is used for get number of days for particular given month
	 * when given date is Date Type
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 *//*
	public static int getDaysForMonth1(java.util.Date date)
			throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	*//**
	 * This method is used for get Added Date add by months
	 * 
	 * @param noOfMonths
	 * @param strDate
	 * @return
	 * @throws ParseException
	 *//*
	public static String getAddedDateByMonths(int noOfMonths, String strDate)
			throws ParseException {
		java.util.Date date = getDateByString(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, +noOfMonths);
		cal.add(Calendar.DATE, -1);
		String strdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		strdate = sdf.format(cal.getTime());
		return strdate;
	}

	*//**
	 * This method is used for get Date Date Type when Given date is String Type
	 * 
	 * @param strDate
	 * @return
	 *//*
	public static java.util.Date getDateByString(String strDate) {
		DateFormat formatter;
		java.util.Date date = null;
		try {
			formatter = new SimpleDateFormat("dd MMM yyyy");
			date = (java.util.Date) formatter.parse(strDate);
		} catch (ParseException e) {
			logger.info("Exception :" + e);
		}
		return date;

	}

	*//**
	 * This method is used for Get the date Date Type when Given date is String
	 * Type
	 * 
	 * @param strDate
	 * @param formatOfDate
	 * @return Date
	 *//*
	public static java.util.Date getDateByString(String strDate,
			String formatOfDate) {
		DateFormat formatter;
		java.util.Date date = null;
		try {
			formatter = new SimpleDateFormat(formatOfDate);
			date = (java.util.Date) formatter.parse(strDate);
		} catch (ParseException e) {
			logger.info("Exception :" + e);
		}
		return date;
	}

	public static String getDateByStringFormat(String strDate) {
		DateFormat formatter = null;
		String date = null;
		try {
			formatter = new SimpleDateFormat("ddMMyyyy");
			date = formatter.format(DateUtil.getDateByString(strDate));

		} catch (Exception e) {
			logger.info("Exception :" + e);
		}
		return date;

	}

	public static Date getVsoftDateByString(String inputDate) {

		return Date.valueOf(getFormattedDate(inputDate));
	}

	public static com.vsoftcorp.time.Date getVSoftDateByString(String date) {

		return com.vsoftcorp.time.Date.valueOf(getDateByString(date));
	}

	public static String getFormatedDateByString(String strDate,
			String sourceFormat, String destinationFormat) {
		DateFormat sourceFormatter, destinationFormatter;
		java.util.Date date = null;
		String destinationDate = "";
		try {
			sourceFormatter = new SimpleDateFormat(sourceFormat);
			date = (java.util.Date) sourceFormatter.parse(strDate);
			destinationFormatter = new SimpleDateFormat(destinationFormat);
			destinationDate = destinationFormatter.format(date);
		} catch (ParseException e) {
			logger.info("Exception :" + e);
		}
		return destinationDate;
	}

	public static String getStringDateByDate(Date date) {
		String strDate = "";
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("dd MMM yyyy");
			strDate = formatter.format(date);
		} catch (Exception e) {
			logger.info("Exception in getStringDateByDate : " + e);
		}
		return strDate;

	}

	*//**
	 * This method is used for compare the two dates if dateOne > dateTwo means
	 * return the "-1" other wise return "1"
	 * 
	 * @param dateOne
	 * @param dateTwo
	 * @return String
	 *//*

	public static String compareTwoDates(java.util.Date dateOne,
			java.util.Date dateTwo) {
		String result = "1";
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(dateOne);
		cal2.setTime(dateTwo);
		if (cal1.after(cal2)) {
			result = "-1";
		}
		return result;
	}

	
	 * @ this method will return the date in the form String to compte any date
	 * from a given date and period to be added
	 * 
	 * @ theDate is account opening date
	 * 
	 * @ period is the period howmuch is adding to the date
	 * 
	 * @ periodIn gives M= months, D =Days ,Y = Years this will give finaldate =
	 * theDate + period*( periodIn)
	 * 
	 * @Dependencies == SbInvestOpenD.jsp
	 * 
	 * @a1094
	 
	public static java.util.Date addPeriod(java.util.Date theDate,
			int theperiod, Period theperiodIn) {

		java.util.Date resultentDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		System.out.println(theperiod + "input date" + theDate + "----"
				+ theperiodIn);

		if (theperiodIn.equals(Period.DAYS)) {
			cal.add(Calendar.DATE, theperiod);
		}
		if (theperiodIn.equals(Period.MONTHS)) {
			cal.add(Calendar.MONTH, theperiod);
		}
		if (theperiodIn.equals(Period.YEAR)) {
			cal.add(Calendar.YEAR, theperiod);
		}
		resultentDate = cal.getTime();

		return resultentDate;
	}

	// Merged

	
	 * @ this method will return the date in the form String to compte any date
	 * from a given date and period to be added
	 * 
	 * @ theDate is account opening date
	 * 
	 * @ period is the period howmuch is adding to the date
	 * 
	 * @ periodIn gives M= months, D =Days ,Y = Years this will give finaldate =
	 * theDate - period*( periodIn)
	 * 
	 * @Dependencies == SbInvestOpenD.jsp
	 * 
	 * @a1094
	 
	public static java.util.Date substractPeriod(java.util.Date theDate,
			int theperiod, Period theperiodIn) {

		java.util.Date resultentDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		System.out.println(theperiod
				+ "iam in getDateByAddingPeriod  inputDate 1111==" + theDate
				+ "----" + theperiodIn);

		if (theperiodIn.equals(Period.DAYS)) {
			cal.add(Calendar.DATE, theperiod * (-1));
		}
		if (theperiodIn.equals(Period.MONTHS)) {
			cal.add(Calendar.MONTH, theperiod * (-1));
		}
		if (theperiodIn.equals(Period.YEAR)) {
			cal.add(Calendar.YEAR, theperiod * (-1));
		}
		resultentDate = cal.getTime();

		return resultentDate;
	}

	// Merged
	public static java.util.Date getNextHalfYearEndDate(java.util.Date theDate) {

		if (theDate == null)
			throw new RuntimeException("getHalfYearEndDate");

		java.util.Date currentHalfYearEndDate = getHalfYearEndDate(theDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentHalfYearEndDate);
		cal.add(Calendar.MONTH, 6);
		Calendar accountOpenDate = DateUtil.toCalendar(cal.getTime());

		int currentYear = getYear(accountOpenDate.getTime());

		java.util.Date firstHalfYearEndDate = getDate("30 Jun " + currentYear);

		java.util.Date secondHalfYearEndDate = getDate("31 Dec "
				+ (currentYear));

		java.util.Date halfYearEndDate = null;

		if (accountOpenDate.getTime().compareTo(firstHalfYearEndDate) <= 0) {
			halfYearEndDate = firstHalfYearEndDate;

		} else if (accountOpenDate.getTime().compareTo(firstHalfYearEndDate) > 0
				&& accountOpenDate.getTime().compareTo(secondHalfYearEndDate) <= 0) {
			halfYearEndDate = secondHalfYearEndDate;

		}
		return halfYearEndDate;

	}

	public static java.util.Date getHalfYearEndDate(java.util.Date theDate) {

		if (theDate == null)
			throw new RuntimeException("getHalfYearEndDate");

		int currentYear = getYear(theDate);

		java.util.Date firstHalfYearEndDate = getDate("30 Jun " + currentYear);

		java.util.Date secondHalfYearEndDate = getDate("31 Dec "
				+ (currentYear));

		java.util.Date halfYearEndDate = null;

		if (theDate.compareTo(firstHalfYearEndDate) <= 0) {
			halfYearEndDate = firstHalfYearEndDate;

		} else if (theDate.compareTo(firstHalfYearEndDate) > 0
				&& theDate.compareTo(secondHalfYearEndDate) <= 0) {
			halfYearEndDate = secondHalfYearEndDate;

		}
		return halfYearEndDate;

	}

	public static java.util.Date getNextQuarterEndDate(java.util.Date theDate) {

		if (theDate == null)
			throw new RuntimeException(
					"Date should not be null in getNextQuarterEndDate");

		java.util.Date currentQuarterEndDate = getQuarterEndDate(theDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentQuarterEndDate);
		cal.add(Calendar.MONTH, 3);
		Calendar accountOpenDate = DateUtil.toCalendar(cal.getTime());

		int currentYear = getYear(accountOpenDate.getTime());
		java.util.Date firstQuarterEndDate = getDate("31 Mar " + currentYear);
		java.util.Date secondQuarterEndDate = getDate("30 Jun " + currentYear);
		java.util.Date thirdQuarterEndDate = getDate("30 Sep " + currentYear);
		java.util.Date fourthQuarterEndDate = getDate("31 Dec " + currentYear);

		java.util.Date quarterEndDate = null;

		if (accountOpenDate.getTime().compareTo(firstQuarterEndDate) <= 0) {
			quarterEndDate = firstQuarterEndDate;

		} else if (accountOpenDate.getTime().compareTo(firstQuarterEndDate) > 0
				&& accountOpenDate.getTime().compareTo(secondQuarterEndDate) <= 0) {
			quarterEndDate = secondQuarterEndDate;

		} else if (accountOpenDate.getTime().compareTo(secondQuarterEndDate) > 0
				&& accountOpenDate.getTime().compareTo(thirdQuarterEndDate) <= 0) {
			quarterEndDate = thirdQuarterEndDate;

		} else if (accountOpenDate.getTime().compareTo(thirdQuarterEndDate) > 0
				&& accountOpenDate.getTime().compareTo(fourthQuarterEndDate) <= 0) {
			quarterEndDate = fourthQuarterEndDate;

		}
		return quarterEndDate;

	}

	public static java.util.Date getPreviousQuarterEndDate(
			java.util.Date theDate) {

		if (theDate == null)
			throw new RuntimeException(
					"Date should not be null in getQuarterEndDate");

		int currentYear = getYear(theDate);
		java.util.Date firstQuarterEndDate = getDate("31 Mar " + currentYear);
		java.util.Date secondQuarterEndDate = getDate("30 Jun " + currentYear);
		java.util.Date thirdQuarterEndDate = getDate("30 Sep " + currentYear);
		java.util.Date fourthQuarterEndDate = getDate("31 Dec " + currentYear);

		java.util.Date quarterEndDate = null;
		// System.out.println(theDate+"^^^^^555^^^^^^^^^^^^==="+secondQuarterEndDate);

		if (theDate.compareTo(firstQuarterEndDate) <= 0) {
			quarterEndDate = fourthQuarterEndDate;
			System.out.println("^^^^^^^1^^^^^^^^^^===" + quarterEndDate);

		} else if (theDate.compareTo(firstQuarterEndDate) > 0
				&& theDate.compareTo(secondQuarterEndDate) < 0) {
			quarterEndDate = firstQuarterEndDate;
			System.out.println("^^^^^^^^2^^^^^^^^^===" + quarterEndDate);

		} else if (theDate.compareTo(secondQuarterEndDate) > 0
				&& theDate.compareTo(thirdQuarterEndDate) < 0) {
			quarterEndDate = secondQuarterEndDate;
			System.out.println("^^^^^^^3^^^^^^^^^^===" + quarterEndDate);

		} else if (theDate.compareTo(thirdQuarterEndDate) > 0
				&& theDate.compareTo(fourthQuarterEndDate) <= 0) {
			quarterEndDate = thirdQuarterEndDate;
			System.out.println("^^^^^^^^^^4^^^^^^^===" + quarterEndDate);

		}
		System.out.println(theDate
				+ "^^^^^previousgetQuarterEndDate^^^^^^^^^^^^==="
				+ quarterEndDate);
		return quarterEndDate;

	}

	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());

	}

	public static String current_time(DateUtil DateUtlities) {
		return DateUtil.now("HH:mm:ss aaa");
	}

	public static int lastDayOfMonth(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	public static String format(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String getMonthEndDate(String EnteredDate) {
		Calendar accountOpenDate = DateUtil.toCalendar(EnteredDate);

		int currentYear = accountOpenDate.get(Calendar.YEAR);
		int curentMonth = accountOpenDate.get(Calendar.MONTH);
		int monthEndDay = accountOpenDate
				.getActualMaximum(Calendar.DAY_OF_MONTH);

		Calendar c = Calendar.getInstance();

		c.set(currentYear, curentMonth, monthEndDay);
		String returnDate = DateUtil.getMonFormat(c.getTime());
		return returnDate;
	}

	public static String getMonthFirstDate(String EnteredDate) {
		Calendar accountOpenDate = DateUtil.toCalendar(EnteredDate);

		int currentYear = accountOpenDate.get(Calendar.YEAR);
		int curentMonth = accountOpenDate.get(Calendar.MONTH);

		Calendar c = Calendar.getInstance();

		c.set(currentYear, curentMonth, 1);
		String returnDate = DateUtil.getMonFormat(c.getTime());
		return returnDate;
	}

	public static java.util.Date getMonthEndDate(java.util.Date EnteredDate) {
		Calendar accountOpenDate = DateUtil.toCalendar(EnteredDate);

		int currentYear = accountOpenDate.get(Calendar.YEAR);
		int curentMonth = accountOpenDate.get(Calendar.MONTH);
		int monthEndDay = accountOpenDate
				.getActualMaximum(Calendar.DAY_OF_MONTH);

		Calendar c = Calendar.getInstance();

		c.set(currentYear, curentMonth, monthEndDay);
		System.out.println("Month End Date :::::::;" + c.getTime());
		return c.getTime();
	}

	public static java.util.Date getNextMonthEndDate(java.util.Date EnteredDate) {
		// getMonthEndDate(EnteredDate);

		java.util.Date currentMonthEndDate = getMonthEndDate(EnteredDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentMonthEndDate);
		cal.add(Calendar.DATE, 1);
		Calendar accountOpenDate = DateUtil.toCalendar(cal.getTime());

		int currentYear = accountOpenDate.get(Calendar.YEAR);
		int curentMonth = accountOpenDate.get(Calendar.MONTH);
		int monthEndDay = accountOpenDate
				.getActualMaximum(Calendar.DAY_OF_MONTH);

		Calendar c = Calendar.getInstance();
		c.set(currentYear, curentMonth, monthEndDay);
		return c.getTime();
	}

	public static int getMonthValue(String strDate) throws ParseException {
		DateFormat formatter;
		java.util.Date date;
		formatter = new SimpleDateFormat("dd MMM yyyy");
		date = (java.util.Date) formatter.parse(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static java.util.Date addByNoOfDays(java.util.Date date, int days)
			throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	public static double DifferenceInYears(java.util.Date date1,
			java.util.Date date2) {
		double days = DifferenceInDays(date1, date2);
		System.out.println("The DAYS ARE   " + days);
		return days / 365.2425;
	}

	public static double DifferenceInDays(java.util.Date date1,
			java.util.Date date2) {
		return DifferenceInHours(date1, date2) / 24.0;
	}

	public static double DifferenceInHours(java.util.Date date1,
			java.util.Date date2) {
		return DifferenceInMinutes(date1, date2) / 60.0;
	}

	public static double DifferenceInMinutes(java.util.Date date1,
			java.util.Date date2) {
		return DifferenceInSeconds(date1, date2) / 60.0;
	}

	public static double DifferenceInSeconds(java.util.Date date1,
			java.util.Date date2) {
		return DifferenceInMilliseconds(date1, date2) / 1000.0;
	}

	private static double DifferenceInMilliseconds(java.util.Date date1,
			java.util.Date date2) {
		return Math.abs(GetTimeInMilliseconds(date1)
				- GetTimeInMilliseconds(date2));
	}

	private static long GetTimeInMilliseconds(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getTimeInMillis()
				+ cal.getTimeZone().getOffset(cal.getTimeInMillis());
	}

	public static String getStringDateFromAnyFormat(String date, String format) {
		String formatedDate = date;
		DateFormat df = new SimpleDateFormat(format);
		java.util.Date today = getFormattedDate(date);
		formatedDate = df.format(today);
		return formatedDate;
	}

	
	 * public static void main(String[] args) { //System.out.println(new
	 * com.vsoftcorp.time.Date().now().toString("ddMMyyyy"));
	 * //System.out.println(new
	 * com.vsoftcorp.time.Second().now().toString("HHmmss"));
	 * 
	 * long xyz = -123;
	 * 
	 * if (String.valueOf(xyz).startsWith("-")) { System.out.println(xyz * -1);
	 * } }
	 

	public static void main(String arg[]) {
		System.out.println("**********  " + getVsoftDateByString("2/12/2014"));
		System.out.println("**********  "
				+ getStringDateFromAnyFormat("02/12/2014", "dd MMM yyyy"));
		System.out.println("vsoft date " + DateUtil.getVsoftDateByString("15 Feb 2014"));
	}

}
*/