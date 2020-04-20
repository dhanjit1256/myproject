package com.example.DCompany.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminViewAttendance extends AppCompatActivity {
    Date date;
    String currentdate, monthyear;
    String UID;
    DatabaseReference userref,predefine;

    TextView Date1, Time1, Option1, Date2, Time2, Option2, Date3, Time3, Option3, Date4, Time4, Option4, Date5, Time5, Option5,
    Date6, Time6, Option6, Date7, Time7, Option7, Date8, Time8, Option8, Date9, Time9, Option9, Date10, Time10, Option10,
            Date11, Time11, Option11, Date12, Time12, Option12, Date13, Time13, Option13, Date14, Time14, Option14, Date15, Time15, Option15,
            Date16, Time16, Option16, Date17, Time17, Option17, Date18, Time18, Option18, Date19, Time19, Option19, Date20, Time20, Option20,
            Date21, Time21, Option21, Date22, Time22, Option22, Date23, Time23, Option23, Date24, Time24, Option24, Date25, Time25, Option25,
            Date26, Time26, Option26, Date27, Time27, Option27, Date28, Time28, Option28, Date29, Time29, Option29, Date30, Time30, Option30,
            Date31, Time31, Option31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_attendance);


        Date1=(TextView)findViewById(R.id.date1); Time1=(TextView)findViewById(R.id.time1); Option1=(TextView)findViewById(R.id.option1);
        Date2=(TextView)findViewById(R.id.date2); Time2=(TextView)findViewById(R.id.time2); Option2=(TextView)findViewById(R.id.option2);
        Date3=(TextView)findViewById(R.id.date3); Time3=(TextView)findViewById(R.id.time3); Option3=(TextView)findViewById(R.id.option3);
        Date4=(TextView)findViewById(R.id.date4); Time4=(TextView)findViewById(R.id.time4); Option4=(TextView)findViewById(R.id.option4);
        Date5=(TextView)findViewById(R.id.date5); Time5=(TextView)findViewById(R.id.time5); Option5=(TextView)findViewById(R.id.option5);
        Date6=(TextView)findViewById(R.id.date6); Time6=(TextView)findViewById(R.id.time6); Option6=(TextView)findViewById(R.id.option6);
        Date7=(TextView)findViewById(R.id.date7); Time7=(TextView)findViewById(R.id.time7); Option7=(TextView)findViewById(R.id.option7);
        Date8=(TextView)findViewById(R.id.date8); Time8=(TextView)findViewById(R.id.time8); Option8=(TextView)findViewById(R.id.option8);
        Date9=(TextView)findViewById(R.id.date9); Time9=(TextView)findViewById(R.id.time9); Option9=(TextView)findViewById(R.id.option9);
        Date10=(TextView)findViewById(R.id.date10); Time10=(TextView)findViewById(R.id.time10); Option10=(TextView)findViewById(R.id.option10);
        Date11=(TextView)findViewById(R.id.date11); Time11=(TextView)findViewById(R.id.time11); Option11=(TextView)findViewById(R.id.option11);
        Date12=(TextView)findViewById(R.id.date12); Time12=(TextView)findViewById(R.id.time12); Option12=(TextView)findViewById(R.id.option12);
        Date13=(TextView)findViewById(R.id.date13); Time13=(TextView)findViewById(R.id.time13); Option13=(TextView)findViewById(R.id.option13);
        Date14=(TextView)findViewById(R.id.date14); Time14=(TextView)findViewById(R.id.time14); Option14=(TextView)findViewById(R.id.option14);
        Date15=(TextView)findViewById(R.id.date15); Time15=(TextView)findViewById(R.id.time15); Option15=(TextView)findViewById(R.id.option15);
        Date16=(TextView)findViewById(R.id.date16); Time16=(TextView)findViewById(R.id.time16); Option16=(TextView)findViewById(R.id.option16);
        Date17=(TextView)findViewById(R.id.date17); Time17=(TextView)findViewById(R.id.time17); Option17=(TextView)findViewById(R.id.option17);
        Date18=(TextView)findViewById(R.id.date18); Time18=(TextView)findViewById(R.id.time18); Option18=(TextView)findViewById(R.id.option18);
        Date19=(TextView)findViewById(R.id.date19); Time19=(TextView)findViewById(R.id.time19); Option19=(TextView)findViewById(R.id.option19);
        Date20=(TextView)findViewById(R.id.date20); Time20=(TextView)findViewById(R.id.time20); Option20=(TextView)findViewById(R.id.option20);
        Date21=(TextView)findViewById(R.id.date21); Time21=(TextView)findViewById(R.id.time21); Option21=(TextView)findViewById(R.id.option21);
        Date22=(TextView)findViewById(R.id.date22); Time22=(TextView)findViewById(R.id.time22); Option22=(TextView)findViewById(R.id.option22);
        Date23=(TextView)findViewById(R.id.date23); Time23=(TextView)findViewById(R.id.time23); Option23=(TextView)findViewById(R.id.option23);
        Date24=(TextView)findViewById(R.id.date24); Time24=(TextView)findViewById(R.id.time24); Option24=(TextView)findViewById(R.id.option24);
        Date25=(TextView)findViewById(R.id.date25); Time25=(TextView)findViewById(R.id.time25); Option25=(TextView)findViewById(R.id.option25);
        Date26=(TextView)findViewById(R.id.date26); Time26=(TextView)findViewById(R.id.time26); Option26=(TextView)findViewById(R.id.option26);
        Date27=(TextView)findViewById(R.id.date27); Time27=(TextView)findViewById(R.id.time27); Option27=(TextView)findViewById(R.id.option27);
        Date28=(TextView)findViewById(R.id.date28); Time28=(TextView)findViewById(R.id.time28); Option28=(TextView)findViewById(R.id.option28);
        Date29=(TextView)findViewById(R.id.date29); Time29=(TextView)findViewById(R.id.time29); Option29=(TextView)findViewById(R.id.option29);
        Date30=(TextView)findViewById(R.id.date30); Time30=(TextView)findViewById(R.id.time30); Option30=(TextView)findViewById(R.id.option30);
        Date31=(TextView)findViewById(R.id.date31); Time31=(TextView)findViewById(R.id.time31); Option31=(TextView)findViewById(R.id.option31);

        date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd:MM:YYYY");
        currentdate=dateFormat.format(date);
        monthyear=currentdate.substring(2);


        Bundle b=getIntent().getExtras();
        UID= b.getString("User");
        userref= FirebaseDatabase.getInstance().getReference().child("Musers").child(UID).child("Attendance");
        predefine=FirebaseDatabase.getInstance().getReference().child("PreDefine").child("HollydayList");

            function("01" + monthyear, Date1, Time1, Option1);
            function("02" + monthyear, Date2, Time2, Option2);
            function("03" + monthyear, Date3, Time3, Option3);
            function("04" + monthyear, Date4, Time4, Option4);
            function("05" + monthyear, Date5, Time5, Option5);
            function("06" + monthyear, Date6, Time6, Option6);
            function("07" + monthyear, Date7, Time7, Option7);
            function("08" + monthyear, Date8, Time8, Option8);
            function("09" + monthyear, Date9, Time9, Option9);
            function("10" + monthyear, Date10, Time10, Option10);
            function("11" + monthyear, Date11, Time11, Option11);
            function("12" + monthyear, Date12, Time12, Option12);
            function("13" + monthyear, Date13, Time13, Option13);
            function("14" + monthyear, Date14, Time14, Option14);
            function("15" + monthyear, Date15, Time15, Option15);
            function("16" + monthyear, Date16, Time16, Option16);
            function("17" + monthyear, Date17, Time17, Option17);
            function("18" + monthyear, Date18, Time18, Option18);
            function("19" + monthyear, Date19, Time19, Option19);
            function("20" + monthyear, Date20, Time20, Option20);
            function("21" + monthyear, Date21, Time21, Option21);
            function("22" + monthyear, Date22, Time22, Option22);
            function("23" + monthyear, Date23, Time23, Option23);
            function("24" + monthyear, Date24, Time24, Option24);
            function("25" + monthyear, Date25, Time25, Option25);
            function("26" + monthyear, Date26, Time26, Option26);
            function("27" + monthyear, Date27, Time27, Option27);
            function("28" + monthyear, Date28, Time28, Option28);
            function("29" + monthyear, Date29, Time29, Option29);
            function("30" + monthyear, Date30, Time30, Option30);
            function("31" + monthyear, Date31, Time31, Option31);



    }


    private void function(final String s, final TextView d, final TextView t, final TextView o)
    {
        d.setText(s);
                    userref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(s).exists()) {
                                String time = dataSnapshot.child(s).getValue().toString();
                                String timeis = time.substring(0, 5);
                                String details = time.substring(6, 13);
                                String datee = time.substring(14, 23);
                                t.setText(timeis);
                                o.setText(details);
                                t.setTextColor(Color.GREEN);
                                o.setTextColor(Color.GREEN);
                                d.setTextColor(Color.GREEN);

                            }
                            else {

                                predefine.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child(s).exists()){
                                            String desk=dataSnapshot.child(s).getValue().toString();
                                            o.setText(desk);
                                            o.setTextColor(Color.BLUE);
                                            d.setTextColor(Color.BLUE);
                                        }
                                        else {
                                            t.setText("Absent");
                                            o.setText("Absent");

                                            d.setTextColor(Color.RED);
                                            t.setTextColor(Color.RED);
                                            o.setTextColor(Color.RED);

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

}
