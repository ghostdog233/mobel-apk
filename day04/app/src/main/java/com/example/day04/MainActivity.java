package com.example.day04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         setContentView(R.layout.linearlayout);
         Button ButtonOK=(Button)findViewById(R.id.button);
         startActivity(Intent intent);
        startActivityForResult(Intent intent，int requestCode)

    }
}
