package com.ct467.model;

import java.util.Date;

public class HocSinh {
    private String maHocSinh;
    private String hoTen;
    private Date ngaySinh;
    private String diaChi;
    private String sdtPhuHuynh;
    private Lop lop;

    public HocSinh() {
    }

    public HocSinh(String maHocSinh, String hoTen, Date ngaySinh, String diaChi, String sdtPhuHuynh, Lop lop) {
        this.maHocSinh = maHocSinh;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.sdtPhuHuynh = sdtPhuHuynh;
        this.lop = lop;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdtPhuHuynh() {
        return sdtPhuHuynh;
    }

    public void setSdtPhuHuynh(String sdtPhuHuynh) {
        this.sdtPhuHuynh = sdtPhuHuynh;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    
}
