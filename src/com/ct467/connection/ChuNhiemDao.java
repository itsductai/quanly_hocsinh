package com.ct467.connection;

import java.util.ArrayList;
import java.util.List;
import com.ct467.model.ChuNhiem;
import com.ct467.model.GiaoVien;
import com.ct467.model.Lop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ChuNhiemDao {
    Lop lop;
    GiaoVien giaoVien;
    public List<ChuNhiem> getDSChuNhiem(){
        List<ChuNhiem> dsChuNhiem = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from chunhiem cn join lop l on l.malop = cn.malop join giaovien gv on gv.maGiaoVien = cn.maGiaoVien;";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                  rs.getString("NIENKHOA"));
                giaoVien = new GiaoVien(
                    rs.getInt("MAGIAOVIEN"),
                    rs.getString("HOTEN"),
                    rs.getDate("NGAYSINH"),
                    rs.getString("DIACHI"),
                    rs.getString("SODIENTHOAI"));
                ChuNhiem chuNhiem = new ChuNhiem(
                        lop,
                        giaoVien,
                        rs.getString("NAMHOC"));
                dsChuNhiem.add(chuNhiem);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsChuNhiem;
    }
    
    public void addChuNhiem(ChuNhiem chuNhiem){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "insert into chunhiem(MALOP, MAGIAOVIEN, NAMHOC) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, chuNhiem.getLop().getMaLop());
            preparedStatement.setInt(2, chuNhiem.getGiaoVien().getMaGiaoVien());
            preparedStatement.setString(3, chuNhiem.getNamHoc());
            
            int res = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public void updateChuNhiem(ChuNhiem chuNhiem){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "update chunhiem set NAMHOC = ? where maLop = ? and maGiaoVien = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, chuNhiem.getNamHoc());
            preparedStatement.setInt(2, chuNhiem.getLop().getMaLop());
            preparedStatement.setInt(3, chuNhiem.getGiaoVien().getMaGiaoVien());
            
            int res = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    
    public void deleteChuNhiem(Lop lop, GiaoVien giaovien){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "delete from chunhiem where maLop = ? and maGiaoVien = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, lop.getMaLop());
            preparedStatement.setInt(2, giaovien.getMaGiaoVien());
            
            int res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public List<ChuNhiem> timKiemChuNhiem(int maLop, int maGiaoVien, String namHoc){
        List<ChuNhiem> dsChuNhiem = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "select * from chunhiem cn join lop l on l.malop = cn.malop join giaovien gv on gv.maGiaoVien = cn.maGiaoVien where cn.maLop = ? or cn.maGiaoVien = ? or cn.namHoc = ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maLop);
            preparedStatement.setInt(2,maGiaoVien);
            preparedStatement.setString(3,namHoc);

            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                  rs.getString("NIENKHOA"));
                giaoVien = new GiaoVien(
                    rs.getInt("MAGIAOVIEN"),
                    rs.getString("HOTEN"),
                    rs.getDate("NGAYSINH"),
                    rs.getString("DIACHI"),
                    rs.getString("SODIENTHOAI"));
                ChuNhiem chuNhiem = new ChuNhiem(
                        lop,
                        giaoVien,
                        rs.getString("NAMHOC"));
                dsChuNhiem.add(chuNhiem);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsChuNhiem;
    }
}
