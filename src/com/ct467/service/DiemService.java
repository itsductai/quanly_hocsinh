package com.ct467.service;

import com.ct467.connection.DiemDao;
import com.ct467.model.Diem;
import java.util.List;

public class DiemService {
    private DiemDao diemDao;

    public DiemService() {
        this.diemDao = new DiemDao();
    }
    
    public List<Diem> getDSDiem(){
        return this.diemDao.getDSDiem();
    }
    
    public void addDiem(Diem diem){
        this.diemDao.addDiem(diem);
    }
    
//    public void updateDiem(Diem diem){
//        this.diemDao.updateDiem(diem);
//    }
//    
//    public void deleteDiem(int maDiem){
//        this.diemDao.deleteDiem(maDiem);
//    }
//    
//    public List<Diem> timKiemDiem(String tenDiem, String nienKhoa){
//        return this.diemDao.timKiemDiem(tenDiem, nienKhoa);
//    }
}
