package com.example.DCompany.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Viewattendance extends AppCompatActivity {
    @BindView(R.id.presentcount)
    TextView presentcount;
    @BindView(R.id.holidaycount)
    TextView holidaycount;
    @BindView(R.id.absentcount)
    TextView absentcount;


    Date date, day;
    String currentdate, monthyear, currentday, dayoneformonth,monthintext;
    String UID;
    DatabaseReference userref, predefine;
    int monthis, pcount, hcount, acount;
    int monthdecrease = 1;
    int yeardecrease = 0;
    int totaldays;
    public int[] ID = {0, R.id.D1, R.id.D2, R.id.D3, R.id.D4, R.id.D5, R.id.D6, R.id.D7, R.id.D8, R.id.D9, R.id.D10, R.id.D11, R.id.D12
            , R.id.D13, R.id.D14, R.id.D15, R.id.D16, R.id.D17, R.id.D18, R.id.D19, R.id.D20, R.id.D21, R.id.D22, R.id.D23, R.id.D24
            , R.id.D25, R.id.D26, R.id.D27, R.id.D28, R.id.D29, R.id.D30, R.id.D31, R.id.D32, R.id.D33, R.id.D34, R.id.D35,
            R.id.D36, R.id.D37, R.id.D38, R.id.D39, R.id.D40, R.id.D41, R.id.D42};
    @BindView(R.id.topmonthyear)
    TextView topmonthyear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewattendance);
        ButterKnife.bind(this);


        date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:YYYY");
        currentdate = dateFormat.format(date);
        monthyear = currentdate.substring(2);
        String month = currentdate.substring(3, 5);
        monthis= Integer.parseInt(month);
        String year = currentdate.substring(6, 10);


        Bundle b = getIntent().getExtras();
        UID = b.getString("User");
        userref = FirebaseDatabase.getInstance().getReference().child("Musers").child(UID).child("Attendance");
        predefine = FirebaseDatabase.getInstance().getReference().child("PreDefine").child("HolidayList");


        main(Integer.parseInt(month), Integer.parseInt(year));
    }


    public void main(int monthfuction, int yearfunction) {
        getperfectday(yearfunction, monthfuction - 1);

        for (int i = 1; i <= totaldays; i++) {

            String newday;
            TextView t;
            t = findViewById(ID[i]);

            if (dayoneformonth.equals("Mon")) {
                t = findViewById(ID[i]);
            } else if (dayoneformonth.equals("Tue")) {
                t = findViewById(ID[i + 1]);
            } else if (dayoneformonth.equals("Wed")) {
                t = findViewById(ID[i + 2]);
            } else if (dayoneformonth.equals("Thu")) {
                t = findViewById(ID[i + 3]);
            } else if (dayoneformonth.equals("Fri")) {
                t = findViewById(ID[i + 4]);
            } else if (dayoneformonth.equals("Sat")) {
                t = findViewById(ID[i + 5]);
            } else if (dayoneformonth.equals("Sun")) {
                t = findViewById(ID[i + 6]);
            } else {
                Toast.makeText(Viewattendance.this, "Something is wrong" + currentday, Toast.LENGTH_LONG).show();
            }


            String d = Integer.toString(i);
            if (d.length() == 1) {
                newday = "0" + d;
            } else {
                newday = d;
            }
            String newmonthfun;
            String m = Integer.toString(monthfuction);
            if (m.length() == 1) {
                newmonthfun = "0" + m;
            } else {
                newmonthfun = m;
            }

            topmonthyear.setText(monthintext + ", " + yearfunction);
            Toast.makeText(Viewattendance.this,   totaldays+newday + ":" + newmonthfun + ":" + yearfunction, Toast.LENGTH_LONG).show();
            checkattendance(Integer.toString(yearfunction), newmonthfun, newday + ":" + newmonthfun + ":" + yearfunction, t, newday);
        }
    }


    public void getperfectday(int yearint, int monthint) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yearint);
        c.set(Calendar.MONTH, monthint);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date fe = c.getTime();
        totaldays=c.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayoneformonth = fe.toString().substring(0, 3);
        monthintext=fe.toString().substring(4, 7);
    }


    private void checkattendance(String y, String m, String dates, TextView textView, String c) {


        userref.child(y).child(m).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(dates).exists()) {
                    textView.setText(c);
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setTextColor(Color.BLUE);
                    textView.setVisibility(View.VISIBLE);

                    //SetPresentCount
                    pcount = (int) dataSnapshot.getChildrenCount();
                    String h = Integer.toString(pcount);
                    presentcount.setText(h);
                } else {
                    predefine.child(y).child(m).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(dates).exists()) {
                                textView.setText(c);
                                textView.setBackgroundColor(Color.RED);
                                textView.setVisibility(View.VISIBLE);

                                //SetHolidayCount
                                hcount = (int) dataSnapshot.getChildrenCount();
                                String h = Integer.toString(hcount);
                                holidaycount.setText(h);
                            } else {
                                textView.setText(c);
                                textView.setBackgroundColor(Color.WHITE);
                                textView.setTextColor(Color.RED);
                                textView.setVisibility(View.VISIBLE);

                                //SetAbsentCount
                                acount = totaldays- (hcount+pcount);
                                String h = Integer.toString(acount);
                                absentcount.setText(h);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clickprevious(View view) {
        for (int i = 1; i <= 42; i++) {
            TextView t = findViewById(ID[i]);
            t.setVisibility(View.INVISIBLE);
        }
        presentcount.setText("");

        int newdate = monthis - monthdecrease;
        int year = 2020 - yeardecrease;

        main(newdate, year);

        monthdecrease++;
        if (newdate <= 1) {
            monthdecrease = 0;
            monthis = 12;
            yeardecrease++;
        }

    }


}
