package com.vino.mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONException;
import org.json.JSONObject;

public class VinoMainActivity extends Activity implements View.OnClickListener {

    public static final String DOMAIN = "domain";
    public static final String VINTAGE = "vintage";
    private String domain;

    private String vintage;

    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        Button scanBtn = (Button) findViewById(R.id.btn_scan);
        scanBtn.setOnClickListener(this);

        prefs = getSharedPreferences("vino-preferences", MODE_PRIVATE);

        loadState();
        refreshUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_scan) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            retrieveWineBottle(scanningResult.getContents());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void loadState() {
        this.domain = prefs.getString(DOMAIN, null);
        this.vintage = prefs.getString(VINTAGE, null);
    }

    private void saveState() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DOMAIN, this.domain);
        editor.putString(VINTAGE, this.vintage);
        editor.apply();
    }

    private void refreshUI() {
        if (this.domain != null) {
            ((TextView) findViewById(R.id.text_domain)).setText(this.domain);
        }
        if (this.vintage != null) {
            ((TextView) findViewById(R.id.text_vintage)).setText(this.vintage);
        }
    }

    public void retrieveWineBottle(String barcode) {

        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new JsonObjectRequest(Request.Method.GET, "http://192.168.1.101:8080/rest/bottles/" + barcode, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                // Update model about domain, vintage & sticker image
                                VinoMainActivity.this.domain = jsonObject.getJSONObject("domain").getString("name");
                                VinoMainActivity.this.vintage = jsonObject.getString("vintage");
                                // Update UI
                                saveState();
                                refreshUI();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, null));
            queue.start();
        } catch (Exception e) {
            Log.e("ErrorListener", e.getLocalizedMessage());
        }
    }
}
