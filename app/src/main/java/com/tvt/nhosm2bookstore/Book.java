package com.tvt.nhosm2bookstore;

import java.io.Serializable;

public class Book  implements Serializable {
    public int ID;
    public  String NameBook;
    public  String NameTG;
    public String IDTG;
    public String ViewBook;
    public String Tim;
    public  String Content;
    public  byte[] hinh;
    public String day;

    public Book(int id,String NameBook, String nameTG,String IDTG,String ViewBook,String Tim, String Content, byte[] hinh, String day) {
        this.ID=id;
        this.NameBook = NameBook;
        this.NameTG = nameTG;
        this.IDTG=IDTG;
        this.ViewBook=ViewBook;
        this.Tim=Tim;
        this.Content = Content;
        this.hinh = hinh;
        this.day = day;
    }


}
