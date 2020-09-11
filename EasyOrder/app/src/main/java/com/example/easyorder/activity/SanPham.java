package com.example.easyorder.activity;

public class SanPham {
    public Integer hinh;
    public String ten;
    public Integer gia;

    //constructor
    public SanPham( String ten, Integer gia, Integer hinh) {
        this.hinh = hinh;
        this.ten = ten;
        this.gia = gia;
    }
}
