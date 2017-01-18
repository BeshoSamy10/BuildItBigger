package com.example.beshoy.androidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeTeller extends AppCompatActivity {

    TextView joke_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_teller);
        joke_textview = (TextView) findViewById(R.id.joke);

        joke_textview.setText(getIntent().getStringExtra("joke"));
    }
}
