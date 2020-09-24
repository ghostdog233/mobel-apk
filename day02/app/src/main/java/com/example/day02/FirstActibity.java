package com.example.day02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class FirstActibity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);//加载布局
        Button button1 =(Button)findViewById(R.id.button)
                button1.setOnClickListener(new View.On);
    }
}