package com.tvt.nhosm2bookstore;

import static com.tvt.nhosm2bookstore.MainActivity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateBook extends AppCompatActivity {

    EditText name;
    EditText textInputEditText;
    Button tao,themimg;
    Connection connection;
    ImageView img;
    String NameTG;
    int REQUEST_CODE_FOLDER=123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        // lấy giá trị

        AnhXa();

        themimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);

            }
        });

        tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten=name.getText().toString();
                Intent intentBook=getIntent();
                String noidung=textInputEditText.getText().toString();
                SimpleDateFormat databaseDateFormate = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateandTime = databaseDateFormate.format(new Date());
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[]hinhanh=byteArrayOutputStream.toByteArray();

                ConSQL c=new ConSQL();
                connection=c.conclass();
                if(c==null){
                    Toast.makeText(CreateBook.this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        String sqlstatement = "Select * from PUser where UserName='"+user+"'";
                        Statement smt = connection.createStatement();
                        ResultSet set = smt.executeQuery(sqlstatement);
                        if(set.next()){
                            NameTG=set.getString("NameUer");
                        }


                    }
                    catch (Exception e){
                        Log.e("Error: ",e.getMessage());
                    }
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CreateBook (NameBook,NameTG,IDTG,ViewBook,Tim,Content,IMG,DayCreate) VALUES (?,?,?,?,?,?,?,?)");
                        preparedStatement.setString(1, ten);
                        preparedStatement.setString(2, NameTG);
                        preparedStatement.setString(3, user);
                        preparedStatement.setString(4, "0");
                        preparedStatement.setString(5, "0");
                        preparedStatement.setString(6, noidung);
                        preparedStatement.setBytes(7, hinhanh);
                        preparedStatement.setString(8, currentDateandTime);

                        preparedStatement.execute();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                Toast.makeText(CreateBook.this,"Thêm Thành Công",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(CreateBook.this,Create.class);
                startActivity(intent);

            }
        });

    }



    public void AnhXa(){
        name=(EditText)findViewById(R.id.editTextTextPersonName);
        textInputEditText=(EditText) findViewById(R.id.textInputLayout);
        tao=(Button) findViewById(R.id.them);
        img=(ImageView)findViewById(R.id.imgThem);
        themimg=(Button) findViewById(R.id.themimg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_FOLDER && resultCode ==RESULT_OK && data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream =getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}