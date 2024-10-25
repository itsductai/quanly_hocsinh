package com.ct467.service;

import com.ct467.connection.ChuNhiemDao;
import com.ct467.model.ChuNhiem;
import com.ct467.model.Lop;
import com.ct467.model.GiaoVien;
import java.util.List;

public class ChuNhiemService {
    private ChuNhiemDao chuNhiemDao;
    public ChuNhiemService() {
        this.chuNhiemDao = new ChuNhiemDao();
    }
    
    public List<ChuNhiem> getDSChuNhiem(){
        return this.chuNhiemDao.getDSChuNhiem();
    }
    
    public void addChuNhiem(ChuNhiem chuNhiem){
        this.chuNhiemDao.addChuNhiem(chuNhiem);
    }
    
    public void updateChuNhiem(ChuNhiem chuNhiem){
        this.chuNhiemDao.updateChuNhiem(chuNhiem);
    }
    
    public void deleteChuNhiem(Lop lop, GiaoVien giaoVien){
        this.chuNhiemDao.deleteChuNhiem(lop, giaoVien);
    }
    
    public List<ChuNhiem> timKiemChuNhiem(int maLop, int maGiaoVien, String namHoc){
        return this.chuNhiemDao.timKiemChuNhiem(maLop, maGiaoVien, namHoc);
    }
}
