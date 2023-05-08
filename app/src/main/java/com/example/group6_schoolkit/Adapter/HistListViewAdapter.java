package com.example.group6_schoolkit.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.group6_schoolkit.R;

import java.util.ArrayList;

public class HistListViewAdapter extends BaseAdapter {


    ArrayList<String> history = new ArrayList<>();

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public HistListViewAdapter(ArrayList<String> history) {
        this.history = history;
    }

    @Override
    public int getCount() {
        return history.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //inflate
        if(view==null){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_layout, viewGroup, false);
        }

        //txtView
        TextView txt = view.findViewById(R.id.txtTag);
        TextView txt2 = view.findViewById(R.id.txtDetails);
        txt2.setText(history.get(i));
        txt.setText("");

        //

        return view;
    }
}
