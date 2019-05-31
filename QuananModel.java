package com.example.booky.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.example.booky.Controller.Interface.OdauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuananModel implements Parcelable {
    boolean giaohang;
    boolean datban;
    int choose=1;
    String giodongcua;
    String giomocua;
    String tenquanan;
    String videogiothieu;
    String maquanan;
    String sodienthoai;
    String gmail;
    List<Bitmap> bitmapList;
    List<ThucDonModel> thucDons;
    long luotthich;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<BinhLuanModel> BinhLuanModel;
    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;
    List<orderQuanAn> orderQuanAnHotnhat = new ArrayList<>();
    List<orderQuanAnKhoangCach> orderQuanAnKhoangCaches = new ArrayList<>();
    List<SearchQuanAn> searchQuanAns ;
    long giatoida, giatoithieu;
Context context;
    public List<ThucDonModel> getThucDons() {
        return thucDons;
    }

    public void setThucDons(List<ThucDonModel> thucDons) {
        this.thucDons = thucDons;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    public boolean isDatban() {
        return datban;
    }

    public void setDatban(boolean datban) {
        this.datban = datban;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    protected QuananModel(Parcel in) {
        giaohang = in.readByte() != 0;
        datban = in.readByte() !=0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        maquanan = in.readString();
        sodienthoai = in.readString();
        gmail = in.readString();
        //chiNhanhQuanAnModelList = in.createTypedArrayList(ChiNhanhQuanAnModel.CREATOR);
        hinhanhquanan = in.createStringArrayList();
        chiNhanhQuanAnModelList = new ArrayList<ChiNhanhQuanAnModel>();
        in.readTypedList(chiNhanhQuanAnModelList,ChiNhanhQuanAnModel.CREATOR);
        BinhLuanModel = new ArrayList<BinhLuanModel>();
        in.readTypedList(BinhLuanModel, com.example.booky.Model.BinhLuanModel.CREATOR);
        giatoithieu = in.readLong();
        giatoida = in.readLong();
        tienich = in.createStringArrayList();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeByte((byte) (datban ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(maquanan);
        dest.writeString(sodienthoai);
        dest.writeString(gmail);
        dest.writeStringList(hinhanhquanan);
        dest.writeTypedList(chiNhanhQuanAnModelList);
        dest.writeTypedList(BinhLuanModel);
        dest.writeLong(giatoida);
        dest.writeLong(giatoithieu);
        dest.writeStringList(tienich);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuananModel> CREATOR = new Creator<QuananModel>() {
        @Override
        public QuananModel createFromParcel(Parcel in) {
            return new QuananModel(in);
        }

        @Override
        public QuananModel[] newArray(int size) {
            return new QuananModel[size];
        }
    };

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    DatabaseReference noderoot;

    public List<com.example.booky.Model.BinhLuanModel> getBinhLuanModel() {
        return BinhLuanModel;
    }

    public void setBinhLuanModel(List<com.example.booky.Model.BinhLuanModel> binhLuanModel) {
        BinhLuanModel = binhLuanModel;
    }


    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }



    public QuananModel(int choice) {
        this.choose = choice;
        noderoot = FirebaseDatabase.getInstance().getReference();
    }
    public QuananModel(int choice, List<SearchQuanAn> searchQuanAns) {
        this.choose = choice;
        this.searchQuanAns = searchQuanAns;
        noderoot = FirebaseDatabase.getInstance().getReference();
    }
    public QuananModel() {
        noderoot = FirebaseDatabase.getInstance().getReference();
    }
    public QuananModel(Context context) {
        this.context = context;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogiothieu() {
        return videogiothieu;
    }

    public void setVideogiothieu(String videogiothieu) {
        this.videogiothieu = videogiothieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }


   public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    /*public void getDanhSachQuanAn(final OdauInterface odauInterface){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");
                for(DataSnapshot valueQuanan:dataSnapshotQuanAn.getChildren()){
                    QuananModel quananModel = valueQuanan.getValue(QuananModel.class);
                    odauInterface.getDanhSachQuanAnModel(quananModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };*/
    private  DataSnapshot dataRoot;
    public void getDanhSachQuanAn(final OdauInterface odauInterface,final Location vitrihientai/*,final int itemtieptheo,final int itemdaco*/){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(choose == 1){
                dataRoot = dataSnapshot;
                LayDanhSachQuanAn(dataSnapshot,odauInterface,vitrihientai/*,itemtieptheo,itemdaco*/);}
                if(choose == 2){
                    if(orderQuanAnHotnhat.size()==0){
                    dataRoot = dataSnapshot;
                DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");
                for(DataSnapshot valueQuanan:dataSnapshotQuanan.getChildren()){
                    orderQuanAn orderSnapshot = new orderQuanAn();
                    orderSnapshot.setMaQuanAn(valueQuanan.getKey());
                    DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(orderSnapshot.getMaQuanAn());
                    double diemtong = 0;
                    int size =0;
                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                        diemtong += valueBinhLuan.child("chamdiem").getValue(double.class);
                        size++;

                    }
                    if(size == 0) orderSnapshot.setEveragepoint(0);
                    else orderSnapshot.setEveragepoint(diemtong/size);
                    orderQuanAnHotnhat.add(orderSnapshot);
                }
                    Collections.sort(orderQuanAnHotnhat);
                    }
                LayDanhSachQuanAnHotNhat(dataSnapshot,odauInterface,vitrihientai/*,itemtieptheo,itemdaco*/);
                }
                if(choose == 3){
                    if(orderQuanAnKhoangCaches.size() ==0){
                        dataRoot = dataSnapshot;
                        DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");
                        for(DataSnapshot valueQuanan:dataSnapshotQuanan.getChildren()){
                            orderQuanAnKhoangCach orderSnapshot = new orderQuanAnKhoangCach();
                            orderSnapshot.setMaQuanAn(valueQuanan.getKey());
                            DataSnapshot snapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(orderSnapshot.getMaQuanAn());
                            double minkhoangcach= 10000;
                            for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()){
                                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                                Location vitriquanan = new Location("");
                                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                                double khoangcach = vitrihientai.distanceTo(vitriquanan)/1000;
                                minkhoangcach = (khoangcach < minkhoangcach)? khoangcach : minkhoangcach;
                            }
                            orderSnapshot.setKhoangcach(minkhoangcach);
                            orderQuanAnKhoangCaches.add(orderSnapshot);
                        }
                        Collections.sort(orderQuanAnKhoangCaches);
                    }
                    LayDanhSachQuanAnGanNhat(dataSnapshot,odauInterface,vitrihientai);
                }

                if(choose == 4){
                    dataRoot = dataSnapshot;
                    LayDanhSachQuanAnSearch(dataSnapshot,odauInterface,vitrihientai);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        if(dataRoot != null){
            LayDanhSachQuanAn(dataRoot,odauInterface,vitrihientai/*,itemtieptheo,itemdaco*/);
        }
        else {
             noderoot.addListenerForSingleValueEvent(valueEventListener);
        }
    }
    public void LayDanhSachQuanAn(DataSnapshot dataSnapshot,final OdauInterface odauInterface ,final Location vitrihientai/*,int itemtieptheo,int itemdaco*/) {
                int i=0;
                DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");

                for(DataSnapshot valueQuanan:dataSnapshotQuanan.getChildren()) {
                   /* if(i == itemtieptheo) break;
                    if(i < itemdaco){
                        i++;
                        continue;
                    }
                    i++; */
                    QuananModel quananModel = valueQuanan.getValue(QuananModel.class);
                    quananModel.setMaquanan(valueQuanan.getKey());
                    Log.d("gmail ",quananModel.getGmail());
                   // Lay hinh anh
                    DataSnapshot dataSnapshotHinhAnhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanan.getKey());
                    List<String> Hinhanhlist = new ArrayList<>();
                    for(DataSnapshot datahinhquanan:dataSnapshotHinhAnhQuanAn.getChildren()){
                        Hinhanhlist.add(datahinhquanan.getValue(String.class));
                    }
//                    Log.d("kiemtra",quananModel.getTienich().get(0) + " "+quananModel.getTienich().get(1));
                    quananModel.setHinhanhquanan(Hinhanhlist);
                    // Lay Binh luan

                    DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quananModel.getMaquanan());

                    List<BinhLuanModel> BinhluanmodeList = new ArrayList<>();
                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                        Log.d("diem =",valueBinhLuan.child("chamdiem").getValue()+"");
                        BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                        binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                        ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                        binhLuanModel.setThanhVienModel(thanhVienModel);

                        List<String> hinhanhBinhLuanList = new ArrayList<>();
                        DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                        for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                            hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                        }
                        binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);
                        BinhluanmodeList.add(binhLuanModel);
                    }
                    quananModel.setBinhLuanModel(BinhluanmodeList);
                    // lay chi nhanh quan an
                    DataSnapshot snapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(quananModel.getMaquanan());
                    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();

                    for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()){
                        ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                        Location vitriquanan = new Location("");
                        vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                        vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                        double khoangcach = vitrihientai.distanceTo(vitriquanan)/1000;
                        chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                        chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
                    }

                    quananModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
                    /*for(DataSnapshot valueBinhluan:SnapshotBinhLuan.getChildren()){
                        BinhLuanModel binhLuanModel = valueBinhluan.getValue(BinhLuanModel.class);
                        Log.d("test","starrt");
                        binhLuanModel.setMabinhluan(valueBinhluan.getKey());
                        ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                        binhLuanModel.setThanhVienModel(thanhVienModel);
                        Log.d("test",binhLuanModel.mabinhluan);
                        List<String> hinhanhBinhLuanList = new ArrayList<>();
                        DataSnapshot snapshotNodeHABL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                        for(DataSnapshot valueHinhBinhLuan: snapshotNodeHABL.getChildren() ){
                            hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                        }
                        binhLuanModel.setHinhanhBinhluanlist(hinhanhBinhLuanList);
                        BinhluanmodeList.add(binhLuanModel);
                    }
                    quananModel.setBinhLuanModel(BinhluanmodeList); */
                    odauInterface.getDanhSachQuanAnModel(quananModel);
                };

            }

    public void LayDanhSachQuanAnHotNhat(DataSnapshot dataSnapshot,final OdauInterface odauInterface,final Location vitrihientai/*,int itemtieptheo,int itemdaco*/) {
        int i=0;

        DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");

        for(orderQuanAn orderQuanAn : orderQuanAnHotnhat) {
            /*if(i == itemtieptheo) break;
            if(i < itemdaco){
                i++;
                continue;
            }
            i++; */

            DataSnapshot valueQuanan= dataSnapshotQuanan.child(orderQuanAn.getMaQuanAn()+"");
            QuananModel quananModel = valueQuanan.getValue(QuananModel.class);
            quananModel.setMaquanan(valueQuanan.getKey());
            Log.d("xyzt",quananModel.getTenquanan()+" ");
            // Lay hinh anh
            DataSnapshot dataSnapshotHinhAnhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanan.getKey());
            List<String> Hinhanhlist = new ArrayList<>();
            for(DataSnapshot datahinhquanan:dataSnapshotHinhAnhQuanAn.getChildren()){
                Hinhanhlist.add(datahinhquanan.getValue(String.class));
            }
//                    Log.d("kiemtra",quananModel.getTienich().get(0) + " "+quananModel.getTienich().get(1));
            quananModel.setHinhanhquanan(Hinhanhlist);
            // Lay Binh luan

            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quananModel.getMaquanan());

            List<BinhLuanModel> BinhluanmodeList = new ArrayList<>();
            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                Log.d("diem =",valueBinhLuan.child("chamdiem").getValue()+"");
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                    hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);
                BinhluanmodeList.add(binhLuanModel);
            }
            quananModel.setBinhLuanModel(BinhluanmodeList);
            // lay chi nhanh quan an
            DataSnapshot snapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(quananModel.getMaquanan());
            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();

            for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()){
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                double khoangcach = vitrihientai.distanceTo(vitriquanan)/1000;
                Log.d("Khoang cach",khoangcach+"");
                chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                Log.d("Chi nhanh quan an",chiNhanhQuanAnModel.getDiachi());
                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }

            quananModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
            Log.d("Quananmodel",quananModel.getTenquanan()+"");
            odauInterface.getDanhSachQuanAnModel(quananModel);

        };

    }
    public void LayDanhSachQuanAnGanNhat(DataSnapshot dataSnapshot,final OdauInterface odauInterface,final Location vitrihientai/*,int itemtieptheo,int itemdaco*/) {
        int i=0;

        DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");
        for(orderQuanAnKhoangCach orderQuanAn : orderQuanAnKhoangCaches) {
            Log.d("orderkhoangcach",orderQuanAn.getMaQuanAn()+"   "+orderQuanAn.getKhoangcach());
        }
        for(orderQuanAnKhoangCach orderQuanAn : orderQuanAnKhoangCaches) {

            /*if(i == itemtieptheo) break;
            if(i < itemdaco){
                i++;
                continue;
            }
            i++; */
            DataSnapshot valueQuanan= dataSnapshotQuanan.child(orderQuanAn.getMaQuanAn()+"");
            QuananModel quananModel = valueQuanan.getValue(QuananModel.class);
            quananModel.setMaquanan(valueQuanan.getKey());
            Log.d("xyzt",quananModel.getTenquanan()+" ");
            // Lay hinh anh
            DataSnapshot dataSnapshotHinhAnhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanan.getKey());
            List<String> Hinhanhlist = new ArrayList<>();
            for(DataSnapshot datahinhquanan:dataSnapshotHinhAnhQuanAn.getChildren()){
                Hinhanhlist.add(datahinhquanan.getValue(String.class));
            }
//                    Log.d("kiemtra",quananModel.getTienich().get(0) + " "+quananModel.getTienich().get(1));
            quananModel.setHinhanhquanan(Hinhanhlist);
            // Lay Binh luan

            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quananModel.getMaquanan());

            List<BinhLuanModel> BinhluanmodeList = new ArrayList<>();
            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                Log.d("diem =",valueBinhLuan.child("chamdiem").getValue()+"");
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                    hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);
                BinhluanmodeList.add(binhLuanModel);
            }
            quananModel.setBinhLuanModel(BinhluanmodeList);
            // lay chi nhanh quan an
            DataSnapshot snapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(quananModel.getMaquanan());
            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();

            for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()){
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                double khoangcach = vitrihientai.distanceTo(vitriquanan)/1000;
                Log.d("Khoang cach",khoangcach+"");
                chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                Log.d("Chi nhanh quan an",chiNhanhQuanAnModel.getDiachi());
                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }

            quananModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
            Log.d("Quananmodel",quananModel.getTenquanan()+"");
            odauInterface.getDanhSachQuanAnModel(quananModel);

        };
    }
    public void LayDanhSachQuanAnSearch(DataSnapshot dataSnapshot,final OdauInterface odauInterface,final Location vitrihientai/*,int itemtieptheo,int itemdaco*/) {
        int i=0;

        DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");
        for(orderQuanAnKhoangCach orderQuanAn : orderQuanAnKhoangCaches) {
        }
        for(SearchQuanAn searchQuanAn : searchQuanAns) {

            /*if(i == itemtieptheo) break;
            if(i < itemdaco){
                i++;
                continue;
            }
            i++; */
            DataSnapshot valueQuanan= dataSnapshotQuanan.child(searchQuanAn.getMaQuanAn()+"");
            QuananModel quananModel = valueQuanan.getValue(QuananModel.class);
            quananModel.setMaquanan(valueQuanan.getKey());
            Log.d("xyzt",quananModel.getTenquanan()+" ");
            // Lay hinh anh
            DataSnapshot dataSnapshotHinhAnhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanan.getKey());
            List<String> Hinhanhlist = new ArrayList<>();
            for(DataSnapshot datahinhquanan:dataSnapshotHinhAnhQuanAn.getChildren()){
                Hinhanhlist.add(datahinhquanan.getValue(String.class));
            }
//                    Log.d("kiemtra",quananModel.getTienich().get(0) + " "+quananModel.getTienich().get(1));
            quananModel.setHinhanhquanan(Hinhanhlist);
            // Lay Binh luan

            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quananModel.getMaquanan());

            List<BinhLuanModel> BinhluanmodeList = new ArrayList<>();
            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                Log.d("diem =",valueBinhLuan.child("chamdiem").getValue()+"");
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                    hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);
                BinhluanmodeList.add(binhLuanModel);
            }
            quananModel.setBinhLuanModel(BinhluanmodeList);
            // lay chi nhanh quan an
            DataSnapshot snapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(quananModel.getMaquanan());
            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();

            for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()){
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                double khoangcach = vitrihientai.distanceTo(vitriquanan)/1000;
                Log.d("Khoang cach",khoangcach+"");
                chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                Log.d("Chi nhanh quan an",chiNhanhQuanAnModel.getDiachi());
                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }

            quananModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
            Log.d("Quananmodel",quananModel.getTenquanan()+"");
            odauInterface.getDanhSachQuanAnModel(quananModel);

        };
    }

    public static ArrayList<String> getArrayPrefs(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        List<String> array = new ArrayList<>();
        for(int i=0;i<size;i++)
            array.add(prefs.getString(arrayName + "_" + i, null));
        return (ArrayList<String>) array;
    }
}

