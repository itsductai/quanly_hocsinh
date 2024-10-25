package com.ct467.model;


public class PhongLop {
    private Lop lop;
    private PhongHoc phongHoc;
    private String hocKi_namHoc;

    public PhongLop() {
    }

    public PhongLop(PhongHoc phongHoc, Lop lop, String hocKi_namHoc) {
        this.lop = lop;
        this.phongHoc = phongHoc;
        this.hocKi_namHoc = hocKi_namHoc;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    public PhongHoc getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(PhongHoc phongHoc) {
        this.phongHoc = phongHoc;
    }

    public String getHocKi_namHoc() {
        return hocKi_namHoc;
    }

    public void setHocKi_namHoc(String hocKi_namHoc) {
        this.hocKi_namHoc = hocKi_namHoc;
    }
}
