package com.example.autovent_2025.UI.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autovent_2025.Model.WindowItem;
import com.example.autovent_2025.R;
import java.util.List;

public class WindowAdapter extends RecyclerView.Adapter<WindowAdapter.VH> {
    public interface OnToggleChanged {
        void onToggle(int position, boolean isOpen, WindowItem item);
    }

    private final List<WindowItem> list;
    private final LayoutInflater inflater;
    private final OnToggleChanged toggleListener;

    public WindowAdapter(Context ctx, List<WindowItem> list, OnToggleChanged toggleListener) {
        this.inflater = LayoutInflater.from(ctx);
        this.list = list;
        this.toggleListener = toggleListener;
        setHasStableIds(true);
    }

    @Override public long getItemId(int position) { return list.get(position).getTitle().hashCode(); }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_window, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        WindowItem item = list.get(pos);
        h.tvBadge.setText(item.getBuilding());
        h.tvTitle.setText(item.getTitle());

        // 리스너 중복 방지
        h.swOpen.setOnCheckedChangeListener(null);
        h.swOpen.setChecked(item.isOpen());
        h.tvState.setText(item.isOpen() ? "열림" : "닫힘");

        h.swOpen.setOnCheckedChangeListener((btn, checked) -> {
            item.setOpen(checked);
            h.tvState.setText(checked ? "열림" : "닫힘");
            if (toggleListener != null) {
                toggleListener.onToggle(h.getAdapterPosition(), checked, item);
            }
        });
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvBadge, tvTitle, tvState;
        CheckBox swOpen;
        VH(@NonNull View v) {
            super(v);
            tvBadge = v.findViewById(R.id.tvBadge);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvState = v.findViewById(R.id.tvState);
            swOpen = v.findViewById(R.id.swOpen);
        }
    }
}
