/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vino.backend;

import com.vino.backend.servers.JettyWebServer;

/**
 * User: walien
 * Date: 10/04/13
 * Time: 21:26
 */
public class VinoServer {

    private static final String WEB_XML_FILE = "vino-srv/src/main/webapp/WEB-INF/web.xml";
    private static final String WEBAPP_RESOURCES = "vino-web/app";
    private static final String BIND_INTERFACE = "localhost";
    private static final int BIND_PORT = 8080;

    public static void main(String[] args) throws Exception {

        // Instantiate the Jetty Web Server
        new JettyWebServer(WEB_XML_FILE, WEBAPP_RESOURCES,
                BIND_PORT, BIND_INTERFACE).startAndAwait();

    }
}
