package com.vsoftcorp.kls.util;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.util.EntityManagerUtil;

public class KLSServer {

	private static Logger logger = Logger.getLogger(KLSServer.class);

	private static boolean intialized = false;

	private static KLSServer klsServer = null;

	private KLSServer() {
		if (!intialized) {
			init("klsPersistenceUnit");
		}
	}

	public static void init(String thePersistentUnit) {
		EntityManagerUtil.init(thePersistentUnit);
		intialized = true;
		logger.info("KLS Server is Intialized ..");
	}

	public static KLSServer getInstance() {
		if (klsServer == null) {
			klsServer = new KLSServer();
		}
		return klsServer;
	}

	public static void main(String[] args) {
		System.out.println("testing" + KLSServer.getInstance());
	}

}
