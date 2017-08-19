package com.vsoftcorp.kls.service.impl;
/**
 * @author a1565
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Document;
import com.vsoftcorp.kls.data.DocumentData;
import com.vsoftcorp.kls.dataaccess.IDocumentDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IDocumentService;
import com.vsoftcorp.kls.service.helper.DocumentHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class DocumentService implements IDocumentService{

	private static final Logger logger = Logger.getLogger(DocumentService.class);

	@Override
	public void saveDocument(DocumentData data) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				logger.info("Start:Calling saveDocument() method in DocumentService.. ");
				IDocumentDAO dao=KLSDataAccessFactory.getDocumentDAO();
				Document master=DocumentHelper.getDocument(data);
				try{
					if(master!=null&&data.getId()==null)
					dao.saveDocument(master);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error("Unable to save Document data  in saveDocument() method..");
					throw new DataAccessException("Unable to save Document data", e.getCause());
				}
				logger.info("End:Successfully completed saving Document data in db");
	}

	@Override
	public void updateDocument(DocumentData data) {
		// TODO Auto-generated method stub
		logger.info("Start:Calling updateDocument() method in DocumentService.. ");
		IDocumentDAO dao=KLSDataAccessFactory.getDocumentDAO();
		Document master=DocumentHelper.getDocument(data);
		try{
			Integer id=master.getId();
			if(master!=null&&id!=null)
			dao.updateDocument(master);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Unable to update Document data ..in updateDocument() method..");
		throw new DataAccessException("Unable to updae Document data", e.getCause());
		}
		logger.info("End:Successfully completed updating Document data in db");
	}

	@Override
	public List<DocumentData> getAllDocumentList() {
		// TODO Auto-generated method stub
		logger.info("Start:Calling getAllDocument() method in DocumentService.. ");
		IDocumentDAO dao=KLSDataAccessFactory.getDocumentDAO();
		List<DocumentData> data= new ArrayList<DocumentData>();
		List<Document> master=null;
		try
		{
			master=dao.getAllDocument();
			for (Document Document : master) {
				data.add(DocumentHelper.getDocumentData(Document));

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to get Document data in getAllDocument() method..");
			throw new DataAccessException("Unable to get Document data", e.getCause());
		}
		logger.info("End:Successfully completed getting all Document data from db");

		return data;
	}

	@Override
	public DocumentData getDocumentById(Integer id) {
		logger.info("Start:Calling getAllDocument() method in DocumentService.. ");
		IDocumentDAO dao=KLSDataAccessFactory.getDocumentDAO();
		DocumentData data= new DocumentData();
		Document master=null;
		try
		{
			master=dao.getDocumentById(id);
			data = DocumentHelper.getDocumentData(master);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to get Document data in getAllDocument() method..");
			throw new DataAccessException("Unable to get Document data", e.getCause());
		}
		logger.info("End:Successfully completed getting all Document data from db");

		return data;
	}

	

}
