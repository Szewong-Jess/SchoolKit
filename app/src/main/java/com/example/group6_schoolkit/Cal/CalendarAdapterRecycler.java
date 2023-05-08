package com.example.group6_schoolkit.Cal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group6_schoolkit.R;
import com.example.group6_schoolkit.taskCrud.TaskModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//import kotlinx.coroutines.scheduling.Task;

//2
public class CalendarAdapterRecycler extends RecyclerView.Adapter<CalendarAdapterRecycler.ViewHolder_>{
//3 add data
    ArrayList<String> daysInMonth;
    String month;
    List<TaskModel> tasks;
//    List<TaskModel> taskToReturn = new ArrayList<>();
    SetonClick_ setonClick_;
    HashMap<Integer,ArrayList<TaskModel> > taskAndPosition = new HashMap<>();


    public CalendarAdapterRecycler(ArrayList<String> daysInMonth, String month, List<TaskModel> tasks, SetonClick_ setonClick_) {
        this.daysInMonth = daysInMonth;
        this.month=month;
        this.tasks=tasks;
        this.setonClick_=setonClick_;
    }
    public CalendarAdapterRecycler(ArrayList<String> daysInMonth, String month, List<TaskModel> tasks) {
        this.daysInMonth = daysInMonth;
        this.month=month;
        this.tasks=tasks;

    }

    public CalendarAdapterRecycler() {
    }

    //6
    //4 constructor
    @NonNull
    @Override
    public ViewHolder_ onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cal_layout_cell, parent, false);
        //setting up the size of the cells in calendar view
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.12);
        ViewHolder_ holder = new ViewHolder_(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                List<TaskModel> taskToReturn = new ArrayList<>(taskAndPosition.get(holder.getAdapterPosition()));
//                taskAndPosition.put(holder.getAdapterPosition(), taskToReturn);
//                taskToReturn.clear();


                try{
                    ArrayList<TaskModel> tasksToReturn = new ArrayList<>();
                    if(taskAndPosition.get(holder.getAdapterPosition())==null){

                    }else{
                        tasksToReturn=taskAndPosition.get(holder.getAdapterPosition());
                    }
                    setonClick_.onClick_(holder.getAdapterPosition(),tasksToReturn);
//                    taskToReturn.clear(); notifyDataSetChanged();
                }catch (Exception e){
                    System.out.println("ERROR from clicker try - catch" + e.getMessage());
                }

//                    taskToReturn=(taskAndPosition.get(holder.getAdapterPosition()));



                notifyDataSetChanged();
            }
        });



        return holder;
    }
//7
    @Override
    public void onBindViewHolder(@NonNull ViewHolder_ holder, int position) {
        holder.textView.setText(daysInMonth.get(position));
//        List<TaskModel> listOfTask=new ArrayList<>();
//        System.out.println(tasks.size());
        for(int i=0;i< tasks.size();i++){
            String dueDate = tasks.get(i).getDueDate();
            String[] parts = dueDate.split("-");
            String[] partsForMonth = month.split(" ");

            String year = parts[0];   // "2023"
            String month2 = parts[1];  // "04"
            String day = parts[2];    // "23"
            String monthFromDB = partsForMonth[0];

//            System.out.println(month2+monthFromDB);
            if(month2.equals("01") && monthFromDB.equals("January")){
//                System.out.println("APRIL TEST OK " + daysInMonth.size());
                colorGrey(day,  position,  holder, tasks.get(i));
            } else if (month2.equals("02") && monthFromDB.equals("February")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("03") && monthFromDB.equals("March")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("04") && monthFromDB.equals("April")) {
                colorGrey(day,  position,  holder, tasks.get(i));

            }else if (month2.equals("05") && monthFromDB.equals("May")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("06") && monthFromDB.equals("June")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("07") && monthFromDB.equals("July")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("08") && monthFromDB.equals("August")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("09") && monthFromDB.equals("September")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("10") && monthFromDB.equals("October")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("11") && monthFromDB.equals("November")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }else if (month2.equals("12") && monthFromDB.equals("December")) {
                colorGrey(day,  position,  holder, tasks.get(i));
            }
            else{
                System.out.println("APRIL TEST NOT OK");
            }

        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<TaskModel> taskToReturn = new ArrayList<>();
//                taskToReturn=taskAndPosition.get(holder.getAdapterPosition());
//                setonClick_.onClick_(holder.getAdapterPosition(),taskToReturn);
//                try{
//
//                }catch (Exception e){
////                    taskToReturn.clear();
//                }
//
////                    taskToReturn=(taskAndPosition.get(holder.getAdapterPosition()));
//
//
//
//                notifyDataSetChanged();
//            }
//        });
    }
//5
    @Override
    public int getItemCount() {
        return daysInMonth.size();
    }

    //1 write innerclass holder
    public class ViewHolder_ extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder_(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.cellDayText);
        }
    }

    public void colorGrey(String day, int position, ViewHolder_ holder, TaskModel task){

//        for(int j=0;j<daysInMonth.size();j++){
//
            try{
                if(Integer.parseInt(daysInMonth.get(position))==Integer.parseInt(day)){
                    switch (task.getImportance().toString()){
                        case "Low":holder.textView.setBackgroundColor(Color.LTGRAY);break;
                        case "Medium":holder.textView.setBackgroundColor(Color.GREEN);break;
                        case "High":holder.textView.setBackgroundColor(Color.RED);break;
                        default:break;
                    }
////                    holder.textView.setBackgroundColor(Color.LTGRAY);
//                    List<TaskModel> taskList=new ArrayList<>();
////                    taskList.add(task);
////                    taskAndPosition.put(holder.getAdapterPosition(),taskList);
//
//                    //check if List from hashmap is empty
//                    List<TaskModel> tasklistTocheck = taskAndPosition.get(holder.getAdapterPosition());
//                    if(tasklistTocheck.isEmpty() && tasklistTocheck==null){
//                        taskList.add(task);
//                        taskAndPosition.put(holder.getAdapterPosition(),taskList);
//                    }else {
//                        List<TaskModel> taskToAdd = taskAndPosition.get(holder.getAdapterPosition());
//                        taskAndPosition.put(holder.getAdapterPosition(),taskToAdd);
//                    }

                    ArrayList<TaskModel> taskList = taskAndPosition.get(holder.getAdapterPosition());
                    if (taskList == null || taskList.isEmpty()) {
                        taskList = new ArrayList<>();
                        taskList.add(task);
                        // Add the list to the hashmap with the holder's adapter position as the key
                        taskAndPosition.put(holder.getAdapterPosition(), taskList);

                        Set<TaskModel> set = new HashSet<>(taskAndPosition.get(holder.getAdapterPosition()));
                        taskAndPosition.get(holder.getAdapterPosition()).clear();
                        taskAndPosition.get(holder.getAdapterPosition()).addAll(set);
                    }else {
                        // If the list already exists, add the task to it
                        taskList.add(task);
                        Set<TaskModel> set = new HashSet<>(taskAndPosition.get(holder.getAdapterPosition()));
                        taskAndPosition.get(holder.getAdapterPosition()).clear();
                        taskAndPosition.get(holder.getAdapterPosition()).addAll(set);
                    }
//




//                    break;
                }
            }catch (Exception e){

            }

//        }
    }

    public interface SetonClick_{
        public void onClick_(int i, ArrayList<TaskModel> task);
//        public void taskToReturn(List<TaskModel> task);

    }
}
