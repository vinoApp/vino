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

package com.vino.backend.persistence;

import com.vino.backend.model.PendingWineBottle;

import java.util.HashMap;
import java.util.Map;

/**
 * User: walien
 * Date: 8/4/13
 * Time: 6:26 PM
 */
public class PendingsBundle {

    private static PendingsBundle INSTANCE;

    private Map<String, PendingWineBottle> pendings;

    private PendingsBundle() {
        pendings = new HashMap<String, PendingWineBottle>();
    }

    public static PendingsBundle getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PendingsBundle();
        }
        return INSTANCE;
    }

    public Map<String, PendingWineBottle> getPendings() {
        return pendings;
    }

}
