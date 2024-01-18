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

package uk.gov.hmcts.pdm.publicdisplay.common.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


/**
 * HTTP Request handler implementation used to mock JSON endpoint.
 * 
 * @author uphillj
 *
 */
public class MockJsonEndpoint implements HttpRequestHandler {

    /** The Constant MAPPER. */
    private static final ObjectMapper MAPPER;

    /**
     * Initialise the single ObjectMapper instance.
     */
    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /** The request headers. */
    private final ConcurrentHashMap<String, String> requestHeaders = new ConcurrentHashMap<>();

    /** The request body. */
    private String requestBody = "";

    /** The response body. */
    private Object responseBody;

    /** The response code. */
    private int responseCode;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.http.protocol.HttpRequestHandler#handle(org.apache.http.HttpRequest,
     * org.apache.http.HttpResponse, org.apache.http.protocol.HttpContext)
     */
    @Override
    public void handle(final HttpRequest request, final HttpResponse response,
        final HttpContext context) throws HttpException, IOException {
        // Get request headers
        requestHeaders.clear();
        for (Header header : request.getAllHeaders()) {
            final String headerName = header.getName();
            requestHeaders.put(headerName, header.getValue());
        }

        // Get request body if one was posted or reset
        requestBody = "";
        if (request instanceof HttpEntityEnclosingRequest) {
            final HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            if (entity != null) {
                requestBody = EntityUtils.toString(entity);
            }
        }

        // Set response body if one specified
        if (responseBody != null) {
            // If response body is byte array
            if (responseBody instanceof byte[]) {
                final byte[] responseBytes = (byte[]) responseBody;
                final ByteArrayEntity entity =
                    new ByteArrayEntity(responseBytes, ContentType.APPLICATION_OCTET_STREAM);
                response.setEntity(entity);
            } else if (responseBody instanceof String) {
                final String responseString = (String) responseBody;
                final StringEntity entity = new StringEntity(responseString,
                    ContentType.create("application/json", "UTF-8"));
                response.setEntity(entity);
            }
        }

        // Set response code
        response.setStatusCode(responseCode);
    }

    /**
     * Gets the request header.
     *
     * @param name the name
     * @return the request header
     */
    public String getRequestHeader(final String name) {
        return requestHeaders.get(name);
    }

    /**
     * Gets the request body.
     *
     * @param <T> the generic type
     * @param objectType the object type
     * @return the request
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public <T> T getRequestBody(final Class<T> objectType) throws IOException {
        T value = null;
        if (requestBody.length() > 0) {
            if (String.class.isAssignableFrom(objectType)) {
                value = objectType.cast(requestBody);
            } else {
                value = MAPPER.readValue(requestBody, objectType);
            }
        }
        return value;
    }

    /**
     * Gets the response body.
     *
     * @return the response body
     */
    public Object getResponseBody() {
        return responseBody;
    }

    /**
     * Sets the response body.
     *
     * @param object the new response body
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void setResponseBody(final Object object) throws IOException {
        if (object instanceof byte[]) {
            responseBody = object;
        } else if (object instanceof String) {
            responseBody = object;
        } else {
            responseBody = MAPPER.writeValueAsString(object);
        }
    }

    /**
     * Sets the response code.
     *
     * @param statusCode the new response code
     */
    public void setResponseCode(final int statusCode) {
        responseCode = statusCode;
    }
}
