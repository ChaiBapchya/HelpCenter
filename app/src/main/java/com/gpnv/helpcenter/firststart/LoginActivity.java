package com.gpnv.helpcenter.firststart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gpnv.helpcenter.DBAdapter;
import com.gpnv.helpcenter.MainActivity;
import com.gpnv.helpcenter.R;

/**
 * Created by GANESH on 3/15/2016.
 */
public class LoginActivity extends AppCompatActivity {
EditText name;
    EditText email;
    EditText age;
    EditText lunchTime;
    EditText dinnerTime;
DBAdapter dbAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.login_email);
        name = (EditText)findViewById(R.id.login_name);
        age = (EditText)findViewById(R.id.login_age);
        lunchTime = (EditText)findViewById(R.id.lunch_time);
        dinnerTime = (EditText)findViewById(R.id.dinner_time);
        dbAdapter = new DBAdapter(this);
    }

    public void addLogin(View view){

        String login_name= name.getText().toString();
        String login_email= email.getText().toString();
        String login_age= age.getText().toString();
        String login_lunchTime= lunchTime.getText().toString();
        String login_dinnerTime= dinnerTime.getText().toString();
        //Toast.makeText(this, login_age+"\n"+login_email+"\n"+login_name+"\n"+"ccessful", Toast.LENGTH_SHORT).show();
        long id=dbAdapter.insertUser(login_name, login_email, login_age, login_lunchTime, login_dinnerTime);
        if(id<0){
            Toast.makeText(this, "unsuccessful", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
        }
        String data= dbAdapter.getUser();
        //prepare your data
        TextView tv =(TextView) findViewById(R.id.login_text);
        tv.setText(data);
        // you can set id, tag, text color, font, ...
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);





    }
}