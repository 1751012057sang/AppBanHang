package com.example.cuahangdoanonline.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangdoanonline.R;
import com.example.cuahangdoanonline.ultil.Server;


import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtho,edtten,edtemail,edtmatkhau,edtsdt,edtdc;
    private RadioButton rdnam,rdnu;
    private Button bttdangkytaikhoan;
    private TextView textViewcanhbao;
    private String gtinh="Nam";
    private String ta="";
    private boolean ok=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        toolbar=(Toolbar)findViewById(R.id.toolbardangky);
        Actiontoolbar();
        anhxa();
        if(rdnu.isChecked()){
            gtinh="Nữ";
        }
        bttdangkytaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtho.length()==0 || edtten.length()==0 || edtemail.length()==0 || edtmatkhau.length()==0|| edtsdt.length()==0 ||edtdc.length()==0 ){
                    textViewcanhbao.setText(" Vui lòng điền đầy đủ thông tin!");

                }
                else if(edtmatkhau.length()<=6 || edtemail.length()<=6){
                    String t = edtemail.getText().toString();
                    ta = t.substring(t.length() - 10);
                    textViewcanhbao.setText(" Email hoặc mặt khẩu phải từ 6 ký tự!");

                }else if(edtsdt.length()<=9){
                    textViewcanhbao.setText(" Số Điên Thoại Không Hợp Lệ!");

                }
                else if(ta.equals("@")) {
                    textViewcanhbao.setText("Email Không hợp lệ!");
                }else {
                    themtaikhoan();
                    //  DangNhapActivity.database.QueryData("INSERT INTO TaiKhoan VALUES(NULL,'"+ edtho.getText().toString().trim()+"','"+edtten.getText().toString().trim()+"','"+edtemail.getText().toString().trim()+"','"+edtmatkhau.getText().toString()+"','"+edtsdt.getText().toString().trim()+"','"+gtinh+"',1)");
                    //  startActivity(new Intent(getApplicationContext(),DangNhapActivity.class));
                }

            }
        });
    }
    public void themtaikhoan(){
        final RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.duongdandangkitaikhoan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")) {
                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
                }else if(response.trim().equals("Tài Khoản Đã Tồn Tại!")){
                    Toast.makeText(DangKyActivity.this,response,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKyActivity.this,"Lỗi Xảy Ra!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("ho",edtho.getText().toString().trim());
                params.put("ten",edtten.getText().toString().trim());
                params.put("email",edtemail.getText().toString().trim());
                params.put("matkhau",edtmatkhau.getText().toString().trim());
                params.put("sdt",edtsdt.getText().toString().trim());
                params.put("gioitinh",gtinh);
                params.put("diachi",edtdc.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void anhxa(){
        edtsdt=(EditText)findViewById(R.id.edt_sdt);
        edtdc=(EditText)findViewById(R.id.edt_diachi);
        textViewcanhbao=(TextView)findViewById(R.id.textviewcanhbao);
        edtho=(EditText)findViewById(R.id.edt_ho);
        edtten=(EditText)findViewById(R.id.edt_ten);
        edtemail=(EditText)findViewById(R.id.edt_email);
        edtmatkhau=(EditText)findViewById(R.id.edt_matkhau);
        bttdangkytaikhoan=(Button)findViewById(R.id.btn_signup);
        rdnam=(RadioButton)findViewById(R.id.radiobuttonnam);
        rdnu=(RadioButton)findViewById(R.id.radiobuttonnu);

    }
    public void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}