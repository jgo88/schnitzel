/**
 * (c) iteratec GmbH 2012
 */
package de.iteratec.schnitzel.server.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This listener will be run if the servlet container starts and does some basic setup tasks.
 * 
 * @author Martin Schacherl
 */
public class StartupListener implements ServletContextListener {

	// logger
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//This autowiring needs to be performed to set the @Value-annotated fields.
		final WebApplicationContext springCtx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		springCtx.getAutowireCapableBeanFactory().autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

		setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
		setProperty("faces.PROJECT_STAGE", "development");

		initializeSlf4jJavaUtilLoggingBridge();
	}

	private static void setProperty(String key, String value) {
		System.setProperty(key, value);
		LOGGER.info("Set system property '{}' to '{}'", key, value);
	}

	/**
	 * Removes java.util.logging handlers and installs the SLF4JBridgeHandler, which bridges j.u.l logging to SLF4J.
	 */
	private void initializeSlf4jJavaUtilLoggingBridge() {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
}
