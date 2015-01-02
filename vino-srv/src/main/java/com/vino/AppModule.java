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

package com.vino;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.RestxRequest;
import restx.factory.AutoStartable;
import restx.factory.Module;
import restx.factory.Provides;
import restx.mongo.MongoModule;
import restx.security.*;

import javax.inject.Named;
import java.util.regex.Pattern;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 11:16 PM
 */

@Module(priority = -100)
public class AppModule {

    private static final Logger logger = LoggerFactory.getLogger(AppModule.class);

    @Provides
    public SignatureKey signatureKey() {
        return new SignatureKey(
                "vino -4553657335605629125 1e6dcc7f-a28e-4511-8828-1fcad574eae4 vino"
                        .getBytes(Charsets.UTF_8));
    }

    @Provides
    @Named("app.name")
    public String appName() {
        return "vino";
    }

    @Provides
    @Named("mongo.db")
    public String jongoDbName() {
        return "vino";
    }

    @Provides
    @Named("restx.admin.password")
    public String restxAdminPassword() {
        return "vino";
    }

    @Provides
    @Named("StdRestxSecurityManager")
    public StdRestxSecurityManager permitsAllAlways() {
        return new StdRestxSecurityManager() {
            @Override
            public void check(RestxRequest request, Permission permission) {
                // Permit all
            }
        };
    }

    @Provides
    public AutoStartable mongoConnectionLogger(final @Named("restx.server.id") Optional<String> serverId,
                                               final @Named("mongo.db") String dbName,
                                               final @Named(MongoModule.MONGO_CLIENT_NAME) MongoClient client) {
        return new AutoStartable() {
            @Override
            public void start() {
                logger.info("{} - connected to Mongo db '{}' @ {}", serverId.or("-"), dbName, client.getAllAddress());
            }
        };
    }

    @Provides
    public CORSAuthorizer getCorsAuthorizer() {
        return new StdCORSAuthorizer.Builder()
                .setPathMatcher(Predicates.<CharSequence>alwaysTrue())
                .setPathMatcher(Predicates.contains(Pattern.compile(".*")))
                .setAllowedMethods(ImmutableList.of("GET", "POST"))
                .build();
    }

}
