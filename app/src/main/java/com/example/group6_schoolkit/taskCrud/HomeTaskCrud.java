package com.example.group6_schoolkit.taskCrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.group6_schoolkit.Cal.MainActivityCal;
import com.example.group6_schoolkit.Login.activity_login;
import com.example.group6_schoolkit.R;
import com.example.group6_schoolkit.Utils.DataBaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomeTaskCrud extends AppCompatActivity {
    Button button, button2;
    ImageButton btn_logout;

    TextView taskHomeTitle;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
//    ArrayList<String> sampleList = new ArrayList<>(Arrays.asList("task1", "task2", "Task3"));
    ListView listMy;
    private CalendarView calendarView;
    private Button btnSwitchView;
    private DataBaseHelper myDB;

    ArrayList<String> usersList = new ArrayList<>();
    HashMap<String, String> userEmailList = new HashMap<>();
    private DatabaseReference mDatabase;
    String nameDisplay,roleDisplay, email;





    //Weather API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_task_crud);
        getSupportActionBar().hide();

        //API
        TextView testWeather = findViewById(R.id.textViewTestWeather);
        TextView textViewCity = findViewById(R.id.textViewCity);

        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=Vancouver&appid=e0d951f88f25e04392121560f7ccc632";
        //Weather Api


        //Joke Api
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(weatherUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response != null) {

                        //icon

                        JSONArray weatherArray = response.getJSONArray("weather");
                        JSONObject weatherObj = weatherArray.getJSONObject(0);
                        String weatherIconCode = weatherObj.getString("icon");
                        ImageView imgweatherIcon = findViewById(R.id.imageViewHomeWeatherIcon);
                        String iconURL = "https://openweathermap.org/img/wn/" + weatherIconCode + "@2x.png";
                        Glide.with(HomeTaskCrud.this).load(iconURL).into(imgweatherIcon);


                        //temperature
                        JSONObject mainObj = response.getJSONObject("main");
                        double temp = mainObj.getDouble("temp");
                        double celsius = temp - 273.15;
                        double feelsLike = mainObj.getDouble("feels_like");
                        double tempMin = mainObj.getDouble("temp_min");
                        double tempMax = mainObj.getDouble("temp_max");
                        int pressure = mainObj.getInt("pressure");
                        int humidity = mainObj.getInt("humidity");
                        testWeather.setText(String.format("%.2f\u2103", celsius));

                        //City
                        String cityName = response.getString("name");
                        textViewCity.setText(cityName);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                testWeather.setText(String.valueOf(error));

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        //requestQueue.add(jsonObjectRequest2);
        //end of API

//        String nameDisplay= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().getResult().child("name").getValue().toString();
//        mDatabase = FirebaseDatabase.getInstance().getReference().child()

        button = findViewById(R.id.btnSeeAllTasks);
        button2 = findViewById(R.id.btnAddTask);
        btn_logout = findViewById(R.id.btn_logout);
        taskHomeTitle = findViewById(R.id.taskHomeTitle);
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myDB = new DataBaseHelper(HomeTaskCrud.this);
        listMy = findViewById(R.id.listViewHomeTaskCrud);

        //dire to edit task
       /* listMy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("clicked","Is clicked");
                Toast.makeText(HomeTaskCrud.this, "Clicked!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(HomeTaskCrud.this, EditTaskActivity.class);
                myIntent.putExtra("TITLE",adapter.nameLists.get(position).getTitle());
                myIntent.putExtra("DESC", adapter.nameLists.get(position).getDescription());
                myIntent.putExtra("OWNER", adapter.nameLists.get(position).getOwner());
                myIntent.putExtra("DATE",adapter.nameLists.get(position).getDueDate());
                myIntent.putExtra("IMPORTANCE",adapter.nameLists.get(position).getImportance());
                myIntent.putExtra("CATEGORY",adapter.nameLists.get(position).getCategory());
                myIntent.putExtra("COURSE",adapter.nameLists.get(position).getCourse());
                myIntent.putExtra("COMMENT",adapter.nameLists.get(position).getCommentBox());
                myIntent.putExtra("DESCRIPTION",adapter.nameLists.get(position).getDescription());
                myIntent.putExtra("ID", adapter.nameLists.get(position).getId());
                startActivity(myIntent);
            }
        }); */
        calendarView = findViewById(R.id.calendarView);
        calendarView.setVisibility(View.GONE);
        btnSwitchView = findViewById(R.id.btnSwitchView);

        //flag

        btnSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this will go to the calendar view
                startActivity(new Intent(HomeTaskCrud.this, MainActivityCal.class));
                Intent intent = new Intent(HomeTaskCrud.this, MainActivityCal.class);
                intent.putExtra("userloggedinrole", roleDisplay);
                intent.putExtra("userloggedinemail", email);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                ArrayList<TaskModel> tasks = myDB.getTasksForDate(year, month, dayOfMonth);
                CustomAdapterForListVIew adapter = new CustomAdapterForListVIew(tasks);
                listMy.setAdapter(adapter);
                listMy.setVisibility(View.VISIBLE);
                calendarView.setVisibility(View.GONE);
            }
        });








