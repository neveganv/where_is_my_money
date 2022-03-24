package com.example.whereismymoney;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    ArrayList money_id, money_description, money_sum, money_status;

    CustomAdapter(Context context, ArrayList money_id, ArrayList money_description, ArrayList money_sum, ArrayList money_status) {
        this.context = context;
        this.money_id = money_id;
        this.money_description = money_description;
        this.money_sum = money_sum;
        this.money_status = money_status;

    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.money_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        if (String.valueOf(money_status.get(position)).equals("0")) {
            holder.card_money.setCardBackgroundColor(Color.parseColor("#FCE4EC"));
            holder.sum_text.setText("-" + String.valueOf(money_sum.get(position)));
        }
        else {
            holder.card_money.setCardBackgroundColor(Color.parseColor("#E8F5E9"));
            holder.sum_text.setText("+" + String.valueOf(money_sum.get(position)));
        }
        holder.description_text.setText(String.valueOf(money_description.get(position)));
    }

    @Override
    public int getItemCount() {
        return money_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView description_text, sum_text;
        CardView card_money;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_money = itemView.findViewById(R.id.card_money);
            description_text = itemView.findViewById(R.id.description_text);
            sum_text = itemView.findViewById(R.id.sum_text);
        }
    }
}
