package com.tvt.nhosm2bookstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText edtemail,edtpass;
    Button btn;
    CheckBox remember;
    SharedPreferences sharedPreference,userbook;
    public static String user;
    TextView tao;

    // kết nối SQL
    Connection connection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        sharedPreference=getSharedPreferences("dataLoggin",MODE_PRIVATE);
        userbook=getSharedPreferences("User",MODE_PRIVATE);

        // lấy giá trị
        edtemail.setText(sharedPreference.getString("username",""));
        edtpass.setText(sharedPreference.getString("password",""));
        remember.setChecked(sharedPreference.getBoolean("checked",false));

        tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Create_Account.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String User = edtemail.getText().toString().trim();
                String pw = edtpass.getText().toString().trim();

                ConSQL c=new ConSQL();
                connection=c.conclass();
                if (c!=null){
                    SharedPreferences.Editor edittoruser=userbook.edit();
                    edittoruser.putString("username",User);
                    edittoruser.commit();
                    user=userbook.getString("username","");

                    try {
                        String sqlstatement = "Select * from PUser where UserName='"+User+"' and PassWord='"+pw+"'";

                        Statement smt = connection.createStatement();
                        ResultSet set = smt.executeQuery(sqlstatement);

                        if(set.next())
                        {
                                Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(MainActivity.this,Home.class);

                                if (remember.isChecked()){
                                    SharedPreferences.Editor edittor=sharedPreference.edit();
                                    edittor.putString("username",User);
                                    edittor.putString("password",pw);
                                    edittor.putBoolean("checked",true);
                                    edittor.commit();
                                }
                                else {
                                    SharedPreferences.Editor edittor=sharedPreference.edit();
                                    edittor.remove("username");
                                    edittor.remove("password");
                                    edittor.remove("checked");
                                    edittor.commit();

                                }
                                connection.close();
                                startActivity(intent);

//                            }

                        }

                        else
                        {
                            Toast.makeText(MainActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                            set.close();
                        }

                    }
                    catch (Exception e){
                        Log.e("Error: ",e.getMessage());
                    }


                }
                else {
                    Toast.makeText(MainActivity.this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();

                }


            }
        });



    }
    private  void  AnhXa(){
        edtemail=(EditText) findViewById(R.id.editemail);
        edtpass=(EditText) findViewById(R.id.editpassword);
        btn=(Button) findViewById(R.id.button);
        remember=(CheckBox)findViewById(R.id.checkBoxRemenber);
        tao=(TextView) findViewById(R.id.taotk);

    }
    // lấy dữ liệu từ sql

}