//        if(user.getUid()== FirebaseDatabase.getInstance().g)





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTaskCrud.this, AllTasksActivity.class);
                intent.putExtra("USEREMAILLIST", userEmailList);
                intent.putExtra("USERS", usersList);
                intent.putExtra("USERLOGGEDIN",nameDisplay);
                intent.putExtra("EMAIL",email);
                intent.putExtra("ROLE",roleDisplay);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTaskCrud.this, AddTaskActivity.class);
                //this is to get the list of users
                intent.putExtra("USERS", usersList);
                intent.putExtra("USEREMAILLIST", userEmailList);
                intent.putExtra("USERLOGGEDIN",nameDisplay);
                intent.putExtra("EMAIL",email);
                intent.putExtra("ROLE",roleDisplay);
                startActivity(intent);
            }
        });


       //Logout
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeTaskCrud.this);
                builder.setMessage("Are you sure you want to log out?")
                                .setTitle("Confirm")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(HomeTaskCrud.this, activity_login.class));
                                            }
                                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });



        //this is to get the user information and the role, to display
        mDatabase= FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot =task.getResult();
                        nameDisplay = String.valueOf(dataSnapshot.child("name").getValue());
                        roleDisplay = String.valueOf(dataSnapshot.child("role").getValue());
                        email = String.valueOf(dataSnapshot.child("email").getValue());
                        taskHomeTitle.setText("WELCOME "+"\n"+nameDisplay
                        +"\n"+ roleDisplay);
                        if(roleDisplay==null){
                            Toast.makeText(HomeTaskCrud.this, "unknown Role is login", Toast.LENGTH_SHORT).show();
                        }else if(roleDisplay.equals("Admin")){
                            CustomAdapterForListVIew adapter = new CustomAdapterForListVIew(myDB.getTasksForDateAfterToday(),HomeTaskCrud.this, roleDisplay);
                            listMy.setAdapter(adapter);
                        }else{
                            CustomAdapterForListVIew adapter = new CustomAdapterForListVIew(myDB.getTasksForDateAfterTodayForOneUser(email),HomeTaskCrud.this, roleDisplay);
                            listMy.setAdapter(adapter);
                        }

                        Toast.makeText(HomeTaskCrud.this, "email is "+email, Toast.LENGTH_SHORT).show();
                        Toast.makeText(HomeTaskCrud.this, "Name is "+nameDisplay, Toast.LENGTH_SHORT).show();
                    }else{
//                        System.out.println(task.getException().toString()+ " Innser Error");
                        Toast.makeText(HomeTaskCrud.this, "Inner error", Toast.LENGTH_SHORT).show();
                    }
                }else{
//                    System.out.println(task.getException().toString()+" Outer Error");
                    Toast.makeText(HomeTaskCrud.this, "Outer error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //this is to get all regstered users
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> userList = snapshot.getChildren();
                for (DataSnapshot x:userList
                     ) {
//                    System.out.println(x.child("name").getValue().toString());
                    usersList.add(x.child("name").getValue().toString());
                    userEmailList.put(x.child("name").getValue().toString(), x.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}