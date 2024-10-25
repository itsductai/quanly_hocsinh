package com.ct467.model;

public class PhongHoc {
    private int soPhong;
    private int soChoToiDa;

    public PhongHoc() {
    }

    public PhongHoc(int soPhong, int soChoToiDa) {
        this.soPhong = soPhong;
        this.soChoToiDa = soChoToiDa;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public int getSoChoToiDa() {
        return soChoToiDa;
    }

    public void setSoChoToiDa(int soChoToiDa) {
        this.soChoToiDa = soChoToiDa;
    }

    @Override
    public String toString() {
        return String.valueOf(soPhong);
    }
    
    
}
