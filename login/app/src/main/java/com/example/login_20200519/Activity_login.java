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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private EditText et_email, et_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_login).setOnClickListener(onClickListener);  // 이벤트 함수 연결
        findViewById(R.id.btn_isMember).setOnClickListener(onClickListener);

        firebaseAuth = FirebaseAuth.getInstance();  // 토큰 객체화
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view){
            switch(view.getId()){
                case R.id.btn_login :
                    login();
                    break;

                case R.id.btn_isMember :  // 회원이 아닌 경우 회원가입 창으로 이동
                    myStartActivity(Activity_join.class);
                    break;
            }
        }
    };

    private void login() {  // 로그인 버튼 클릭시 진입
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();  // 입력 정보 받아옴
        String password = ((EditText) findViewById(R.id.et_pw)).getText().toString();

        if(email.length() > 0 && password.length() > 0){
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // email과 pw가 모두 입력이 완료되었다면 진입
                                FirebaseUser user = firebaseAuth.getCurrentUser();  // 토큰 값 발급
                                Toast.makeText(Activity_login.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                myStartActivity(Activity_connect.class);  // 로그인 이후 넘어갈 화면으로 이동
                            }
                        }
                    });
        } else{
            Toast.makeText(Activity_login.this, "이메일 또는 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void myStartActivity(Class c){
        // 인텐트 변경해주는 함수
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
