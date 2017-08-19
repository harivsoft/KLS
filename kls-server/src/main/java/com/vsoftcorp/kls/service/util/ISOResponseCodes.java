package com.vsoftcorp.kls.service.util;

/*
 * @author a9153
 * Response codes for ISO Data element 39. As per ISO 8583 1987 specification
 */

public class ISOResponseCodes {

	public static final String APPROVED = "00";
	public static final String REFER_CARD_ISSUER = "2";
	public static final String INVALID_MERCHANT = "3";
	public static final String DO_NOT_HONOR = "4";
	public static final String UNABLE_TO_PROCESS_INVALID_ACCOUNT = "5";
	public static final String INVALID_TRANSACTION_TERMINAL = "6";
	public static final String ISSUER_TIMEOUT = "8";
	public static final String NO_ORIGINAL = "9";
	public static final String UNABLE_TO_REVERSE = "10";
	public static final String INVALID_TRANSACTION_ACCOUNT_CLOSED = "12";
	public static final String INVALID_AMOUNT = "13";
	public static final String INVALID_CARD = "14";
	public static final String CARD_DOES_NOT_EXIST = "15";
	public static final String INVALID_BUSINESS_DATE = "17";
	public static final String NETWORK_ERROR = "18";
	public static final String SYSTEM_ERROR = "19";
	public static final String NO_FROM_ACCOUNT = "20";
	public static final String NO_TO_ACCOUNT = "21";
	public static final String NO_CURRENT_ACCOUNT = "22";
	public static final String NO_SAVINGS_ACCOUNT = "23";
	public static final String NO_CREDIT_ACCOUNT = "24";
	public static final String UNABLE_TO_LOCATE_RECORD = "25";
	public static final String CARD_NOT_CONFIRM = "26";
	public static final String ACCOUNT_ERROR = "27";
	public static final String MESSAGE_FORMAT_ERROR = "30";
	public static final String INVALID_BIN = "31";
	public static final String SUSPECT_FRAUD = "34";
	public static final String RESTRICTED_CARD = "36";
	public static final String TRANSACTION_NOT_ALLOWED = "39";
	public static final String HOT_CARD = "41";
	public static final String SPECIAL_PICK_UP = "42";
	public static final String HOT_CARD_PICK_UP = "43";
	public static final String PICK_UP_CARD = "44";
	public static final String NO_FUNDS = "51";
	public static final String EXPIRED_CARD = "54";
	public static final String INCORRECT_PIN = "55";
	public static final String NO_CARD_RECORD = "56";
	public static final String TRANSACTION_NOT_PERMITTED_ON_CARD = "57";
	public static final String TRANSACTION_NOT_PERMITTED_ON_TERMINAL = "58";
	public static final String EXCEEDS_LIMIT = "61";
	public static final String RESTRICTED_CARD_ACCOUNT_FREEZED = "62";
	public static final String MAC_KEY_ERROR = "63";
	public static final String EXCEEDS_FREQUENCY_LIMIT = "65";
	public static final String EXCEEDS_ACQUIRER_LIMIT = "66";
	public static final String RETAIN_CARD = "67";
	public static final String LATE_RESPONSE = "68";
	public static final String EXCEEDS_PIN_ENTRIES = "75";
	public static final String INVALID_ACCOUNT = "76";
	public static final String NO_SHARING_ARRANGEMENT = "77";
	public static final String FUNCTION_NOT_AVAILABLE = "78";
	public static final String KEY_VIOLATION_ERROR = "79";
	public static final String INVALID_LIFE_CYCLE_ON_TRANSACTION = "84";
	public static final String PIN_KEY_ERROR = "87";
	public static final String INVALID_AUTHORIZATION_CODE = "88";
	public static final String SECURITY_VIOLATION = "89";
	public static final String HOST_UNAVAILABLE = "91";
	public static final String INVALID_ISSUER_IN_TRANSACTION = "92";
	public static final String INVALID_ACQUIRER_IN_TRANSACTION = "93";
	public static final String DUPLICATE_TRANSACTION = "94";
	public static final String DUPLICATE_REVERSAL = "98";
	public static final String INVALID_LOAN_OPERATIVE_GL="99";
	public static final String INVALID_LOAN_INTEREST_GL="100";
	public static final String INVALID_BANK_CASH_IN_HAND_GL="101";
	public static final String INVALID_BANK_CASH_IN_TRANSIT_GL="102";
	public static final String INVALID_PACS_SB_ACCOUNT="103";
	public static final String INVALID_LOAN_INTEREST_RECEIVED_GL="104";
	public static final String INVALID_LOAN_PENAL_INTEREST_GL="105";
	public static final String INVALID_LOAN_PENAL_INTEREST_RECEIVED_GL="106";

}
