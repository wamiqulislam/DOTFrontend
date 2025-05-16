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
    private final List<Batch> batches;
    private final Set<Long> selectedIds = new HashSet<>();

    public BatchAdapter(List<Batch> batches) {
        this.batches = batches;
    }

    @NonNull
    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_batch, parent, false);
        return new BatchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchViewHolder holder, int position) {
        Batch b = batches.get(position);
        holder.tvBatchId.setText("Batch #" + b.getBatchId());
        holder.tvDestination.setText(
                b.getDestination().getCity() + ", " + b.getDestination().getCountry()
        );

        // prevent unwanted recursion when recycling views
        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.setChecked(selectedIds.contains(b.getBatchId()));

        holder.cbSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) selectedIds.add(b.getBatchId());
            else selectedIds.remove(b.getBatchId());
        });

        // clicking the whole row also toggles the checkbox
        holder.itemView.setOnClickListener(v -> {
            boolean newState = !holder.cbSelect.isChecked();
            holder.cbSelect.setChecked(newState);
        });
    }

    @Override
    public int getItemCount() {
        return batches.size();
    }

    /** Replace the adapter’s data and clear previous selections */
    public void updateData(List<Batch> newBatches) {
        batches.clear();
        batches.addAll(newBatches);
        selectedIds.clear();
        notifyDataSetChanged();
    }

    /** Return the list of Batches currently selected */
    public List<Batch> getSelected() {
        List<Batch> list = new ArrayList<>();
        for (Batch b : batches) {
            if (selectedIds.contains(b.getBatchId())) {
                list.add(b);
            }
        }
        return list;
    }

    static class BatchViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbSelect;
        TextView tvBatchId, tvDestination;

        BatchViewHolder(@NonNull View itemView) {
            super(itemView);
            cbSelect     = itemView.findViewById(R.id.cbSelect);
            tvBatchId    = itemView.findViewById(R.id.tvBatchId);
            tvDestination= itemView.findViewById(R.id.tvDestination);
        }
    }
}
