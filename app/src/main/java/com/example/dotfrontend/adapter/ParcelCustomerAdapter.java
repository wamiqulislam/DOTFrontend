package com.example.dotfrontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotfrontend.R;
import com.example.dotfrontend.model.Parcel;

import java.util.ArrayList;
import java.util.List;

public class ParcelCustomerAdapter
        extends RecyclerView.Adapter<ParcelCustomerAdapter.VH> {

    private final List<Parcel> items;

    public ParcelCustomerAdapter(List<Parcel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parcel_customer, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Parcel p = items.get(pos);
        h.tvParcelId.setText("Parcel #" + p.getParcelId());
        h.tvType.setText("Type: " + p.getType());
        h.tvWeight.setText("Weight: " + p.getWeight());
        h.tvOrigin.setText("Origin: " +
                p.getOriginId().getCity() + ", " + p.getOriginId().getCountry());
        h.tvDestination.setText("Dest: " +
                p.getDestinationId().getCity() + ", " + p.getDestinationId().getCountry());
        h.tvSendAddress.setText("Sender Addr: " + p.getSendAddress());
        h.tvAddress.setText("Receiver Addr: " + p.getAddress());
        long batchId = p.getBatch() != null
                ? p.getBatch().getBatchId()
                : -1L;
        h.tvBatchId.setText("Batch ID: " +
                (batchId >= 0 ? batchId : "N/A"));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<Parcel> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvParcelId, tvType, tvWeight,
                tvOrigin, tvDestination,
                tvSendAddress, tvAddress,
                tvBatchId;

        VH(@NonNull View v) {
            super(v);
            tvParcelId      = v.findViewById(R.id.tvParcelId);
            tvType          = v.findViewById(R.id.tvType);
            tvWeight        = v.findViewById(R.id.tvWeight);
            tvOrigin        = v.findViewById(R.id.tvOrigin);
            tvDestination   = v.findViewById(R.id.tvDestination);
            tvSendAddress   = v.findViewById(R.id.tvSendAddress);
            tvAddress       = v.findViewById(R.id.tvAddress);
            tvBatchId       = v.findViewById(R.id.tvBatchId);
        }
    }
}
