package com.example.booky.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;


import com.example.booky.Adapters.AdapterBinhLuan;
import com.example.booky.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAnActivity extends  AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    TextView txtTenQuanAn,txtDiaChi,txtThoiGianHoatDong,txtTrangThaiHoatDong,txtTongSoHinhAnh,txtTongSoBinhLuan,txtTongSoCheckIn,txtTongSoLuuLai,txtTieuDeToolbar,txtGioiHanGia,txtTenWifi,txtMatKhauWifi,txtNgayDangWifi;
    ImageView imHinhAnhQuanAn,imgPlayTrailer;
    Button btnBinhLuan;
    LinearLayout khungWifi;
    QuananModel quanAnModel;
    Toolbar toolbar;
    TextView txttoolbar;
    RecyclerView recyclerViewBinhLuan;
    LinearLayout khungTienIch;
    View khungTinhNang;
    VideoView videoView;
    RecyclerView recyclerThucDon;
    AdapterBinhLuan adapterBinhLuan;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    ChiTietQuanController chiTietQuanController;
    ThucDonController thucDonController;
    Button btnDatBan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);
        quanAnModel = getIntent().getParcelableExtra("quanan");
        //Log.d("Matienich",quanAnModel.getTienich().get(0));
        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn);
        txtThoiGianHoatDong = (TextView) findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = (TextView) findViewById(R.id.txtTrangThaiHoatDong);
        txtTongSoBinhLuan = (TextView) findViewById(R.id.tongSoBinhLuan);
        txtTongSoCheckIn = (TextView) findViewById(R.id.tongSoCheckIn);
        txtTongSoHinhAnh = (TextView) findViewById(R.id.tongSoHinhAnh);
        imHinhAnhQuanAn = (ImageView) findViewById(R.id.imHinhQuanAn);
        txttoolbar = (TextView) findViewById(R.id.txtTieuDeToolbar);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChiQuanAn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerViewBinhLuan = (RecyclerView) findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        txtGioiHanGia = findViewById(R.id.txtGioiHanGia);
        khungTienIch = (LinearLayout) findViewById(R.id.khungTienTich);
        txtTenWifi = (TextView) findViewById(R.id.txtTenWifi);
        txtMatKhauWifi = (TextView) findViewById(R.id.txtMatKhauWifi);
        khungWifi = (LinearLayout) findViewById(R.id.khungWifi);
        txtNgayDangWifi = (TextView) findViewById(R.id.txtNgayDangWifi);
        khungTinhNang = (View) findViewById(R.id.khungTinhNang) ;
        recyclerThucDon = (RecyclerView) findViewById(R.id.recyclerThucDon);
        btnBinhLuan = (Button) findViewById(R.id.btnBinhLuan);
        //txtTenWifi = (TextView) findViewById(R.id.)
        //toolbar.setTitle("");
        thucDonController = new ThucDonController();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        chiTietQuanController = new ChiTietQuanController();
        mapFragment.getMapAsync(this);
        btnDatBan = (Button) findViewById(R.id.btnDatBanOdau);
        HienThiChiTietQuanAn();
        khungTinhNang.setOnClickListener(this);
        btnBinhLuan.setOnClickListener(this);
        btnDatBan.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //thucDonController.getDanhSachThucDonQuanAnTheoMa(this,quanAnModel.getMaquanan(),recyclerThucDon);
    }

    private void HienThiChiTietQuanAn(){
        //Log.d("kiemtra1",quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi()+" abc");
        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        txtDiaChi.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());
        txtTongSoHinhAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        txtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModel().size() + "");
        txttoolbar.setText(quanAnModel.getTenquanan());
        if(quanAnModel.getTienich() != null){
        DownLoadHinhTienIch();}
        if(quanAnModel.getGiatoida() != 0 && quanAnModel.getGiatoithieu() != 0){
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoithieu = numberFormat.format(quanAnModel.getGiatoithieu()) + " đ";
            String giatoida = numberFormat.format(quanAnModel.getGiatoida()) + " đ";
            txtGioiHanGia.setText( giatoithieu + " - " + giatoida );
        }else{
            txtGioiHanGia.setVisibility(View.INVISIBLE);
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAnModel.getGiomocua();
        String giodongcua = quanAnModel.getGiodongcua();


        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if(dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)){
                //gio mo cua
                txtTrangThaiHoatDong.setText(getString(R.string.dangmocua));
            }else{
                //dong cua
                txtTrangThaiHoatDong.setText(getString(R.string.dadongcua));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imHinhAnhQuanAn.setImageBitmap(bitmap);
            }
        });

        // hien thi danh sach mon an
        //Load danh sach binh luan cua quan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new AdapterBinhLuan(this,R.layout.custom_layout_binhluan,quanAnModel.getBinhLuanModel());
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        NestedScrollView nestedScrollViewChiTiet = (NestedScrollView) findViewById(R.id.nestScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0,0);

        chiTietQuanController.HienThiDanhSachWifiQuanAn(quanAnModel.getMaquanan(),txtTenWifi,txtMatKhauWifi,txtNgayDangWifi);
        khungWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDanhSachWifi = new Intent(ChiTietQuanAnActivity.this,CapNhatDanhSachWifiActivity.class);
                iDanhSachWifi.putExtra("maquanan",quanAnModel.getMaquanan());
                startActivity(iDanhSachWifi);
            }
        });
        thucDonController.getDanhSachThucDonQuanAnTheoMa(this,quanAnModel.getMaquanan(),recyclerThucDon);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
        double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();

        LatLng latLng = new LatLng(latitude,longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModel.getTenquanan());

        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);
        googleMap.moveCamera(cameraUpdate);
    }

    private void DownLoadHinhTienIch(){
        for (String matienich : quanAnModel.getTienich()){
            Log.d("ktmatienich",matienich);
            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);

                    StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            ImageView imageTienIch = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80,80);
                            layoutParams.setMargins(10,10,10,10);
                            imageTienIch.setLayoutParams(layoutParams);
                            imageTienIch.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            imageTienIch.setPadding(5,5,5,5);
                            imageTienIch.setImageBitmap(bitmap);
                            khungTienIch.addView(imageTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.khungTinhNang:
                Intent iDanDuong = new Intent(this,DanDuongToiQuanAnActivity.class);
                Log.d("latitude",quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude()+"");
                Log.d("longitude",quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude()+"");
                iDanDuong.putExtra("latitude",quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
                iDanDuong.putExtra("longitude",quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                startActivity(iDanDuong);
                break;

                case R.id.btnBinhLuan:
                Intent iBinhLuan = new Intent(this,BinhLuanActivity.class);
                iBinhLuan.putExtra("maquanan",quanAnModel.getMaquanan());
                iBinhLuan.putExtra("tenquan",quanAnModel.getTenquanan());
                iBinhLuan.putExtra("diachi",quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
                startActivity(iBinhLuan);
                break;
                case R.id.btnDatBanOdau:
                    Intent iDatBan = new Intent(this,DatBanActitity.class);
                    iDatBan.putExtra("email",quanAnModel.getGmail());
                    iDatBan.putExtra("sodienthoai",quanAnModel.getSodienthoai());
                    iDatBan.putExtra("tenquan",quanAnModel.getTenquanan());
                    startActivity(iDatBan);
                    break;
        }
    }
}
