package com.example.booky.View;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.booky.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatBanActitity extends AppCompatActivity implements View.OnClickListener {
EditText edsoluong,edsodienthoai,edghichu;
Button btndatban,btnchonngay,btnchongio;
String giodatban,ngaydatban,gmail,sodienthoai;
Toolbar toolbardatban;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_datban);
        btndatban = (Button) findViewById(R.id.btnDatban);
        edsoluong = (EditText) findViewById(R.id.edSoluongnguoi);
        edghichu = (EditText) findViewById(R.id.edghichu) ;
        edsodienthoai = (EditText) findViewById(R.id.edsodienthoai);
        btnchonngay = (Button) findViewById(R.id.btnChonNgay);
        btnchongio= (Button) findViewById(R.id.btnChongio);
        btndatban.setOnClickListener(this);
        btnchongio.setOnClickListener(this);
        btnchonngay.setOnClickListener(this);
        toolbardatban = (Toolbar) findViewById(R.id.datbantoolbar);
        gmail = getIntent().getStringExtra("email");
        sodienthoai = getIntent().getStringExtra("sodienthoai");
        toolbardatban.setTitle(getIntent().getStringExtra("tenquan"));
        setSupportActionBar(toolbardatban);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public void onClick(final View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
            switch (v.getId()){
                case R.id.btnChongio:
                    TimePickerDialog giodatbanTimePickerDialog = new TimePickerDialog(DatBanActitity.this, new TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            giodatban = hourOfDay +":"+minute;
                            ((Button)v).setText(giodatban);
                        }
                    },gio,phut,true);
                    giodatbanTimePickerDialog.show();
                    break;
                case  R.id.btnChonNgay:
                    DatePickerDialog ngaydatbanDatePickerDialog = new DatePickerDialog(DatBanActitity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            ngaydatban= dayOfMonth+"/"+month+"/"+year;
                            ((Button)v).setText(ngaydatban);
                        }
                    },year,month,day);
                    Date today = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(today);
                    c.add( Calendar.DATE, -1 ) ;
                    long minDate = c.getTime().getTime() ;
                    c.add( Calendar.DATE, 2 ) ;
                    long maxDate = c.getTime().getTime();
                    ngaydatbanDatePickerDialog.getDatePicker().setMaxDate(maxDate);
                    ngaydatbanDatePickerDialog.getDatePicker().setMinDate(minDate);
                    ngaydatbanDatePickerDialog.show();
                    break;
                case R.id.btnDatban:
                    guigmaidatban();

            }

    }
    protected void guigmaidatban(){
        String songuoi = edsoluong.getText().toString();
        String sodienthoai = edsodienthoai.getText().toString();
        String ghichu = edghichu.getText().toString();
        if(edsoluong.length()==0 || edsodienthoai.length() < 9 || giodatban== null || ngaydatban == null ){
            Toast.makeText(this,"Quý khách vui lòng điền đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        }
        else {
            String subject = "BOOKY trân trọng thông báo, bạn có một lịch đặt bàn mới từ user ";
            String massage = "Nội dung đặt bàn:\n Chúng tôi có " + songuoi + " người, chúng tôi muốn đặt bàn trước vào lúc " + giodatban + " ngày " + ngaydatban + ", nếu quán xác nhận vui lòng liên hệ chúng tôi qua số điện thoại "
                    + sodienthoai ;
            if(ghichu.length() ==0){ massage += " \nGhi chú: " + ghichu;}
            Sendmail sm = new Sendmail(this, gmail, subject, massage);
            sm.execute();
        }
    }


}
