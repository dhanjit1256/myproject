package com.example.DCompany.afterlogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.HomeActivity;
import com.example.DCompany.R;

public class oneActivity extends AppCompatActivity {
    ImageView tohome;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        window=this.getWindow();
        window.setStatusBarColor(Color.WHITE);

        tohome=(ImageView)findViewById(R.id.tohome);
        tohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (oneActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
