package com.example.vizoralejelento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PeriodItemAdapter extends RecyclerView.Adapter<PeriodItemAdapter.ViewHolder>{

    private ArrayList<PeriodItem> periodItemsArray;
    private Context mContext;

    PeriodItemAdapter(Context context, ArrayList<PeriodItem> records) {
        this.periodItemsArray = records;
        this.mContext = context;
    }


    @Override
    public PeriodItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(PeriodItemAdapter.ViewHolder holder, int position) {
        PeriodItem periodItem = periodItemsArray.get(position);
        holder.bindTo(periodItem);

    }

    @Override
    public int getItemCount() {
        return periodItemsArray.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView month;
        private TextView quantity;

        ViewHolder(View itemView) {
            super(itemView);

            month = itemView.findViewById(R.id.textMonth);
            quantity = itemView.findViewById(R.id.textQuantity);
        }

        public void bindTo(PeriodItem periodItem) {
            month.setText(periodItem.getMonth());
            quantity.setText(periodItem.getQuantity());
        }
    }
}
