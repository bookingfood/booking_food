package com.example.booky.Model;

import java.util.Comparator;

public class orderQuanAn implements Comparable<orderQuanAn>{
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getEveragepoint() {
        return Everagepoint;
    }

    public void setEveragepoint(double everagepoint) {
        Everagepoint = everagepoint;
    }

    public String getMaQuanAn() {
        return maQuanAn;
    }

    public void setMaQuanAn(String maQuanAn) {
        this.maQuanAn = maQuanAn;
    }

    double distance;
    double Everagepoint;
    String maQuanAn;

    @Override
    public int compareTo(orderQuanAn o) {
        if(this.Everagepoint == o.Everagepoint) return 0;
        else if(this.Everagepoint < o.Everagepoint) return 1;
        else  return -1;
    }
}
