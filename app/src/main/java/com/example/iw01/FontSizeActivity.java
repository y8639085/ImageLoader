package com.example.iw01;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FontSizeActivity extends AppCompatActivity {

    private long fontID;
    private List<String> fonts;
    private ArrayAdapter<String> fontAdapter;
    private float currentSize;
    private int currentFont;
    private TextView textView;
    private AssetManager mgr;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fontsize);
        textView = findViewById(R.id.txv);
        Bundle bundle = getIntent().getExtras();
        currentSize = bundle.getFloat("currentSize");
        currentFont = bundle.getInt("currentFont");
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentSize);
//        textView.setTypeface(tf);
        textView.setText(textView.getText().toString());
//        Log.e("jieshoudao", String.valueOf(currentSize));

        final Spinner fontSpinner = (Spinner) findViewById(R.id.font);
        fonts = new ArrayList<String>();
        fonts.add("DroidSans");
        fonts.add("Bolton");
        fonts.add("Terra");
        fonts.add("Zapfino");

        fontAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fonts);
        fontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(fontAdapter);
        fontSpinner.setSelection(currentFont, true);
        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id = fontAdapter.getPosition(fontAdapter.getItem(position));
                fontID = id;
                currentFont = (int)id;
                mgr = getAssets();
                switch ((int)id) {
                    case 0:
                        tf = Typeface.createFromAsset(mgr, "fonts/DroidSans.ttf");
                        textView.setTypeface(tf);
                        textView.setText(textView.getText().toString());
                        break;
                    case 1:
                        tf = Typeface.createFromAsset(mgr, "fonts/BoltonShadowed.ttf");
                        textView.setTypeface(tf);
                        textView.setText(textView.getText().toString());
                        break;
                    case 2:
                        tf = Typeface.createFromAsset(mgr, "fonts/terra_normal.ttf");
                        textView.setTypeface(tf);
                        textView.setText(textView.getText().toString());
                        break;
                    case 3:
                        tf = Typeface.createFromAsset(mgr, "fonts/Zapfino.ttf");
                        textView.setTypeface(tf);
                        textView.setText(textView.getText().toString());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            /*    AssetManager mgr = getAssets();
                Typeface tf = Typeface.createFromAsset(mgr, "fonts/DroidSans.ttf");
                textView.setTypeface(tf);
                textView.setText(textView.getText().toString());*/
            }
        });
    }

    public void bigger(View v){     // response of button
        float size = currentSize + 20f;
        currentSize = size;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
//        Log.e("gaibianhou", String.valueOf(currentSize));
    }

    public void smaller(View v){     // response of button
        float size = currentSize - 20f;
        currentSize = size;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void confirm(View v) {
        Bundle bundle = new Bundle();
//        bundle.putLong("fontResult", fontID);
        bundle.putFloat("currentSize", currentSize);
        bundle.putInt("currentFont", currentFont);
//        Log.e("jiaoge", String.valueOf(currentSize));
        Intent result = new Intent();
        result.putExtras(bundle);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}