package com.example.autovent_2025.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autovent_2025.Model.LoginResponse;
import com.example.autovent_2025.Network.RetrofitHelper;
import com.example.autovent_2025.R;
import com.example.autovent_2025.UI.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPw;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPw = findViewById(R.id.etPw);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> submit());
    }

    private void submit() {
        String email = safeText(etEmail);
        String password = safeText(etPw);

        // 1) 입력값 검증
        if (email.isEmpty()) {
            etEmail.setError("이메일을 입력하세요");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("올바른 이메일 형식이 아닙니다");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPw.setError("비밀번호를 입력하세요");
            etPw.requestFocus();
            return;
        }

        // 2) 로딩 상태
        setLoading(true);

        // 3) 로그인 API 호출 (백엔드 JSON 키: email, passwords)
        RetrofitHelper.login(email, password, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> resp) {
                setLoading(false);

                if (!resp.isSuccessful()) {
                    // HTTP 오류 (ex. 400/401/404/500)
                    if (resp.code() == 401) {
                        Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "로그인 실패 (" + resp.code() + ")", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                LoginResponse body = resp.body();
                if (body == null) {
                    Toast.makeText(LoginActivity.this, "응답이 비어 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (body.isSuccess()) {
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    // TODO: 토큰/유저정보 저장 필요 시 여기서 처리
                    goMain();
                } else {
                    String msg = body.getMessage() != null ? body.getMessage() : "로그인에 실패했습니다.";
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String safeText(EditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private void setLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
        btnLogin.setAlpha(loading ? 0.6f : 1f);
    }

    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
