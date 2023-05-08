package com.example.group6_schoolkit.taskCrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.group6_schoolkit.Adapter.HistListViewAdapter;
import com.example.group6_schoolkit.R;

import java.util.ArrayList;

public class CommentHistory extends AppCompatActivity {
    ListView listView;
    ArrayList<String> histList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_history);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        String hist = intent.getStringExtra("HIST");
//        System.out.println(hist);

        String[] valueSplit = hist.split("\\|");
        for (String x:valueSplit
             ) {
            System.out.println(x);
            histList.add(x);
        }

        listView=findViewById(R.id.histListView);
        HistListViewAdapter adapter = new HistListViewAdapter(histList);
        listView.setAdapter(adapter);

    }
}