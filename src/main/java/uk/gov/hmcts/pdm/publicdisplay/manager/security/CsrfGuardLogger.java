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

package uk.gov.hmcts.pdm.publicdisplay.manager.security;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

/**
 * CSRF Guard ILogger implementation which directs logging to log4j and is configured in the
 * csrfguard.properties file.
 * 
 * @author uphillj
 *
 */
public class CsrfGuardLogger implements ILoggerFactory {

    /** The serialVersionUID. */
    private static final long serialVersionUID = -7299989134574075898L;

    /** The LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CsrfGuardLogger.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.owasp.csrfguard.log.ILogger#log(java.lang.String)
     */
    public void log(final String msg) {
        String logMsg = msg.replaceAll("(\\r|\\n)", "");
        LOGGER.info(logMsg);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.owasp.csrfguard.log.ILogger#log(java.lang.Exception)
     */
    public void log(final Exception exception) {
        LOGGER.warn(exception.getLocalizedMessage(), exception);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.owasp.csrfguard.log.ILogger#log(org.owasp.csrfguard.log.LogLevel, java.lang.String)
     */
    public void log(final LogLevel level, final String msg) {
        // Remove CR and LF characters to prevent CRLF injection
        final String sanitizedMsg = msg.replaceAll("(\\r|\\n)", "");

        switch (level) {
            case TRACE:
                LOGGER.trace(sanitizedMsg);
                break;
            case DEBUG:
                LOGGER.debug(sanitizedMsg);
                break;
            case INFO:
                LOGGER.info(sanitizedMsg);
                break;
            case WARN:
                LOGGER.warn(sanitizedMsg);
                break;
            case ERROR:
                LOGGER.error(sanitizedMsg);
                break;
            case FATAL:
                LOGGER.error(sanitizedMsg);
                break;
            default:
                throw new CsrfGuardException("unsupported log level " + level);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.owasp.csrfguard.log.ILogger#log(org.owasp.csrfguard.log.LogLevel,
     * java.lang.Exception)
     */
    public void log(final LogLevel level, final Exception exception) {
        switch (level) {
            case TRACE:
                LOGGER.trace(exception.getLocalizedMessage(), exception);
                break;
            case DEBUG:
                LOGGER.debug(exception.getLocalizedMessage(), exception);
                break;
            case INFO:
                LOGGER.info(exception.getLocalizedMessage(), exception);
                break;
            case WARN:
                LOGGER.warn(exception.getLocalizedMessage(), exception);
                break;
            case ERROR:
                LOGGER.error(exception.getLocalizedMessage(), exception);
                break;
            case FATAL:
                LOGGER.error(exception.getLocalizedMessage(), exception);
                break;
            default:
                throw new CsrfGuardException("unsupported log level " + level);
        }
    }

    @Override
    public Logger getLogger(String name) {
        // TODO Auto-generated method stub
        return null;
    }
}
