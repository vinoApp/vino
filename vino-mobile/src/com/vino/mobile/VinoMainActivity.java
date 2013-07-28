package com.vino.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class VinoMainActivity extends Activity implements View.OnClickListener {

    private Button scanBtn;
    private TextView formatTxt, contentTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        scanBtn = (Button) findViewById(R.id.scanButton);
        formatTxt = (TextView) findViewById(R.id.scanFormat);
        contentTxt = (TextView) findViewById(R.id.scanContent);

        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.scanButton) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            formatTxt.setText("FORMAT: " + scanningResult.getFormatName());
            contentTxt.setText("CONTENT: " + scanningResult.getContents());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
