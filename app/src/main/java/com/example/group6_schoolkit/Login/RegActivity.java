package com.example.group6_schoolkit.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.group6_schoolkit.R;
import com.example.group6_schoolkit.taskCrud.HomeTaskCrud;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity {
    Button buttonLogin,buttonRegister;
    EditText editTextTextPersonName, editTextTextEmailAddress, editTextTextPassword;
    Spinner spinnerRole;
    FirebaseAuth firebaseAuth;
    String _role;
//    DatabaseReference mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        getSupportActionBar().hide();


        buttonLogin=findViewById(R.id.buttonLogin);
        buttonRegister=findViewById(R.id.buttonRegister);
        editTextTextPersonName=findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress=findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=findViewById(R.id.editTextTextPassword);
        spinnerRole = findViewById(R.id.spinnerRole);


        firebaseAuth=FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegActivity.this, activity_login.class));
            }
        });

//        if(firebaseAuth.getCurrentUser()!=null){
//            startActivity(new Intent(RegActivity.this, HomeTaskCrud.class));
//            finish();
//        }

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(RegActivity.this, spinnerRole.getSelectedItem().toString()+" Selected", Toast.LENGTH_SHORT).show();
                        _role=spinnerRole.getSelectedItem().toString().trim();
                        break;
                    case 1:
                        Toast.makeText(RegActivity.this, spinnerRole.getSelectedItem().toString()+" Selected", Toast.LENGTH_SHORT).show();
                        _role=spinnerRole.getSelectedItem().toString().trim();
                        break;
                    default:
                        Toast.makeText(RegActivity.this, "None selected", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _email = editTextTextEmailAddress.getText().toString().trim();
                String _password = editTextTextPassword.getText().toString().trim();
                String _name=editTextTextPersonName.getText().toString().trim();
                int flag=0;
                String output="";
                try{
                if(_email.equals("")){
                    flag=1;
                    output="Please input your email address";
                }
                if(_password.equals("")){
                    if(flag==1){
                        output +=" and password";
                    }else{
                        flag=1;
                        output="Please input your password";
                    }
                }
                if(_name.equals("")){
                    if(flag==1){
                        output=output.replace("and",",");
                        output +=" and name";
                    }else{
                        flag=1;
                        output="Please input your name";
                    }
                }
                if(flag==1){
                    Toast.makeText(RegActivity.this, output, Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(_email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            UserClass Newuser = new UserClass(_name, _email,_role);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(Newuser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegActivity.this, "USER CREATED IN DB", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(RegActivity.this, "USER CREATION DB NOT GOOFD", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(RegActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                System.out.println(task.getException().toString());
                                            }
                                        }
                                    });

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(_name)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(RegActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegActivity.this, activity_login.class));
                                        }
                                    });

                        }else{
                            Toast.makeText(RegActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }
}