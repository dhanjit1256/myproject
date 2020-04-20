//package com.example.employeportal;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.zxing.Result;
//
//import me.dm7.barcodescanner.zxing.ZXingScannerView;
//
//public class SentCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
//    ZXingScannerView ScannerView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ScannerView = new ZXingScannerView(this);
//        setContentView(ScannerView);
//    }
//
//
//    @Override
//    public void handleResult(Result result) {
//        MainActivity.resultView.setText(result.getText());
//        onBackPressed();
//
//
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        ScannerView.stopCamera();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        ScannerView.setResultHandler(this);
//        ScannerView.startCamera();
//    }
//}