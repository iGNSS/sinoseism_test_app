package com.gxu.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView evettextview=findViewById(R.id.textView1);
        Bundle bundle=this.getIntent().getExtras();
        String data=bundle.getString("data");
        evettextview.setText(data);


    }
}