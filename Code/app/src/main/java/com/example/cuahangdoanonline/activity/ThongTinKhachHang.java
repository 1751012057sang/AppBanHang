package com.example.cuahangdoanonline.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangdoanonline.R;
import com.example.cuahangdoanonline.ultil.CheckConnection;
import com.example.cuahangdoanonline.ultil.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHang extends AppCompatActivity {
    private Button bttxacnhan,btttrove;
    private EditText edttenkh,edtemail,edtsdt;
    private String tenkh,email,sdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        Anhxa();
        btttrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EventButton();
    }

    private void EventButton() {
        bttxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenkh=edttenkh.getText().toString();
                email=edtemail.getText().toString();
                sdt=edtsdt.getText().toString();
                if(email.length()>0 && sdt.length()>0 && tenkh.length()>0){
                    final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.duongthongtinkhachhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("mahoadon",madonhang);
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1=new StringRequest(Request.Method.POST, Server.duongchitiecdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("machitiec",response);
                                        if(response.equals("success")){
                                            MainActivity.manggiohang.clear();
                                            CheckConnection.ShowToast_short(getApplicationContext(),"Bạn đã thêm giỏ hàng thành công");
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            CheckConnection.ShowToast_short(getApplicationContext(),"Mời bạn tiếp tục mua sản phẩm");
                                        }else{
                                            CheckConnection.ShowToast_short(getApplicationContext(),"dữ liệu của bạn đã bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray=new JSONArray();
                                        for(int i=0;i<MainActivity.manggiohang.size();i++){
                                            JSONObject object=new JSONObject();
                                            try {
                                                object.put("madonhang",madonhang);
                                                object.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                object.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                object.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                                object.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(object);

                                        }
                                        HashMap<String, String> hashMap=new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(stringRequest1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap=new HashMap<String, String>();
                            hashMap.put("tenkhachhang",tenkh);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConnection.ShowToast_short(getApplicationContext(),"Bạn vui lòng điền đầy đủ thông tin");
                }
            }
        });
    }

    private void Anhxa() {
        btttrove=(Button)findViewById(R.id.buttonhukhachhang);
        bttxacnhan=(Button)findViewById(R.id.buttonxacnhankhachhang);
        edtemail=(EditText)findViewById(R.id.edittexttmailkhachhang);
        edtsdt=(EditText)findViewById(R.id.edittexttsdtkhachhang);
        edttenkh=(EditText) findViewById(R.id.edittexttenkhachhang);
    }
}
