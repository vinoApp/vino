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

package com.vino.mobile;

import android.app.Activity;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

/**
 * User: walien
 * Date: 7/28/13
 * Time: 1:36 PM
 */
public class WineBottleRESTUnit {

    public static void retrieveWineBottle(final Activity activity, String barcode, Response.Listener<JSONObject> listener) {

        try {
            RequestQueue queue = Volley.newRequestQueue(activity);
            queue.add(new JsonObjectRequest(Request.Method.GET, "http://192.168.1.101:8080/rest/bottles/" + barcode, null,
                    listener,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }
            ));
            queue.start();
        } catch (Exception e) {
            Log.e("ErrorListener", e.getLocalizedMessage());
        }
    }

}
