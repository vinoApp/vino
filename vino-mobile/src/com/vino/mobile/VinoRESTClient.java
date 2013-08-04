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

import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: walien
 * Date: 8/4/13
 * Time: 12:48 AM
 */
public class VinoRESTClient {

    public static final String GET_BOTTLE_BY_BARCODE_URL = "http://192.168.1.101:8080/rest/bottles/";
    public static final String ADD_PENDING_BOTTLE_URL = "http://192.168.1.101:8080/rest/pendings/";
    public static final String CELLAR_URL = "http://192.168.1.101:8080/rest/cellar/";


    public static void retrieveWineBottle(final VinoMainActivity activity, String barcode) {

        try {
            RequestQueue queue = Volley.newRequestQueue(activity);
            queue.add(new JsonObjectRequest(Request.Method.GET, GET_BOTTLE_BY_BARCODE_URL + barcode, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                // The response status
                                String status = jsonObject.getString("status");

                                // If the bottle has not been found
                                if ("BOTTLE_NOT_FOUND".equalsIgnoreCase(status)) {
                                    activity.isFound = false;
                                    activity.resetState();
                                }

                                if ("OK".equalsIgnoreCase(status)) {
                                    // Update model about domain, vintage & sticker image
                                    activity.isFound = true;
                                    activity.domain = jsonObject.getJSONObject("bottle").getJSONObject("domain").getString("name");
                                    activity.vintage = jsonObject.getJSONObject("bottle").getString("vintage");
                                    activity.sticker = jsonObject.getJSONObject("bottle").getJSONObject("domain").getString("sticker");
                                }

                                // Save state
                                activity.saveState();

                                // Refresh UI
                                activity.refreshUI();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(VinoConstants.LOG_TAG, String.format("ConnectionError : %s", volleyError.getMessage()));
                }
            }
            ));

            queue.start();
        } catch (Exception e) {
            Log.e(VinoConstants.LOG_TAG, e.getLocalizedMessage());
        }
    }

    public static void addPendingBottle(final VinoMainActivity activity, String barcode, String stickerImage, final int qty) {

        // Build the JSON object
        JSONObject object = new JSONObject();
        try {
            object.put("barcode", barcode);
            object.put("stickerImage", stickerImage);
            object.put("qty", qty);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            final RequestQueue queue = Volley.newRequestQueue(activity);
            queue.add(new JsonObjectRequest(Request.Method.POST, ADD_PENDING_BOTTLE_URL, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Toast.makeText(activity, qty + " bouteilles en attente ajoutées", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(VinoConstants.LOG_TAG, String.format("ConnectionError : %s", volleyError.getMessage()));
                }
            }
            ));
            queue.start();
        } catch (Exception e) {
            Log.e(VinoConstants.LOG_TAG, e.getLocalizedMessage());
        }
    }

    public static void addBottle(final VinoMainActivity activity, String barcode, int qty) {
        // TODO
        Toast.makeText(activity, qty + " bouteilles ajoutées dans la cave.", Toast.LENGTH_LONG).show();
    }

    public static void removeBottle(final VinoMainActivity activity, String barcode, int qty) {
        // TODO
        Toast.makeText(activity, qty + " bouteilles retirées de la cave.", Toast.LENGTH_LONG).show();
    }
}
