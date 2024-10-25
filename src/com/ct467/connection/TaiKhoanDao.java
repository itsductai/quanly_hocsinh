package com.ct467.connection;

import com.ct467.model.HocSinh;
import java.util.ArrayList;
import java.util.List;
import com.ct467.model.TaiKhoan;
import com.ct467.model.GiaoVien;
import com.ct467.utils.VaiTro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TaiKhoanDao {
    public List<TaiKhoan> getDSTaiKhoan(){
        List<TaiKhoan> dsTaiKhoan = new ArrayList<>();
        
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from taikhoan";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                TaiKhoan taiKhoan = new TaiKhoan(
                    rs.getString("USERNAME"),
                    rs.getString("PASSWORD"),
                    rs.getString("VAITRO"),
                    rs.getInt("MAGIAOVIEN"),
                    rs.getString("MAHOCSINH"));
                dsTaiKhoan.add(taiKhoan);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsTaiKhoan;
    }    
    
//    public void addTaiKhoan(TaiKhoan taiKhoan){
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "insert into taikhoan(username, password, vaitro, magiaovien, mahocsinh) VALUES (?, ?, ?, ?, ?)";
//        
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, taiKhoan.getUsername());
//            preparedStatement.setString(2, taiKhoan.getPasword());
//            preparedStatement.setString(3, taiKhoan.getVaiTro());
//            preparedStatement.setInt(3, taiKhoan.getGiaoVien());
//            preparedStatement.setString(3, taiKhoan.getHocKi_namHoc());
//            int res = preparedStatement.executeUpdate();
//            
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//    }
//    
//    public void updateTaiKhoan(TaiKhoan taikhoan){
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "update taikhoan set NIENKHOA = ?, TENLOP = ? where mataikhoan = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, taikhoan.getNienKhoa());
//            preparedStatement.setInt(3, taikhoan.getMaTaiKhoan());
//            preparedStatement.setString(2, taikhoan.getTenTaiKhoan());
//            
//            int res = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//    }
    
//    public void deleteTaiKhoan(TaiKhoan taiKhoan){
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "delete from taikhoan where sophong = ? and malop = ? and hockynamhoc = ?";
//        
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, taiKhoan.getPhongHoc().getSoPhong());
//            preparedStatement.setInt(2, taiKhoan.getLop().getMaLop());
//            preparedStatement.setString(3, taiKhoan.getHocKi_namHoc());
//            int res = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//    }
    
//    public List<TaiKhoan> timKiemTaiKhoan(String tenTaiKhoan, String nienKhoa){
//        List<TaiKhoan> dsTaiKhoan = new ArrayList<>();
//        Connection connection = JDBCConnection.getJDBCConnection();
//        
//        String sql = "select * from taikhoan where tenTaiKhoan = ? or nienkhoa = ?";
//        
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, tenTaiKhoan);
//            preparedStatement.setString(2, nienKhoa);
//            
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while(rs.next()){
//                TaiKhoan taikhoan = new TaiKhoan(
//                    rs.getInt("MALOP"),
//                    rs.getString("TENLOP"),
//                    rs.getString("NIENKHOA"));
//                dsTaiKhoan.add(taikhoan);
//            }
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//        return dsTaiKhoan;
//    }
}
