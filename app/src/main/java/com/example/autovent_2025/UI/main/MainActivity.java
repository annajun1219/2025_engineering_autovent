package com.example.autovent_2025.UI.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autovent_2025.R;
import com.example.autovent_2025.UI.alarm.AlarmActivity;
import com.example.autovent_2025.UI.window.WindowActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- 하단바 설정 ---
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            // 현재 탭 하이라이트
            bottomNav.setSelectedItemId(R.id.nav_home);

            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    return true; // 현재 화면
                } else if (id == R.id.nav_alarm) {
                    Intent i = new Intent(this, AlarmActivity.class);
                    // 기존 액티비티 앞으로 가져와서 스택 중복 방지
                    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.nav_window) {
                    Intent i = new Intent(this, WindowActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }

        // --- 진행도(LinearProgressIndicator) XML에서 progress 사용 불가 → 코드로 설정 ---
        LinearProgressIndicator progress = findViewById(R.id.progress);
        TextView tvPct = findViewById(R.id.tvProgressPct);
        if (progress != null) {
            progress.setIndeterminate(false);
            int pct = 50; // 예시 값. 실데이터에 맞춰 갱신하면 됨.
            // setProgressCompat이 있으면 애니메이션 가능
            try {
                progress.setProgressCompat(pct, true);
            } catch (Throwable t) {
                progress.setProgress(pct);
            }
            if (tvPct != null) {
                tvPct.setText(pct + "%");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 다른 탭에서 돌아왔을 때도 하단바 선택 상태 유지
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) bottomNav.setSelectedItemId(R.id.nav_home);
    }
}
