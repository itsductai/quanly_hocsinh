package com.ct467.model;

public class Diem {
    private String maHocSinh;
    private int maMonHoc;
    private float diemMieng;
    private float diem15Phut;
    private float diem1Tiet;
    private float diemThi;

    public Diem() {
    }

    public Diem(String maHocSinh, int maMonHoc, float diemMieng, float diem15Phut, float diem1Tiet, float diemThi) {
        this.maHocSinh = maHocSinh;
        this.maMonHoc = maMonHoc;
        this.diemMieng = diemMieng;
        this.diem15Phut = diem15Phut;
        this.diem1Tiet = diem1Tiet;
        this.diemThi = diemThi;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public int getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(int maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public float getDiemMieng() {
        return diemMieng;
    }

    public void setDiemMieng(float diemMieng) {
        this.diemMieng = diemMieng;
    }

    public float getDiem15Phut() {
        return diem15Phut;
    }

    public void setDiem15Phut(float diem15Phut) {
        this.diem15Phut = diem15Phut;
    }

    public float getDiem1Tiet() {
        return diem1Tiet;
    }

    public void setDiem1Tiet(float diem1Tiet) {
        this.diem1Tiet = diem1Tiet;
    }

    public float getDiemThi() {
        return diemThi;
    }

    public void setDiemThi(float diemThi) {
        this.diemThi = diemThi;
    }
    
    
}
