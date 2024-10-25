package com.ct467.model;

import java.util.Objects;

public class Lop {
    private int maLop;
    private String tenLop;
    private String nienKhoa;

    public Lop() {
    }

    public Lop(int maLop, String tenLop, String nienKhoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.nienKhoa = nienKhoa;
    }

    @Override
    public String toString() {
        if(maLop == 0){
            return "<tất cả lớp>";
        }
        return maLop + " - " + tenLop;
    }
    
    public int getMaLop() {
        return maLop;
    }

    public void setMaLop(int maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getNienKhoa() {
        return nienKhoa;
    }

    public void setNienKhoa(String nienKhoa) {
        this.nienKhoa = nienKhoa;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Lop lop = (Lop) obj;
        return this.maLop == lop.maLop;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maLop);  // Dùng thuộc tính chính để tạo hash code
    }
}
