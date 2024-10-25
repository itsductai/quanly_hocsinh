package com.ct467.model;

import com.ct467.utils.VaiTro;

public class TaiKhoan {
    private String username;
    private String pasword;
    private String vaiTro;
    private int maGiaoVien;
    private String maHocSinh;

    public TaiKhoan() {
    }

    public TaiKhoan(String username, String pasword, String vaiTro, int maGiaoVien, String maHocSinh) {
        this.username = username;
        this.pasword = pasword;
        this.vaiTro = vaiTro;
        this.maGiaoVien = maGiaoVien;
        this.maHocSinh = maHocSinh;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public int getMaGiaoVien() {
        return maGiaoVien;
    }

    public void setMaGiaoVien(int maGiaoVien) {
        this.maGiaoVien = maGiaoVien;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }
    
    
}
