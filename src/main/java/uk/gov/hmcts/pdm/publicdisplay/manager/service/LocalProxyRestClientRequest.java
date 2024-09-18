package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.RestException;
import uk.gov.hmcts.pdm.publicdisplay.common.rest.JsonRequest;
import uk.gov.hmcts.pdm.publicdisplay.common.rest.JsonRequestFactory;
import uk.gov.hmcts.pdm.publicdisplay.common.rest.JsonWebTokenType;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IServiceAuditService;

@Component
public class LocalProxyRestClientRequest {

    /** The timeout in seconds for socket, connect & request. */
    @Value("#{applicationConfiguration.restClientTimeout}")
    protected Integer timeout;

    /** The expiry in seconds for json web token. */
    @Value("#{applicationConfiguration.restTokenExpiry}")
    protected Integer expiry;

    /** The json request factory. */
    @Autowired
    private JsonRequestFactory jsonRequestFactory;

    /** The service audit service. */
    @Autowired
    private IServiceAuditService serviceAuditService;

    /** The Constant IPD_PROTOCOL. */
    private static final String IPD_PROTOCOL = "http://";

    /** The Constant IPD_CONTEXT. */
    private static final String IPD_CONTEXT = "/ipdmanager/api";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalProxyRestClientRequest.class);


    /**
     * Creates a json request configured with url, timeout and expiry.
     *
     * @param localProxy the local proxy
     * @param path the path of the url
     * @return the json request
     */
    protected JsonRequest createJsonRequest(final ILocalProxy localProxy, final String path) {
        final JsonRequest jsonRequest = jsonRequestFactory.getJsonRequest();
        jsonRequest.setTokenType(JsonWebTokenType.DISPLAY_MANAGER);
        jsonRequest.setTimeout(timeout);
        jsonRequest.setExpiry(expiry);
        jsonRequest.setUrl(getBaseUrl(localProxy) + path);
        return jsonRequest;
    }

    /**
     * Gets the base url.
     *
     * @param localProxy the local proxy
     * @return the base url
     */
    private String getBaseUrl(final ILocalProxy localProxy) {
        return IPD_PROTOCOL + localProxy.getIpAddress() + IPD_CONTEXT;
    }

    /**
     * Send a request with the body being the supplied request which is either a json string or an
     * object to be converted to a json string and return required response.
     *
     * @param <T> the type of response object
     * @param localProxy the local proxy
     * @param service the service name
     * @param path the service path
     * @param request json string or an object to convert to json
     * @param responseType the response type
     * @return the response
     * @throws RestException the rest exception
     */
    protected <T> T sendRequest(final ILocalProxy localProxy, final String service,
        final String path, final Object request, final Class<T> responseType) {
        LOGGER.debug("sendRequest()");
        final JsonRequest jsonRequest = createJsonRequest(localProxy, path);
        try {
            return jsonRequest.sendRequest(request, responseType);
        } finally {
            this.auditService(service, jsonRequest);
        }
    }

    /**
     * Send a request with the body being the supplied request which is either a json string or an
     * object to be converted to a json string and return no response.
     *
     * @param localProxy the local proxy
     * @param service the service name
     * @param path the service path
     * @param request json string or an object to convert to json
     * @throws RestException the rest exception
     */
    protected void sendRequest(final ILocalProxy localProxy, final String service,
        final String path, final Object request) {
        LOGGER.debug("sendRequest({},{})", localProxy.getIpAddress(), service);
        final JsonRequest jsonRequest = createJsonRequest(localProxy, path);
        try {
            jsonRequest.sendRequest(request);
        } finally {
            this.auditService(service, jsonRequest);
        }
    }

    /**
     * Audit service call which logs any exceptions to prevent rest call being interrupted.
     *
     * @param service the service called
     * @param jsonRequest the json request
     */
    protected void auditService(final String service, final JsonRequest jsonRequest) {
        LOGGER.debug("auditService({})", service);
        try {
            serviceAuditService.auditService(service, jsonRequest);
        } catch (final DataAccessException ex) {
            LOGGER.error("Exception occurred auditing service call", ex);
        }
    }

}
