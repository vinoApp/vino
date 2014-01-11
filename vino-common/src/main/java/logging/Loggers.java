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

package logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: walien
 * Date: 1/11/14
 * Time: 5:00 PM
 */
public class Loggers {

    public static final String VINO_SRV = "vino-srv";
    public static final String VINO_COMMON = "vino-common";

    public static final Logger SRV = LoggerFactory.getLogger(VINO_SRV);
    public static final Logger COMMON = LoggerFactory.getLogger(VINO_COMMON);

}
