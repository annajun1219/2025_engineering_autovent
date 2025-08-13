package com.example.autovent_2025.UI.alarm;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.autovent_2025.Model.Alarm;
import com.example.autovent_2025.R;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {

    private RecyclerView rv;
    private AlarmAdapter adapter;

    // 현재 화면에서 유지할 목록(서버 붙기 전까지 메모리에서 갱신)
    private final List<Alarm> alarms = new ArrayList<>();

    // 편집 결과 받기
    private final ActivityResultLauncher<Intent> editLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String id = data.getStringExtra("alarmId");
                    int hour = data.getIntExtra("hour", 8);
                    int minute = data.getIntExtra("minute", 0);
                    int openMin = data.getIntExtra("openMinutes", 60);
                    String repeat = data.getStringExtra("repeat");

                    for (int i = 0; i < alarms.size(); i++) {
                        Alarm old = alarms.get(i);
                        if (old.id.equals(id)) {
                            // ✅ 새 객체로 교체 (불변 패턴)
                            Alarm updated = new Alarm(
                                    old.id,
                                    old.label,
                                    LocalTime.of(hour, minute),
                                    old.enabled,
                                    openMin,
                                    repeat,
                                    old.mode,
                                    old.cycleHours,
                                    old.timeHours,
                                    old.startTime
                            );
                            alarms.set(i, updated);
                            break;
                        }
                    }
                    // ✅ 새로운 리스트 인스턴스로 submit (DiffUtil 트리거)
                    adapter.submitList(new ArrayList<>(alarms));
                }
            });


    // 추가 결과 받기
    private final ActivityResultLauncher<Intent> addLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String id = data.getStringExtra("alarmId");
                    String label = data.getStringExtra("label");
                    int hour = data.getIntExtra("hour", 8);
                    int minute = data.getIntExtra("minute", 0);
                    int openMin = data.getIntExtra("openMinutes", 60);
                    String repeat = data.getStringExtra("repeat");

                    Alarm newAlarm = new Alarm(
                            id, label != null ? label : "알람",
                            LocalTime.of(hour, minute),
                            true,
                            openMin, repeat,
                            "default", 4.0f, 0.5f, LocalDateTime.now()
                    );
                    alarms.add(newAlarm);
                    adapter.submitList(new ArrayList<>(alarms));
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        rv = findViewById(R.id.rvAlarms);

        adapter = new AlarmAdapter(this, new AlarmAdapter.AlarmListener() {
            @Override
            public void onToggle(Alarm alarm) {
                // TODO 서버 연동 시 토글 API만 호출 (UI는 어댑터 쪽에서 처리)
            }

            @Override
            public void onOpenSettings(Alarm alarm) {
                // 편집 화면으로 이동 (결과 받기)
                Intent intent = new Intent(AlarmActivity.this, AlarmEditActivity.class);
                intent.putExtra("alarmId", alarm.getId());
                intent.putExtra("hour", alarm.getTime().getHour());
                intent.putExtra("minute", alarm.getTime().getMinute());
                intent.putExtra("openMinutes", alarm.getOpenMinutes());
                intent.putExtra("repeat", alarm.getRepeat());
                editLauncher.launch(intent);
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        RecyclerView.ItemAnimator animator = rv.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        // 초기 목록 구성
        alarms.addAll(mockAlarms());
        adapter.submitList(new ArrayList<>(alarms));

        // 추가 버튼 → 결과 받기
        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            Intent intent = new Intent(AlarmActivity.this, AlarmAddActivity.class);
            addLauncher.launch(intent);
        });
    }

    private List<Alarm> mockAlarms() {
        List<Alarm> list = new ArrayList<>();
        list.add(new Alarm(
                "alarm-1", "알람1",
                LocalTime.of(10, 0),
                true,
                60, "매일",
                "default", 4.0f, 0.5f,
                LocalDateTime.now().withHour(12).withMinute(0)
        ));
        list.add(new Alarm(
                "alarm-2", "알람2",
                LocalTime.of(7, 30),
                false,
                30, "주중",
                "dust", 6.0f, 0.25f,
                LocalDateTime.now().withHour(6).withMinute(0)
        ));
        return list;
    }
}
