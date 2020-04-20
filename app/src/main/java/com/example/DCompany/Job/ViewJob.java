package com.example.DCompany.Job;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.DCompany.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewJob extends AppCompatActivity {
    TextView Title, Details, Amount;
    Button Accept, Reject, Delete;
    String jobkey, userid, useris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);
        Window w=this.getWindow();
        w.setStatusBarColor(getResources().getColor(R.color.green));

        Title=(TextView)findViewById(R.id.titlejob);
        Details=(TextView)findViewById(R.id.detailsjob);
        Amount=(TextView)findViewById(R.id.amountjob);
        Accept=(Button) findViewById(R.id.accept);
        Reject=(Button)findViewById(R.id.reject);
        Delete=(Button)findViewById(R.id.delete);

        Bundle bundle=getIntent().getExtras();
        jobkey=bundle.getString("SelectedJob");
        userid=bundle.getString("Userid");
        useris=bundle.getString("Useris");

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userdatabase = FirebaseDatabase.getInstance().getReference().child("Musers").child(userid).child("HollyDayJob");
                userdatabase.child("Accept").setValue(jobkey);

                DatabaseReference joblistdatabase= FirebaseDatabase.getInstance().getReference().child("PreDefine").child("JobList");
                joblistdatabase.child(jobkey).removeValue();
            }
        });

        Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (TextUtils.equals(useris, "USER")){
            Delete.setVisibility(View.GONE);
            ShowJobDetails();
        }
        else if (TextUtils.equals(useris, "ADMI")){
            Accept.setVisibility(View.GONE);
            Reject.setVisibility(View.GONE);
            ShowJobDetails();
        }
        else{

        }
    }


    private void ShowJobDetails() {
        DatabaseReference jobdetailsdatabase= FirebaseDatabase.getInstance().getReference().child("PreDefine").child("JobDetails").child(jobkey);
        jobdetailsdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title=dataSnapshot.child("Title").getValue().toString(); Title.setText(title);
                String details=dataSnapshot.child("Details").getValue().toString(); Details.setText(details);
                String amount=dataSnapshot.child("Amount").getValue().toString(); Amount.setText(amount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
