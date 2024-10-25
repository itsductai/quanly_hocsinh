package com.ct467.service;

import com.ct467.connection.PhongLopDao;
import com.ct467.model.PhongLop;
import java.util.List;

public class PhongLopService {
    private PhongLopDao phongLopDao;

    public PhongLopService() {
        this.phongLopDao = new PhongLopDao();
    }
    
    public List<PhongLop> getDSPhongLop(){
        return this.phongLopDao.getDSPhongLop();
    }

    
    public void addPhongLop(PhongLop phongLop){
        this.phongLopDao.addPhongLop(phongLop);
    }
    
//    public void updatePhongLop(PhongLop phongLop){
//        this.phongLopDao.updatePhongLop(phongLop);
//    }
    
    public void deletePhongLop(PhongLop phongLop){
        this.phongLopDao.deletePhongLop(phongLop);
    }
    
//    public List<PhongLop> timKiemPhongLop(String tenPhongLop, String nienKhoa){
//        return this.phongLopDao.timKiemPhongLop(tenPhongLop, nienKhoa);
//    }
}
