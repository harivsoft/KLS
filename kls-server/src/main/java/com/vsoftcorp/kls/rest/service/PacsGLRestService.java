package com.vsoftcorp.kls.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.data.PacsData;
import com.vsoftcorp.kls.service.IPacsService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;
/**
 * 
 * @author a1565
 *
 */
@Path("/pacsgl")
public class PacsGLRestService {

	private static final Logger logger = Logger.getLogger(PacsGLRestService.class);

	public PacsGLRestService() {
		// TODO Auto-generated constructor stub
	}

	private static final PacsGLRestService INSTANCE = new PacsGLRestService();
	
	public static PacsGLRestService getInstance() {
		// TODO Auto-generated method stub
		return INSTANCE;
	}
	
	
	@GET
	@Path("/getpacsdatabypacsid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsDataByPacsId(@QueryParam("pacsId") Integer pacsId) {

		logger.info("Start : Calling PacsGLRestService in getPacsDataByPacsId() ");
		PacsData pacsData = new PacsData();
		String jsonAllPacs = "";
		try {
			IPacsService service = KLSServiceFactory.getPacsService();
			pacsData = service.getPacsByPacId(pacsId);
			Gson gson = new GsonBuilder().create();
			jsonAllPacs = gson.toJson(pacsData);
		} catch (Exception e) {
			logger.error("Error While Getting Pac Data by Pac Id from db..");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling PacsGLRestService.getPacsDataByPacsId() ");
		return jsonAllPacs;
	}
}
