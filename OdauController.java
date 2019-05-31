package com.example.booky.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.booky.Adapters.AdapderRecyclerOdau;
import com.example.booky.Controller.Interface.OdauInterface;
import com.example.booky.Model.QuananModel;
import com.example.booky.Model.SearchQuanAn;
import com.example.booky.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class OdauController {
    QuananModel quananModel;
    Context context;
    int itemdaco = 3;
    int choose;
    List<SearchQuanAn> searchQuanAns;
    AdapderRecyclerOdau adapderRecyclerOdau;
    //AdapderRecyclerOdau adapderRecyclerOdau;
    public OdauController(Context context,int choose) {
        this.context = context;
        quananModel = new QuananModel(choose);
        this.choose = choose;
    }
    public OdauController(Context context,int choose,List<SearchQuanAn> searchQuanAns) {
        this.context = context;
        quananModel = new QuananModel(choose,searchQuanAns);
        this.choose = choose;
        this.searchQuanAns = searchQuanAns;
    }
    public void getDanhsachQuananController(Context context, NestedScrollView nestedScrollView,RecyclerView recyclerOdau, final Location vitrihientai){
        final List<QuananModel> quananModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapderRecyclerOdau = new AdapderRecyclerOdau(context,quananModelList, R.layout.custom_layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapderRecyclerOdau);
        final OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhSachQuanAnModel(final QuananModel quanAnModel) {
                Log.d("adapter",quanAnModel.getTenquanan());
                final List<Bitmap> bitmaps = new ArrayList<>();
                for(String linkhinh : quanAnModel.getHinhanhquanan()){

                    StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
                    long ONE_MEGABYTE = 512*512;
                    storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            bitmaps.add(bitmap);
                            quanAnModel.setBitmapList(bitmaps);
                            if(quanAnModel.getBitmapList().size() == quanAnModel.getHinhanhquanan().size()){
                                quananModelList.add(quanAnModel);
                                adapderRecyclerOdau.notifyDataSetChanged();
                            }

                        }
                    });
                }


            }
        };


        /*nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) !=null){
                    if(scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()){
                        itemdaco += 3;
                        quananModel.getDanhSachQuanAn(odauInterface,vitrihientai,itemdaco,itemdaco-3);
                    }
                }
            }
        }); */
        quananModel.getDanhSachQuanAn(odauInterface, vitrihientai/*,itemdaco,0*/);

    };
}
