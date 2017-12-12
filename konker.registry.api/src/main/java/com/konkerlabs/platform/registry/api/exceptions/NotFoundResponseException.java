package com.konkerlabs.platform.registry.api.exceptions;

import java.util.Locale;
import java.util.Map;

import com.konkerlabs.platform.registry.business.services.api.ServiceResponse;
import com.konkerlabs.platform.registry.business.model.OauthClientDetails;

public class NotFoundResponseException extends Exception {

    private static final long serialVersionUID = 7053566393654994274L;

    private Map<String, Object[]> responseMessages;

    public NotFoundResponseException(ServiceResponse<?> serviceResponse) {
        this.responseMessages = serviceResponse != null ? serviceResponse.getResponseMessages() : null;
    }

    public Map<String, Object[]> getResponseMessages() {
        return responseMessages;
    }

}
