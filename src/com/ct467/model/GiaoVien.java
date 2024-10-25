package com.ct467.model;

import java.util.Date;
import java.util.Objects;

public class GiaoVien {
    private int maGiaoVien;
    private String hoTen;
    private Date ngaySinh;
    private String diaChi;
    private String soDienThoai;

    public GiaoVien() {
    }

    public GiaoVien(int maGiaoVien, String hoTen, Date ngaySinh, String diaChi, String soDienThoai) {
        this.maGiaoVien = maGiaoVien;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public int getMaGiaoVien() {
        return maGiaoVien;
    }

    public void setMaGiaoVien(int maGiaoVien) {
        this.maGiaoVien = maGiaoVien;
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

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @Override
    public String toString() {
        if(maGiaoVien == 0){
            return "<tất cả giáo viên>";
        }
        return maGiaoVien + " - " + hoTen;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GiaoVien giaoVien = (GiaoVien) obj;
        return this.maGiaoVien == giaoVien.maGiaoVien;  // So sánh theo mã giáo viên (hoặc thuộc tính chính khác)
    }

    @Override
    public int hashCode() {
        return Objects.hash(maGiaoVien);  // Dùng thuộc tính chính để tạo hash code
    }
}
