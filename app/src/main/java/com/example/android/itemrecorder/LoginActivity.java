package com.example.android.itemrecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
  EditText editText;
  Toast toast=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText=findViewById(R.id.username_edit_box);
    }

    public void signIn(View view) {
        String name=editText.getText().toString();

        if(name.length()==0){
            if(toast!=null){
                toast.cancel();
            }
            toast=Toast.makeText(this,"Please Enter Username",Toast.LENGTH_LONG);
            toast.show();
        }
         else if(name.equals("spatel")){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        else {

            if(toast!=null){
                toast.cancel();
            }
            toast=Toast.makeText(this,"Wrong Username",Toast.LENGTH_LONG);
            toast.show();
            return;
        }

    }
}
