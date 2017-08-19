package com.vsoftcorp.kls.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class StartupListener implements ServletContextListener {

	private static final Logger logger = Logger
			.getLogger(StartupListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent theServletContext) {
	}

	@Override
	public void contextInitialized(ServletContextEvent theServletContext) {
		logger.info("listener started");
		KLSServer.init("klsPersistenceUnit");
	}

}
