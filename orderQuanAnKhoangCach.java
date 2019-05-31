package com.example.booky.Model;

public class orderQuanAnKhoangCach implements Comparable<orderQuanAnKhoangCach> {
    public String getMaQuanAn() {
        return MaQuanAn;
    }

    public void setMaQuanAn(String maQuanAn) {
        MaQuanAn = maQuanAn;
    }

    public Double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(Double khoangcach) {
        this.khoangcach = khoangcach;
    }

    String MaQuanAn;
    Double khoangcach;

    @Override
    public int compareTo(orderQuanAnKhoangCach o) {
        if(this.khoangcach > o.khoangcach) return 1;
        else if(this.khoangcach == o.khoangcach) return 0;
        else return -1;
    }
}
