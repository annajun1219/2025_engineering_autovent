package com.example.autovent_2025.UI.Energy;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.autovent_2025.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

public class EnergyActivity extends AppCompatActivity {

    // --- 요약/헤더 ---
    private TextView tvWeeklyKwhSaved, tvKwhSavedValue, tvCo2SavedValue, tvCostSavedValue;

    // --- 미세먼지 차단 효과 ---
    private TextView tvPmImprovementPct, tvPmIndoorAvg, tvPmOutdoorRef, tvPmAvoidedDose;

    // --- 자연광 기반 조명 절감 ---
    private TextView tvSunlightRule, tvLightOffMinutes, tvLightSavedKwh, tvLightSavedEtc;

    // --- 주간 환기 기록 ---
    private ImageView ivWeeklyMiniChart, ivVentBarChart;
    private TextView tvVentTotal, tvVentAvg, tvVentAutoCount;

    // ==== 환경 상수(필요시 서버에서 내려받아 교체) ====
    private static final double PRICE_PER_KWH = 130.0;     // 원/kWh (샘플)
    private static final double CO2_PER_KWH   = 0.424;     // kg CO2e/kWh (샘플)
    private static final double LAMP_POWER_KW = 0.032;     // 32W 형광등 1개 기준
    private static final int    LAMP_COUNT    = 12;        // 교실 램프 개수(샘플)
    private static final double SUN_THRESHOLD_LUX = 400.0; // 조명 OFF 임계치(샘플)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_report);
        setTitle("주간 에너지 리포트");

        MaterialToolbar tb = findViewById(R.id.toolbar);
        tb.setNavigationOnClickListener(v -> finish());
        Drawable navIcon = ContextCompat.getDrawable(this, R.drawable.ic_back);
        if (navIcon != null) {
            navIcon.setBounds(0, 0, 48, 48); // 원하는 크기(px 단위)
            tb.setNavigationIcon(navIcon);
        }

        TextView t = findViewById(R.id.tvWeeklyKwhSaved);
        if (t != null) t.setText("절감 9.99 kWh (연결 OK)");


