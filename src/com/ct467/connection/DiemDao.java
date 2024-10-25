package com.ct467.connection;

import java.util.ArrayList;
import java.util.List;
import com.ct467.model.Diem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DiemDao {
    public List<Diem> getDSDiem(){
        List<Diem> dsDiem = new ArrayList<>();
        
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from diem";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){   
                Diem diem = new Diem(
                    rs.getString("MAHOCSINH"),
                    rs.getInt("MAMONHOC"),
                    rs.getFloat("DIEMMIENG"),
                    rs.getFloat("DIEM15PHUT"),
                    rs.getFloat("DIEM1TIET"),
                    rs.getFloat("DIEMTHI"));
                dsDiem.add(diem);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsDiem;
    }    
    
    public void addDiem(Diem diem){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "insert into diem(MAHOCSINH, MAMONHOC, DIEMMIENG, DIEM15PHUT, DIEM1TIET, DIEMTHI) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, diem.getMaHocSinh());
            preparedStatement.setInt(2, diem.getMaMonHoc());
            preparedStatement.setFloat(3, diem.getDiemMieng());
            preparedStatement.setFloat(4, diem.getDiem15Phut());
            preparedStatement.setFloat(5, diem.getDiem1Tiet());
            preparedStatement.setFloat(6, diem.getDiemThi());

            int res = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
//    public void updateDiem(Diem diem){
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "update diem set NIENKHOA = ?, TENLOP = ? where madiem = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, diem.getNienKhoa());
//            preparedStatement.setInt(3, diem.getMaDiem());
//            preparedStatement.setString(2, diem.getTenDiem());
//            
//            int res = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//    }
//    
//    public void deleteDiem(int maDiem){
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "delete from diem where madiem = ?";
//        
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, maDiem);
//            
//            int res = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//    }
//    
//    public List<Diem> timKiemDiem(String tenDiem, String nienKhoa){
//        List<Diem> dsDiem = new ArrayList<>();
//        Connection connection = JDBCConnection.getJDBCConnection();
//        
//        String sql = "select * from diem where tenDiem = ? or nienkhoa = ?";
//        
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, tenDiem);
//            preparedStatement.setString(2, nienKhoa);
//            
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while(rs.next()){
//                Diem diem = new Diem(
//                    rs.getInt("MALOP"),
//                    rs.getString("TENLOP"),
//                    rs.getString("NIENKHOA"));
//                dsDiem.add(diem);
//            }
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//        return dsDiem;
//    }
    
}
