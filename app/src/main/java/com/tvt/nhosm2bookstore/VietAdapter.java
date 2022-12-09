package com.tvt.nhosm2bookstore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class VietAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Book> books;

    public VietAdapter(Context context, int layout,List<Book>books){
        this.context=context;
        this.layout=layout;
        this.books=books;
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =inflater.inflate(layout,null);
        TextView txtTen=(TextView)view.findViewById(R.id.NameViet);
        txtTen.setText(books.get(i).NameBook);
        TextView txtview=(TextView)view.findViewById(R.id.like);
        txtview.setText(books.get(i).ViewBook);
        TextView txtlike=(TextView)view.findViewById(R.id.view);
        txtlike.setText(books.get(i).Tim);
        ImageView img =(ImageView) view.findViewById(R.id.imgViet);
        Bitmap bitmap= BitmapFactory.decodeByteArray(books.get(i).hinh, 0, books.get(i).hinh.length);
        img.setImageBitmap(bitmap);

        return view;
    }
}
