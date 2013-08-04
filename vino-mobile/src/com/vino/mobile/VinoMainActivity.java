package com.vino.mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.*;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;

public class VinoMainActivity extends Activity {


    private SharedPreferences prefs;

    public String barcode;
    public String domain;
    public String vintage;
    public String sticker;
    public boolean isFound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        setListeners();

        prefs = getSharedPreferences(VinoConstants.VINO_PREFERENCES, MODE_PRIVATE);

        loadState();
        refreshUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Picture Taking Result
        if (requestCode == VinoConstants.PICTURE_TAKEN_REQUEST_CODE && intent != null) {

            // Retrieve taken picture
            Bundle extras = intent.getExtras();
            Bitmap takenSticker = (Bitmap) extras.get("data");

            // Check image nullity
            if (takenSticker == null) {
                return;
            }

            // Encode bitmap in base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            takenSticker.compress(Bitmap.CompressFormat.PNG, 100, baos);
            this.sticker = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));

            // Refresh UI
            refreshUI();

            return;
        }

        // BarCode Scanning Result
        if (requestCode == IntentIntegrator.REQUEST_CODE) {

            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                this.barcode = scanningResult.getContents();
                VinoRESTClient.retrieveWineBottle(this, this.barcode);
            } else {
                Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setListeners() {

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.btn_scan:
                        // Reset UI
                        resetState();
                        saveState();
                        refreshUI();

                        IntentIntegrator scanIntegrator = new IntentIntegrator(VinoMainActivity.this);
                        scanIntegrator.initiateScan();
                        break;

                    case R.id.btn_take_picture:
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, VinoConstants.PICTURE_TAKEN_REQUEST_CODE);
                        break;

                    case R.id.btn_add:
                        String qty = ((EditText) findViewById(R.id.text_qty)).getText().toString();
                        if (qty.isEmpty()) {
                            return;
                        }
                        if (!isFound) {
                            VinoRESTClient.addPendingBottle(VinoMainActivity.this, VinoMainActivity.this.barcode,
                                    VinoMainActivity.this.sticker, Integer.parseInt(qty));
                        } else {
                            VinoRESTClient.addBottle(VinoMainActivity.this, VinoMainActivity.this.barcode,
                                    Integer.parseInt(qty));
                        }
                        ((EditText) findViewById(R.id.text_qty)).setText("");
                        resetState();
                        saveState();
                        refreshUI();
                        break;

                    case R.id.btn_remove:
                        if (!isFound) {
                            return;
                        }
                        qty = ((EditText) findViewById(R.id.text_qty)).getText().toString();
                        if (qty.isEmpty()) {
                            return;
                        }
                        VinoRESTClient.removeBottle(VinoMainActivity.this, VinoMainActivity.this.barcode, Integer.parseInt(qty));
                        ((EditText) findViewById(R.id.text_qty)).setText("");
                        resetState();
                        saveState();
                        refreshUI();
                        break;
                }
            }
        };

        Button scanBtn = (Button) findViewById(R.id.btn_scan);
        scanBtn.setOnClickListener(listener);

        Button takePictureBtn = (Button) findViewById(R.id.btn_take_picture);
        takePictureBtn.setOnClickListener(listener);

        Button addBottleBtn = (Button) findViewById(R.id.btn_add);
        addBottleBtn.setOnClickListener(listener);

        Button removeBottleBtn = (Button) findViewById(R.id.btn_remove);
        removeBottleBtn.setOnClickListener(listener);
    }

    public void loadState() {
        this.barcode = prefs.getString(VinoConstants.BARCODE, null);
        this.domain = prefs.getString(VinoConstants.DOMAIN, null);
        this.vintage = prefs.getString(VinoConstants.VINTAGE, null);
        this.sticker = prefs.getString(VinoConstants.STICKER, null);
        this.isFound = prefs.getBoolean(VinoConstants.IS_FOUND, true);
    }

    public void saveState() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(VinoConstants.BARCODE, this.barcode);
        editor.putString(VinoConstants.DOMAIN, this.domain);
        editor.putString(VinoConstants.VINTAGE, this.vintage);
        editor.putString(VinoConstants.STICKER, this.sticker);
        editor.putBoolean(VinoConstants.IS_FOUND, this.isFound);
        editor.commit();
    }

    public void resetState() {
        this.domain = getString(R.string.unknown_text);
        this.vintage = getString(R.string.unknown_text);
        this.sticker = "";
    }

    public void refreshUI() {
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
        findViewById(R.id.btn_remove).setEnabled(this.isFound);
    }
}
