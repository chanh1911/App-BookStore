package com.tvt.nhosm2bookstore;

import android.annotation.SuppressLint;
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

public class Edit_Delete_Book extends AppCompatActivity {

    EditText name,nd;
    Button xoa,sua,themimg;
    ImageView img;
    Connection connection;
    int REQUEST_CODE_FOLDER=123;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_book);
        Intent intent=getIntent();
        name=(EditText)findViewById(R.id.editTextTextPersonName) ;
        nd= (EditText) findViewById(R.id.textInputLayout);
        img=(ImageView)findViewById(R.id.imgThem);
        themimg=(Button) findViewById(R.id.themimg);
        xoa=(Button)findViewById(R.id.xoa);
        sua=(Button)findViewById(R.id.sua);


        themimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);

            }
        });
        String book= intent.getStringExtra("Book");

        ConSQL c=new ConSQL();
        connection=c.conclass();
        String sqlstatement = "Select * from CreateBook where ID="+book+"";
        try {
            Statement smt = connection.createStatement();
            ResultSet set = smt.executeQuery(sqlstatement);
            if(set.next()){
                name.setText(set.getString("NameBook"));
                nd.setText(set.getString("Content"));
                byte[]hinhanh=set.getBytes("IMG");
                Bitmap bitmap=BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
                img.setImageBitmap(bitmap);
            }
        }
        catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }


        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sqlXoa="DELETE FROM CreateBook\n" +
                        "WHERE ID ="+book+"";
                try {
                    Statement smt = connection.createStatement();
                    smt.executeUpdate(sqlXoa);
                    connection.close();

                }
                catch (Exception e){
                    Log.e("Error: ",e.getMessage());
                }
                Toast.makeText(Edit_Delete_Book.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                Intent intent;
                intent = new Intent(Edit_Delete_Book.this,Create.class);
                startActivity(intent);






            }
        });

        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[]hinhanh=byteArrayOutputStream.toByteArray();
                Toast.makeText(Edit_Delete_Book.this,"Đang tải ảnh",Toast.LENGTH_SHORT).show();



                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CreateBook SET NameBook = ? ,  Content = ?, IMG= ? WHERE ID = "+book+"");
                    preparedStatement.setString(1, String.valueOf(name.getText()));
                    preparedStatement.setString(2, String.valueOf(nd.getText()));
                    preparedStatement.setBytes(3, hinhanh);

                    preparedStatement.execute();
                    Toast.makeText(Edit_Delete_Book.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
                    connection.close();
                }
                catch (Exception e){
                    Log.e("Error: ",e.getMessage());
                }
                Intent intent =new Intent(Edit_Delete_Book.this,Create.class);
                startActivity(intent);




            }
        });
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