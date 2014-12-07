/*
 *
 *  * Copyright 2013 - Elian ORIOU
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

// Rename all @class fields
db.regions.update({}, {$set: {'@class': 'com.vino.domain.WineRegion'}}, {multi: true});
db.aocs.update({}, {$set: {'@class': 'com.vino.domain.WineAOC'}}, {multi: true});
db.domains.update({}, {$set: {'@class': 'com.vino.domain.WineDomain'}}, {multi: true});
db.cellars.update({}, {$set: {'@class': 'com.vino.domain.WineCellar'}}, {multi: true});

// Change keys.collection field -> keys.type field

db.keys.update({}, {$rename: {'collection': 'type'}}, {multi: true});

var keys = db.keys.find();
keys.forEach(function (key) {

    key.type = key.type.substring(0, key.type.length - 1).toUpperCase();

    db.keys.save(key);
});

