package com.vsoftcorp.kls.loans.gl.service.impl;

import java.util.ArrayList;
import java.util.List;

public class TTUMFormat {
	
	private static List<TTUMField> fields = new ArrayList();
	private static TTUMFormat _TTUMFormat = new TTUMFormat();
	
	private TTUMFormat(){
		fields.add( new TTUMField ("ACCOUNT_NUMBER",16,1,16,'L',true," ") );
		fields.add( new TTUMField ("CURRENCY_CODE_OF_ACCOUNT_NUMBER",3,17,19,'L',true,"INR") );
		fields.add( new TTUMField ("SERVICE_OUTLET",8,20,27,'L',true," ") );
		fields.add( new TTUMField ("PART_TRAN_TYPE",1,28,28,'N',false," ") );
		fields.add( new TTUMField ("TRANSACTION_AMOUNT",17,29,45,'R',true," ") );
		fields.add( new TTUMField ("TRANSACTION_PARTICULARS",30,46,75,'L',false," ") );
		fields.add( new TTUMField ("ACCOUNT_REPORT_CODE",5,76,80,'L',false," ") );
		fields.add( new TTUMField ("REFERENCE_NUMBER",20,81,100,'L',false," ") );
		fields.add( new TTUMField ("INSTRUMENT_TYPE",5,101,105,'L',false," ") );		
		fields.add( new TTUMField ("INSTRUMENT_DATE",10,106,115,'L',false," ") ); //MANDATORY IF INSTRUMENT TYPE IS PRESENT
		fields.add( new TTUMField ("INSTRUMENT_ALPHA",6,116,121,'L',false," ") );
		fields.add( new TTUMField ("ACTUAL_INSTRUMENT_NUMBER",16,122,137,'R',false," ") );
		fields.add( new TTUMField ("NAVIGATION_FLAG_FOR_HO_TRANS",1,138,138,'N',false," ") );		
		fields.add( new TTUMField ("REFERENCE_AMOUNT",17,139,155,'R',true," ") );		
		fields.add( new TTUMField ("REFERENCE_CURRENCY_CODE",3,156,158,'L',true,"INR") );
		fields.add( new TTUMField ("RATE_CODE",5,159,163,'L',true,"00000") );
		fields.add( new TTUMField ("RATE",15,164,178,'R',false," ") );
		fields.add( new TTUMField ("VALUE_DATE",10,179,188,'L',false," ") );
		fields.add( new TTUMField ("CATEGORY_CODE",5,189,193,'L',false," ") );
		fields.add( new TTUMField ("TO_FROM_BANK_CODE",6,194,199,'L',false," ") );		
		fields.add( new TTUMField ("TO_FROM_BRANCH_CODE",6,200,205,'L',false," ") );		
		fields.add( new TTUMField ("ADVC_EXTN_CNTR_CODE",2,206,207,'L',false," ") );
		fields.add( new TTUMField ("BAR_ADVC_GEN_IND",1,208,208,'N',false," ") );
		fields.add( new TTUMField ("BAR_OR_ADVC_NUM",12,209,220,'L',false," ") );		
		fields.add( new TTUMField ("BAR_OR_ADVC_DATE",10,221,230,'L',false," ") );
		fields.add( new TTUMField ("BILL_NO",20,231,250,'L',false," ") );
		fields.add( new TTUMField ("HDR_TEXT_CODE",5,251,255,'L',false," ") );
		fields.add( new TTUMField ("HDR_FREE_TEXT",30,256,285,'L',false," ") );
		fields.add( new TTUMField ("PARTICULARS1",40,286,325,'L',false," ") );
		fields.add( new TTUMField ("PARTICULARS_2",40,326,365,'L',false," ") );
		fields.add( new TTUMField ("PARTICULARS_3",40,366,405,'L',false," ") );
		fields.add( new TTUMField ("PARTICULARS_4",40,406,445,'L',false," ") );		
		fields.add( new TTUMField ("PARTICULARS_5",40,446,485,'L',false," ") );
		fields.add( new TTUMField ("AMT_LINE_1",17,486,502,'R',false," ") );		
		fields.add( new TTUMField ("AMT_LINE_2",17,503,519,'R',false," ") );
		fields.add( new TTUMField ("AMT_LINE_3",17,520,536,'R',false," ") );
		fields.add( new TTUMField ("AMT_LINE_4",17,537,553,'R',false," ") );
		fields.add( new TTUMField ("AMT_LINE_5",17,554,570,'R',false," ") );
		fields.add( new TTUMField ("REMARKS",30,571,600,'L',false," ") );
		fields.add( new TTUMField ("PAYEE_AC",16,601,616,'L',false," ") );
		fields.add( new TTUMField ("RCVD_BAR_OR_ADVC_NUM",12,617,628,'L',false," ") );
		fields.add( new TTUMField ("RCVD_BAR_OR_ADVC_DATE",10,629,638,'L',false," ") );
		fields.add( new TTUMField ("ORIGINAL_TRAN_DATE",10,639,648,'L',false," ") );
		fields.add( new TTUMField ("ORIGINAL_TRAN_ID",9,649,657,'R',false," ") );
		fields.add( new TTUMField ("ORIGINAL_PART_TRAN_SRL_NO",4,658,661,'R',false," ") );
		fields.add( new TTUMField ("FREE_TEXT",256,662,917,'L',false," ") );
		fields.add( new TTUMField ("ENTITY_ID",16,918,933,'L',false," ") );
		fields.add( new TTUMField ("ENTITY_TYPE",5,934,938,'L',false," ") );
		fields.add( new TTUMField ("FLOW_CODE",5,939,943,'L',false," ") ); 
	}
	
	public static TTUMFormat getTTUMFormat(){
		return _TTUMFormat;
	}

	public static List<TTUMField> getFields() {
		return fields;
	}

	public static void setFields(List<TTUMField> fields) {
		TTUMFormat.fields = fields;
	}
	
	

}
