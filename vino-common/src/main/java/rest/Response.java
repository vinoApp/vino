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

package rest;

import com.google.common.base.Optional;

/**
 * User: walien
 * Date: 1/12/14
 * Time: 12:52 AM
 */
public class Response {

    public enum TechnicalStatus {

        OK,
        DB_INSERT_OK,
        DB_REMOVE_OK,
        DB_ERROR,
        INVALID_PARAMS,
    }

    public enum BusinessStatus {

        NO_RECORD_FOUND,
        DOMAIN_NOT_FOUND
    }


    private Optional<TechnicalStatus> technicalStatus;

    private Optional<BusinessStatus> businessStatus;

    private String message;

    public Response() {

    }

    public static ResponseBuilder withStatuses(Optional<TechnicalStatus> technical, Optional<BusinessStatus> business) {
        return new ResponseBuilder(technical, business);
    }

    public static class ResponseBuilder {

        private Response response = new Response();

        public ResponseBuilder(Optional<TechnicalStatus> technical, Optional<BusinessStatus> business) {
            response.technicalStatus = technical;
            response.businessStatus = business;
        }

        public ResponseBuilder withMessage(String message, Object... params) {
            response.message = String.format(message, params);
            return this;
        }

        public Response build() {
            return response;
        }
    }

    public Optional<TechnicalStatus> getTechnicalStatus() {
        return technicalStatus;
    }

    public Optional<BusinessStatus> getBusinessStatus() {
        return businessStatus;
    }

    public String getMessage() {
        return message;
    }
}
