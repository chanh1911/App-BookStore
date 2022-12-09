package com.tvt.nhosm2bookstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Profile extends AppCompatActivity {
    Button dangxuat;
    TextView name,soSachViet,luotTim;
    SharedPreferences username;
    Connection connection;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dangxuat=(Button) findViewById(R.id.dangxuat);
        name=(TextView)findViewById(R.id.textAvata) ;
        soSachViet=(TextView)findViewById(R.id.soSachViet) ;
        luotTim=(TextView)findViewById(R.id.luotTim) ;

        username=getSharedPreferences("User",MODE_PRIVATE);;
        String user=username.getString("username","");


        String sqlstatement="Select * from PUser where UserName='"+user+"'";
        squery(sqlstatement,name,"NameUer");
        String sqlLike="select Sum(isnull(cast(Tim as int),0)) as Tim from CreateBook where IDTG='"+user+"'";
        squery(sqlLike,luotTim,"Tim");
        String sqlBook="SELECT COUNT(ID) as Book from CreateBook where IDTG='"+user+"'";
        squery(sqlBook,soSachViet,"Book");

        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void squery(String sql,TextView view,String colum ){
        ConSQL c=new ConSQL();
        connection=c.conclass();

        if(c!=null){
            try {
                String sqlstatement = sql;
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(sqlstatement);
                if (set.next()) {
                    view.setText(set.getString(colum));
                } else {

                    System.out.println("No rows returned");

                }
                connection.close();
            }

            catch (Exception e){
                Log.e("Error: ",e.getMessage());
            }

        }
        else {
            Toast.makeText(Profile.this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();
        }

    }
}