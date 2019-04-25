package com.example.iw01;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class ImageActivity extends AppCompatActivity {
    private TextView text;
    private Boolean isBold = false;
    private Boolean isItalic = false;
    private float currentSize;
    private int currentFont;
    private int currentColour;
    private ImageView imageView;
    final static int REQUEST_ALBUM = 1;
    final static int REQUEST_CAMERA = 2;
    final static int REQUEST_COLORTYPE = 3;
    final static int REQUEST_FONTSIZE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        text = findViewById(R.id.text);
        currentSize = text.getTextSize();
        currentFont = 0;
        currentColour = 0;
//        Log.e("chushizhi", String.valueOf(currentSize));
    }

    public void getSystemImage(View v) {
        Intent localIntent = new Intent();
        localIntent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        localIntent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(localIntent, REQUEST_ALBUM);
    }

    public void takePhoto(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
    }

    public void ColorTypePage(View v) {
        Intent intent = new Intent(ImageActivity.this,ColorTypeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBold", isBold);
        bundle.putBoolean("isItalic", isItalic);
        bundle.putInt("currentColour", currentColour);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_COLORTYPE);
    }

    public void FontSizePage(View v) {
        Intent intent = new Intent(ImageActivity.this,FontSizeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putFloat("currentSize", currentSize);
        bundle.putInt("currentFont", currentFont);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_FONTSIZE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_ALBUM && resultCode == RESULT_OK) {
            Uri uri = intent.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);/* 将Bitmap设定到ImageView */
            } catch (FileNotFoundException e) {
//                Log.e("Exception", e.getMessage(),e);
            }
        }
        else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            if (intent != null) {
                if (intent.hasExtra("data")) {
                    Bitmap bitmap = intent.getParcelableExtra("data");
                    imageView.setImageBitmap(bitmap);//imageView即为当前页面需要展示照片的控件，可替换
                }
            }
        }
        else if (requestCode == REQUEST_COLORTYPE && resultCode == RESULT_OK) {
            Bundle bundle = intent.getExtras();
            Boolean italicResult = bundle.getBoolean("italicResult");
            Boolean boldResult = bundle.getBoolean("boldResult");
            currentColour = bundle.getInt("currentColour");
//            int colorResult = (int)bundle.getLong(("colorResult"));
            if (italicResult){
                text.getPaint().setTextSkewX(-0.5f);
                isItalic = true;
            }
            else {
                text.getPaint().setTextSkewX(0);
                isItalic = false;
            }
            if (boldResult){
                text.getPaint().setFakeBoldText(true);
                isBold = true;
            }
            else {
                text.getPaint().setFakeBoldText(false);
                isBold = false;
            }
            switch (currentColour) {
                case 0:
                    text.setTextColor(Color.BLACK);
                    currentColour = 0;
                    break;
                case 1:
                    text.setTextColor(Color.WHITE);
                    currentColour = 1;
                    break;
                case 2:
                    text.setTextColor(Color.RED);
                    currentColour = 2;
                    break;
                case 3:
                    text.setTextColor(Color.YELLOW);
                    currentColour = 3;
                    break;
                case 4:
                    text.setTextColor(Color.BLUE);
                    currentColour = 4;
                    break;
                case 5:
                    text.setTextColor(Color.GREEN);
                    currentColour = 5;
                    break;
            }
        }
        else if (requestCode == REQUEST_FONTSIZE && resultCode == RESULT_OK) {
            Bundle bundle = intent.getExtras();
            currentSize = bundle.getFloat("currentSize");
            currentFont = bundle.getInt("currentFont");
//            Log.e("chuanhuilai", String.valueOf(currentSize));
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentSize);
            AssetManager mgr = getAssets();
            Typeface tf;
            switch (currentFont) {
                case 0:
                    tf = Typeface.createFromAsset(mgr, "fonts/DroidSans.ttf");
                    text.setTypeface(tf);
                    text.setText(text.getText().toString());
                    currentFont = 0;
                    break;
                case 1:
                    tf = Typeface.createFromAsset(mgr, "fonts/BoltonShadowed.ttf");
                    text.setTypeface(tf);
                    text.setText(text.getText().toString());
                    currentFont = 1;
                    break;
                case 2:
                    tf = Typeface.createFromAsset(mgr, "fonts/terra_normal.ttf");
                    text.setTypeface(tf);
                    text.setText(text.getText().toString());
                    currentFont = 2;
                    break;
                case 3:
                    tf = Typeface.createFromAsset(mgr, "fonts/Zapfino.ttf");
                    text.setTypeface(tf);
                    text.setText(text.getText().toString());
                    currentFont = 3;
                    break;
            }
        }
    }
}