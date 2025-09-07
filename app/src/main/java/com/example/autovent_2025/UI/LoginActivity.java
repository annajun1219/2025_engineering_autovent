package com.example.autovent_2025.UI;

import android.content.Intent;
import android.content.SharedPreferences;
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

    // 연동안 해도 로그인 통과시키는 테스트 스위치 (실연동 시 false)
    private static final boolean OFFLINE_LOGIN = true;

    // 이메일 저장용 키
    private static final String PREFS = "user_prefs";
    private static final String KEY_EMAIL = "user_email";

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

        // 입력값 검증
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

        // 오프라인 모드: 즉시 성공 처리 + 이메일 저장
        if (OFFLINE_LOGIN) {
            saveEmail(email);
            Toast.makeText(this, "오프라인 로그인(테스트)", Toast.LENGTH_SHORT).show();
            goMain();
            return;
        }

        // ===== 실제 연동 모드 =====
        setLoading(true);
        RetrofitHelper.login(email, password, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> resp) {
                setLoading(false);

                if (!resp.isSuccessful()) {
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
                    // 이메일 저장 (응답에 이메일이 있으면 그걸 쓰고, 없으면 입력값 사용)
                    // 예: saveEmail(body.getUsername() != null ? body.getUsername() : email);
                    saveEmail(email);

                    // (선택) 토큰 등 추가 저장
                    // saveToken(body.getAccessToken());

                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
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

    private void saveEmail(String email) {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        prefs.edit().putString(KEY_EMAIL, email).apply();
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
