package com.example.booky.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booky.Adapters.AdapterHienThiHinhBinhLuanDuocChon;
import com.example.booky.Controller.BinhLuanController;
import com.example.booky.Model.BinhLuanModel;
import com.example.booky.R;

import java.util.ArrayList;
import java.util.List;


public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTenQuanAn, txtDiaChiQuanAn,txtDangBinhLuan,txtDiemRating;
    Toolbar toolbar;
    EditText edTieuDeBinhLuan,edNoiDungBinhLuan;
    ImageButton btnChonHinh;
    RecyclerView recyclerViewChonHinhBinhLuan;
    AdapterHienThiHinhBinhLuanDuocChon adapterHienThiHinhBinhLuanDuocChon;
    BinhLuanController binhLuanController;
    RatingBar ratingBar;

    final int REQUEST_CHONHINHBINHLUAN = 11;
    String maquanan;
    SharedPreferences sharedPreferences;

    List<String> listHinhDuocChon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        maquanan = getIntent().getStringExtra("maquanan");
        String tenquan = getIntent().getStringExtra("tenquan");
        String diachi = getIntent().getStringExtra("diachi");

        sharedPreferences = getSharedPreferences("luudangnhap",MODE_PRIVATE);

        txtDiaChiQuanAn = (TextView) findViewById(R.id.txtDiaChiQuanAn);
        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn);
        txtDangBinhLuan = (TextView) findViewById(R.id.txtDangBinhLuan);
        edTieuDeBinhLuan = (EditText) findViewById(R.id.edTieuDeBinhLuan);
        edNoiDungBinhLuan = (EditText) findViewById(R.id.edNoiDungBinhLuan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnChonHinh = (ImageButton) findViewById(R.id.btnChonHinh);
        txtDiemRating = (TextView) findViewById(R.id.DiemRating) ;
        ratingBar =(RatingBar) findViewById(R.id.Rating_Bar);
        recyclerViewChonHinhBinhLuan = (RecyclerView) findViewById(R.id.recyclerChonHinhBinhLuan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewChonHinhBinhLuan.setLayoutManager(layoutManager);

        binhLuanController = new BinhLuanController();
        listHinhDuocChon = new ArrayList<>();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtDiemRating.setText("Bạn đã rate quán "+ String.format("%.1f",rating*2)+" ");
            }
        });
        txtDiaChiQuanAn.setText(diachi);
        txtTenQuanAn.setText(tenquan);

        btnChonHinh.setOnClickListener(this);
        txtDangBinhLuan.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnChonHinh:
                Intent iChonHinhBinhLuan = new Intent(this,ChonHinhBinhLuanActivity.class);
                startActivityForResult(iChonHinhBinhLuan,REQUEST_CHONHINHBINHLUAN);

                break;

            case R.id.txtDangBinhLuan:
                BinhLuanModel binhLuanModel = new BinhLuanModel();
                String tieude = edTieuDeBinhLuan.getText().toString();
                String noidung = edNoiDungBinhLuan.getText().toString();
                String mauser = sharedPreferences.getString("mauser","");
                if(tieude.length() ==0 || noidung.length() == 0){
                    Toast.makeText(this,"Vui lòng nhập đầy đủ các trường!!",Toast.LENGTH_SHORT).show();
                }
                else{
                binhLuanModel.setTieude(tieude);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setChamdiem(ratingBar.getRating()*2);
                binhLuanModel.setLuotthich(0);
                binhLuanModel.setMauser(mauser);
                binhLuanController.ThemBinhLuan(maquanan,binhLuanModel,listHinhDuocChon);}
                if(ratingBar.getRating()*2 < 5 ){ Toast.makeText(this,"Bình luận thành công, chúng tôi xin lỗi vì trải nghiệm này của bạn!",Toast.LENGTH_SHORT);}
                else {Toast.makeText(this,"Bình luận thành công, chúng tôi hy vọng bạn thích trải nghiệm ở đây!",Toast.LENGTH_SHORT);}
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHONHINHBINHLUAN){
            if(resultCode == RESULT_OK){

                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhBinhLuanDuocChon(this,R.layout.custom_layout_hienthibinhluanduocchon,listHinhDuocChon);
               recyclerViewChonHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
               adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();

            }
        }

    }
}
