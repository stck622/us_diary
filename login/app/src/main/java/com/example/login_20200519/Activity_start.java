package com.example.login_20200519;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_start extends AppCompatActivity {

    private Button loginButton, joinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        CustomActionBar ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();

        findViewById(R.id.btn_login).setOnClickListener(onClickListener);  // 이벤트 연결
        findViewById(R.id.btn_join).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        // 로그인, 회원가입 버튼 시 여기로 진입
        @Override
        public void onClick(View v) {
            // ID를 판단하여 버튼에 맞는 페이지 호출
            switch (v.getId()){
                case R.id.btn_login :
                    myStartActivity(Activity_login.class);
                    break;

                case R.id.btn_join :
                    myStartActivity(Activity_join.class);
                    break;
            }
        }
    };

    private void myStartActivity(Class c){
        // 인텐트 변경해주는 함수
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
