package com.tvt.nhosm2bookstore;

import java.util.List;

public class category {
    public String namecategory;
    public List<Book> books;

    public category(String namecategory, List<Book> books) {
        this.namecategory = namecategory;
        this.books = books;
    }

    public String getNameCategory() {
        return namecategory;
    }

    public void setNameCategory(String nameCategory) {
        this.namecategory = nameCategory;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
