package com.example.DCompany;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.DCompany.Admin.AdminHome.dRef;
import static com.example.DCompany.Admin.AdminHome.eRef;

public class HomeActivity extends AppCompatActivity {
    public static DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Musers");
    public static FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    public static String userid=firebaseUser.getUid();

    private BottomNavigationView mNav;
    private  FrameLayout mFrame;
    private HomeFragment mHomeFrag;
    protected UserFragment userfrag= new UserFragment();
    NotificationFragment notiFrag=new NotificationFragment();
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        window=this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.navbar));

        final RelativeLayout relativelayout=findViewById(R.id.relativelayout);

        mNav=(BottomNavigationView)findViewById(R.id.xmainnavbar);
        mHomeFrag=new HomeFragment();
        setFragment(mHomeFrag);

        mNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.xhomebutton:
                        relativelayout.setVisibility(View.VISIBLE);
                        setFragment(mHomeFrag);
                        return true;

                    case R.id.xnotibutton:
                        relativelayout.setVisibility(View.GONE);
                        setFragment(notiFrag);
                        return true;

                    case R.id.xprofilebutton:
                        relativelayout.setVisibility(View.GONE);
                        setFragment(userfrag);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.xmainframe, fragment);
        fragmentTransaction.commit();
    }

}
