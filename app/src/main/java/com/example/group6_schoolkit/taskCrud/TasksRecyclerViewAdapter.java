package com.example.group6_schoolkit.taskCrud;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group6_schoolkit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder> {

    private ArrayList<TaskModel> tasks = new ArrayList<>();

    private Context mContext;

    public TasksRecyclerViewAdapter(Context mContext){
        this.mContext=mContext;
    }

    private int dueDayInYear;
    private String role;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(tasks.get(position).getTitle());

        holder.txtAuthor.setText(tasks.get(position).getOwner());
        holder.txtDescription.setText(tasks.get(position).getDescription()+"\n"+tasks.get(position).getEmail());
        holder.txtViewDate.setText(tasks.get(position).getDueDate());
        holder.btnImportance.setText(tasks.get(position).getImportance());


        //Timer Task
        Calendar todayCalendar = Calendar.getInstance();
        int today = todayCalendar.get(Calendar.DAY_OF_YEAR);
        //parse the string 3-06-2023 to DAY_OF_YEAR
        String dueDay = holder.txtViewDate.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = format.parse(dueDay);
            Calendar dueCalendar = Calendar.getInstance();
            dueCalendar.setTime(dueDate);
            dueDayInYear = dueCalendar.get(Calendar.DAY_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if((dueDayInYear-today)<3){
            holder.textViewTimerTask.setTextColor(Color.RED);
        }

        holder.textViewTimerTask.setText(String.valueOf(dueDayInYear-today)+" Days");

        //---end of Timer task

        if(holder.btnImportance.getText().toString().equals("High")){
            holder.btnImportance.setBackgroundColor(Color.parseColor("#FFA921"));
        }else if(holder.btnImportance.getText().toString().equals("Medium")){
            holder.btnImportance.setBackgroundColor(Color.parseColor("#FEE582"));
        }else if(holder.btnImportance.getText().toString().equals("Low")){
            holder.btnImportance.setBackgroundColor(Color.parseColor("#FFFBC3"));
        }

        //this is to set the image to 'check' if status is complete
        if(tasks.get(position).getStatus()==0){
            holder.imgBook.setImageResource(R.drawable.ic_stat_name);
        }
        //This is for Task Details
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "task is clickd", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, EditTaskActivity.class);
                intent.putExtra("TITLE", tasks.get(position).getTitle());
                intent.putExtra("DESC", tasks.get(position).getDescription());
                intent.putExtra("OWNER", tasks.get(position).getOwner());
                intent.putExtra("DATE",tasks.get(position).getDueDate());
                intent.putExtra("IMPORTANCE",tasks.get(position).getImportance());
                intent.putExtra("CATEGORY",tasks.get(position).getCategory());
                intent.putExtra("COURSE",tasks.get(position).getCourse());
                //intent.putExtra("OWNER",tasks.get(position).getOwner());
                intent.putExtra("COMMENT",tasks.get(position).getCommentBox());
                intent.putExtra("DESCRIPTION",tasks.get(position).getDescription());
                intent.putExtra("ID", tasks.get(position).getId());
                intent.putExtra("ROLE", "User");
                intent.putExtra("ROLE2", role);
                intent.putExtra("STATUS", tasks.get(position).getStatus());
                mContext.startActivity(intent);
            }
        });


     //   if(tasks.get(position).getExpanded()){
      //      holder.expandedRelativeLayout.setVisibility(View.VISIBLE);
      //      holder.downArrow.setVisibility(View.GONE);
     //   }else{
      //      holder.expandedRelativeLayout.setVisibility(View.GONE);
      //      holder.downArrow.setVisibility(View.VISIBLE);
      //  }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setBooks(ArrayList<TaskModel> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void setBooks(ArrayList<TaskModel> tasks, String role) {
        this.tasks = tasks;
        notifyDataSetChanged();
        this.role=role;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgBook;
        private TextView txtTitle,txtAuthor, txtDescription;
        private TextView txtViewDate;
        private Button btnImportance;
        private TextView textViewTimerTask;


        // private ImageView downArrow, upArrow;
      //  private RelativeLayout expandedRelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent=itemView.findViewById(R.id.parent);
            imgBook=itemView.findViewById(R.id.userimage);
            txtViewDate = itemView.findViewById(R.id.textViewDate);
            txtTitle= itemView.findViewById(R.id.textViewTitle);
            txtAuthor=itemView.findViewById(R.id.textViewAuthor);
            txtDescription = itemView.findViewById(R.id.textViewDescription);
            btnImportance = itemView.findViewById(R.id.buttonImportance);
            textViewTimerTask = itemView.findViewById(R.id.textViewTimerTask);

        }
    }
}
