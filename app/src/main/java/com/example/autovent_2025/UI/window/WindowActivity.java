package com.example.autovent_2025.UI.window;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovent_2025.Model.WindowItem;
import com.example.autovent_2025.R;
import com.example.autovent_2025.UI.alarm.AlarmActivity;
import com.example.autovent_2025.UI.main.MainActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class WindowActivity extends AppCompatActivity {

    private EditText etSearch;
    private CheckBox swAllOpen;
    private RecyclerView rvWindows;
    private ChipGroup chipGroup;
    private WindowAdapter adapter;

    private final List<WindowItem> all = new ArrayList<>();
    private final List<WindowItem> viewList = new ArrayList<>();

    private String selectedBuilding = ""; // 칩 선택 상태
    private String query = "";            // 검색어 상태

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("창문 관리");

        etSearch = findViewById(R.id.etSearch);
        swAllOpen = findViewById(R.id.swAllOpen);
        rvWindows = findViewById(R.id.rvWindows);
        chipGroup = findViewById(R.id.chipGroup);

        // ===== 하단바 네비게이션 설정 =====
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_window); // 현재 탭 하이라이트
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_window) return true; // 현재 화면

                if (id == R.id.nav_home) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    return true;
                }
                if (id == R.id.nav_alarm) {
                    Intent i = new Intent(this, AlarmActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
        // ==================================

        loadDummy(); // 나중에 Retrofit으로 교체

        adapter = new WindowAdapter(this, viewList, (pos, isOpen, item) -> {
            // TODO: 백엔드 연동 시 개별 창문 상태 업데이트 API 호출
        });
        rvWindows.setLayoutManager(new LinearLayoutManager(this));
        rvWindows.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                query = s.toString();
                applyFilter();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        swAllOpen.setOnCheckedChangeListener((btn, checked) -> {
            for (WindowItem w : all) w.setOpen(checked);
            applyFilter();
            // TODO: 백엔드 연동 시 전체 일괄 토글 API 호출
        });

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) {
                selectedBuilding = "";
            } else {
                Chip chip = group.findViewById(checkedId);
                selectedBuilding = chip != null ? chip.getText().toString() : "";
            }
            applyFilter();
        });

        applyFilter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 다른 탭에서 돌아왔을 때도 선택 상태 유지
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) bottomNav.setSelectedItemId(R.id.nav_window);
    }

    private void loadDummy() {
        all.clear();
        all.add(new WindowItem("중앙도서관", "1층 창문A", true));
        all.add(new WindowItem("중앙도서관", "1층 창문B", false));
        all.add(new WindowItem("과학관", "2층 창문C", true));
        all.add(new WindowItem("프라임관", "3층 창문D", false));
        all.add(new WindowItem("명신관", "1층 창문E", true));
        all.add(new WindowItem("순헌관", "2층 창문F", false));
    }

    private void applyFilter() {
        viewList.clear();
        for (WindowItem w : all) {
            boolean matchBuilding = selectedBuilding.isEmpty() || w.getBuilding().equals(selectedBuilding);
            boolean matchQuery = query.isEmpty()
                    || w.getTitle().contains(query)
                    || w.getBuilding().contains(query);
            if (matchBuilding && matchQuery) viewList.add(w);
        }
        adapter.notifyDataSetChanged();
    }
}
