package com.example.booky.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.booky.Model.SearchQuanAn;
import com.example.booky.R;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<SearchQuanAn> {
   Context context;
   ArrayList<SearchQuanAn> arrayList;
   int layoutResource;
    public  CustomArrayAdapter(Context context,int resource, ArrayList<SearchQuanAn> objects){
        super(context,resource,objects);
        this.context = context;
        this.arrayList = objects;
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layoutResource,null);
            TextView txt1 = (TextView)convertView.findViewById(R.id.item1);
            txt1.setText(arrayList.get(position).getTenQuanAn());
            TextView txt2 = (TextView)convertView.findViewById(R.id.item2);
            txt2.setText(arrayList.get(position).getDiachi());
        }
        finally {

        }

       return  convertView;
    }

    @Override
    public int getCount() {
        if (arrayList != null) {
            return Math.min(arrayList.size(), 6);
        } else {
            return 0;
        }
    }
    public ArrayList<SearchQuanAn> getArrayList() {
        return arrayList;
    }
}
