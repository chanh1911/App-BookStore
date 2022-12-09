package com.tvt.nhosm2bookstore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>  {

    public List<Book> mBook;
    public Context context;

    public  void setData(Context context,List<Book> list)
    {
        this.mBook=list;
        this.context=context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new  BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        final Book book = mBook.get(position);
        if (book == null) {
            return;
        }
        byte[] hinhanh=book.hinh;
        Bitmap bitmap= BitmapFactory.decodeByteArray(hinhanh, 0,book.hinh.length);
        holder.imgBook.setImageBitmap(bitmap);
        holder.tvTitle.setText(book.NameBook);
        holder.bookitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickGoToDetailBook(book);
            }
        });

    }

    public void onclickGoToDetailBook(Book book){
        Intent intent = new Intent(context,DetailBook.class);
        String id= String.valueOf(book.ID);
        intent.putExtra("Book",id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
//    public void release(){
//        context=null;
//    }


    @Override
    public int getItemCount() {
        if(mBook!=null){
            return mBook.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgBook;
        public  TextView tvTitle;
        public CardView bookitem;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook =itemView.findViewById(R.id.img_book);
            tvTitle = itemView.findViewById(R.id.tv_title);
            bookitem =itemView.findViewById(R.id.idBook);

        }
    }

}
