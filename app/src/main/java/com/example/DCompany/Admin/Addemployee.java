package com.example.DCompany.Admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Addemployee extends AppCompatActivity {
    String date;
    @BindView(R.id.showdate)
    TextView showdate;
    @BindView(R.id.namevalue)
    EditText namevalue;
    @BindView(R.id.namefild)
    TextInputLayout namefild;
    @BindView(R.id.phonevalue)
    EditText phonevalue;
    @BindView(R.id.phonefild)
    TextInputLayout phonefild;
    @BindView(R.id.gendergroup)
    RadioGroup gendergroup;
    @BindView(R.id.errorgender)
    TextView errorgender;
    @BindView(R.id.doberror)
    TextView doberror;
    @BindView(R.id.successtext)
    TextView successtext;
    @BindView(R.id.successmsg)
    LinearLayout successmsg;
    @BindView(R.id.errortext)
    TextView errortext;
    @BindView(R.id.errormsg)
    LinearLayout errormsg;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemployee);
        ButterKnife.bind(this);
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setTitle("Adding Employee..");
        pd.setMessage("Please wait...");
    }

    public void showpicdate(View view) {

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setCancelable(false);
        View v = getLayoutInflater().inflate(R.layout.datepickerlayout, null);

        ad.setNegativeButton("Cancel", null);
        ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatePicker datepicker = v.findViewById(R.id.datepicker);
                String day = Integer.toString(datepicker.getDayOfMonth());
                String year = Integer.toString(datepicker.getYear());
                String month = Integer.toString(datepicker.getMonth() + 1);

                String newday, newmonth;

                if (day.length() == 1) {
                    newday = "0" + day;
                } else {
                    newday = day;
                }

                if (month.length() == 1) {
                    newmonth = "0" + month;
                } else {
                    newmonth = month;
                }

                date = newday + ":" + newmonth + ":" + year;
                showdate.setText(date);
                Toast.makeText(Addemployee.this, date, Toast.LENGTH_SHORT).show();

            }
        });
        ad.setView(v);
        ad.show();
    }

    public void addemployee(View view) {
        pd.show();
        String ename = namevalue.getText().toString();
        String ephone = phonevalue.getText().toString();
        String edob = showdate.getText().toString();
        String gender = "";

        int selectgender = gendergroup.getCheckedRadioButtonId();
        if (selectgender == R.id.Male) {
            gender = "Male";

            errorgender.setVisibility(View.VISIBLE);
            errorgender.setTextColor(Color.BLUE);
            errorgender.setText("You selected Male");
        }
        else if (selectgender == R.id.Femail) {
            gender = "Femail";

            errorgender.setVisibility(View.VISIBLE);
            errorgender.setTextColor(Color.BLUE);
            errorgender.setText("You selected Femail");
        } else {
            errorgender.setVisibility(View.VISIBLE);
        }

        if (ename.isEmpty() || ename.length()<3) {
            pd.dismiss();
            namefild.setError("Please Enter Employee's Name !");
        } else {
            namefild.setError("");
        }
        if (ephone.isEmpty() || ephone.length()<10 || ephone.length()>10) {
            pd.dismiss();
            phonefild.setError("Please Provide Employee's Phone Number");
        } else {
            phonefild.setError("");
        }
        if (edob.isEmpty()) {
            pd.dismiss();
            doberror.setVisibility(View.VISIBLE);
        } else {
            doberror.setVisibility(View.GONE);
        }

        if (!ename.isEmpty()&& ename.length()>=3 && !ephone.isEmpty()&& ephone.length()==10  && !edob.isEmpty() && !gender.isEmpty()) {
            DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("PreDefine").child("Employees");

            String finalGender = gender;

            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(ephone).exists()) {
                        phonefild.setError("Phone number is already in use");
                        errortext.setText("Sorry! Your phone number" + ephone + " is associated with other account please try with different phone number");
                        errormsg.setVisibility(View.VISIBLE);
                        pd.dismiss();
                    } else {
                        DatabaseReference newref = dref.child(ephone);
                        newref.child("Name").setValue(ename);
                        newref.child("DOB").setValue(edob);
                        newref.child("Phone").setValue(ephone);
                        newref.child("Gender").setValue(finalGender);
                        newref.child("ID").setValue(ename.substring(0, 3) + ephone.substring(0, 3) + edob.substring(0, 2)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    successtext.setText("Great! Employee: " + ename + " has been added successfully to our database with UserID: " + ename.substring(0, 3) + ephone.substring(0, 3) + edob.substring(0, 2));
                                    successmsg.setVisibility(View.VISIBLE);

                                    namevalue.setText(""); namefild.setError(""); phonevalue.setText("");phonefild.setError(""); gendergroup.clearCheck(); showdate.setText("");  doberror.setVisibility(View.GONE); errorgender.setVisibility(View.GONE);
                                } else {
                                    pd.dismiss();
                                    errortext.setText("Some things goes wrong please trai again. Sorry for inconviance..");
                                    errormsg.setVisibility(View.VISIBLE);
                                }

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


    public void closesuccessmsg(View view) {
        successmsg.setVisibility(View.GONE);
        errormsg.setVisibility(View.GONE);
    }

}
