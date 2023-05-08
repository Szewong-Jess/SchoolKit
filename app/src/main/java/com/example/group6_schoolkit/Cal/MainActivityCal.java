package com.example.group6_schoolkit.Cal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group6_schoolkit.R;
import com.example.group6_schoolkit.Utils.DataBaseHelper;
import com.example.group6_schoolkit.taskCrud.CustomAdapterForListVIew;
import com.example.group6_schoolkit.taskCrud.HomeTaskCrud;
import com.example.group6_schoolkit.taskCrud.TaskModel;
import com.example.group6_schoolkit.taskCrud.TasksRecyclerViewAdapter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivityCal extends AppCompatActivity {
    RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private TextView monthYearText;
    private DataBaseHelper myDB;
    List<TaskModel> allTask=new ArrayList<>();
    ArrayList<TaskModel> tasksForUser = new ArrayList<>();
    ArrayList<TaskModel> tasksReturned = new ArrayList<>();
    List<TaskModel> tasksForAdmin = new ArrayList<>();
    TaskModel taskToGet = new TaskModel();
    String userRole,userEmail;
    RecyclerView ListViewCal;
    TasksRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_layout);
        getSupportActionBar().hide();
        ListViewCal=findViewById(R.id.taskRecyclerView);

        Intent intent=getIntent();
        userRole=intent.getExtras().getString("userloggedinrole");
        userEmail=intent.getExtras().getString("userloggedinemail");
        System.out.println(userRole + userEmail);

        myDB = new DataBaseHelper(MainActivityCal.this);
//        allTask =myDB.getAllTasks();

        if(userRole.equals("Admin")){
            tasksForUser=myDB.getAllTasks();
        }else{
            for (TaskModel task:myDB.getAllTasks()
            ) {
                if(task.getEmail().equals(userEmail)){
                    tasksForUser.add(task);
                }
            }
        }
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();





    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

//        CalendarAdapterRecycler adapterRecycler=new CalendarAdapterRecycler(daysInMonth, monthYearFromDate(selectedDate), myDB.getAllTasks());
        CalendarAdapterRecycler adapterRecycler=new CalendarAdapterRecycler(daysInMonth, monthYearFromDate(selectedDate), tasksForUser, new CalendarAdapterRecycler.SetonClick_() {
            @Override
            public void onClick_(int i, ArrayList<TaskModel> task) {
                //listview here
                tasksReturned=task;
                adapter = new TasksRecyclerViewAdapter(MainActivityCal.this);
                ListViewCal.setAdapter(adapter);
                ListViewCal.setLayoutManager(new LinearLayoutManager(MainActivityCal.this));
                adapter.setBooks(tasksReturned, userRole);

                Toast.makeText(MainActivityCal.this, "Holder Position: "+i
                        , Toast.LENGTH_SHORT).show();
                                try {
                    for (TaskModel t: task
                         ) {

                        System.out.println(t.getTitle());
                    }

                }catch (Exception e){
                    Toast.makeText(MainActivityCal.this, "No Task For this day", Toast.LENGTH_SHORT).show();
                }
            }


//            @Override
//            public void taskToReturn(List<TaskModel> task) {
//                try {
//                    for (TaskModel t: task
//                         ) {
//                        Toast.makeText(MainActivityCal.this, t.getTitle(), Toast.LENGTH_SHORT).show();
////                        System.out.println(t.getTitle());
//                    }
//
//                }catch (Exception e){
//                    Toast.makeText(MainActivityCal.this, "No Task For this day", Toast.LENGTH_SHORT).show();
//                }
//
//            }


        });

        GridLayoutManager gd = new GridLayoutManager(this, 7);
        calendarRecyclerView.setLayoutManager(gd);
        calendarRecyclerView.setAdapter(adapterRecycler);

    }

    private String monthYearFromDate(LocalDate selectedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return selectedDate.format(formatter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate selectedDate) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private void initWidgets() {
        calendarRecyclerView=findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        monthYearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityCal.this, HomeTaskCrud.class));
            }
        });
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);

        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);

        setMonthView();
    }


}