package eu.daiad.web.controller;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.validation.FieldError;

import com.ibm.icu.text.MessageFormat;

import eu.daiad.web.model.RestResponse;
import eu.daiad.web.model.error.ApplicationException;
import eu.daiad.web.model.error.Error;
import eu.daiad.web.model.error.ErrorCode;
import eu.daiad.web.model.error.SharedErrorCode;

public abstract class BaseController {

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	private Environment environment;

	protected String getMessage(String code) {
		return messageSource.getMessage(code, null, code, null);
	}

	protected String getMessage(String code, Map<String, Object> properties) {
		String message = messageSource.getMessage(code, null, code, null);

		MessageFormat msgFmt = new MessageFormat(message);

		return msgFmt.format(properties);
	}

	protected String getMessage(ApplicationException ex) {
		return this.getMessage(ex.getCode().getMessageKey(), ex.getProperties());
	}

	protected String getMessage(ErrorCode error) {
		return this.getMessage(error.getMessageKey());
	}

	protected String getMessage(ErrorCode error, Map<String, Object> properties) {
		return this.getMessage(error.getMessageKey(), properties);
	}

	protected Error getError(FieldError error) {
		String code = error.getCodes()[0];

		return new Error(code, this.getMessage(code));
	}

	protected Error getError(ErrorCode error) {
		return new Error(error.getMessageKey(), this.getMessage(error));
	}

	protected Error getError(ErrorCode error, Map<String, Object> properties) {
		return new Error(error.getMessageKey(), this.getMessage(error, properties));
	}

	protected Error getError(Throwable t) {
		return this.getError((Exception) t.getCause());
	}

	protected Error getError(Exception ex) {
		if (ex instanceof ApplicationException) {
			ApplicationException applicationException = (ApplicationException) ex;

			return new Error(applicationException.getCode().getMessageKey(), this.getMessage(applicationException));
		}
		return new Error(SharedErrorCode.UNKNOWN.getMessageKey(), this.getMessage(SharedErrorCode.UNKNOWN
						.getMessageKey()));
	}

	protected RestResponse createResponse(ErrorCode error) {
		RestResponse response = new RestResponse();

		response.add(this.getError(error));

		return response;
	}

	protected RestResponse createResponse(ErrorCode error, AbstractMap.SimpleEntry<String, Object>[] entries) {
		RestResponse response = new RestResponse();

		Map<String, Object> properties = new HashMap<String, Object>();
		for (AbstractMap.SimpleEntry<String, Object> entry : entries) {
			properties.put(entry.getKey(), entry.getValue());
		}

		response.add(this.getError(error, properties));

		return response;
	}

	protected RestResponse createResponse(ErrorCode error, Map<String, Object> properties) {
		RestResponse response = new RestResponse();

		response.add(this.getError(error, properties));

		return response;
	}

	protected eu.daiad.web.model.Runtime getRuntime() {
		return new eu.daiad.web.model.Runtime(this.getActiveProfiles());
	}

	protected String[] getActiveProfiles() {
		return environment.getActiveProfiles();
	}

	protected ApplicationException createApplicationException(ErrorCode code) {
		String pattern = messageSource.getMessage(code.getMessageKey(), null, code.getMessageKey(), null);

		return ApplicationException.create(code, pattern);
	}

	protected ApplicationException wrapApplicationException(Exception ex, ErrorCode code) {
		String pattern = messageSource.getMessage(code.getMessageKey(), null, code.getMessageKey(), null);

		return ApplicationException.wrap(ex, code, pattern);
	}
}
