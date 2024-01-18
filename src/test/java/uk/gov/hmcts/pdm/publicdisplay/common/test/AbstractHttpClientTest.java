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

import org.apache.http.config.SocketConfig;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Base class for testing http client code by instantiating a local server.
 *
 * @author uphillj
 */
@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
public abstract class AbstractHttpClientTest extends AbstractJUnit {

    /** The Constant ORIGIN. */
    public static final String ORIGIN = "TEST/1.1";

    /** The Constant TIMEOUT. */
    public static final int TIMEOUT = 15_000;

    /** The Constant PORT. */
    public static final int PORT = 8080;

    /** The Constant IP_ADDRESS. */
    public static final String IP_ADDRESS = "127.0.0.1";

    /** The Constant BASE_URL. */
    public static final String BASE_URL = "http://" + IP_ADDRESS + ":" + PORT;

    /** The server. */
    private HttpServer server;

    /**
     * Start server.
     *
     * @param pattern the pattern
     * @param handler the handler
     * @throws IOException ioException
     * @throws Exception the exception
     */
    protected void startServer(final String pattern, final HttpRequestHandler handler)
        throws IOException {
        // Setup server configuration
        final SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(TIMEOUT).build();
        ServerBootstrap serverBootstrap = ServerBootstrap.bootstrap().setSocketConfig(socketConfig)
            .setServerInfo(ORIGIN).setListenerPort(PORT).registerHandler(pattern, handler);

        // Start server
        this.server = serverBootstrap.create();
        this.server.start();
    }

    /**
     * Stop server.
     *
     * @throws InterruptedException interruptedException
     * @throws IOException ioException
     */
    protected void stopServer() throws InterruptedException, IOException {
        if (this.server != null) {
            this.server.shutdown(1, TimeUnit.SECONDS);
        }
    }

    /**
     * Gets the server.
     *
     * @return the server
     */
    protected HttpServer getServer() {
        return this.server;
    }
}
