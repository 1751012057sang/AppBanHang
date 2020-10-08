package com.example.cuahangdoanonline.activity;

import android.app.Dialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.cuahangdoanonline.R;
import com.example.cuahangdoanonline.adapter.LoaispAdapter;
import com.example.cuahangdoanonline.adapter.SanphamAdapter;
import com.example.cuahangdoanonline.adapter.girdviewsanpham;
import com.example.cuahangdoanonline.model.Giohang;
import com.example.cuahangdoanonline.model.Loaisp;
import com.example.cuahangdoanonline.model.Sanpham;
import com.example.cuahangdoanonline.ultil.CheckConnection;
import com.example.cuahangdoanonline.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tv_Tentk;
    private ImageButton imgbt_Hinhtk;
    private ViewFlipper viewFlipper;
    private GridView gridView;
    // private RecyclerView recyclerViewmanhinhchinh;
    private NavigationView navigationView;
    private ListView listViewmanhinhchinh;
    private DrawerLayout drawerLayout;
    private ArrayList<Loaisp> mangloaisp;
    private LoaispAdapter loaispAdapter;
    private int id=0;
    private String tenloaisp="";
    private String hinhanhloaisp="";
    private ArrayList<Sanpham> mangsanpham;
    private SanphamAdapter sanphamAdapter;
    private girdviewsanpham girdviewadapter;
    public static ArrayList<Giohang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            LayTenKH();
            GetDuLieuLoaisp();
            //   GetDuLieuSanPhamNew();
            girdview();
            CatchOnItemListView();
            ChangeIntent();
        }else{
            CheckConnection.ShowToast_short(getApplicationContext(),"Bạn Hãy Kiểm Tra Lại Kết Nối");
            finish();
        }

    }

    private void ChangeIntent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangsanpham.get(position));
                startActivity(intent);
            }
        });
    }

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, LapTopActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, SanPhamDaDat.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void girdview() {
        final RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.duongdansanpham, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    int ID=0;
                    String Tensanpham="";
                    Integer Giasanpham=0;
                    String Hinhanhsanpham="";
                    String Motasanpham="";
                    int IDsanpham=0;
                    for(int i = 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");
                            Tensanpham=jsonObject.getString("tensp");
                            Giasanpham=jsonObject.getInt("giasp");
                            Hinhanhsanpham=jsonObject.getString("hinhanhsp");
                            Motasanpham=jsonObject.getString("motasp");
                            IDsanpham=jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                            girdviewadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_short(getApplicationContext(),error.toString());
                finish();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void DangXuat() {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_dangxuat);
        Button b1 = (Button) d.findViewById(R.id.buttonhuy);
        Button b2 = (Button) d.findViewById(R.id.buttonxacnhan);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });
        d.show();

    }

    private void LayTenKH(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.laytenkh, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv_Tentk.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param=new HashMap<String, String>();
                param.put("MaTaiKhoan", String.valueOf(DangNhapActivity.id));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }


    private void GetDuLieuLoaisp() {
        final RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.locahost, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    for(int i= 0 ;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenLoaisp");
                            hinhanhloaisp=jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mangloaisp.add(3,new Loaisp(0,"Liên hệ","https://cdn.icon-icons.com/icons2/1918/PNG/128/iconfinder-document04-1622832_121950.png"));
                    mangloaisp.add(4,new Loaisp(0,"Thông tin","https://st2.depositphotos.com/3369547/11386/v/950/depositphotos_113864336-stock-illustration-avatar-man-icon-people-design.jpg"));
                    mangloaisp.add(5,new Loaisp(0,"Sản phẩm đã đặt","https://cdn.icon-icons.com/icons2/1835/PNG/128/iconfinderwalletpaysaleshop4177573-115989_115926.png"));
                    loaispAdapter.notifyDataSetChanged();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_short(getApplicationContext(),error.toString());
                finish();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }


    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://img3.thuthuatphanmem.vn/uploads/2019/10/08/banner-quang-cao-dien-thoai_103211774.jpg");
        mangquangcao.add("https://www.mayphotocopyhaiphong.com/wp-content/uploads/2019/04/ban-laptop-tai-hai-phong.jpg");
        mangquangcao.add("https://bizweb.dktcdn.net/100/372/934/articles/banner-gram-pc-1600-x-800-min.png?v=1587609760090");
        mangquangcao.add("https://cdn.fptshop.com.vn/Uploads/Originals/2018/6/6/636638758716655000_C1.png");
        mangquangcao.add("https://thietkehaithanh.com/wp-content/uploads/2019/01/thietkehaithanh-banner-laptop.png");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            viewFlipper.setAutoStart(true);
            Animation animation_slide_in=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
            Animation animation_slide_out=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
            viewFlipper.setInAnimation(animation_slide_in);
            viewFlipper.setOutAnimation(animation_slide_out);
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchinh,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang :
                Intent intent=new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Anhxa(){
        imgbt_Hinhtk = (ImageButton)findViewById(R.id.imgbtn_hinhtk);
        imgbt_Hinhtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangXuat();
            }
        });
        tv_Tentk = (TextView)findViewById(R.id.tv_tentk);
        toolbar=(Toolbar)findViewById(R.id.toobarmanhinhchinh);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewlipper);
        //  recyclerViewmanhinhchinh=(RecyclerView)findViewById(R.id.recyclerView);
        navigationView=(NavigationView)findViewById(R.id.navigationview);
        listViewmanhinhchinh=(ListView)findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mangloaisp=new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang chính","https://cdn.icon-icons.com/icons2/1411/PNG/512/g12-places-home_97323.png"));
        loaispAdapter=new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);
        mangsanpham=new ArrayList<>();
        sanphamAdapter=new SanphamAdapter(getApplicationContext(),mangsanpham);
        girdviewadapter=new girdviewsanpham(getApplicationContext(),mangsanpham);
        gridView=(GridView)findViewById(R.id.girdviewsanpham);
        gridView.setAdapter(girdviewadapter);
//        recyclerViewmanhinhchinh.setHasFixedSize(true);
//        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
//        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
        if(manggiohang !=null){

        }else{
            manggiohang=new ArrayList<>();
        }
    }
}
