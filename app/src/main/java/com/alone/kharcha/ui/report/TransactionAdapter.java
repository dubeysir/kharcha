package com.alone.kharcha.ui.report;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alone.kharcha.R;
import com.alone.kharcha.data.entity.TransactionEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter
        extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<TransactionEntity> list = new ArrayList<>();

    public TransactionAdapter(List<TransactionEntity> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<TransactionEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        TransactionEntity t = list.get(position);

        holder.tvTitle.setText(t.title);
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());

        holder.tvDate.setText(
                sdf.format(new Date(t.timestamp))
        );


        // 🔥 INCOME / EXPENSE logic
        if ("INCOME".equals(t.type)) {
            holder.tvAmount.setText("+₹" + t.amount);
            holder.tvAmount.setTextColor(
                    Color.parseColor("#2E7D32")); // green
        } else {
            holder.tvAmount.setText("-₹" + t.amount);
            holder.tvAmount.setTextColor(
                    Color.parseColor("#C62828")); // red
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvAmount, tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
