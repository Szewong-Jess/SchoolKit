package com.example.group6_schoolkit.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.group6_schoolkit.taskCrud.TaskModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import kotlinx.coroutines.scheduling.Task;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "TASK_DATABASE";
    private static final String TABLE_NAME = "TASK_TABLE";
    private static final String COL_1  = "ID";
    private static final String COL_2= "TITLE";
    private static final String COL_3 = "DESCRIPTION";
    private static final String COL_4  = "DUEDATE";
    private static final String COL_5  = "IMPORTANCE";
    private static final String COL_6  = "CATEGORY";
    private static final String COL_7  = "COURSE";
    private static final String COL_8  = "OWNER";
    private static final String COL_9  = "COMMENT";
    private static final String COL_10  = "STATUS";
    private static final String COL_11 = "EMAIL";



    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,  6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DESCRIPTION TEXT, DUEDATE TEXT, IMPORTANCE TEXT, CATEGORY TEXT, COURSE TEXT, OWNER TEXT, COMMENT TEXT, STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
        db.execSQL("ALTER TABLE TASK_TABLE ADD COLUMN EMAIL TEXT DEFAULT NULL;");

    }

    public void insertTask(TaskModel taskModel){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2, taskModel.getTitle());
        values.put(COL_3, taskModel.getDescription());
        values.put(COL_4, taskModel.getDueDate());
        values.put(COL_5, taskModel.getImportance());
        values.put(COL_6, taskModel.getCategory());
        values.put(COL_7, taskModel.getCourse());
        values.put(COL_8, taskModel.getOwner());
        values.put(COL_9, taskModel.getCommentBox());
        values.put(COL_10, taskModel.getStatus());
        values.put(COL_11, taskModel.getEmail());
        db.insert(TABLE_NAME, null, values);
    }

    public void updateTask(int id, TaskModel taskModel){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2, taskModel.getTitle());
        values.put(COL_3, taskModel.getDescription());
        values.put(COL_4, taskModel.getDueDate());
        values.put(COL_5, taskModel.getImportance());
        values.put(COL_6, taskModel.getCategory());
        values.put(COL_7, taskModel.getCourse());
        values.put(COL_8, taskModel.getOwner());
