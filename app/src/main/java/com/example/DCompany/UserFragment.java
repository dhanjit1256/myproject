package com.example.DCompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;


public class UserFragment extends Fragment {
    @BindView(R.id.bankname)
    Spinner bankname;
    @BindView(R.id.accountnumber)
    EditText accountnumber;
    @BindView(R.id.ifsccode)
    EditText ifsccode;
    @BindView(R.id.accountholdername)
    EditText accountholdername;
    @BindView(R.id.accountdetails)
    LinearLayout accountdetails;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference, professionalRef;
    private FirebaseUser currentUser;


    String uid, School, College, Skill;
    TextView school, college, skill, One, Two, Three;
    EditText collegeedit, schooledit, skilledit;
    Button save, edit, logout;
    Window window;

    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user, container, false);

        Button button = (Button) view.findViewById(R.id.testttt);

        logout = (Button) view.findViewById(R.id.xlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //Personal Details
        final TextView name = (TextView) view.findViewById(R.id.xname);
//      final TextView email=(TextView)view.findViewById(R.id.xemail);
//      final TextView phone=(TextView)view.findViewById(R.id.xphone);
        final TextView address = (TextView) view.findViewById(R.id.xaddress);
        final TextView cast = (TextView) view.findViewById(R.id.xcast);
        final TextView gender = (TextView) view.findViewById(R.id.xgender);
        final TextView mname = (TextView) view.findViewById(R.id.xmname);
        final TextView fname = (TextView) view.findViewById(R.id.xfname);
        final TextView homecontact = (TextView) view.findViewById(R.id.xhomephone);
        final TextView dob = (TextView) view.findViewById(R.id.xdob);
        final LinearLayout personal = (LinearLayout) view.findViewById(R.id.xpersonal);
        final Button personalview = (Button) view.findViewById(R.id.xpersonalview);
        final Button personalhide = (Button) view.findViewById(R.id.xpersonalhide);

        personalview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personal.setVisibility(View.VISIBLE);
                personalview.setVisibility(View.GONE);
                personalhide.setVisibility(View.VISIBLE);
            }
        });
        personalhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personal.setVisibility(View.GONE);
                personalhide.setVisibility(View.GONE);
                personalview.setVisibility(View.VISIBLE);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference("Musers");
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user_name = dataSnapshot.child(uid).child("User_Name").getValue().toString();
                name.setText(user_name);
//                String user_phone=dataSnapshot.child(uid).child("User_Phone").getValue().toString();     phone.setText(user_phone);
//                String user_email=currentUser.getEmail().toString();    email.setText(user_email);
//                String user_id=uid.toString();    userid.setText(user_id);
                String user_cast = dataSnapshot.child(uid).child("User_Cast").getValue().toString();
                cast.setText(user_cast);
                String user_address = dataSnapshot.child(uid).child("User_Address").getValue().toString();
                address.setText(user_address);
                String user_gender = dataSnapshot.child(uid).child("User_Gender").getValue().toString();
                gender.setText(user_gender);
                String user_mother = dataSnapshot.child(uid).child("User_Mother_Name").getValue().toString();
                mname.setText(user_mother);
                String user_father = dataSnapshot.child(uid).child("User_Father_Name").getValue().toString();
                fname.setText(user_father);
                String user_HOMEMFnumber = dataSnapshot.child(uid).child("User_Home_Phone").getValue().toString();
                homecontact.setText(user_HOMEMFnumber);
                String user_dob = dataSnapshot.child(uid).child("User_DOB").getValue().toString();
                dob.setText(user_dob);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        //End Personal View
        //Professional View
        school = (TextView) view.findViewById(R.id.xschool);
        college = (TextView) view.findViewById(R.id.xcollege);
        skill = (TextView) view.findViewById(R.id.xskill);

        final EditText schooledit = (EditText) view.findViewById(R.id.editschool);
        final EditText collegeedit = (EditText) view.findViewById(R.id.editcollege);
        final EditText skilledit = (EditText) view.findViewById(R.id.editskill);

        final TextView ErrorS = (TextView) view.findViewById(R.id.errorschool);
        final TextView ErrorC = (TextView) view.findViewById(R.id.errorcollege);
        final TextView Errors = (TextView) view.findViewById(R.id.errorskill);

        final TextView Sveprofessional = (TextView) view.findViewById(R.id.saveprofessional);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.xpb);
        final Button professionalbutton = (Button) view.findViewById(R.id.xprofessionalbutton);
        final Button professionalbuttonhide = (Button) view.findViewById(R.id.xprofessionalbuttonhide);
        final LinearLayout professionalview = (LinearLayout) view.findViewById(R.id.xprofessionalview);
        final LinearLayout professionaledit = (LinearLayout) view.findViewById(R.id.xprofessionaledit);

        final DatabaseReference newRef = mReference.child(uid);

        Sveprofessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strschool = schooledit.getText().toString().trim();
                String strcollege = collegeedit.getText().toString().trim();
                String strskill = skilledit.getText().toString().trim();
                if (strschool.isEmpty()) {
                    ErrorS.setText("This fild can not be empty");
                    ErrorS.setVisibility(View.VISIBLE);
                } else if (strcollege.isEmpty()) {
                    ErrorC.setText("This fild can not be empty");
                    ErrorC.setVisibility(View.VISIBLE);
                } else if (strskill.isEmpty()) {
                    Errors.setText("This fild can not be empty");
                    Errors.setVisibility(View.VISIBLE);
                } else {
                    newRef.child("Professional Details").child("School Name").setValue(strschool);
                    newRef.child("Professional Details").child("College Name").setValue(strcollege);
                    newRef.child("Professional Details").child("Skills").setValue(strskill);

                    professionalview.setVisibility(View.GONE);
                    professionalbutton.setVisibility(View.VISIBLE);
                    professionalbuttonhide.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    professionaledit.setVisibility(View.GONE);
                }
            }
        });


        professionalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                professionalbutton.setVisibility(View.GONE);
                professionalbuttonhide.setVisibility(View.VISIBLE);

                newRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Professional Details").exists()) {
                            String Schoolname = dataSnapshot.child("Professional Details").child("School Name").getValue().toString();
                            String Collegename = dataSnapshot.child("Professional Details").child("College Name").getValue().toString();
                            String Skilldata = dataSnapshot.child("Professional Details").child("Skills").getValue().toString();
                            school.setText(Schoolname);
                            college.setText(Collegename);
                            skill.setText(Skilldata);
                            professionalview.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                        } else {
                            professionaledit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        professionalbuttonhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professionalview.setVisibility(View.GONE);
                professionalbutton.setVisibility(View.VISIBLE);
                professionalbuttonhide.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                professionaledit.setVisibility(View.GONE);
            }
        });
        //end professional


        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        final TextView comp=(TextView) getView().findViewById(R.id.xplscompro) ;
//        DatabaseReference newRef=mReference.child(uid);
//        newRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.child("Professional Details").exists()){
//                    comp.setVisibility(View.VISIBLE);
//                    comp.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), AfterloginActivity.class);
//                            startActivity(intent);
//                        }
//                        });
//
//                }
//                else {
//                    comp.setVisibility(View.GONE);
//                }
//
//
//                }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
