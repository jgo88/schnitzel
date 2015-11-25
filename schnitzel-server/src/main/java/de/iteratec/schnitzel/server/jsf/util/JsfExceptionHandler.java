package de.iteratec.schnitzel.server.jsf.util;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsfExceptionHandler.class);

	private final ExceptionHandler wrapped;

	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			final ExceptionQueuedEvent event = i.next();
			final ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			final Throwable t = context.getException();

			boolean handled = false;
			try {
				handled = handled || handleViewExpiredException(t);
			} finally {
				if (handled) {
					i.remove();
				} else {
					final Throwable rootT = ExceptionUtils.getRootCause(t);
					LOGGER.error(null, rootT); //Just log unhandled exception
				}
			}
		}
		getWrapped().handle(); //Let the parent handle the rest
	}

	private boolean handleViewExpiredException(Throwable e) {
		if ( !(e instanceof ViewExpiredException)) {
			return false;
		}
		final ViewExpiredException vee = (ViewExpiredException) e;

		final FacesContext fc = FacesContext.getCurrentInstance();
		final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		requestMap.put("currentViewId", vee.getViewId());
		nav.handleNavigation(fc, null, "viewExpired"); //Navigate to index
		fc.renderResponse();

		LOGGER.warn(null, vee);
		return true;
	}

}
