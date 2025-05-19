package com.example.dotfrontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotfrontend.R;
import com.example.dotfrontend.model.Parcel;
import java.util.List;

public class ParcelAdapter extends RecyclerView.Adapter<ParcelAdapter.VH> {
    private final List<Parcel> items;

    public ParcelAdapter(List<Parcel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parcel, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Parcel p = items.get(i);
        h.tvParcelId.setText("Parcel #" + p.getParcelId());
        h.tvType.setText("Type: " + p.getType());
        h.tvWeight.setText("Weight: " + p.getWeight());
        h.tvOrigin.setText("From: " +
                p.getOriginId().getCity() + ", " + p.getOriginId().getCountry());
        h.tvDestination.setText("To: " +
                p.getDestinationId().getCity() + ", " + p.getDestinationId().getCountry());
        h.tvSenderAddress.setText("Sender Addr: " + p.getSendAddress());
        h.tvReceiverAddress.setText("Receiver Addr: " + p.getAddress());
        h.tvCustomerId.setText("Customer #: " +
                (p.getCustomerId() != null
                        ? p.getCustomerId().getUserId()
                        : "N/A"));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvParcelId,
                tvType,
                tvWeight,
                tvOrigin,
                tvDestination,
                tvSenderAddress,
                tvReceiverAddress,
                tvCustomerId;

        VH(@NonNull View v) {
            super(v);
            tvParcelId       = v.findViewById(R.id.tvParcelId);
            tvType           = v.findViewById(R.id.tvType);
            tvWeight         = v.findViewById(R.id.tvWeight);
            tvOrigin         = v.findViewById(R.id.tvOrigin);
            tvDestination    = v.findViewById(R.id.tvDestination);
            tvSenderAddress  = v.findViewById(R.id.tvSenderAddress);
            tvReceiverAddress= v.findViewById(R.id.tvReceiverAddress);
            tvCustomerId     = v.findViewById(R.id.tvCustomerId);
        }
    }
}
