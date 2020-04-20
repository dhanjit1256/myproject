package com.example.DCompany.Job;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobselcectActivity extends AppCompatActivity {
    ListView listview;
    ArrayList<String> arraylist;
    ArrayAdapter<String> arrayAdapter;

    String CurrentUserID, CurrentUserIs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobselcect);

        Bundle getUser=getIntent().getExtras();
        String user= getUser.getString("User");
        CurrentUserIs=user.substring(0,4);
        CurrentUserID=user.substring(4);

        listview=(ListView)findViewById(R.id.listview) ;


        arraylist=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(JobselcectActivity.this, android.R.layout.simple_list_item_1, arraylist);
        listview.setAdapter(arrayAdapter);

        final DatabaseReference databsereference= FirebaseDatabase.getInstance().getReference().child("PreDefine").child("JobList");
        databsereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String jobs = dataSnapshot1.getValue(String.class);
                    arraylist.add(jobs);
                    arrayAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                FirebaseUser CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
//                String userid=CurrentUser.getUid();

                String job= listview.getItemAtPosition(position).toString();

                if (TextUtils.equals(CurrentUserIs, "USER")) {
                    Toast.makeText(JobselcectActivity.this, CurrentUserIs, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(JobselcectActivity.this, ViewJob.class);
                    intent.putExtra("SelectedJob", job);
                    intent.putExtra("Userid", CurrentUserID);
                    intent.putExtra("Useris", CurrentUserIs);
                    startActivity(intent);
                }
                else if (TextUtils.equals(CurrentUserIs, "ADMI")){
                    Intent intent = new Intent(JobselcectActivity.this, ViewJob.class);
                    intent.putExtra("SelectedJob", job);
                    intent.putExtra("SelectedJob", job);
                    intent.putExtra("Userid", CurrentUserID);
                    intent.putExtra("Useris", CurrentUserIs);
                    startActivity(intent);
                }

            }
        });
    }
}
