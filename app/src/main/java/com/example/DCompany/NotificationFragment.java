package com.example.DCompany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static com.example.DCompany.HomeActivity.databaseReference;
import static com.example.DCompany.HomeActivity.userid;

public class NotificationFragment extends Fragment {

    String text, date, time, finalvalue,notifications,key;


    public NotificationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);



        final ListView listview= (ListView)view.findViewById(R.id.notificationlist);
        final ArrayList<String> arraylist= new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(getActivity(), R.layout.notilist, R.id.xnotibutton, arraylist);
        listview.setAdapter(arrayAdapter);

        DatabaseReference newref=databaseReference.child(userid).child("Notification");

        newref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notifications=dataSnapshot.getValue(String.class);
                key=dataSnapshot.getKey();
                text=notifications.substring(15);
                time=notifications.substring(0,5);
                date=notifications.substring(5,15);
                finalvalue=text+"\n"+"On: "+date+"  at: "+time;
                arraylist.add(finalvalue);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view;
    }
}
