package com.ct467.service;

import com.ct467.connection.HocSinhDao;
import com.ct467.model.HocSinh;
import java.util.List;

public class HocSinhService {
    private HocSinhDao hocSinhDao;

    public HocSinhService() {
        this.hocSinhDao = new HocSinhDao();
    }
    
    public List<HocSinh> getDSHocSinh(){
        return this.hocSinhDao.getDSHocSinh();
    }
    
    public void addHocSinh(HocSinh hocSinh){
        this.hocSinhDao.addHocSinh(hocSinh);
    }
    
    public void updateHocSinh(HocSinh hocSinh){
        this.hocSinhDao.updateHocSinh(hocSinh);
    }
    
    public void deleteHocSinh(String maHocSinh){
        this.hocSinhDao.deleteHocSinh(maHocSinh);
    }
    
    public List<HocSinh> timKiemHocSinh(String maHocSinh, String hoTen){
        return this.hocSinhDao.timKiemHocSinh(maHocSinh, hoTen);
    }
}
