/*
 * Copyrights and Licenses
 * 
 * Copyright (c) 2015-2016 by the Ministry of Justice. All rights reserved. Redistribution and use
 * in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer. - Redistributions in binary form
 * must reproduce the above copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the distribution. - Products derived
 * from this software may not be called "XHIBIT Public Display Manager" nor may
 * "XHIBIT Public Display Manager" appear in their names without prior written permission of the
 * Ministry of Justice. - Redistributions of any form whatsoever must retain the following
 * acknowledgment: "This product includes XHIBIT Public Display Manager." This software is provided
 * "as is" and any expressed or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are disclaimed. In no event
 * shall the Ministry of Justice or its contributors be liable for any direct, indirect, incidental,
 * special, exemplary, or consequential damages (including, but not limited to, procurement of
 * substitute goods or services; loss of use, data, or profits; or business interruption). However
 * caused any on any theory of liability, whether in contract, strict liability, or tort (including
 * negligence or otherwise) arising in any way out of the use of this software, even if advised of
 * the possibility of such damage.
 */

package uk.gov.hmcts.pdm.publicdisplay.common.rest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.RestException;
import uk.gov.hmcts.pdm.publicdisplay.common.json.ErrorJson;
import uk.gov.hmcts.pdm.publicdisplay.common.util.api.IObjectMapperUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;



/**
 * The Class JsonRequest.
 *
 * @author boparaij
 */
@SuppressWarnings("PMD.LawOfDemeter")
public class JsonRequest {

    /** The status value for success. */
    public static final String STATUS_SUCCESS = "SUCCESS";

    /** The status value for failure. */
    public static final String STATUS_FAILED = "FAILED";

    public static final String REQUEST_URL = "Request url {}";

