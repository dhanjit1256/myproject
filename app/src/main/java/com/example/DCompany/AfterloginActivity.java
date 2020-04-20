package com.example.DCompany;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AfterloginActivity extends AppCompatActivity {
    TextInputEditText schooledit, collegeedit, skilledit;
    ImageButton bankbutton, back;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        schooledit=(TextInputEditText) findViewById(R.id.xschooledit);
        collegeedit=(TextInputEditText) findViewById(R.id.xcollegeedit);
        skilledit=(TextInputEditText) findViewById(R.id.xskilledit);
        bankbutton= (ImageButton) findViewById(R.id.tobankdetails);
        back = (ImageButton) findViewById(R.id.backtoprofile);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AfterloginActivity.this, HomeFragment.class);
                startActivity(intent);
                finish();
            }
        });
        mAuth=FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        bankbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = firebaseUser.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference("Musers").child(userid);
                if (!userid.equals(null)) {
                    String collegevalue = collegeedit.getText().toString().trim();
                    String schoolvalue = schooledit.getText().toString().trim();
                    String skillvalue = skilledit.getText().toString().trim();
                    if (!TextUtils.isEmpty(collegevalue) && !TextUtils.isEmpty(schoolvalue) && !TextUtils.isEmpty(skillvalue)) {
                        DatabaseReference d = databaseReference.child("Professional Details");
                        d.child("School Name").setValue(schoolvalue);
                        d.child("College Name").setValue(collegevalue);
                        d.child("Skill Details").setValue(skillvalue);
                        Toast.makeText(AfterloginActivity.this, "Professional Details has been successfully submitted", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AfterloginActivity.this, "Something wrong, Please try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AfterloginActivity.this, "There was something wrong, Please login again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AfterloginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });



    }
}