//        values.put(COL_9, taskModel.getCommentBox());
        values.put(COL_10, taskModel.getStatus());
        values.put(COL_11, taskModel.getEmail());
        // Retrieve the current value of COL_9 from the database
        Cursor cursor = db.query(TABLE_NAME, new String[] { COL_9 }, "ID=?", new String[] { String.valueOf(id) }, null, null, null);
        String currentCommentBox = null;
        if (cursor.moveToFirst()) {
            currentCommentBox = cursor.getString(cursor.getColumnIndex(COL_9));
        }
        cursor.close();

        // Append the new value to the current value of COL_9
        String newCommentBox = taskModel.getCommentBox();
        if (currentCommentBox != null) {
            newCommentBox = currentCommentBox + "|" + newCommentBox;
        }
        values.put(COL_9, newCommentBox);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db =this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});

    }

    public void deleteAllTasks(){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<TaskModel> getTasksForDate(int year, int month, int dayOfMonth){
        db=this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            c=db.query(TABLE_NAME, null,"strftime('%Y-%m-%d', " + COL_4 + ") = ?", new String[]{String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)}, null, null ,null );
            if(c!=null){
                if(c.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)) );
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));
                        modelList.add(task);
                    }while(c.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }

    public ArrayList<TaskModel> getTasksForDateAfterToday(){
        db=this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            Calendar calendar = Calendar.getInstance();
            c=db.query(TABLE_NAME, null,"strftime('%Y-%m-%d', " + COL_4 + ") >= ?", new String[]{String.format("%04d-%02d-%02d", calendar.get(Calendar.YEAR),  calendar.get(Calendar.MONTH)  + 1,  calendar.get(Calendar.DAY_OF_MONTH))}, null, null ,null );
            if(c!=null){
                if(c.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)) );
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));
                        modelList.add(task);
                    }while(c.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }

    public ArrayList<TaskModel> getTasksForDateAfterTodayForOneUser(String email){
        db=this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            Calendar calendar = Calendar.getInstance();
            c = db.query(TABLE_NAME, null,
                    "strftime('%Y-%m-%d', " + COL_4 + ") >= ? AND " + COL_11 + " = ?",
                    new String[]{String.format("%04d-%02d-%02d", calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)),
                            email},
                    null, null, null);
            if(c!=null){
                if(c.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)) );
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));
                        modelList.add(task);
                    }while(c.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }

    public ArrayList<TaskModel> getTasksForOneUser(String email){
        db = this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            c = db.query(TABLE_NAME, null, COL_11 + " = ?", new String[]{email}, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)));
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));

                        modelList.add(task);
                    } while (c.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }

    //Sort by date
    public ArrayList<TaskModel> getTasksForOneUserSortByDate(String email){
        db = this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            c = db.query(TABLE_NAME, null, COL_11 + " = ?", new String[]{email}, null, null, COL_4+" ASC");
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)));
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));

                        modelList.add(task);
                    } while (c.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }

    //sort by PRIORITY
    public ArrayList<TaskModel> getTasksForOneUserSortByPriority(String email){
        db = this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            c = db.query(TABLE_NAME, null, COL_11 + " = ?", new String[]{email}, null, null, "CASE " + COL_5 + " WHEN 'High' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Low' THEN 3 ELSE 4 END ASC");
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)));
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));

                        modelList.add(task);
                    } while (c.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }


    //Search
    public ArrayList<TaskModel> getTasksForSearchInput(String input){
        db = this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            c = db.query(TABLE_NAME, null, COL_2 + " LIKE ?", new String[]{"%" + input + "%"}, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)));
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));
                        modelList.add(task);
                    } while (c.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }


    public ArrayList<TaskModel> getAllTasks(){
        db=this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            c=db.query(TABLE_NAME, null,null,null, null, null ,null );
            if(c!=null){
                if(c.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)) );
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));
                        modelList.add(task);

                    }while(c.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }

    public ArrayList<TaskModel> getAllTasksSorting(){
        db=this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            c=db.query(TABLE_NAME, null,null,null, null, null ,COL_4+" ASC" );
            if(c!=null){
                if(c.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)) );
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));
                        modelList.add(task);
                    }while(c.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }

    //Sorting by High Medium Low
    public ArrayList<TaskModel> getAllTasksSortingByPriority(){
        db=this.getWritableDatabase();
        Cursor c = null;
        ArrayList<TaskModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            c=db.query(TABLE_NAME, null,null,null, null, null ,"CASE " + COL_5 + " WHEN 'High' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Low' THEN 3 ELSE 4 END ASC");
            if(c!=null){
                if(c.moveToFirst()){
                    do{
                        TaskModel task = new TaskModel();
                        task.setId(c.getInt(c.getColumnIndex(COL_1)));
                        task.setTitle(c.getString(c.getColumnIndex(COL_2)) );
                        task.setDescription(c.getString(c.getColumnIndex(COL_3)));
                        task.setDueDate(c.getString(c.getColumnIndex(COL_4)));
                        task.setImportance(c.getString(c.getColumnIndex(COL_5)));
                        task.setCategory(c.getString(c.getColumnIndex(COL_6)));
                        task.setCourse(c.getString(c.getColumnIndex(COL_7)));
                        task.setOwner(c.getString(c.getColumnIndex(COL_8)));
                        task.setCommentBox(c.getString(c.getColumnIndex(COL_9)));
                        task.setStatus(c.getInt(c.getColumnIndex(COL_10)));
                        task.setEmail(c.getString(c.getColumnIndex(COL_11)));
                        modelList.add(task);
                    }while(c.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            c.close();
        }
        return modelList;
    }

}
