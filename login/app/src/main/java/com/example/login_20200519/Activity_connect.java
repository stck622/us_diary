package com.example.login_20200519;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_connect extends AppCompatActivity {

    TextView tv_waiting;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        firebaseAuth = FirebaseAuth.getInstance();

        tv_waiting = (TextView)findViewById(R.id.tv_waiting);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        // ((TextView) findViewById(R.id.tv_waiting)).setText(user.getDisplayName().toString());  // 닉네임 확인용 코드
    }
}
