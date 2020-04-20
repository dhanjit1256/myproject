package com.example.DCompany.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.Job.AddjobActivity;
import com.example.DCompany.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminHome extends AppCompatActivity {
    public static DatabaseReference dRef, eRef;
    LinearLayout Hollyday, HideNavBar, CheckAttendance, AddJob;
    ImageView Menu;
    NavigationView nView;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        window=this.getWindow();
        window.setStatusBarColor(Color.WHITE);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        dRef= FirebaseDatabase.getInstance().getReference("PreDefine");
        eRef= FirebaseDatabase.getInstance().getReference("Musers");


        Hollyday=(LinearLayout)findViewById(R.id.tohollyday);
        nView=(NavigationView)findViewById(R.id.navbar);


        CheckAttendance=(LinearLayout)findViewById(R.id.xcheckattendace);
        AddJob=(LinearLayout)findViewById(R.id.addjob);

        AddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHome.this, AddjobActivity.class);
                startActivity(intent);
            }
        });

        Hollyday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminHome.this, AdminHolyday.class);
                startActivity(intent);
            }
        });

        Menu=(ImageView)findViewById(R.id.xmenu);
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nView.setVisibility(View.VISIBLE);
            }
        });

        HideNavBar=(LinearLayout)findViewById(R.id.hidenavbar);
        HideNavBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nView.setVisibility(View.GONE);
            }
        });




        CheckAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (AdminHome.this, AdminAttendance.class);
                startActivity(intent);
            }
        });
    }

    public void addemployee(View view) {
        Intent intent=new Intent (AdminHome.this, Addemployee.class);
        startActivity(intent);
    }
}
