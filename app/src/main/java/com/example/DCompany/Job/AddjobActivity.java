package com.example.DCompany.Job;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.example.DCompany.Admin.AdminHome.dRef;

public class AddjobActivity extends AppCompatActivity {
    Window window;
    TextView ErrorTitle, ErrorDetails, ErrorAmount, ErrorAll, LinkDetails, ShowTitle;
    EditText Title, Details, Amount, Links;
    Button Submit, CheckTitle, Close,ToList;
    ProgressDialog P;
    DatabaseReference toJobList, toJobDetails;
    String strTitle, strDetails, strAmount, strLinks,NewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjob);

        window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.purple));

        ErrorTitle = (TextView) findViewById(R.id.errortitle);
        ErrorDetails = (TextView) findViewById(R.id.errordetails);
        ErrorAmount = (TextView) findViewById(R.id.erroramount);
        ErrorAll = (TextView) findViewById(R.id.errorall);
        LinkDetails = (TextView) findViewById(R.id.linkdetails);
        Title = (EditText) findViewById(R.id.title);
        Details = (EditText) findViewById(R.id.details);
        Amount = (EditText) findViewById(R.id.amount);
        Links = (EditText) findViewById(R.id.links);
        Submit = (Button) findViewById(R.id.submit);
        CheckTitle = (Button) findViewById(R.id.checktitle);
        ShowTitle = (TextView) findViewById(R.id.titleis);
        Close=(Button) findViewById(R.id.close);
        ToList=(Button)findViewById(R.id.tojoblist);

        ToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddjobActivity.this, JobselcectActivity.class);
                intent.putExtra("User", "ADMI"+"yyutyuty");
                startActivity(intent);
            }
        });
        P = new ProgressDialog(this);
        P.setTitle("Please Wait...");
        P.setMessage("Checking Values..");

        toJobList = dRef.child("JobList");
        toJobDetails = dRef.child("JobDetails");

        CheckTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                P.show();
                strTitle = Title.getText().toString();

                if (strTitle.isEmpty()) {
                    P.dismiss();
                    ErrorTitle.setText("This fild can not be empty");
                    ErrorTitle.setVisibility(View.VISIBLE);
                } else {
                    toJobDetails.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(strTitle).exists()) {
                                ErrorTitle.setVisibility(View.VISIBLE);
                                ErrorTitle.setText("'" + strTitle + "'" + " - This Title is already exist.");
                                P.dismiss();
                            } else {
//
//                                Title.setText("");
                                Submit.setVisibility(View.VISIBLE);
                                CheckTitle.setVisibility(View.GONE);
                                Title.setVisibility(View.GONE);
                                ShowTitle.setText(strTitle);
                                ShowTitle.setVisibility(View.VISIBLE);
                                ErrorTitle.setVisibility(View.GONE);
                                P.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strDetails = Details.getText().toString();
                strAmount = Amount.getText().toString();
                strLinks = Links.getText().toString();
                if (strDetails.isEmpty()) {
                    P.dismiss();
                    ErrorDetails.setText("This fild can not be empty");
                    ErrorDetails.setVisibility(View.VISIBLE);
                }else{
                    ErrorDetails.setVisibility(View.GONE);
                }
                if (strAmount.isEmpty()) {
                    ErrorAmount.setText("This fild can not be empty");
                    ErrorAmount.setVisibility(View.VISIBLE);
                }else{
                    ErrorAmount.setVisibility(View.GONE);
                }

                if (strAmount.isEmpty()&&strDetails.isEmpty()){
                    ErrorAll.setText("All * filds are required");
                    ErrorAll.setVisibility(View.VISIBLE);
                }
                else{
                    toJobList.child(strTitle).setValue(strTitle);

                    DatabaseReference jobidref=toJobDetails.child(strTitle);
                    jobidref.child("Title").setValue(strTitle);
                    jobidref.child("Details").setValue(strDetails);
                    jobidref.child("Amount").setValue(strAmount);
                    Submit.setVisibility(View.GONE);
                    ShowTitle.setVisibility(View.GONE);
                    CheckTitle.setVisibility(View.VISIBLE);

                    ErrorAll.setVisibility(View.GONE);
                    ErrorTitle.setVisibility(View.GONE);
                    Title.setVisibility(View.VISIBLE);
                    Title.setText("");
                    Details.setText("");
                    Amount.setText("");
                    Links.setText("");
                    NewTitle="";
                    P.dismiss();

                    TextView Showtitlevalue=(TextView)findViewById(R.id.showtitle);
                    TextView Showdetailsvalue=(TextView)findViewById(R.id.showdetails);
                    TextView Showamountvalue=(TextView)findViewById(R.id.showamount);
                    TextView Showlinkvalue=(TextView)findViewById(R.id.showlinks);
                    final LinearLayout Showvaluelayout=(LinearLayout)findViewById(R.id.showvaluelayout);
                    ScrollView sview=(ScrollView) findViewById(R.id.scrollview);

                    if (!strLinks.isEmpty()){
                        jobidref.child("Links").setValue(strLinks);
                        Showlinkvalue.setVisibility(View.VISIBLE);
                        Showlinkvalue.setText(strLinks);
                    }
                    else{
                        Showlinkvalue.setVisibility(View.GONE);
                    }

                    Showtitlevalue.setText(strTitle);
                    Showdetailsvalue.setText(strDetails);
                    Showamountvalue.setText(strAmount);
                    Showvaluelayout.setVisibility(View.VISIBLE);
                    sview.fullScroll(ScrollView.FOCUS_UP);
                    Close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Showvaluelayout.setVisibility(View.GONE);
                        }
                    });

                }


            }
        });
    }
}