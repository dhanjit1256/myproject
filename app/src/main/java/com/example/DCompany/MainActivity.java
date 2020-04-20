package com.example.DCompany;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.Admin.AdminHome;
import com.example.DCompany.afterlogin.oneActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

     FirebaseAuth mAuth;
     EditText email, password;
     Button submit,signin, Ebutton, Abutton, Admin;
     TextView regester;
     ImageButton Back;


    EditText phonenumber;
    EditText authcode;
    TextView cocegenerate;
    Button Bt;
    String getcode;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonenumber=findViewById( R.id.phonenumber);
        authcode=(EditText)findViewById(R.id.authcode);
        cocegenerate=(TextView) findViewById(R.id.cocegenerate);
        Bt=(Button) findViewById(R.id.cirLoginButton);


        progressDialog=new ProgressDialog(MainActivity.this);

        regester= (TextView) findViewById(R.id.xregester);
        email = (EditText) findViewById(R.id.xname);
        password = (EditText) findViewById(R.id.xpassword);
        submit = (Button) findViewById(R.id.xbutton);
        Ebutton= (Button) findViewById(R.id.employeebutton);
        Abutton= (Button) findViewById(R.id.adminbutton);
        Back= (ImageButton) findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ebutton.setVisibility(View.VISIBLE);
                Abutton.setVisibility(View.VISIBLE);
                email.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                Back.setVisibility(View.GONE);
            }
        });

        Ebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ebutton.setVisibility(View.GONE);
                Abutton.setVisibility(View.GONE);
                email.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                Back.setVisibility(View.VISIBLE);
            }
        });
        Abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(MainActivity.this, AdminHome.class);
               startActivity(intent);
            }
        });

//        regester.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RegesterUser.class);
//                startActivity(intent);
//            }
//        });



        mAuth=FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailString=email.getText().toString();
                String passwordString=password.getText().toString();
                if(emailString.isEmpty()){
                   Toast.makeText(MainActivity.this, "Please Enter Your Email ID", Toast.LENGTH_SHORT).show();
                }
                else if (passwordString.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                }


                if (!emailString.equals("")&&!passwordString.equals("")){
                    progressDialog.setTitle("Please wait...");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(emailString, passwordString)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Failed to SignIn", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        finish();
                                        Intent tohome=new Intent(MainActivity.this, oneActivity.class);
                                        startActivity(tohome);
                                    }
                                }
                            });
                }
            }
        });


        cocegenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bt.setVisibility(View.VISIBLE);
                progressDialog.setTitle("Please wait..");
                progressDialog.setMessage("Checking for OTP, It will auto veriffy you..");
                progressDialog.show();
                code();
            }
        });



        Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              btn();
            }
        });






    }

    private void btn() {
        String code=authcode.getText().toString().trim();
        if (code.isEmpty() || code.length()<=6 || code.length()>=6){
            authcode.requestFocus();
            authcode.setError("Please enter the One Time Password");
        }
        else{
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(getcode,code );
            signInWithPhoneAuthCredential(credential);
        }

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Auth Successfull", Toast.LENGTH_SHORT).show();
                            Intent tohome=new Intent(MainActivity.this, oneActivity.class);
                            finish();
                            startActivity(tohome);



                        } else {

                                Toast.makeText(MainActivity.this, "Errot", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            finish();
            Intent tohome=new Intent(MainActivity.this, HomeActivity.class);
            startActivity(tohome);
        }

    }





    private void code() {
        String number= phonenumber.getText().toString().trim();
        String getnumber ="+91"+number;
        progressDialog.setMessage("Checking Phone Number");
        if (number.isEmpty()){
            phonenumber.setError("Please Enter You Phone Number");
            phonenumber.requestFocus();
            progressDialog.dismiss();
        }
        else if (number.length()<10){
            phonenumber.setError("Phone number should be 10 digits only");
            phonenumber.requestFocus();
            progressDialog.dismiss();
        }
        else {
            progressDialog.setMessage("Sending OTP to "+getnumber);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    getnumber,        // Phone number to verify
                    1,                 // Timeout duration
                    TimeUnit.MINUTES,   // Unit of timeout
                    MainActivity.this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        }
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            progressDialog.setMessage("Checking OTP..");
            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Verification Failed"+e, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            progressDialog.setMessage("OTP Send Sucessfully. Please wait for verification");
            Toast.makeText(MainActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
            getcode=verificationId;
        }
    };

    public void regesteruser(View view) {

        Dialog dilog=new Dialog(MainActivity.this);
        dilog.setCanceledOnTouchOutside(false);
        dilog.setContentView(R.layout.regesterlayout);
        Button cancel=(Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilog.dismiss();
            }
        });
        dilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dilog.show();
    }
}
