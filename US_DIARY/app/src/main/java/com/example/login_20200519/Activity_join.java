package com.example.login_20200519;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Activity_join extends AppCompatActivity {

    private Button isMember, btn_join;
    private FirebaseAuth firebaseAuth;
    private EditText et_email, et_pw, et_nickname;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_join = (Button)findViewById(R.id.btn_join);
        isMember = (Button)findViewById(R.id.btn_notMember);
        et_email = (EditText)findViewById(R.id.et_email);
        et_pw = (EditText)findViewById(R.id.et_pw);
        et_nickname = (EditText)findViewById(R.id.et_nickname);

        isMember.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_join.this, Activity_login.class);
                startActivity(intent);
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_email.getText().toString().equals("") && !et_pw.getText().toString().equals("")) {
                    // 아이디와 비밀번호가 공백이 아닌 경우
                    createUser(et_email.getText().toString(), et_pw.getText().toString());
                } else {
                    // 아이디와 비밀번호가 공백인 경우
                    Toast.makeText(Activity_join.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void createUser(final String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(Activity_join.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()  // 닉네임 업데이트
                                    .setDisplayName(et_nickname.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {  // 회원가입 이후 닉네임 설정이 성공적으로 완료될 시
                                                mFirebaseAnalytics.setUserProperty("connect_code",  "testcodejimin");  // 연결 코드 추가
                                                Intent intent = new Intent(Activity_join.this, MainActivity.class);
                                                startActivity(intent);  // 로그인 화면으로 돌아감
                                            }
                                        }
                                    });

                            finish();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(Activity_join.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
