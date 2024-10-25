package com.ct467.connection;

import com.ct467.model.PhongHoc;
import java.util.ArrayList;
import java.util.List;
import com.ct467.model.PhongLop;
import com.ct467.model.Lop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PhongLopDao {
    public List<PhongLop> getDSPhongLop(){
        List<PhongLop> dsPhongLop = new ArrayList<>();
        
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from phong_lop pl join phonghoc ph on ph.sophong = pl.sophong join lop l on l.malop = pl.malop";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                PhongHoc phongHoc = new PhongHoc(
                    rs.getInt("SOPHONG"),
                    rs.getInt("SOCHOTOIDA"));
                Lop lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                    rs.getString("NIENKHOA"));
                
                PhongLop phongLop = new PhongLop(
                    phongHoc,
                    lop,
                    rs.getString("HOCKYNAMHOC"));
                dsPhongLop.add(phongLop);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsPhongLop;
    }    
    
    public void addPhongLop(PhongLop phongLop){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "insert into phong_lop(sophong, malop, hockinamhoc) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, phongLop.getPhongHoc().getSoPhong());
            preparedStatement.setInt(2, phongLop.getLop().getMaLop());
            preparedStatement.setString(3, phongLop.getHocKi_namHoc());
            
            int res = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
//    public void updatePhongLop(PhongLop phong_lop){
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "update phong_lop set NIENKHOA = ?, TENLOP = ? where maphong_lop = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, phong_lop.getNienKhoa());
//            preparedStatement.setInt(3, phong_lop.getMaPhongLop());
//            preparedStatement.setString(2, phong_lop.getTenPhongLop());
//            
//            int res = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//    }
    
    public void deletePhongLop(PhongLop phongLop){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "delete from phong_lop where sophong = ? and malop = ? and hockynamhoc = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, phongLop.getPhongHoc().getSoPhong());
            preparedStatement.setInt(2, phongLop.getLop().getMaLop());
            preparedStatement.setString(3, phongLop.getHocKi_namHoc());
            int res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
//    public List<PhongLop> timKiemPhongLop(String tenPhongLop, String nienKhoa){
//        List<PhongLop> dsPhongLop = new ArrayList<>();
//        Connection connection = JDBCConnection.getJDBCConnection();
//        
//        String sql = "select * from phong_lop where tenPhongLop = ? or nienkhoa = ?";
//        
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, tenPhongLop);
//            preparedStatement.setString(2, nienKhoa);
//            
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while(rs.next()){
//                PhongLop phong_lop = new PhongLop(
//                    rs.getInt("MALOP"),
//                    rs.getString("TENLOP"),
//                    rs.getString("NIENKHOA"));
//                dsPhongLop.add(phong_lop);
//            }
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//        return dsPhongLop;
//    }
}
