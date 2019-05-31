package com.example.booky.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.booky.Adapters.CustomArrayAdapter;
import com.example.booky.Controller.OdauController;
import com.example.booky.Model.QuananModel;
import com.example.booky.Model.SearchQuanAn;
import com.example.booky.R;

import java.util.ArrayList;
import java.util.List;

public class OdauFragment extends Fragment {
    OdauController odauController ;
    RecyclerView recyclerOdau;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;
    RadioButton rdMoinhat,rdHotNhat,rdGanNhat;
    CheckBox checkBox ;
    ListView lvSearch;
    Button btnSearch;
    List<SearchQuanAn> searchQuanAns = new ArrayList<>();
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau,container,false);
        btnSearch = getActivity().findViewById(R.id.btnStartSearch);
        lvSearch = getActivity().findViewById(R.id.lsSearch);
        recyclerOdau = view.findViewById(R.id.recyclerOdau);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestScrollViewODau);
        rdMoinhat = (RadioButton) view.findViewById(R.id.rdMoinhat) ;
        rdHotNhat = (RadioButton) view.findViewById(R.id.rdHotnhat) ;
        rdGanNhat = (RadioButton) view.findViewById(R.id.rdGanNhat);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchQuanAns.clear();
                searchQuanAns.add((SearchQuanAn) lvSearch.getAdapter().getItem(position));
                lvSearch.setVisibility(View.GONE);
                sharedPreferences = getContext().getSharedPreferences("chuoiloc", Context.MODE_PRIVATE);
                Log.d("fragmentText: ",sharedPreferences.getString("maquanan","0"));
                sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
                Location vitrihientai = new Location("");
                vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
                vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
                odauController = new OdauController(getContext(),4,searchQuanAns);
                odauController.getDanhsachQuananController(getContext(),nestedScrollView,recyclerOdau,vitrihientai);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuanAns.clear();
                for(int i=0;i<lvSearch.getChildCount();i++){
                    searchQuanAns.add((SearchQuanAn) lvSearch.getAdapter().getItem(i));
                }
                for(SearchQuanAn dss:searchQuanAns){
                    Log.d("dss",dss.getTenQuanAn());
                }
                lvSearch.setVisibility(View.GONE);
                sharedPreferences = getContext().getSharedPreferences("chuoiloc", Context.MODE_PRIVATE);
                Log.d("fragmentText: ",sharedPreferences.getString("maquanan","0"));
                sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
                Location vitrihientai = new Location("");
                vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
                vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
                odauController = new OdauController(getContext(),4,searchQuanAns);
                odauController.getDanhsachQuananController(getContext(),nestedScrollView,recyclerOdau,vitrihientai);

            }
        });
        rdMoinhat.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                rdMoinhat.setTextColor(getResources().getColor(R.color.lightgray));
                return true;
            }
        });
        rdMoinhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdMoinhat.setTextColor(getResources().getColor(R.color.lightgray));
                rdGanNhat.setTextColor(getResources().getColor(R.color.colorWhite));
                rdHotNhat.setTextColor(getResources().getColor(R.color.colorWhite));
                sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
                Location vitrihientai = new Location("");
                vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
                vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
                odauController = new OdauController(getContext(),1);
                odauController.getDanhsachQuananController(getContext(),nestedScrollView,recyclerOdau,vitrihientai);

            }
        });

        rdGanNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdMoinhat.setTextColor(getResources().getColor(R.color.colorWhite));
                rdGanNhat.setTextColor(getResources().getColor(R.color.lightgray));
                rdHotNhat.setTextColor(getResources().getColor(R.color.colorWhite));
                sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
                Location vitrihientai = new Location("");
                vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
                vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
                odauController = new OdauController(getContext(),3);
                odauController.getDanhsachQuananController(getContext(),nestedScrollView,recyclerOdau,vitrihientai);
            }
        });
        rdHotNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdMoinhat.setTextColor(getResources().getColor(R.color.colorWhite));
                rdGanNhat.setTextColor(getResources().getColor(R.color.colorWhite));
                rdHotNhat.setTextColor(getResources().getColor(R.color.lightgray));
                sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
                Location vitrihientai = new Location("");
                vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
                vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
                odauController = new OdauController(getContext(),2);
                odauController.getDanhsachQuananController(getContext(),nestedScrollView,recyclerOdau,vitrihientai);
            }
        });
        /*QuananModel quananModel = new QuananModel();
        quananModel.getDanhSachQuanAn();*/
        //odauController.getDanhsachQuananController(getContext(),nestedScrollView,recyclerOdau,vitrihientai);
        return view;

    }

    public static ArrayList<String> getArrayPrefs(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        List<String> array = new ArrayList<>();
        for(int i=0;i<size;i++)
            array.add(prefs.getString(arrayName + "_" + i, null));
        return (ArrayList<String>) array;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
