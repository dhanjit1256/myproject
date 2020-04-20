package com.example.DCompany;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.DCompany.Admin.AdminAttendance;
import com.example.DCompany.Admin.AdminViewAttendance;
import com.example.DCompany.Admin.Viewattendance;
import com.example.DCompany.Job.JobselcectActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;



public class HomeFragment extends Fragment {

    LocationManager locationManager;
    LocationListener locationListener;
    Date time, date;
    Button Location, AcceptHolyday, RejectHolyday, Uploadfile;
    TextView  Longitude,HolydayText;
    ProgressDialog progressDialog;
    String CurrentTime, OfficeTimeHH, CurrentDate, officeloc1, officeloc2, officestarttimehh, finalvalue, UserID, UserName;
    DatabaseReference mRef, newRef;
    FirebaseAuth mAuth;
    FirebaseUser CurrentUser;
    ProgressBar p;
    ScrollView MainFrame;
    RelativeLayout HolydayPopup;

//    String CHANDAL_ID="p";
//    int ID=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        MainFrame = (ScrollView) v.findViewById(R.id.mainframe);
        p = (ProgressBar) v.findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait, We are Checking Your Current Location, It will take some time...");
        Location = (Button) v.findViewById(R.id.loc);
        Longitude = (TextView) v.findViewById(R.id.generatelocation);
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();
        UserID = CurrentUser.getUid().toString();
        mRef = FirebaseDatabase.getInstance().getReference("Musers").child(UserID);
        newRef = mRef.child("Attendance");

        LinearLayout Test=(LinearLayout)v.findViewById(R.id.test);
        LinearLayout CheckAttendance =(LinearLayout)v.findViewById(R.id.checkattendance);

        CheckAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), Viewattendance.class);
                intent.putExtra("User", UserID);
                startActivity(intent);

            }
        });


        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getActivity(), MainActivity2.class);
                startActivity(intent);
            }
        });


        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                time = new Date();
                SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm ");
                CurrentTime = timeformat.format(time).trim();
                OfficeTimeHH = CurrentTime.substring(0, 2);
                officeloc1 = "26.182";
                officeloc2 = "26.183";
                officestarttimehh = "03";

                if (OfficeTimeHH.equals(officestarttimehh)) {
                    locationManager.requestLocationUpdates("gps", 1000, 1, locationListener);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "These is not the right time for Attendance, Please try at 9AM-10AM",Toast.LENGTH_LONG).show();

                }

            }
        });

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {

                double a = location.getLatitude();
                String value = new Double(a).toString().trim();
                finalvalue = value.substring(0, 6).trim();

                    if (officeloc1.equals(finalvalue) || officeloc2.equals(finalvalue)) {
                        DatabaseReference finalref=newRef.child(CurrentDate);
                        finalref.setValue(CurrentTime+" Present "+CurrentDate);
                        Longitude.setText("Great! Your attendance for " + CurrentDate + " Successfully Submitted !");
                        Longitude.setTextColor(Color.BLUE);
                        Longitude.setTextSize(20);
                        Longitude.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();

                        DatabaseReference notificationref=mRef.child("Notification");
                        notificationref.child(CurrentDate+CurrentTime).setValue(CurrentTime+CurrentDate+"Your attendace has been successfully submited");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Please enter office complex and Try again", Toast.LENGTH_LONG).show();
                    }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getContext(), "Please turn your GPS on", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        };


        HolydayPopup=(RelativeLayout)v.findViewById(R.id.holydaypopup);
        HolydayText = (TextView) v.findViewById(R.id.holydaytext);
        AcceptHolyday=(Button)v.findViewById(R.id.yeshollyday);
        RejectHolyday = (Button) v.findViewById(R.id.nohollyday);
        Uploadfile=(Button)v.findViewById(R.id.holidayupload);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        p.setVisibility(View.VISIBLE);
        date= new Date();
        SimpleDateFormat dateformat=new SimpleDateFormat("dd:MM:YYYY");
        CurrentDate=dateformat.format(date);

        //Check for attendace
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(CurrentDate).exists()) {
                    Longitude.setVisibility(View.VISIBLE);
                    Location.setVisibility(View.GONE);

                    String setattendance = dataSnapshot.child(CurrentDate).getValue().toString();
                    Toast.makeText(getContext(), "Your attendance is already taken", Toast.LENGTH_LONG).show();
                    Longitude.setText("Your attendance is taken !" + "\n" + "Date: " + CurrentDate + " Time: " + setattendance);

                    Longitude.setTextColor(Color.BLUE);
                    Longitude.setTextSize(20);
                    p.setVisibility(View.GONE);
                    MainFrame.setVisibility(View.VISIBLE);
                }
                else{
                    p.setVisibility(View.GONE);
                    MainFrame.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Check for job, hollyayjob
        final DatabaseReference holydayref=FirebaseDatabase.getInstance().getReference().child("PreDefine").child("HollydayList");
        final DatabaseReference aa= mRef.child("HollyDayJob");
        aa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Accept").exists()){
                    HolydayPopup.setVisibility(View.VISIBLE);
                    AcceptHolyday.setVisibility(View.GONE);
                    RejectHolyday.setVisibility(View.GONE);
                    HolydayText.setText("Please upload your job details. We are appreciate your choice.");
                    Uploadfile.setVisibility(View.VISIBLE);
                    p.setVisibility(View.GONE);
                    MainFrame.setVisibility(View.VISIBLE);
                }
                else if (dataSnapshot.child(CurrentDate).exists()){
                    HolydayPopup.setVisibility(View.GONE);
                    p.setVisibility(View.GONE);
                    MainFrame.setVisibility(View.VISIBLE);
                }
                else {
                    holydayref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(CurrentDate).exists()){
                                HolydayPopup.setVisibility(View.VISIBLE);
                                HolydayText.setText("Hey! Today is a holyday, Do you want to work for us from home");
                                p.setVisibility(View.GONE);
                                MainFrame.setVisibility(View.VISIBLE);
                                AcceptHolyday.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(getActivity(), JobselcectActivity.class);
                                        intent.putExtra("User", "USER"+UserID);
                                        startActivity(intent);
                                    }
                                });
                                RejectHolyday.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HolydayPopup.setVisibility(View.GONE);
                                        aa.child(CurrentDate).setValue("Reject");
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserName=dataSnapshot.child("User_Name").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

}
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.child("Professional Details").exists()){
//
//                    Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    NotificationCompat.Builder nnnn=new NotificationCompat.Builder(getActivity(), CHANDAL_ID);
//                    nnnn.setSmallIcon(R.drawable.address);
//                    nnnn.setContentTitle("DFBDFGD");
//                    nnnn.setContentText("fafasfhasf");
//                    nnnn.setSound(uri);
//                    Fragment touser=new UserFragment();
//                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//                    FragmentTransaction f=fragmentManager.beginTransaction();
//                    f.replace(R.id.xmainframe, touser);
//                    f.addToBackStack(null);
//                    f.commit();
//
//                    NotificationManagerCompat nnnnnnn= NotificationManagerCompat.from(getContext());
//                    nnnnnnn.notify(ID, nnnn.build());
//                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
//
//                        NotificationChannel notificationChannel=new NotificationChannel(CHANDAL_ID, "Welcome Notification",NotificationManager.IMPORTANCE_DEFAULT);
//                        NotificationManager notificationManager=(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//                        notificationManager.createNotificationChannel(notificationChannel);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
