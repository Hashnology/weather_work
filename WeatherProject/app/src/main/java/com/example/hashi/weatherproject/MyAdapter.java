package com.example.hashi.weatherproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private MyWeather current;
    private List<MyWeather> data;
    private  MyViewHolder viewHolder;

    public MyAdapter(Context context, List<MyWeather> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view, parent, false);
        viewHolder = new MyViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        current = data.get(position);

        final String id = current.getId();
        final String description = current.getDescription();
        final String temperature = current.getTemperature();
        final String windspeed = current.getWindspeed();
        final String city = current.getCity();
        final String country = current.getCountry();


        viewHolder.tvDescription.setText(description);
        viewHolder.tvTemperature.setText(temperature);
        viewHolder.tvWindSpeed.setText(windspeed);
        viewHolder.tvCity.setText(city);
        viewHolder.tvCountry.setText(country);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
