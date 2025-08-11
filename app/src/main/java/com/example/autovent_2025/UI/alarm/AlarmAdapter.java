package com.example.autovent_2025.UI.alarm;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovent_2025.Model.Alarm;
import com.example.autovent_2025.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends ListAdapter<Alarm, AlarmAdapter.VH> {

    public interface AlarmListener {
        void onToggle(Alarm alarm);
        void onOpenSettings(Alarm alarm);
    }

    private final LayoutInflater inflater;
    private final AlarmListener listener;

    public AlarmAdapter(Context ctx, AlarmListener listener) {
        super(DIFF);
        this.inflater = LayoutInflater.from(ctx);
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_alarm, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Alarm a = getItem(position);

        h.tvLabel.setText(a.label);
        h.tvTime.setText(a.timeText());
        h.tvSub.setText(a.subText());

        h.swEnable.setSelected(a.enabled);
        h.swEnable.setActivated(a.enabled);

        h.swEnable.setOnClickListener(v -> {
            if (listener != null) listener.onToggle(a);
        });

        h.btnSettings.setOnClickListener(v -> {
            if (listener != null) listener.onOpenSettings(a);
        });
    }

    public void update(Alarm updated) {
        List<Alarm> curr = new ArrayList<>(getCurrentList());
        for (int i = 0; i < curr.size(); i++) {
            if (TextUtils.equals(curr.get(i).id, updated.id)) {
                curr.set(i, updated);
                submitList(curr);
                return;
            }
        }
    }

    public static final DiffUtil.ItemCallback<Alarm> DIFF = new DiffUtil.ItemCallback<Alarm>() {
        @Override
        public boolean areItemsTheSame(@NonNull Alarm o, @NonNull Alarm n) {
            return TextUtils.equals(o.id, n.id);
        }
        @Override
        public boolean areContentsTheSame(@NonNull Alarm o, @NonNull Alarm n) {
            return o.enabled == n.enabled
                    && TextUtils.equals(o.label, n.label)
                    && o.openMinutes == n.openMinutes
                    && TextUtils.equals(o.repeatText, n.repeatText);
        }
    };

    public static class VH extends RecyclerView.ViewHolder {
        public final TextView tvLabel, tvTime, tvSub;
        public final ImageButton btnSettings, swEnable;
        public final ImageView ivAlarm;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.tvLabel);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSub = itemView.findViewById(R.id.tvSub);
            btnSettings = itemView.findViewById(R.id.btnSettings);
            swEnable = itemView.findViewById(R.id.swEnable);
            ivAlarm = itemView.findViewById(R.id.ivAlarm);
        }
    }
}
