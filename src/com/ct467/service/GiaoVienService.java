package com.ct467.service;

import com.ct467.connection.GiaoVienDao;
import com.ct467.model.GiaoVien;
import java.util.List;

public class GiaoVienService {
    private GiaoVienDao giaoVienDao;

    public GiaoVienService() {
        this.giaoVienDao = new GiaoVienDao();
    }
    
    public List<GiaoVien> getDSGiaoVien(){
        return this.giaoVienDao.getDSGiaoVien();
    }
    
    public void addGiaoVien(GiaoVien hocsinh){
        this.giaoVienDao.addGiaoVien(hocsinh);
    }
    
    public void updateGiaoVien(GiaoVien hocsinh){
        this.giaoVienDao.updateGiaoVien(hocsinh);
    }
    
    public void deleteGiaoVien(int maGiaoVien){
        this.giaoVienDao.deleteGiaoVien(maGiaoVien);
    }
    
    public List<GiaoVien> timKiemGiaoVien(int maGiaoVien, String hoTen){
        return this.giaoVienDao.timKiemGiaoVien(maGiaoVien, hoTen);
    }
    
    public GiaoVien getGiaoVien(int maGiaoVien){
        return this.giaoVienDao.getGiaoVien(maGiaoVien);
    }
}
