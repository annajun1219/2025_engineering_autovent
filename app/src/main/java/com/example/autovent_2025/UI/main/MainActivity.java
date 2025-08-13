package com.example.autovent_2025.UI.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // XML 붙이기
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("modes", MODE_PRIVATE);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> true);

        TextView tvTemp = findViewById(R.id.tvTemp);
        TextView tvHiLo = findViewById(R.id.tvHiLo);
        TextView tvLocation = findViewById(R.id.tvLocation);
        TextView tvWeatherDesc = findViewById(R.id.tvWeatherDesc);

        CheckBox swAuto = findViewById(R.id.swAuto);
        CheckBox swDust = findViewById(R.id.swDust);
        CheckBox swRain = findViewById(R.id.swRain);
        TextView tvCurrentMode = findViewById(R.id.tvCurrentMode);

        // 샘플 텍스트
        tvTemp.setText("19°");
        tvHiLo.setText("H:24°  L:18°");
        tvLocation.setText("Montreal, Canada");
        tvWeatherDesc.setText("Mid Rain");

        // 토글 저장/복원
        swAuto.setChecked(prefs.getBoolean("auto", true));
        swDust.setChecked(prefs.getBoolean("dust", false));
        swRain.setChecked(prefs.getBoolean("rain", false));
        tvCurrentMode.setText(swAuto.isChecked() ? "자동" : "수동");

        CompoundButton.OnCheckedChangeListener save = (btn, checked) -> {
            prefs.edit()
                    .putBoolean("auto", swAuto.isChecked())
                    .putBoolean("dust", swDust.isChecked())
                    .putBoolean("rain", swRain.isChecked())
                    .apply();
            tvCurrentMode.setText(swAuto.isChecked() ? "자동" : "수동");
        };
        swAuto.setOnCheckedChangeListener(save);
        swDust.setOnCheckedChangeListener(save);
        swRain.setOnCheckedChangeListener(save);

        // ▶ Retrofit 사용 시 여기서 fetchWeatherAndAir("Seoul,KR") 같은 함수 호출
    }
}
