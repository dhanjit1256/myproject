package com.example.DCompany.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.DCompany.Admin.AdminHome.eRef;

public class AdminAttendance extends AppCompatActivity {
    Date date;
    String MonthYear;
    ArrayList list;
    ListView listView;
    String u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance);
        listView=(ListView)findViewById(R.id.xlistview);
        list=new ArrayList();
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(AdminAttendance.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        eRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    String name=d.child("User_Name").getValue().toString();

                    list.add(name);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selecteduser=listView.getItemAtPosition(position).toString();

                Query root=eRef.orderByChild("User_Name").equalTo(selecteduser);
                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d:dataSnapshot.getChildren()) {
                            u = d.getKey().toString();
                            Intent intent=new Intent(AdminAttendance.this, Viewattendance.class);
                            intent.putExtra("User", u);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
//        Query a = eRef.orderByChild("User_Name").equalTo("Dhanjit Deka");
//
//        a.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot c:dataSnapshot.getChildren()) {
//                    String key=c.getKey().toString();
//                    Toast.makeText(AdminAttendance.this, key, Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//

//        newRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.child("21:"+MonthYear).exists()){
//                    Toast.makeText(AdminAttendance.this, "adsdsdshd", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(AdminAttendance.this, "aaaaaaaaaaaaaaaaaaaaaaaaaa", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        date=new Date();
        SimpleDateFormat dateformat=new SimpleDateFormat("dd:MM:YYYY");
        String currentdate=dateformat.format(date);
        MonthYear=currentdate.substring(3);
    }
}
