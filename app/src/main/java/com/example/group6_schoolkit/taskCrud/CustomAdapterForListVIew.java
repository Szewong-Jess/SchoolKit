package com.example.group6_schoolkit.taskCrud;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group6_schoolkit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//import kotlinx.coroutines.scheduling.Task;

public class CustomAdapterForListVIew extends BaseAdapter {
    ArrayList<TaskModel> nameLists = new ArrayList<>();
    private int dueDayInYear;
    private Context mContext;
    private String role;

    public CustomAdapterForListVIew(ArrayList<TaskModel> nameLists) {
        this.nameLists = nameLists;
    }

    public CustomAdapterForListVIew(ArrayList<TaskModel> nameLists, Context mContext, String role) {
        this.nameLists = nameLists;
        this.mContext = mContext;
        this.role=role;
    }

    @Override
    public int getCount() {
        return nameLists.size();
    }

    @Override
    public Object getItem(int i) {
        return nameLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //inflate
        if(view==null){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.taskitemviewforlistview, viewGroup, false);
        }
        //set text view
        TextView txtviewHomeTitle = view.findViewById(R.id.textViewHomeTitle);
        TextView txtViewHomeDate = view.findViewById(R.id.textViewHomeDate);
        TextView txtViewHomeDes = view.findViewById(R.id.textViewHomeDes);
        Button btnHomeImportance = view.findViewById(R.id.buttonHomeImportance);
        TextView textViewHomeTaskOwner = view.findViewById(R.id.textViewHomeTaskOwner);


        //TimerTask------
        Calendar todayCalendar = Calendar.getInstance();
        int today = todayCalendar.get(Calendar.DAY_OF_YEAR);

        //parse the string 3-06-2023 to DAY_OF_YEAR
        String dueDay = nameLists.get(i).getDueDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = format.parse(dueDay);
            Calendar dueCalendar = Calendar.getInstance();
            dueCalendar.setTime(dueDate);
            dueDayInYear = dueCalendar.get(Calendar.DAY_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if((dueDayInYear-today)<=3){
            btnHomeImportance.setTextColor(Color.RED);
        }
        btnHomeImportance.setText(String.valueOf(dueDayInYear-today)+" Days");

        //End of timer task


        txtviewHomeTitle.setText(nameLists.get(i).getTitle().toString());
        txtViewHomeDate.setText(nameLists.get(i).getDueDate().toString());
        txtViewHomeDes.setText(nameLists.get(i).getDescription().toString());
        textViewHomeTaskOwner.setText(nameLists.get(i).getOwner());

        String importance = nameLists.get(i).getImportance();
        if(importance.equals("High")){
            btnHomeImportance.setBackgroundColor(Color.parseColor("#FFA921"));
        }else if(importance.equals("Medium")){
            btnHomeImportance.setBackgroundColor(Color.parseColor("#FEE582"));
        }else if(importance.equals("Low")){
            btnHomeImportance.setBackgroundColor(Color.parseColor("#FFFBC3"));
        }
        //drawable?
        //put into inttent


        //dire to edit task
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(mContext,EditTaskActivity.class);
                myIntent.putExtra("TITLE",nameLists.get(i).getTitle());
                myIntent.putExtra("DESC", nameLists.get(i).getDescription());
                myIntent.putExtra("OWNER", nameLists.get(i).getOwner());
                myIntent.putExtra("DATE",nameLists.get(i).getDueDate());
                myIntent.putExtra("IMPORTANCE",nameLists.get(i).getImportance());
                myIntent.putExtra("CATEGORY",nameLists.get(i).getCategory());
                myIntent.putExtra("COURSE",nameLists.get(i).getCourse());
                myIntent.putExtra("COMMENT",nameLists.get(i).getCommentBox());
                myIntent.putExtra("DESCRIPTION",nameLists.get(i).getDescription());
                myIntent.putExtra("ID", nameLists.get(i).getId());
                myIntent.putExtra("ROLE", "User");
                myIntent.putExtra("ROLEfromHome", role);
                myIntent.putExtra("STATUSFromList", nameLists.get(i).getStatus());
                mContext.startActivity(myIntent);

            }
        });





        return view;
    }

    public void setBooks(ArrayList<TaskModel> tasks) {
        this.nameLists = tasks;
        notifyDataSetChanged();
    }
}
