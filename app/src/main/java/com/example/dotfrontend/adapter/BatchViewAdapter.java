package com.example.dotfrontend.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotfrontend.R;
import com.example.dotfrontend.model.Batch;
import java.util.List;

public class BatchViewAdapter extends RecyclerView.Adapter<BatchViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Batch batch);
    }

    private final List<Batch> batches;
    private final OnItemClickListener listener;

    public BatchViewAdapter(List<Batch> batches, OnItemClickListener listener) {
        this.batches = batches;
        this.listener = listener;
        Log.d("BatchViewAdapter", "........................................3");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("BatchViewAdapter", "........................................1");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_batch_simple, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("BatchViewAdapter", "........................................4");

        Batch b = batches.get(position);
        holder.tvBatchId.setText("Batch #" + b.getBatchId());
        holder.tvDestination.setText("Dest: " + b.getDestination().getCity() + ", " + b.getDestination().getCountry());
        holder.tvCurrentLocation.setText("Current: " + b.getCurrentLocation().getCity() + ", " + b.getCurrentLocation().getCountry());
        holder.tvWeight.setText("Weight: " + b.getWeight());
        holder.tvStatus.setText("Status: " + b.getStatus().name());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(b));
    }

    @Override
    public int getItemCount() {
        return batches.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBatchId, tvDestination, tvCurrentLocation, tvWeight, tvStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBatchId         = itemView.findViewById(R.id.tvBatchId);
            tvDestination     = itemView.findViewById(R.id.tvDestination);
            tvCurrentLocation = itemView.findViewById(R.id.tvCurrentLocation);
            tvWeight          = itemView.findViewById(R.id.tvWeight);
            tvStatus          = itemView.findViewById(R.id.tvStatus);
        }
    }
}
