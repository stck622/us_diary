package com.example.login_20200519;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    EditText et_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_join).setOnClickListener(onClickListener);
        findViewById(R.id.btn_notMember).setOnClickListener(onClickListener);

        et_nickname = (EditText)findViewById(R.id.et_nickname);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_join :
                    signUp();
                    break;
            }
        }
    };

    private void signUp(){  // 회원가입 버튼을 누를 시 진입
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_pw)).getText().toString();
        String pwConfirm = ((EditText) findViewById(R.id.et_pwConfirm)).getText().toString();

        /* 모든 정보를 입력했다면 진입 */
        if(email.length() > 0 && password.length() > 0 && pwConfirm.length() > 0){
            /* 비밀번호와 비밀번호 재입력 값이 같다면 진입 */
            if(password.equals(pwConfirm)){
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {  // 현재 위치에서 메서드 호출
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                /* 회원가입이 정상적으로 완료되었다면? */
                                if(task.isSuccessful()){
                                    FirebaseUser user = firebaseAuth.getCurrentUser();  // 토큰 값 발급
                                    /* 닉네임 업데이트 */
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(et_nickname.getText().toString())
                                            .build();
                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    /* 회원가입 이후 닉네임 설정이 성공적으로 완료될 시 진입 */
                                                    if (task.isSuccessful()) {
                                                        Intent intent = new Intent(Activity_join.this, Activity_start.class);
                                                        Toast.makeText(Activity_join.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                                        myStartActivity(Activity_login.class);  // 회원가입 이후 넘어갈 화면으로 이동
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            } else{  // 비밀번호와 비밀번호 확인 텍스트가 일치하지 않는다면?
                Toast.makeText(Activity_join.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(Activity_join.this, "모든 칸에 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void myStartActivity(Class c){
        // 인텐트 변경해주는 함수
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
