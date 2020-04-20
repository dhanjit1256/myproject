package com.example.DCompany.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.DCompany.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminHolyday extends AppCompatActivity {

    @BindView(R.id.showpickdate)
    TextView showpickdate;
    @BindView(R.id.errorpicdate)
    TextView errorpicdate;
    @BindView(R.id.eventname)
    EditText eventname;
    @BindView(R.id.eventerror)
    TextView eventerror;
    @BindView(R.id.eventdetails)
    EditText eventdetails;
    @BindView(R.id.showdatepickerlayout)
    LinearLayout showdatepickerlayout;
    String date;
    DatePicker datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_holyday);
        ButterKnife.bind(this);
        datepicker=(DatePicker)findViewById(R.id.datepicker);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.BLACK);




    }


    public void submitholiday(View view) {
        String maindate= showpickdate.getText().toString();
        String Event = eventname.getText().toString();
        String EventDetails = eventdetails.getText().toString();

        if (maindate.isEmpty() || Event.isEmpty()) {

            if (maindate.isEmpty()) {
                errorpicdate.setVisibility(View.VISIBLE);
            }
            else {
                errorpicdate.setVisibility(View.GONE);
            }

            if (Event.isEmpty()) {
                eventerror.setVisibility(View.VISIBLE);
            } else {
                eventerror.setVisibility(View.GONE);
            }

        } else {

            String m = maindate.substring(3, 5);
            String y = maindate.substring(6, 10);

            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("PreDefine").child("HolidayList").child(y).child(m).child(maindate);
            database.child("Event").setValue(Event);
            if (!EventDetails.isEmpty()) {
                database.child("EventDetails").setValue(EventDetails);
            }
        }

    }

    public void showpicdate(View view) {
        showdatepickerlayout.setVisibility(View.VISIBLE);
        showpickdate.setVisibility(View.GONE);
    }

    public void selectdate(View view) {
        datepicerconfigclass datepicerconfigclass=new datepicerconfigclass();
        date= datepicerconfigclass.datepicerconfig(Integer.toString(datepicker.getDayOfMonth()), Integer.toString(datepicker.getMonth()+1), Integer.toString(datepicker.getYear()));

        showpickdate.setText(date);
        showdatepickerlayout.setVisibility(View.GONE);
        showpickdate.setVisibility(View.VISIBLE);

    }

    public static class datepicerconfigclass{
        String newday, newmonth;


        public String datepicerconfig(String day, String month, String year)
        {

            if (day.length()==1){
            newday = "0" + day;
            }
            else{
                newday = day;
            }

            if (month.length()==1){
                newmonth = "0" + month;
            }
            else{
                newmonth = month;
            }

            return (newday+":"+newmonth+":"+year);
        }
    }

    public void goback(View view) {

    }
}
