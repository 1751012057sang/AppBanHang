package com.example.easyorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyorder.R;
import com.example.easyorder.activity.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater = null;
    ArrayList<SanPham> sp = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<SanPham> sp) {
        this.context = context;
        this.sp = sp;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sp.size();
    }

    @Override
    public Object getItem(int position) {
        return sp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {
        if(converView == null) {
            converView = inflater.inflate(R.layout.cardview_sanpham, viewGroup, false);
        }

        TextView ten = (TextView) converView.findViewById(R.id.txt_tensp);
        TextView gia = (TextView) converView.findViewById(R.id.txt_giasp);
        ImageView hinh = (ImageView)converView.findViewById(R.id.img_sanpham);

        ten.setText(sp.get(position).ten);
        gia.setText(String.valueOf(sp.get(position).gia));
        Picasso.with(context).load(sp.get(position).hinh).resize(100,100).into(hinh);

        return converView;
    }
}
