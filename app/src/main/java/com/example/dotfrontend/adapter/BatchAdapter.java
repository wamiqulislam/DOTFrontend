package com.example.dotfrontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotfrontend.R;
import com.example.dotfrontend.model.Batch;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.BatchViewHolder> {
    public interface OnBatchClickListener { void onBatchClick(Batch batch); }

    private final List<Batch> batches;
    private final Set<Long> selectedIds = new HashSet<>();
    private OnBatchClickListener listener;

    public BatchAdapter(List<Batch> batches) {
        this.batches = batches;
    }

    public BatchAdapter(List<Batch> batches, OnBatchClickListener listener) {
        this.batches = batches;
        this.listener = listener;
    }

    public void setOnBatchClickListener(OnBatchClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_batch, parent, false);
        return new BatchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchViewHolder holder, int pos) {
        Batch b = batches.get(pos);
        holder.tvBatchId.setText("Batch #" + b.getBatchId());
        holder.tvDestination.setText(
                "Dest: " + b.getDestination().getCity() + ", " + b.getDestination().getCountry()
        );
        holder.tvCurrentLocation.setText(
                "Current: " + b.getCurrentLocation().getCity() + ", " + b.getCurrentLocation().getCountry()
        );
        holder.tvWeight.setText("Weight: " + b.getWeight());
        holder.tvStatus.setText("Status: " + b.getStatus().name());

        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.setChecked(selectedIds.contains(b.getBatchId()));
        holder.cbSelect.setOnCheckedChangeListener((cb, checked) -> {
            if (checked) selectedIds.add(b.getBatchId());
            else selectedIds.remove(b.getBatchId());
        });

        holder.itemView.setOnClickListener(v -> {
            boolean newState = !holder.cbSelect.isChecked();
            holder.cbSelect.setChecked(newState);
            if (listener != null) listener.onBatchClick(b);
        });
    }

    @Override
    public int getItemCount() {
        return batches.size();
    }

    public void updateData(List<Batch> newBatches) {
        batches.clear();
        batches.addAll(newBatches);
        selectedIds.clear();
        notifyDataSetChanged();
    }

    public List<Batch> getSelected() {
        List<Batch> out = new ArrayList<>();
        for (Batch b : batches) {
            if (selectedIds.contains(b.getBatchId())) out.add(b);
        }
        return out;
    }

    static class BatchViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbSelect;
        TextView tvBatchId, tvDestination, tvCurrentLocation, tvWeight, tvStatus;

        BatchViewHolder(@NonNull View v) {
            super(v);
            cbSelect          = v.findViewById(R.id.cbSelect);
            tvBatchId         = v.findViewById(R.id.tvBatchId);
            tvDestination     = v.findViewById(R.id.tvDestination);
            tvCurrentLocation = v.findViewById(R.id.tvCurrentLocation);
            tvWeight          = v.findViewById(R.id.tvWeight);
            tvStatus          = v.findViewById(R.id.tvStatus);
        }
    }
}
