package com.example.iw01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class ColorTypeActivity extends AppCompatActivity {

    private boolean isItalic;
    private boolean isBold;
    private long ID;
    private int currentColour;
    private List<String> colours;
    private ArrayAdapter<String> colourAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colortype);

        Bundle bundle = getIntent().getExtras();
        isBold = bundle.getBoolean("isBold");
        isItalic = bundle.getBoolean("isItalic");
        currentColour = bundle.getInt("currentColour");
//        Log.d("jiaoge", Boolean.toString(isBold));
//        Log.d("jiaoge", Boolean.toString(isItalic));

        Spinner colourSpinner = (Spinner) findViewById(R.id.colour);

        colours = new ArrayList<String>();
        colours.add("Black");
        colours.add("White");
        colours.add("Red");
        colours.add("Yellow");
        colours.add("Blue");
        colours.add("Green");

        colourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colours);
        colourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colourSpinner.setAdapter(colourAdapter);
        colourSpinner.setSelection(currentColour, true);
        colourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id = colourAdapter.getPosition(colourAdapter.getItem(position));
                ID = id;
                currentColour = (int)id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                /*textView.setTextColor(Color.BLACK);
                textView.setText(textView.getText().toString());*/
            }
        });
    }

    public void bold(View v) {
        if(isBold){
            isBold = false;
        }
        else {
            isBold = true;
        }
    }

    public void italic(View v) {
        if(isItalic){
            isItalic = false;
        }
        else {
            isItalic = true;
        }
//        Log.d("jiaoge", "=====italic: " + isItalic);
    }

    public void confirm(View v) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("italicResult",isItalic);
        bundle.putBoolean("boldResult",isBold);
//        bundle.putLong("colorResult", ID);
        bundle.putInt("currentColour", currentColour);
        Intent result = new Intent();
        result.putExtras(bundle);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}