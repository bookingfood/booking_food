package com.example.booky.View;

import android.content.Intent;
import android.content.SharedPreferences;

import java.text.Normalizer;
import java.util.regex.Pattern;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.booky.Adapters.AdapterViewpagerHomepage;
import com.example.booky.Adapters.CustomArrayAdapter;
import com.example.booky.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class homepage_activity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    ViewPager ViewPagerHomepage;
    RadioButton rdOdau,rdAngi;
    RadioGroup groupOdauAngi;
    EditText edSearch;
    CheckBox btnSearch;
    ListView lsSearch;
    ImageView btnaddquanan;
    Button btnStartSearch;
    List<SearchQuanAn> Danhsachquanan = new ArrayList<>();
    List<SearchQuanAn> Ketqualoc = new ArrayList<SearchQuanAn>();
    DatabaseReference noderoot;
    CustomArrayAdapter customArrayAdapter;
    String Maquanan;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        String TAG = homepage_activity.class.getSimpleName();
        ViewPagerHomepage = (ViewPager) findViewById(R.id.viewpager_hompage);
        rdOdau = (RadioButton) findViewById(R.id.rd_odau);
        rdAngi = (RadioButton) findViewById(R.id.rd_angi);
        groupOdauAngi = (RadioGroup) findViewById(R.id.group_odau_angi);
        edSearch = (EditText) findViewById(R.id.edSearch);
        btnSearch = (CheckBox) findViewById(R.id.btnSearch);
        lsSearch = (ListView) findViewById(R.id.lsSearch);
        noderoot = FirebaseDatabase.getInstance().getReference();
        btnStartSearch = (Button) findViewById(R.id.btnStartSearch);
        btnaddquanan = (ImageView) findViewById(R.id.btnaddquanan);
        final LinearLayout linrSearch = (LinearLayout) findViewById(R.id.Searchview);
        sharedPreferences = getSharedPreferences("chuoiloc",MODE_PRIVATE);
        AdapterViewpagerHomepage AdapterViewpagerHomepage = new AdapterViewpagerHomepage(getSupportFragmentManager());
        noderoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.child("quanans").getChildren()){
                    SearchQuanAn searchQuanAn= new SearchQuanAn();
                    searchQuanAn.setMaQuanAn(singleSnapshot.getKey());
                    searchQuanAn.setTenQuanAn(singleSnapshot.child("tenquanan").getValue().toString());
                    for(DataSnapshot snapshotchinhanh: dataSnapshot.child("chinhanhquanans").child(searchQuanAn.getMaQuanAn()).getChildren()){
                        searchQuanAn.setDiachi(snapshotchinhanh.child("diachi").getValue().toString());
                        Log.d("chinhanhquanan",searchQuanAn.getDiachi());
                    }
                    Danhsachquanan.add(searchQuanAn);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lsSearch.setVisibility(View.VISIBLE);
                filter(edSearch.getText().toString());
                for(SearchQuanAn searchQuanAn: Ketqualoc){
                    Log.d("ketquala",searchQuanAn.getTenQuanAn());
                }
                Log.d("soluongkq",Ketqualoc.size()+"");
                customArrayAdapter = new CustomArrayAdapter(homepage_activity.this,R.layout.layout_search_quanan, (ArrayList<SearchQuanAn>) Ketqualoc);
                lsSearch.setAdapter(customArrayAdapter);
            }
            SharedPreferences sharedPreferences;
            @Override
            public void afterTextChanged(Editable s) {
                /*SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("maquanan",Maquanan);
                editor.commit(); */

            }
        });
        btnaddquanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iThemQuan = new Intent(homepage_activity.this,ThemQuanAnActivity.class);
                startActivity(iThemQuan);
            }
        });
        btnSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edSearch.setVisibility(View.VISIBLE);
                    lsSearch.setVisibility(View.GONE);
                    btnStartSearch.setVisibility(View.VISIBLE);
                    rdAngi.setVisibility(View.GONE);
                    rdOdau.setVisibility(View.GONE);
                    linrSearch.setGravity(Gravity.CENTER );
                    btnSearch.setWidth(20);
                    btnSearch.setHeight(20);
                    btnSearch.setBackgroundDrawable(ContextCompat.getDrawable(homepage_activity.this,R.drawable.ic_cross));
                    edSearch.setText("");
                    btnaddquanan.setVisibility(View.GONE);
                }
                if(!isChecked){

                    edSearch.setVisibility(View.GONE);
                    lsSearch.setVisibility(View.GONE);
                    btnStartSearch.setVisibility(View.GONE);
                    rdOdau.setVisibility(View.VISIBLE);
                    rdAngi.setVisibility(View.VISIBLE);
                    linrSearch.setGravity(Gravity.RIGHT| Gravity.CENTER);
                    btnSearch.setBackgroundDrawable(ContextCompat.getDrawable(homepage_activity.this,R.drawable.ic_search));
                    btnaddquanan.setVisibility(View.VISIBLE);
                }
            }
        });
        ViewPagerHomepage.setAdapter(AdapterViewpagerHomepage);
        ViewPagerHomepage.addOnPageChangeListener(this);
        groupOdauAngi.setOnCheckedChangeListener(this);
        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        lsSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ban da chon: ",Ketqualoc.get(position).getTenQuanAn());
            }
        });

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0: rdOdau.setChecked(true);
                break;
            case 1: rdAngi.setChecked(true);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    switch(checkedId){
        case R.id.rd_angi:
            ViewPagerHomepage.setCurrentItem(1);
            break;
        case R.id.rd_odau:
            ViewPagerHomepage.setCurrentItem(0);
            break;
    }
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        Ketqualoc.clear();
        if(charText.length() == 0){
            Ketqualoc.addAll(Danhsachquanan);
        }
        else{
            for(SearchQuanAn searchQuanAn: Danhsachquanan){
                if(unAccent(searchQuanAn.getTenQuanAn().toLowerCase(Locale.getDefault())).contains(charText) || searchQuanAn.getTenQuanAn().toLowerCase(Locale.getDefault()).contains(charText) ){
                    Ketqualoc.add(searchQuanAn);
                    Maquanan += " "+searchQuanAn.getTenQuanAn();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }


}
