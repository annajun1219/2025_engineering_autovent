package com.example.autovent_2025.UI.alarm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autovent_2025.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class AlarmAddActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private TextInputEditText etOpenMin;
    private Spinner spRepeat;

    // 필요시 리소스로 빼도 됨
    private static final String[] REPEAT_OPTIONS = new String[]{"매일", "주중", "주말", "사용자 지정"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 네 XML 이름 사용
        setContentView(R.layout.activity_alarm_add);

        // 뷰 찾기
        timePicker = findViewById(R.id.timePicker);
        etOpenMin  = findViewById(R.id.etOpenMin);
        spRepeat   = findViewById(R.id.spRepeat);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnSave   = findViewById(R.id.btnSave);

        // 초기화
        timePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= 23) {
            timePicker.setHour(8);
            timePicker.setMinute(0);
        } else {
            timePicker.setCurrentHour(8);
            timePicker.setCurrentMinute(0);
        }

        ArrayAdapter<String> repeatAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, REPEAT_OPTIONS);
        spRepeat.setAdapter(repeatAdapter);
        spRepeat.setSelection(0); // "매일"

        // 취소
        btnCancel.setOnClickListener(v -> finish());

        // 저장
        btnSave.setOnClickListener(v -> {
            int hour, minute;
            if (Build.VERSION.SDK_INT >= 23) {
                hour = timePicker.getHour();
                minute = timePicker.getMinute();
            } else {
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
            }

            int openMin = parseIntSafe(
                    etOpenMin.getText() == null ? "" : etOpenMin.getText().toString(), 60);
            String repeat = (String) spRepeat.getSelectedItem();

            if (openMin <= 0) {
                Toast.makeText(this, "개방 지속 시간을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 결과로 돌려보내기 (AlarmActivity에서 ActivityResult로 받으면 리스트에 추가 가능)
            Intent data = new Intent();
            data.putExtra("alarmId", "alarm-" + UUID.randomUUID());
            data.putExtra("label", "알람"); // 필요하면 입력 필드 추가해도 됨
            data.putExtra("hour", hour);
            data.putExtra("minute", minute);
            data.putExtra("openMinutes", openMin);
            data.putExtra("repeat", repeat);

            setResult(RESULT_OK, data);
            finish();
        });
    }

    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
}
