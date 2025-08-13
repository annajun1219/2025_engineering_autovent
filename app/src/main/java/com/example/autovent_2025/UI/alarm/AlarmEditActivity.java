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

public class AlarmEditActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private TextInputEditText etOpenMin;
    private Spinner spRepeat;

    private String alarmId;
    private int hour;
    private int minute;
    private int openMinutes;
    private String repeat; // "매일", "주중" 등

    private static final String[] REPEAT_OPTIONS = new String[]{"매일", "주중", "주말", "사용자 지정"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit); // XML 이름에 맞춰주세요

        // 1) 뷰 바인딩
        timePicker = findViewById(R.id.timePicker);
        etOpenMin  = findViewById(R.id.etOpenMin);
        spRepeat   = findViewById(R.id.spRepeat);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnSave   = findViewById(R.id.btnSave);

        // 2) 인텐트로 값 수신
        alarmId     = getIntent().getStringExtra("alarmId");
        hour        = getIntent().getIntExtra("hour", 8);
        minute      = getIntent().getIntExtra("minute", 0);
        openMinutes = getIntent().getIntExtra("openMinutes", 60);
        repeat      = getIntent().getStringExtra("repeat");
        if (repeat == null) repeat = "매일";

        // 3) 위젯 초기화
        timePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= 23) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        } else {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }

        etOpenMin.setText(String.valueOf(openMinutes));

        ArrayAdapter<String> repeatAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, REPEAT_OPTIONS);
        spRepeat.setAdapter(repeatAdapter);
        spRepeat.setSelection(indexOf(REPEAT_OPTIONS, repeat));

        // 4) 버튼 동작
        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            // 현재 입력값 읽기
            int newHour, newMinute;
            if (Build.VERSION.SDK_INT >= 23) {
                newHour = timePicker.getHour();
                newMinute = timePicker.getMinute();
            } else {
                newHour = timePicker.getCurrentHour();
                newMinute = timePicker.getCurrentMinute();
            }

            int newOpenMin = parseIntSafe(etOpenMin.getText() == null ? "" : etOpenMin.getText().toString(), 60);
            String newRepeat = (String) spRepeat.getSelectedItem();

            // TODO: 서버에 수정 API 호출 or 로컬 DB 업데이트
            // 예시: Toast만 표시
            String msg = "알람 수정됨\n시간: " + fmt(newHour) + ":" + fmt(newMinute)
                    + " / 개방 " + newOpenMin + "분 / 반복: " + newRepeat;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

            // 필요하면 결과로 돌려보내기 (AlarmActivity가 갱신 받을 때)
            Intent data = new Intent();
            data.putExtra("alarmId", alarmId);
            data.putExtra("hour", newHour);
            data.putExtra("minute", newMinute);
            data.putExtra("openMinutes", newOpenMin);
            data.putExtra("repeat", newRepeat);
            setResult(RESULT_OK, data);

            finish();
        });
    }

    private static int indexOf(String[] arr, String value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(value)) return i;
        }
        return 0;
    }

    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    private static String fmt(int n) {
        return (n < 10 ? "0" : "") + n;
    }
}
