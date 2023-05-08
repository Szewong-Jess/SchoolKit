package com.example.group6_schoolkit.taskCrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.group6_schoolkit.R;

public class TaskActivity extends AppCompatActivity {
    TextView txtView3, txtView4, txtView5;
    Button btn_edit,btn_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        txtView3=findViewById(R.id.textView3);
        txtView4=findViewById(R.id.textView4);
        txtView5=findViewById(R.id.textView5);

        Intent intent=getIntent();
        txtView3.setText(intent.getStringExtra("TITLE")+"\n"+intent.getStringExtra("ID"));
        txtView5.setText(intent.getStringExtra("DESC"));
        txtView4.setText(intent.getStringExtra("OWNER"));
//        btn_edit=findViewById(R.id.btn_edit);
//        btn_delete=findViewById(R.id.btn_delete);
//        btn_edit.setOnClickListener((View v)-> {
//                Bundle bundle=new Bundle();
//                bundle.putString("TITLE",intent.getStringExtra("TITLE"));
//                bundle.putString("DESC",intent.getStringExtra("DESC"));
//                bundle.putString("OWNER",intent.getStringExtra("OWNER"));
//                bundle.putString("DATE",intent.getStringExtra("DATE"));
//                bundle.putString("IMPORTANCE",intent.getStringExtra("IMPORTANCE"));
//                bundle.putString("CATEGORY",intent.getStringExtra("CATEGORY"));
//                bundle.putString("COURSE",intent.getStringExtra("COURSE"));
//                bundle.putString("OWNER",intent.getStringExtra("OWNER"));
//                bundle.putString("COMMENT",intent.getStringExtra("COMMENT"));
//                bundle.putString("DESCRIPTION",intent.getStringExtra("DESCRIPTION"));
//                startActivity(new Intent(TaskActivity.this,EditTaskActivity.class).putExtras(bundle));
//
//        });

    }
}