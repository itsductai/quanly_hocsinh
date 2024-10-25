package com.ct467.model;

public class ChuNhiem {
    private Lop lop;
    private GiaoVien giaoVien;
    private String namHoc;

    public ChuNhiem() {
    }

    public ChuNhiem(Lop lop, GiaoVien giaoVien, String namHoc) {
        this.lop = lop;
        this.giaoVien = giaoVien;
        this.namHoc = namHoc;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    public GiaoVien getGiaoVien() {
        return giaoVien;
    }

    public void setGiaoVien(GiaoVien giaoVien) {
        this.giaoVien = giaoVien;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }
    
}
