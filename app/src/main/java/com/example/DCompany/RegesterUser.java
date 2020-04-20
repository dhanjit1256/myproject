package com.example.DCompany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegesterUser extends AppCompatActivity {


    private EditText email, password, name, phone,  address, usermname, userfname, userdateofbirth, userhomephone;
    private Spinner gender, cast;
    private Button regester;
    private TextView login;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private  String spinnergender, spinnercast;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester_user);

        window=this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.regester));

        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference= mDatabase.getReference().child("Musers");
        mAuth=FirebaseAuth.getInstance();

        name= (EditText)findViewById(R.id.xname);
        email= (EditText)findViewById(R.id.xemail);
        password= (EditText)findViewById(R.id.xpassword);
        phone= (EditText)findViewById(R.id.xphone);
        regester=(Button) findViewById(R.id.xregester);
        usermname= (EditText)findViewById(R.id.xmname);
        userfname=(EditText) findViewById(R.id.xfname);
        address=(EditText)findViewById(R.id.xaddress);
        gender= (Spinner) findViewById(R.id.xgender);
        cast=(Spinner)findViewById(R.id.xcast);
        userdateofbirth= (EditText)findViewById(R.id.xdob);
        userhomephone=(EditText)findViewById(R.id.xhomephone);

        List<String> gendercategories = new ArrayList<>();
        gendercategories.add(0, "Select Employee's Gender");
        gendercategories.add("Male");
        gendercategories.add("Femail");
        ArrayAdapter<String> data;
        data=new ArrayAdapter(this,android.R.layout.simple_spinner_item, gendercategories);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(data);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              spinnergender= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(RegesterUser.this, "Please Select Your Gender", Toast.LENGTH_LONG).show();
            }
        });

        List<String> castlist= new ArrayList<>();
        castlist.add("Select Employee's Cast");
        castlist.add("General");
        castlist.add("ST");
        castlist.add("SC");
        castlist.add("OBC");
        ArrayAdapter<String> castdata;
        castdata=new ArrayAdapter(RegesterUser.this, android.R.layout.simple_spinner_item, castlist);
        castdata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cast.setAdapter(castdata);
        cast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnercast=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(RegesterUser.this, "Please Select Your Cast", Toast.LENGTH_LONG).show();
            }
        });



        regester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name= name.getText().toString().trim();
                final String Email= email.getText().toString().trim();
                final String Password= password.getText().toString().trim();
                final String Phone= phone.getText().toString().trim();
                final String Address= address.getText().toString().trim();
                final String Fname= userfname.getText().toString().trim();
                final String Mname= usermname.getText().toString().trim();
                final String Homephone= userhomephone.getText().toString().trim();
                final String UserDOB= userdateofbirth.getText().toString().trim();


                if (!TextUtils.isEmpty(Name)&&!TextUtils.isEmpty(Email)&&!TextUtils.isEmpty(Password)&&!TextUtils.isEmpty(Phone)){

                    final ProgressDialog progressDialog=new ProgressDialog(RegesterUser.this);
                    progressDialog.setTitle("Creating Account...");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String userid=mAuth.getCurrentUser().getUid();
                            DatabaseReference currentuserDb=mDatabaseReference.child(userid);
                            currentuserDb.child("User_Name").setValue(Name);
                            currentuserDb.child("User_Phone").setValue(Phone);
                            currentuserDb.child("User_Address").setValue(Address);
                            currentuserDb.child("User_DOB").setValue(UserDOB);
                            currentuserDb.child("User_Father_Name").setValue(Fname);
                            currentuserDb.child("User_Mother_Name").setValue(Mname);
                            currentuserDb.child("User_Gender").setValue(spinnergender);
                            currentuserDb.child("User_Cast").setValue(spinnercast);
                            currentuserDb.child("User_Home_Phone").setValue(Homephone);

                            progressDialog.dismiss();
                            finish();
                            Intent intent=new Intent(RegesterUser.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }


}