    public static final String UTF8 = "UTF-8";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonRequest.class);

    /** The default timeout in seconds for socket, connect & request. */
    private static final Integer DEFAULT_TIMEOUT = 30;

    /** The default expiry in seconds for json web token. */
    private static final Integer DEFAULT_EXPIRY = 300;

    /** The object mapper util. */
    private final IObjectMapperUtil objectMapperUtil;

    private static final JsonWebTokenUtility JSWTUINSTANCE = JsonWebTokenUtility.INSTANCE;

    private static final String BEARERHEADER = JsonWebTokenUtility.INSTANCE.REQUEST_HEADER_BEARER;

    private static final TimeUnit SECONDS = TimeUnit.SECONDS;
    
    private static final String REPLACE_CHARS = "[\n\r]";
    
    private static final String UNDERSCORE = "_";

    /** The timeout in seconds for socket, connect & request. */
    private Integer timeout = DEFAULT_TIMEOUT;

    /** The expiry in seconds for json web token. */
    private Integer expiry = DEFAULT_EXPIRY;

    /** The token type. */
    private JsonWebTokenType tokenType;

    /** The url. */
    private String url;

    /** The message id. */
    private String messageId;

    /** The request text. */
    private String requestText;

    /** The response text. */
    private String responseText;

    /** The status (SUCCESS or FAILED). */
    private String status;

    /**
     * Instantiates a new json request.
     *
     * @param objectMapperUtil the object mapper util
     */
    public JsonRequest(final IObjectMapperUtil objectMapperUtil) {
        this.objectMapperUtil = objectMapperUtil;
    }

    /**
     * Gets the timeout.
     * 
     * @return the timeout
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * Sets the timeout.
     * 
     * @param timeout the timeout to set
     */
    public void setTimeout(final Integer timeout) {
        this.timeout = timeout;
    }

    /**
     * getExpiry.
     * 
     * @return the expiry
     */
    public Integer getExpiry() {
        return expiry;
    }

    /**
     * setExpiry.
     * 
     * @param expiry the expiry to set
     */
    public void setExpiry(final Integer expiry) {
        this.expiry = expiry;
    }

    /**
     * getTokenType.
     * 
     * @return the token type
     */
    public JsonWebTokenType getTokenType() {
        return tokenType;
    }

    /**
     * setTokenType.
     * 
     * @param tokenType the token type to set
     */
    public void setTokenType(final JsonWebTokenType tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * getMessageId.
     * 
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * getRequestText.
     * 
     * @return the request text
     */
    public String getRequestText() {
        return requestText;
    }

    /**
     * getResponseText.
     * 
     * @return the response text
     */
    public String getResponseText() {
        return responseText;
    }

    /**
     * getStatus.
     * 
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Send get request and return no response.
     *
     * @throws RestException the rest exception
     */
    public void sendRequest() {
        LOGGER.info(REQUEST_URL, url.replaceAll(REPLACE_CHARS, UNDERSCORE));

        // Create get object from url
        final HttpGet httpGet = createHttpGet();

        // Send request and return no response
        sendRequest(httpGet, Void.class);
    }

    /**
     * Send get request and return required response.
     *
     * @param <T> the type of response object
     * @param responseType the response type
     * @return the response
     * @throws RestException the rest exception
     */
    public <T> T sendRequest(final Class<T> responseType) {
        LOGGER.info(REQUEST_URL, url);

        // Create get object from url
        final HttpGet httpGet = createHttpGet();

        // Send request and return response
        return sendRequest(httpGet, responseType);
    }

    /**
     * Send post request with the body being the supplied request which is either a json string or
     * an object to be converted to a json string and return no response.
     *
     * @param request json string or an object to convert to json
     * @throws RestException the rest exception
     */
    public void sendRequest(final Object request) {
        LOGGER.info(REQUEST_URL, url.replaceAll(REPLACE_CHARS, UNDERSCORE));

        // Create post object from request and url
        final HttpPost httpPost = createHttpPost(request);

        // Send request and return no response
        sendRequest(httpPost, Void.class);
    }

    /**
     * Send post request with the body being the supplied request which is either a json string or
     * an object to be converted to a json string and return required response.
     *
     * @param <T> the type of response object
     * @param request json string or an object to convert to json
     * @param responseType the response type
     * @return the response
     * @throws RestException the rest exception
     */
    public <T> T sendRequest(final Object request, final Class<T> responseType) {
        LOGGER.info(REQUEST_URL, url.replaceAll(REPLACE_CHARS, UNDERSCORE));

        // Create post object from request and url
        final HttpPost httpPost = createHttpPost(request);

        // Send request and return response
        return sendRequest(httpPost, responseType);
    }

    /**
     * Send request and return required response.
     *
     * @param <T> the type of response object
     * @param request the request
     * @param responseType the response type
     * @return the response
     * @throws RestException the rest exception
     */
    private <T> T sendRequest(final HttpUriRequest request, final Class<T> responseType) {
        LOGGER.info("Sending request {}", request.getRequestLine());

        // Initial status is failure until proven otherwise with no response
        setStatus(STATUS_FAILED);

        // Create unique id for message
        this.messageId = UUID.randomUUID().toString();

        // Create request configuration with each timeout set in milliseconds
        final int timeoutMillis = (int) SECONDS.toMillis(timeout);
        final RequestConfig config = RequestConfig.custom().setSocketTimeout(timeoutMillis)
            .setConnectTimeout(timeoutMillis).setConnectionRequestTimeout(timeoutMillis).build();

        // Create json web token with the expiration set to current date plus expiry
        final String token =
            BEARERHEADER + " " + JSWTUINSTANCE.generateToken(tokenType, messageId, expiry);
        request.addHeader(JsonWebTokenUtility.REQUEST_HEADER_AUTHORIZATION, token);

        // Create http client using the above request configuration
        try (CloseableHttpClient httpClient =
            HttpClients.custom().setDefaultRequestConfig(config).build();) {
            // Send http request and process response via handler
            final T response = httpClient.execute(request, new JsonResponseHandler<>(responseType));

            // Request was successful if reached here as no exception thrown
            setStatus(STATUS_SUCCESS);

            // Return response in required type
            return response;
        } catch (final HttpResponseException ex) {
            responseText = ExceptionUtils.getStackTrace(ex);
            throw new RestException(ex.getMessage(), ex);
        } catch (final IOException ex) {
            responseText = ExceptionUtils.getStackTrace(ex);
            throw new RestException(
                "Error occurred sending request to " + request.getURI().getHost(), ex);
        }
    }

    /**
     * Creates http get from url.
     *
     * @return the http get
     */
    private HttpGet createHttpGet() {

        // Create get object from url
        return new HttpGet(url);
    }

    /**
     * Creates http post from request.
     *
     * @param request the request
     * @return the http post
     */
    private HttpPost createHttpPost(final Object request) {
        // Create json request body from the request object which is:
        // 1) json string OR
        // 2) object to convert to json string
        requestText = "";
        if (request != null && String.class.isAssignableFrom(request.getClass())) {
            requestText = (String) request;
        } else if (request != null) {
            requestText = objectMapperUtil.getJsonString(request);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request body {}", requestText.replaceAll(REPLACE_CHARS, UNDERSCORE));
        }

        // Create post object from request body
        final HttpPost httpPost = new HttpPost(url);
        final StringEntity entity =
            new StringEntity(requestText, ContentType.create("application/json", UTF8));
        httpPost.setEntity(entity);

        return httpPost;
    }

    /**
     * The Class JsonResponseHandler which converts the response to the required response type or
     * throws an exception if there was an error response.
     * 
     * @param <T> the response type
     */
    private final class JsonResponseHandler<T> implements ResponseHandler<T> {

        /** The response type. */
        private final Class<T> responseType;

        /**
         * Instantiates a new json response handler for the required response type.
         *
         * @param responseType the response type
         */
        private JsonResponseHandler(final Class<T> responseType) {
            this.responseType = responseType;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
         */
        @Override
        public T handleResponse(final HttpResponse httpResponse)
            throws HttpResponseException, IOException {
            // Get the status and body of the response
            final StatusLine statusLine = httpResponse.getStatusLine();
            final int statusCode = statusLine.getStatusCode();
            final HttpEntity entity = httpResponse.getEntity();
            LOGGER.info("Received response {} from url {}", statusLine, url);

            // 2xx status codes are successful responses
            if (statusCode >= HttpStatus.SC_OK && statusCode <= HttpStatus.SC_MULTIPLE_CHOICES) {
                // Return the required response
                return convertResponse(entity);

                // 500 status code is an error response
            } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                // Try to get the error message from the response
                final String errorMessage = getErrorMessage(entity);

                // If found an error in response, throw rest exception with message
                if (StringUtils.isNotBlank(errorMessage)) {
                    throw new RestException(errorMessage);

                    // Else throw generic response exception with the response reason
                } else {
                    throw new HttpResponseException(statusCode, statusLine.getReasonPhrase());
                }

                // All other status codes generate an exception with the response reason
            } else {
                throw new HttpResponseException(statusCode, statusLine.getReasonPhrase());
            }
        }

        /**
         * Convert json response into the required response type when the response is not null and
         * when a response is actually expected.
         *
         * @param entity the entity
         * @return the response
         * @throws IOException Signals that an I/O exception has occurred.
         */
        private T convertResponse(final HttpEntity entity) throws IOException {
            T response = null;
            // If a response has been returned and a response is expected for request
            if (entity != null && !Void.class.isAssignableFrom(responseType)) {
                // If the bytes of the response are required, e.g. file
                if (byte[].class.isAssignableFrom(responseType)) {
                    response = responseType.cast(EntityUtils.toByteArray(entity));

                    // Else the response is to be treated as a string, e.g. json
                } else {
                    // Retrieve the response as a string and return that if required
                    // or convert the json response to the required response type
                    responseText = EntityUtils.toString(entity, UTF8);
                    if (String.class.isAssignableFrom(responseType)) {
                        response = responseType.cast(responseText);
                    } else {
                        response = objectMapperUtil.getJsonObject(responseText, responseType);
                    }
                }
            }
            return response;
        }

        /**
         * Attempt to retrieve the error message from the response but it could fail if the error
         * prevented the server returning json so ensure all exceptions are caught so the real error
         * is not lost.
         *
         * @param entity the entity
         * @return the error message
         */
        private String getErrorMessage(final HttpEntity entity) {
            String errorMessage = null;
            if (entity != null) {
                try {
                    responseText = EntityUtils.toString(entity, UTF8);
                    if (StringUtils.isNotBlank(responseText)) {
                        final ErrorJson error =
                            objectMapperUtil.getJsonObject(responseText, ErrorJson.class);
                        if (StringUtils.isNotBlank(error.getMessage())) {
                            errorMessage = error.getMessage();
                            LOGGER.error(error.getMessage());
                            LOGGER.error(error.getDetail());
                        }
                    }
                } catch (final ParseException | IOException | RestException ex) {
                    LOGGER.warn("Exception occurred getting error from response", ex);
                }
            }
            return errorMessage;
        }
    }
}
