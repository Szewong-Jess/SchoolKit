package com.example.group6_schoolkit.taskCrud;

import java.util.ArrayList;

public class TaskUtil {
    private static TaskUtil instace;
    private static ArrayList<TaskModel> allTasks;

    private TaskUtil(){
        if(allTasks==null){
            allTasks=new ArrayList<>();
            initData();
        }
    }

    private void initData() {
        allTasks.add(new TaskModel("title1","desc1", "dueDate1", "imp", "category", "course", "owner", "comment", 1,"email"));
        allTasks.add(new TaskModel("title2","desc2", "dueDate12", "imp", "category", "course", "owner2", "comment", 1, "email"));
        allTasks.add(new TaskModel("title3","desc3", "dueDate13", "imp", "category", "course", "owner3", "comment", 1, "email"));
    }

    public static TaskUtil getInstance(){
        if(instace!=null){
            return instace;
        }else{
            instace=new TaskUtil();
            return instace;
        }
    }

    public static ArrayList<TaskModel> getAllTasks(){
        return allTasks;
    }

    public TaskModel getTasksByid(int id){
        for (TaskModel t:allTasks
             ) {if(t.getId()==id){
                 return t;
        }

        }
            return null;

    }

    public void addTask(TaskModel taskModel){
        allTasks.add(taskModel);
    }


}
