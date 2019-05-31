package com.example.booky.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booky.Controller.ThucDonController;
import com.example.booky.Model.MonAnModel;
import com.example.booky.Model.ThucDonModel;
import com.example.booky.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.HolderMonAn> {

    Context context;
    List<MonAnModel> monAnModelList;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences ;
    ThucDonModel thucDonModel;
    //public static List<DatMon> datMonList = new ArrayList<>();

    public AdapterMonAn(Context context, List<MonAnModel> monAnModelList, ThucDonModel thucDonModel){
        this.context = context;
        this.monAnModelList = monAnModelList;
        this.thucDonModel = thucDonModel;

    }

    @Override
    public HolderMonAn onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_monan,parent,false);
        return new HolderMonAn(view);
    }
    private DataSnapshot dataroot;
    @Override
    public void onBindViewHolder(final HolderMonAn holder, int position) {
        final MonAnModel monAnModel = monAnModelList.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());
        holder.imlikefood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.imlikefood.getTag() == "1"){
                    holder.imlikefood.setImageResource(R.drawable.ic_notlike);
                    holder.imlikefood.setTag("0");
                    DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
                }
                else{
                    holder.imlikefood.setImageResource(R.drawable.ic_likefood);
                    holder.imlikefood.setTag("1");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }

    public class HolderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn,txtSoLuong;
        ImageView imlikefood;
        public HolderMonAn(View itemView) {
            super(itemView);
            txtTenMonAn = (TextView) itemView.findViewById(R.id.txtTenMonAn);
            imlikefood =(ImageView) itemView.findViewById(R.id.like_food);
        }
    }
}
