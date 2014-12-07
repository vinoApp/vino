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
import restx.RestxRequest;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.Permission;
import restx.security.SignatureKey;
import restx.security.StdRestxSecurityManager;

import javax.inject.Named;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 11:16 PM
 */

@Module(priority = -100)
public class AppModule {

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
}
