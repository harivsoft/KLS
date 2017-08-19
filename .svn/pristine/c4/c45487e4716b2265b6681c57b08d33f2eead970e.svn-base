package com.vsoftcorp.kls.dataaccess.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.dataaccess.IKLSOmniDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.ResultSetConvertor;

public class KLSOmniDAO implements IKLSOmniDAO {
	
	private static final Logger logger = Logger.getLogger(KLSOmniDAO.class);

	@Override
	public String getData(String query) {
		logger.info("Start: Fetching Data for Omni based on query parameter");
		logger.info("Generated query based on parameter is: "+query);
		String jsonData=null;
		try {
			Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/jtaDataSourcePacs");
            Connection conn = ds.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            jsonData=ResultSetConvertor.convertResultSetIntoJSON(rs).toString();
		} catch (Exception e) {
			logger.error("Error while retriving data from the database in getData() of KLSOmniDAO");
			throw new DataAccessException("Error while retriving data "
					+ " from the database in getData() of KLSOmniDAO", e.getCause());
		}
		logger.info("End: Fetching Data for Omni based on query parameter");
		return jsonData;
	}

}
