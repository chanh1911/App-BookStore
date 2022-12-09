package com.tvt.nhosm2bookstore;

import static com.tvt.nhosm2bookstore.MainActivity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DetailBook extends AppCompatActivity {

    TextView ten,nd,like;
    ImageView img;
    Boolean l=false;
    Connection connection;
    String idtg;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        ten=(TextView) findViewById(R.id.tentruyen);
        like=(TextView) findViewById(R.id.like);
        nd=(TextView) findViewById(R.id.noidung);
        img=(ImageView) findViewById(R.id.imgbook);

        Intent intent=getIntent();
        if(intent==null){
            return;
        }

        String book= intent.getStringExtra("Book");
        ConSQL c=new ConSQL();

        connection=c.conclass();
        String sqlstatement = "Select * from CreateBook where ID="+book+"";
        try {
            Statement smt = connection.createStatement();
            ResultSet set = smt.executeQuery(sqlstatement);
            if(set.next()){
                ten.setText(set.getString("NameBook"));
                nd.setText(set.getString("Content"));
                idtg=set.getString("IDTG");
                byte[]hinhanh=set.getBytes("IMG");
                Bitmap bitmap=BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
                img.setImageBitmap(bitmap);
                connection.close();
            }
        }
        catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }


        if( idtg != user){
            String getView="Select * from CreateBook where ID="+book+"";
            String sqlview="UPDATE CreateBook SET ViewBook = '"+(getcolum(getView,"ViewBook")+1)+"' WHERE ID = '"+book+"'";

            sql(sqlview);
        }
        else {
            return;
        }

    }
    public int getcolum(String sql, String colum ){
        ConSQL c=new ConSQL();
        connection=c.conclass();
        int i=0;

        if(c!=null){
            try {
                String sqlstatement = sql;
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(sqlstatement);
                if (set.next()) {
                    i= Integer.parseInt(set.getString(colum));
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
            Toast.makeText(this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();
        }
        return i;

    }
    public void sql(String sql ){
        ConSQL c=new ConSQL();
        connection=c.conclass();

        if(c!=null){
            try {
                String sqlstatement = sql;
                Statement smt = connection.createStatement();
                smt.executeUpdate(sqlstatement);
                connection.close();
            }

            catch (Exception e){
                Log.e("Error: ",e.getMessage());
            }

        }
        else {
            Toast.makeText(this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();
        }

    }
}