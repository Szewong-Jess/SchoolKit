package com.example.group6_schoolkit.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.group6_schoolkit.R;
import com.example.group6_schoolkit.taskCrud.HomeTaskCrud;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class activity_login extends AppCompatActivity {
    TextView editText_LoginPage_StudentId, editTextTextPassword2;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        //Joke Api
        TextView jokeTextView = findViewById(R.id.textViewJokeApi);
        String jokeUrl = "https://v2.jokeapi.dev/joke/Programming";

        //Joke Api
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(jokeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response != null) {
                        String joke = response.getString("joke").toString();
                        //String delivery = response.getString("delivery").toString();

                        jokeTextView.setText(joke);
                        //jokeTextView.setText(response.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jokeTextView.setText("Error ");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        Button btn_LoginPage=findViewById(R.id.btn_LoginPage_Login);
        Button btn_LoginPage_Register = findViewById(R.id.btn_LoginPage_Register);
        editText_LoginPage_StudentId=findViewById(R.id.editText_LoginPage_StudentId);
        editTextTextPassword2=findViewById(R.id.editTextTextPassword2);
        firebaseAuth=FirebaseAuth.getInstance();



//        btn_LoginPage.setOnClickListener((View v)-> {
//            String _email = editText_LoginPage_StudentId.getText().toString().trim();
//            String _password = editTextTextPassword2.getText().toString().trim();
//            firebaseAuth.signInWithEmailAndPassword(_email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(activity_login.this, "Logged In", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(activity_login.this, HomeTaskCrud.class);
//                        startActivity(intent);
//                    }else{
//                        Toast.makeText(activity_login.this, "not logged in", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//
//        });
        
        btn_LoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String _email=editText_LoginPage_StudentId.getText().toString().trim();
                    int flag=0;
                    String output="";
                    if(_email.equals("")){
                        flag=1;
                        output="Please input your email address";
                    }
                    String _password=editTextTextPassword2.getText().toString().trim();
                    if(_password.equals("")){
                        if(flag==1){
                            output +=" and your password";
                        }else{
                            flag=1;
                            output="Please input your password";
                        }

                    }
                    if(flag==1){
                        Toast.makeText(activity_login.this, ""+output, Toast.LENGTH_SHORT).show();
                    }
                    firebaseAuth.signInWithEmailAndPassword(_email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(activity_login.this, "Logged in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(activity_login.this, HomeTaskCrud.class));
                            }else{
                                Toast.makeText(activity_login.this, "not logged in", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        //this will send the user to the register page if not yet registered
        btn_LoginPage_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    startActivity(new Intent(activity_login.this, RegActivity.class));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        });
    }
}