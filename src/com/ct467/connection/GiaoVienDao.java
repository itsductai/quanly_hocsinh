package com.ct467.connection;

import java.util.ArrayList;
import java.util.List;
import com.ct467.model.GiaoVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GiaoVienDao {
    public List<GiaoVien> getDSGiaoVien(){
        List<GiaoVien> dsGiaoVien = new ArrayList<>();
        
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from giaovien";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                GiaoVien giaoVien = new GiaoVien(
                    rs.getInt("MAGIAOVIEN"),
                    rs.getString("HOTEN"),
                    rs.getDate("NGAYSINH"),
                    rs.getString("DIACHI"),
                    rs.getString("SODIENTHOAI"));
                dsGiaoVien.add(giaoVien);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsGiaoVien;
    }
    
    public void addGiaoVien(GiaoVien giaoVien){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "insert into giaovien(MAGIAOVIEN, HOTEN, NGAYSINH, DIACHI, SODIENTHOAI) VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, giaoVien.getMaGiaoVien());
            preparedStatement.setString(2, giaoVien.getHoTen());
            preparedStatement.setDate(3, new java.sql.Date(giaoVien.getNgaySinh().getTime()));
            preparedStatement.setString(4, giaoVien.getDiaChi());
            preparedStatement.setString(5, giaoVien.getSoDienThoai());
            
            int res = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public void updateGiaoVien(GiaoVien giaoVien){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "update giaovien set HOTEN = ?, NGAYSINH = ?, DIACHI = ?, SODIENTHOAI= ? where magiaovien = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, giaoVien.getHoTen());
            preparedStatement.setDate(2, new java.sql.Date(giaoVien.getNgaySinh().getTime()));
            preparedStatement.setString(3, giaoVien.getDiaChi());
            preparedStatement.setString(4, giaoVien.getSoDienThoai());
            preparedStatement.setInt(5, giaoVien.getMaGiaoVien());
            
            int res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public void deleteGiaoVien(int maGiaoVien){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "delete from giaovien where magiaovien = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maGiaoVien);
            
            int res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public List<GiaoVien> timKiemGiaoVien(int maGiaoVien, String hoTen){
        List<GiaoVien> dsGiaoVien = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "select * from giaovien where magiaovien = ? or hoten like ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maGiaoVien);
            preparedStatement.setString(2,"%" + hoTen + "%");
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                GiaoVien giaoVien = new GiaoVien(
                    rs.getInt("MAGIAOVIEN"),
                    rs.getString("HOTEN"),
                    rs.getDate("NGAYSINH"),
                    rs.getString("DIACHI"),
                    rs.getString("SODIENTHOAI"));
                dsGiaoVien.add(giaoVien);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsGiaoVien;
    }
    
    public GiaoVien getGiaoVien(int maGiaoVien){
        GiaoVien giaoVien = null;
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "select * from giaovien where magiaovien = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maGiaoVien);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                giaoVien = new GiaoVien(
                    rs.getInt("MAGIAOVIEN"),
                    rs.getString("HOTEN"),
                    rs.getDate("NGAYSINH"),
                    rs.getString("DIACHI"),
                    rs.getString("SODIENTHOAI"));
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return giaoVien;
    }
}
