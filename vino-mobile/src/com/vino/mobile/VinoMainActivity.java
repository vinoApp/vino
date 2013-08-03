package com.vino.mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;

public class VinoMainActivity extends Activity implements View.OnClickListener {

    public static final String DOMAIN = "domain";
    public static final String VINTAGE = "vintage";
    public static final String STICKER = "sticker";
    private static final String IS_FOUND = "isFound";
    public static final int PICTURE_TAKEN_REQUEST_CODE = 888;

    private SharedPreferences prefs;

    private String domain;
    private String vintage;
    private String sticker;
    private boolean isFound = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        setListeners();

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
        if (view.getId() == R.id.btn_take_picture) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, PICTURE_TAKEN_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == PICTURE_TAKEN_REQUEST_CODE && intent != null) {

            // Retrieve taken picture
            Bundle extras = intent.getExtras();
            Bitmap takenSticker = (Bitmap) extras.get("data");

            // Check image nullity
            if (takenSticker == null) {
                return;
            }

            // Encode bitmap in base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            takenSticker.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            this.sticker = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));

            // Refresh UI
            refreshUI();

            return;
        }

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            retrieveWineBottle(scanningResult.getContents());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setListeners() {

        Button scanBtn = (Button) findViewById(R.id.btn_scan);
        scanBtn.setOnClickListener(this);

        Button takePictureBtn = (Button) findViewById(R.id.btn_take_picture);
        takePictureBtn.setOnClickListener(this);
    }

    private void loadState() {
        this.domain = prefs.getString(DOMAIN, null);
        this.vintage = prefs.getString(VINTAGE, null);
        this.sticker = prefs.getString(STICKER, null);
        this.isFound = prefs.getBoolean(IS_FOUND, false);
    }

    private void saveState() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DOMAIN, this.domain);
        editor.putString(VINTAGE, this.vintage);
        editor.putString(STICKER, this.sticker);
        editor.putBoolean(IS_FOUND, this.isFound);
        editor.commit();
    }

    private void resetState() {
        this.domain = "Inconnu";
        this.vintage = "Inconnu";
        this.sticker = "";
    }

    private void refreshUI() {
        if (this.domain != null) {
            ((TextView) findViewById(R.id.text_domain)).setText(this.domain);
        }
        if (this.vintage != null) {
            ((TextView) findViewById(R.id.text_vintage)).setText(this.vintage);
        }
        if (this.sticker != null) {
            byte[] decodedString = Base64.decode(sticker, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ((ImageView) findViewById(R.id.img_sticker)).setImageBitmap(decodedByte);
        }
        findViewById(R.id.btn_take_picture).setEnabled(!this.isFound);
    }

    public void retrieveWineBottle(String barcode) {

        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(new JsonObjectRequest(Request.Method.GET, "http://192.168.1.101:8080/rest/bottles/" + barcode, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                // The response status
                                String status = jsonObject.getString("status");

                                // If the bottle has not been found
                                if ("BOTTLE_NOT_FOUND".equalsIgnoreCase(status)) {
                                    VinoMainActivity.this.isFound = false;
                                    resetState();
                                }

                                if ("OK".equalsIgnoreCase(status)) {
                                    // Update model about domain, vintage & sticker image
                                    VinoMainActivity.this.isFound = true;
                                    VinoMainActivity.this.domain = jsonObject.getJSONObject("bottle").getJSONObject("domain").getString("name");
                                    VinoMainActivity.this.vintage = jsonObject.getJSONObject("bottle").getString("vintage");
                                    VinoMainActivity.this.sticker = jsonObject.getJSONObject("bottle").getJSONObject("domain").getString("sticker");
                                }

                                // Save state
                                saveState();

                                // Refresh UI
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
