package com.tvt.nhosm2bookstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Create_Account extends AppCompatActivity {

    Button tao;
    Connection connection;
    EditText email,pass,name;
    String date;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        tao=(Button) findViewById(R.id.tao);
        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.pass);
        name=(EditText) findViewById(R.id.ten);

        tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConSQL c=new ConSQL();
                connection=c.conclass();
                String getname=name.getText().toString().trim();
                String getmail=email.getText().toString().trim();
                String getpass=pass.getText().toString().trim();

                try {
                    String sqlstatement = "Select * from PUser where UserName='"+getmail+"'";

                    Statement smt = connection.createStatement();
                    ResultSet set = smt.executeQuery(sqlstatement);

                    if(set.next())
                    {
                        Toast.makeText(Create_Account.this,"Đã có người dùng này mời nhập email khác",Toast.LENGTH_SHORT).show();

                    }

                    else
                    {
                        Toast.makeText(Create_Account.this,"Tạo tài khoản thành công",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(Create_Account.this,MainActivity.class);
                        startActivity(intent);
                        SimpleDateFormat databaseDateFormate = new SimpleDateFormat("yyyy-MM-dd");
                        date = databaseDateFormate.format(new Date());
                        String sqlTaoTK="INSERT INTO PUser(UserName,PassWord,NameUer,DayCreate)\n" +
                                "VALUES\n" +
                                "('"+getmail+"','"+getpass+"',N'"+getname+"','"+date+"');";
                        Statement smtTK = connection.createStatement();
                        smtTK.executeUpdate(sqlTaoTK);
                        connection.close();

                    }

                }
                catch (Exception e){
                    Log.e("Error: ",e.getMessage());
                }
            }
        });

    }
}