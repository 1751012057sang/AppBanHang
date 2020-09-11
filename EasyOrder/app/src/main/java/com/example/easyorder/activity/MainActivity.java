package com.example.easyorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyorder.R;
import com.example.easyorder.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView danhsach;
    ArrayList<SanPham> sp = new ArrayList<>();
    SanPham[] sanpham = {
            new SanPham( "Cơm gà", 35000 , R.drawable.comga ),
            new SanPham( "Cơm sườn", 28000, R.drawable.comsuon),
            new SanPham( "Bún thịt nướng", 32000, R.drawable.bunthitnuong ),
            new SanPham( "Bún bò Huế", 40000, R.drawable.bunbo ),
            new SanPham( "Bún riêu cua", 37000,R.drawable.bunrieu ),
            new SanPham( "Cơm gà", 28000, R.drawable.banhuot )
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }
    public void init (){
        danhsach = (ListView)findViewById(R.id.lvDanhSach);
        Collections.addAll(sp,sanpham);

        CustomAdapter adapter = new CustomAdapter(MainActivity.this, sp );
        danhsach.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.account:
                Intent dangNhap = new Intent(this, LoginActivity.class);
                startActivity(dangNhap);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}