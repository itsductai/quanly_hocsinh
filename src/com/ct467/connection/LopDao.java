package com.ct467.connection;

import java.util.ArrayList;
import java.util.List;
import com.ct467.model.Lop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LopDao {
    public List<Lop> getDSLop(){
        List<Lop> dsLop = new ArrayList<>();
        
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from lop";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Lop lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                    rs.getString("NIENKHOA"));
                dsLop.add(lop);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsLop;
    }    
    
    public void addLop(Lop lop){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "insert into lop(MALOP, TENLOP, NIENKHOA) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, lop.getMaLop());
            preparedStatement.setString(2, lop.getTenLop());
            preparedStatement.setString(3, lop.getNienKhoa());
            
            int res = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public void updateLop(Lop lop){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "update lop set NIENKHOA = ?, TENLOP = ? where malop = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lop.getNienKhoa());
            preparedStatement.setInt(3, lop.getMaLop());
            preparedStatement.setString(2, lop.getTenLop());
            
            int res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public void deleteLop(int maLop){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "delete from lop where malop = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maLop);
            
            int res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public List<Lop> timKiemLop(String tenLop, String nienKhoa){
        List<Lop> dsLop = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "select * from lop where tenLop = ? or nienkhoa = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenLop);
            preparedStatement.setString(2, nienKhoa);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Lop lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                    rs.getString("NIENKHOA"));
                dsLop.add(lop);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsLop;
    }
    
    public Lop getLop(int maLop){
        Lop lop = null;
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "select * from lop where maLop = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maLop);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                    rs.getString("NIENKHOA"));
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return lop;
    }
    
    
    public Lop getLopByTen(String tenLop){
        Lop lop = null;
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "select * from lop where tenLop = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenLop);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                    rs.getString("NIENKHOA"));
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return lop;
    }
}
