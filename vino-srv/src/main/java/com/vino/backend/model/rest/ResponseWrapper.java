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

package com.vino.backend.model.rest;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:40 PM
 */
public class ResponseWrapper {

    private ResponseStatus status;

    private Object data;

    public ResponseWrapper() {

    }

    public ResponseStatus getStatus() {
        return status;
    }

    public ResponseWrapper setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseWrapper setData(Object data) {
        this.data = data;
        return this;
    }
}
