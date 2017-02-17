package com.example.gk.testuploadjcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.imgod1.blurimageview.BlurImageView;

public class MainActivity extends AppCompatActivity {
    private BlurImageView blur_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blur_main = (BlurImageView) findViewById(R.id.blur_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blur_main:
                if (blur_main.isBlurMode()) {
                    blur_main.showNormal();
                } else {
                    blur_main.showBlur();
                }
                break;
            default:
                break;
        }
    }
}
