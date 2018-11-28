package com.example.hashi.weatherproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTemperature, tvDescription, tvWindSpeed, tvCity, tvCountry;

    public MyViewHolder(View itemView) {
        super(itemView);

        tvTemperature = itemView.findViewById(R.id.tvTemperature);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        tvWindSpeed = itemView.findViewById(R.id.tvWindSpeed);
        tvCity = itemView.findViewById(R.id.tvCity);
        tvCountry = itemView.findViewById(R.id.tvCountry);
    }
}
