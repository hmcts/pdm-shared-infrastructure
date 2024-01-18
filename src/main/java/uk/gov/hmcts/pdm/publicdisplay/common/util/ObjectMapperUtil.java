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

package uk.gov.hmcts.pdm.publicdisplay.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.RestException;
import uk.gov.hmcts.pdm.publicdisplay.common.util.api.IObjectMapperUtil;

import java.io.IOException;



/**
 * The ObjectMapper class used for transforming to and from json is thread safe and so can be static
 * and is reused only by the methods within this class.
 * 
 * @author uphillj
 *
 */
@Component
public class ObjectMapperUtil implements IObjectMapperUtil, FactoryBean<ObjectMapper> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperUtil.class);

    /** The Constant MAPPER. */
    private static final ObjectMapper MAPPER;

    /**
     * Initialise the single ObjectMapper instance.
     */
    static {
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JodaModule());
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public ObjectMapper getObject() throws Exception {
        return MAPPER;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<?> getObjectType() {
        return ObjectMapper.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.json.IObjectMapperUtil#getJsonObject(java.
     * lang. String, java.lang.Class)
     */
    @Override
    public <T> T getJsonObject(final String json, final Class<T> objectType) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(json);
            }
            return MAPPER.readValue(json, objectType);
        } catch (final IOException ex) {
            throw new RestException("Exception occurred converting json to object", ex);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.json.IObjectMapperUtil#getJsonString(java.
     * lang. Object)
     */
    @Override
    public String getJsonString(final Object object) {
        try {
            final String json = MAPPER.writeValueAsString(object);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(json);
            }
            return json;
        } catch (final IOException ex) {
            throw new RestException("Exception occurred converting object to json", ex);
        }
    }

}
