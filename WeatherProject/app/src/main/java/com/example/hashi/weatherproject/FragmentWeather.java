package com.example.hashi.weatherproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentWeather extends Fragment implements View.OnClickListener {

    private View fragmentView;
    private Context context;

    //Instances for Category Recycler View
    private ArrayList<MyWeather> creationsList;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.weather_list, container, false);

        context = getActivity();

        linkViews();

        creationsList = new ArrayList<>();
        recyclerView = fragmentView.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(gridLayoutManager);

        AppDataBase appDataBase = new AppDataBase(getContext());
        appDataBase.open();
        myAdapter = new MyAdapter(context, appDataBase.getAllWeather());
        recyclerView.setAdapter(myAdapter);
        appDataBase.close();


        return fragmentView;
    }

    private void linkViews() {
    }

    @Override
    public void onClick(View v) {
    }
}
