package com.ct467.service;

import com.ct467.connection.TaiKhoanDao;
import com.ct467.model.TaiKhoan;
import java.util.List;

public class TaiKhoanService {
    private TaiKhoanDao taiKhoanDao;

    public TaiKhoanService() {
        this.taiKhoanDao = new TaiKhoanDao();
    }
    
    public List<TaiKhoan> getDSTaiKhoan(){
        return this.taiKhoanDao.getDSTaiKhoan();
    }
    
//    public void addTaiKhoan(TaiKhoan taiKhoan){
//        this.taiKhoanDao.addTaiKhoan(taiKhoan);
//    }
//    
//    public void updateTaiKhoan(TaiKhoan taiKhoan){
//        this.taiKhoanDao.updateTaiKhoan(taiKhoan);
//    }
//    
//    public void deleteTaiKhoan(int maTaiKhoan){
//        this.taiKhoanDao.deleteTaiKhoan(maTaiKhoan);
//    }
//    
//    public List<TaiKhoan> timKiemTaiKhoan(String tenTaiKhoan, String nienKhoa){
//        return this.taiKhoanDao.timKiemTaiKhoan(tenTaiKhoan, nienKhoa);
//    }
}
