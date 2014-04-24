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

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.server.JettyWebServer;
import restx.server.WebServer;

/**
 * User: walien
 * Date: 10/04/13
 * Time: 21:26
 */
public class AppServer {

    private static final Logger logger = LoggerFactory.getLogger(AppServer.class);

    public static final String WEB_INF_LOCATION = "src/main/webapp/WEB-INF/web.xml";
    public static final String WEB_APP_LOCATION = "../vino-ui/app";

    public static void main(String[] args) throws Exception {
        /*
         * load mode from system property if defined, or default to dev
         * be careful with that setting, if you use this class to launch your server in production, make sure to launch
         * it with -Drestx.mode=prod or change the default here
         */
        String mode = System.getProperty("restx.mode", "prod");
        System.setProperty("restx.mode", mode);
        System.setProperty("restx.app.package", "com.vino.backend");

        int port = Integer.valueOf(Optional.fromNullable(System.getenv("PORT")).or("8080"));
        WebServer server = new JettyWebServer(WEB_INF_LOCATION, WEB_APP_LOCATION, port, "0.0.0.0");

        System.setProperty("restx.server.id", server.getServerId());
        System.setProperty("restx.server.baseURL", server.baseUrl());

        server.startAndAwait();
    }
}