        bindViews();
        // 1) 서버 붙기 전까지는 샘플 데이터로 렌더링
        renderWithSample();
    }

    private void bindViews() {
        // 헤더/요약
        tvWeeklyKwhSaved = findViewById(R.id.tvWeeklyKwhSaved);
        tvKwhSavedValue  = findViewById(R.id.tvKwhSavedValue);
        tvCo2SavedValue  = findViewById(R.id.tvCo2SavedValue);
        tvCostSavedValue = findViewById(R.id.tvCostSavedValue);
        ivWeeklyMiniChart= findViewById(R.id.ivWeeklyMiniChart);

        // 미세먼지
        tvPmImprovementPct = findViewById(R.id.tvPmImprovementPct);
        tvPmIndoorAvg      = findViewById(R.id.tvPmIndoorAvg);
        tvPmOutdoorRef     = findViewById(R.id.tvPmOutdoorRef);
        tvPmAvoidedDose    = findViewById(R.id.tvPmAvoidedDose);

        // 조명
        tvSunlightRule   = findViewById(R.id.tvSunlightRule);
        tvLightOffMinutes= findViewById(R.id.tvLightOffMinutes);
        tvLightSavedKwh  = findViewById(R.id.tvLightSavedKwh);
        tvLightSavedEtc  = findViewById(R.id.tvLightSavedEtc);

        // 환기
        ivVentBarChart = findViewById(R.id.ivVentBarChart);
        tvVentTotal    = findViewById(R.id.tvVentTotal);
        tvVentAvg      = findViewById(R.id.tvVentAvg);
        tvVentAutoCount= findViewById(R.id.tvVentAutoCount);
    }

    /** 샘플 데이터로 화면 그리기 (서버 연동 시 이 부분만 교체) */
    private void renderWithSample() {
        // ---- (A) 서버에서 내려올 가정 데이터 ----
        // 미세먼지: 이번 주 실내·실외 평균(㎍/㎥)
        double pmIndoorAvg = 12.0;
        double pmOutdoorIfOpenAvg = 28.0; // "계속 열어둠" 가정치

        // 자연광/조명: 조도 측정 + OFF 누적 분
        double avgLux = 520.0;
        int lightOffMinutes = 185;     // 이번 주 자동 OFF 총 누적분
        // 환기
        int totalVentMinutes = 360;    // 총 환기 6시간
        int ventEvents = 9;            // 자동 트리거 횟수

        // ---- (B) 계산 ----
        double pmImproveRatio = calcPmImproveRatio(pmIndoorAvg, pmOutdoorIfOpenAvg); // 0~1
        double savedKwhByLight = calcLightSavedKwh(lightOffMinutes, LAMP_POWER_KW, LAMP_COUNT);
        double savedCost = savedKwhByLight * PRICE_PER_KWH;
        double savedCo2  = savedKwhByLight * CO2_PER_KWH;

        // ---- (C) 바인딩 ----
        // 헤더 요약
        setText(tvWeeklyKwhSaved, String.format(Locale.KOREA, "절감 %.2f kWh", savedKwhByLight));
        setText(tvKwhSavedValue,  String.format(Locale.KOREA, "%.2f kWh", savedKwhByLight));
        setText(tvCo2SavedValue,  String.format(Locale.KOREA, "%.1f kgCO\u2082e", savedCo2));
        setText(tvCostSavedValue, String.format(Locale.KOREA, "%,.0f원", savedCost));

        // 미세먼지
        setText(tvPmIndoorAvg,  String.format(Locale.KOREA, "%.0f ㎍/㎥", pmIndoorAvg));
        setText(tvPmOutdoorRef, String.format(Locale.KOREA, "%.0f ㎍/㎥", pmOutdoorIfOpenAvg));
        setText(tvPmImprovementPct, String.format(Locale.KOREA, "개선 %d%%", Math.round(pmImproveRatio * 100)));
        setText(tvPmAvoidedDose, String.format(Locale.KOREA,
                "창문 자동닫힘으로 오염 노출 ↓ %d%%", Math.round(pmImproveRatio * 100)));

        // 조명
        setText(tvSunlightRule, String.format(Locale.KOREA,
                "조도 임계치(%.0f lux) 초과 시 형광등 자동 OFF", SUN_THRESHOLD_LUX));
        setText(tvLightOffMinutes, String.format(Locale.KOREA, "%d분", lightOffMinutes));
        setText(tvLightSavedKwh,   String.format(Locale.KOREA, "%.2f kWh", savedKwhByLight));
        setText(tvLightSavedEtc,   String.format(Locale.KOREA, "요금 %,.0f원 · CO₂ %.1f kg 절감", savedCost, savedCo2));

        // 환기
        setText(tvVentTotal, formatHhMm(totalVentMinutes));
        setText(tvVentAvg,   String.format(Locale.KOREA, "%d분", ventEvents == 0 ? 0 : totalVentMinutes / ventEvents));
        setText(tvVentAutoCount, String.format(Locale.KOREA, "%d회", ventEvents));

        // 차트/게이지는 자리표시자(ImageView)만 있음.
        // MPAndroidChart 붙일 땐 여기서 데이터 세팅하면 됨.
    }

    /** 미세먼지 ‘개선율’: 1 - (실내/실외가정). 실외가정이 0이거나 실내가 더 낮으면 0~1 범위로 보정 */
    private double calcPmImproveRatio(double indoor, double outdoorRef) {
        if (outdoorRef <= 0) return 0;
        double r = 1.0 - (indoor / outdoorRef);
        if (r < 0) r = 0;
        if (r > 1) r = 1;
        return r;
    }

    /** 조명 절감 kWh = (OFF분/60) * (램프정격 kW * 개수) */
    private double calcLightSavedKwh(int offMinutes, double lampKw, int count) {
        return (offMinutes / 60.0) * (lampKw * count);
    }

    private String formatHhMm(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        if (h == 0) return String.format(Locale.KOREA, "%d분", m);
        return String.format(Locale.KOREA, "%d시간 %d분", h, m);
    }

    private void setText(TextView tv, String v) {
        if (tv != null) tv.setText(v);
    }
}
