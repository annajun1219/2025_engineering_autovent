package com.example.autovent_2025.UI.alarm;

import android.os.Bundle;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        rv = findViewById(R.id.rvAlarms);

        adapter = new AlarmAdapter(this, new AlarmAdapter.AlarmListener() {
            @Override
            public void onToggle(Alarm alarm) {
                // ✅ 리스트 갱신 금지: 스위치는 어댑터에서 체크박스만 바꾸도록
                // (서버 붙이면 여기서 API만 호출)
            }

            @Override
            public void onOpenSettings(Alarm alarm) {
                // TODO: 설정 화면 이동 예정
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        // 🔻 깜빡임 방지: 변경 애니메이션 끄기(둘 중 하나 선택)
        RecyclerView.ItemAnimator animator = rv.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        // 또는 완전 차단:
        // rv.setItemAnimator(null);

        // 더미 데이터
        adapter.submitList(mockAlarms());
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
