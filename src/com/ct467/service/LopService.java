package com.ct467.service;

import com.ct467.connection.LopDao;
import com.ct467.model.Lop;
import java.util.List;

public class LopService {
    private LopDao lopDao;

    public LopService() {
        this.lopDao = new LopDao();
    }
    
    public List<Lop> getDSLop(){
        return this.lopDao.getDSLop();
    }

    public Lop getLop(int maLop){
        return this.lopDao.getLop(maLop);
    }
    public Lop getLopByTen(String tenLop){
        return this.lopDao.getLopByTen(tenLop);
    }
    
    public void addLop(Lop lop){
        this.lopDao.addLop(lop);
    }
    
    public void updateLop(Lop lop){
        this.lopDao.updateLop(lop);
    }
    
    public void deleteLop(int maLop){
        this.lopDao.deleteLop(maLop);
    }
    
    public List<Lop> timKiemLop(String tenLop, String nienKhoa){
        return this.lopDao.timKiemLop(tenLop, nienKhoa);
    }
}
