// MainActivity.java (상호 배타 모드 전환 완성본)
package com.example.autovent_2025.UI.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autovent_2025.R;
import com.example.autovent_2025.UI.alarm.AlarmActivity;
import com.example.autovent_2025.UI.window.WindowActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MainActivity extends AppCompatActivity {

    private TextView tvCurrentMode;
    private CheckBox swAuto, swDust, swRain;

    // 코드로 체크 상태를 바꿀 때 리스너가 연쇄 호출되는 것을 막기 위한 플래그
    private boolean isChanging = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ----- 현재 모드 UI -----
        tvCurrentMode = findViewById(R.id.tvCurrentMode);
        swAuto = findViewById(R.id.swAuto);
        swDust = findViewById(R.id.swDust);
        swRain = findViewById(R.id.swRain);

        // 공통 리스너: 하나를 ON 하면 나머지는 OFF + 왼쪽 텍스트 갱신
        CompoundButton.OnCheckedChangeListener modeListener = (buttonView, isChecked) -> {
            if (isChanging) return;

            if (isChecked) {
                selectExclusive((CheckBox) buttonView); // 상호 배타 선택

                if (buttonView == swAuto) {
                    tvCurrentMode.setText("자동");
                } else if (buttonView == swDust) {
                    tvCurrentMode.setText("미세먼지");
                } else if (buttonView == swRain) {
                    tvCurrentMode.setText("우천");
                }
            } else {
                // 모든 스위치가 OFF면 "없음"
                if (!swAuto.isChecked() && !swDust.isChecked() && !swRain.isChecked()) {
                    tvCurrentMode.setText("없음");
                }
            }
        };

        swAuto.setOnCheckedChangeListener(modeListener);
        swDust.setOnCheckedChangeListener(modeListener);
        swRain.setOnCheckedChangeListener(modeListener);

        // 초기 상태(기본값을 자동 모드로)
        isChanging = true;          // 초기화 시 리스너 연쇄 방지
        swAuto.setChecked(true);
        swDust.setChecked(false);
        swRain.setChecked(false);
        isChanging = false;
        tvCurrentMode.setText("자동");

        // ----- 하단바 설정 -----
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    return true;
                } else if (id == R.id.nav_alarm) {
                    Intent i = new Intent(this, AlarmActivity.class);
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

        // ----- 진행도 표시 -----
        LinearProgressIndicator progress = findViewById(R.id.progress);
        TextView tvPct = findViewById(R.id.tvProgressPct);
        if (progress != null) {
            progress.setIndeterminate(false);
            int pct = 50; // 예시 값
            try {
                progress.setProgressCompat(pct, true);
            } catch (Throwable t) {
                progress.setProgress(pct);
            }
            if (tvPct != null) tvPct.setText(pct + "%");
        }
    }

    /** target만 체크하고 나머지는 해제 (상호 배타) */
    private void selectExclusive(CheckBox target) {
        isChanging = true;
        swAuto.setChecked(target == swAuto);
        swDust.setChecked(target == swDust);
        swRain.setChecked(target == swRain);
        isChanging = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) bottomNav.setSelectedItemId(R.id.nav_home);
    }
}
