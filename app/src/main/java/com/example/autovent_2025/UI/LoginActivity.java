package com.example.autovent_2025.UI;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.autovent_2025.R;
import com.example.autovent_2025.UI.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            // 로그인 처리 로직
            // 예시: 로그인 성공 시 MainActivity로 이동
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
