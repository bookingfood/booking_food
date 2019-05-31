package com.example.booky.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.booky.Model.BinhLuanModel;
import com.example.booky.Model.ChiNhanhQuanAnModel;
import com.example.booky.Model.QuananModel;
import com.example.booky.R;
import com.example.booky.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

public class AdapderRecyclerOdau extends RecyclerView.Adapter<AdapderRecyclerOdau.ViewHolder> {
    List<QuananModel> QuanAnModelList;
    int resource;
    Context context;
    public AdapderRecyclerOdau(Context context,List<QuananModel> quananModelList, int resource) {
        this.QuanAnModelList = quananModelList;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapderRecyclerOdau.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapderRecyclerOdau.ViewHolder holder, int position) {
        final QuananModel quanAnModel = QuanAnModelList.get(position);
        Log.d("onBinddHolder",quanAnModel.getTenquanan() + " "+position);
        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());
        if(quanAnModel.isGiaohang()){
            holder.btndatmon.setVisibility(View.VISIBLE);
        }

      if(quanAnModel.getBitmapList().size() > 0){
            holder.imgHinhquanAnOdau.setImageBitmap(quanAnModel.getBitmapList().get(0));
        }
      if(quanAnModel.getHinhanhquanan().size()>0){
          StorageReference storagehinhanh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
          long ONE_MEGABYTE = 1024 * 1024;
          storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
              @Override
              public void onSuccess(byte[] bytes) {
                  Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                  holder.imgHinhquanAnOdau.setImageBitmap(bitmap);
              }
          });
      }
        //Lấy danh sách bình luận của quán ăn
        if(quanAnModel.getBinhLuanModel().size() > 0){

            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModel().get(0);
            holder.txtTieudebl.setText(binhLuanModel.getTieude());
            holder.txtNoidungbl.setText(binhLuanModel.getNoidung());
            holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
            setHinhAnhBinhLuan(holder.circleimgbl,binhLuanModel.getThanhVienModel().getHinhanh());
            if(quanAnModel.getBinhLuanModel().size() > 2){
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModel().get(1);
                holder.txtTieudebl2.setText(binhLuanModel2.getTieude());
                holder.txtNoidungbl2.setText(binhLuanModel2.getNoidung());
                holder.txtChamDiemBinhLuan2.setText(binhLuanModel2.getChamdiem() + "");
                setHinhAnhBinhLuan(holder.circleimgbl2,binhLuanModel2.getThanhVienModel().getHinhanh());
            }
            holder.txtTongBinhLuan.setText(quanAnModel.getBinhLuanModel().size() + "");

            int tongsohinhbinhluan = 0;
            double tongdiem = 0;
            //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
            for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModel()){
                tongsohinhbinhluan += binhLuanModel1.getHinhanhBinhLuanList().size();
                tongdiem += binhLuanModel1.getChamdiem();
            }
            if(quanAnModel.getBinhLuanModel().size()>0){
            double diemtrungbinh = tongdiem/quanAnModel.getBinhLuanModel().size();
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.2f",diemtrungbinh));}

            if(tongsohinhbinhluan > 0){
                holder.txtTongHinhBinhLuan.setText(tongsohinhbinhluan + "");
            }

        }else{
            holder.Containerbinhluan.setVisibility(View.GONE);
            holder.Containerbinhluan2.setVisibility(View.GONE);
        }

        //Lấy chi nhánh quán ăn và hiển thị địa chỉ và km
        if(quanAnModel.getChiNhanhQuanAnModelList().size() > 0){
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()){
                if(chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()){
                    chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                }
            }

            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());
            holder.txtKhoanCachQuanAnODau.setText(String.format("%.1f",chiNhanhQuanAnModelTam.getKhoangcach()) + " km");
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Kiemtra1","xtz");
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                iChiTietQuanAn.putExtra("quanan",quanAnModel);
                context.startActivity(iChiTietQuanAn);
            }
        });
    }

    @Override
    public int getItemCount() {
        return QuanAnModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnOdau, txtNoidungbl, txtNoidungbl2, txtTieudebl, txtTieudebl2,txtChamDiemBinhLuan,txtChamDiemBinhLuan2,txtTongBinhLuan,txtTongHinhBinhLuan,txtDiemTrungBinhQuanAn,txtDiaChiQuanAnODau,txtKhoanCachQuanAnODau;
        Button btndatmon;
        ImageView imgHinhquanAnOdau;
        CircleImageView circleimgbl, circleimgbl2;
        LinearLayout Containerbinhluan, Containerbinhluan2;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTenQuanAnOdau = (TextView) itemView.findViewById((R.id.tenquananOdau));
            btndatmon = (Button) itemView.findViewById(R.id.btnDatmonOdau);
            imgHinhquanAnOdau = (ImageView) itemView.findViewById(R.id.imgHinhQuanAnOdau);
            txtNoidungbl = (TextView) itemView.findViewById(R.id.txtnoidungbl);
            txtNoidungbl2 = (TextView) itemView.findViewById(R.id.txtnoidungbl2);
            txtTieudebl = (TextView) itemView.findViewById(R.id.txtTieudebl);
            txtTieudebl2 = (TextView) itemView.findViewById(R.id.txtTieudebl2);
            circleimgbl = (CircleImageView) itemView.findViewById(R.id.circleimgbl);
            circleimgbl2 = (CircleImageView) itemView.findViewById(R.id.circleimgbl2);
            Containerbinhluan =(LinearLayout) itemView.findViewById(R.id.containerbinhluan);
            Containerbinhluan2 =(LinearLayout) itemView.findViewById(R.id.containerbinhluan2);
            txtChamDiemBinhLuan = (TextView) itemView.findViewById(R.id.txtDiembl1);
            txtChamDiemBinhLuan2 = (TextView) itemView.findViewById(R.id.txtdiembl2);
            txtTongBinhLuan = (TextView) itemView.findViewById(R.id.txtTongBinhLuan);
            txtDiemTrungBinhQuanAn = (TextView) itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtTongHinhBinhLuan =(TextView) itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtDiaChiQuanAnODau = (TextView) itemView.findViewById(R.id.txtDiaChiQuanAnOdau);
            txtKhoanCachQuanAnODau = (TextView) itemView.findViewById(R.id.km);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}