package com.ct467.connection;

import java.util.ArrayList;
import java.util.List;
import com.ct467.model.HocSinh;
import com.ct467.model.Lop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HocSinhDao {
    public List<HocSinh> getDSHocSinh(){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<HocSinh> dsHocSinh = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "select * from hocsinh h join lop l on l.malop = h.malop";
        try {
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Lop lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                    rs.getString("NIENKHOA"));
                
                HocSinh hocsinh = new HocSinh(
                    rs.getString("MAHOCSINH"),
                    rs.getString("HOTEN"),
                    rs.getDate("NGAYSINH"),
                    rs.getString("DIACHI"),
                    rs.getString("SODIENTHOAIPHUHUYNH"),
                    lop);
                dsHocSinh.add(hocsinh);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsHocSinh;
    }
    
    public void addHocSinh(HocSinh hocSinh){
        Connection connection = null;

    try {
        connection = JDBCConnection.getJDBCConnection();
        // Tạo câu lệnh SQL để gọi stored procedure
        String sql = "CALL add_hoc_sinh(?, ?, ?, ?, ?, ?)";
        
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hocSinh.getMaHocSinh());
        preparedStatement.setString(2, hocSinh.getHoTen());
        preparedStatement.setDate(3, new java.sql.Date(hocSinh.getNgaySinh().getTime()));
        preparedStatement.setString(4, hocSinh.getDiaChi());
        preparedStatement.setString(5, hocSinh.getSdtPhuHuynh());
        preparedStatement.setInt(6, hocSinh.getLop().getMaLop());
        
        // Thực thi câu lệnh
        preparedStatement.execute();

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "insert into hocsinh(MAHOCSINH, HOTEN, NGAYSINH, DIACHI, SODIENTHOAIPHUHUYNH, MALOP) VALUES (?, ?, ?, ?, ?, ?)";
//        
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, hocSinh.getMaHocSinh());
//            preparedStatement.setString(2, hocSinh.getHoTen());
//            preparedStatement.setDate(3, new java.sql.Date(hocSinh.getNgaySinh().getTime()));
//            preparedStatement.setString(4, hocSinh.getDiaChi());
//            preparedStatement.setString(5, hocSinh.getSdtPhuHuynh());
//            preparedStatement.setInt(6, hocSinh.getLop().getMaLop());
//            
//            int res = preparedStatement.executeUpdate();
//            
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
    }
    
    public void updateHocSinh(HocSinh hocSinh){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "update hocsinh set HOTEN = ?, NGAYSINH = ?, DIACHI = ?, SODIENTHOAIPHUHUYNH = ?, MALOP = ? where mahocsinh = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, hocSinh.getHoTen());
            preparedStatement.setDate(2, new java.sql.Date(hocSinh.getNgaySinh().getTime()));
            preparedStatement.setString(3, hocSinh.getDiaChi());
            preparedStatement.setString(4, hocSinh.getSdtPhuHuynh());
            preparedStatement.setInt(5, hocSinh.getLop().getMaLop());
            preparedStatement.setString(6, hocSinh.getMaHocSinh());
            
            int res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    public void deleteHocSinh(String maHocSinh){
        Connection connection = null;

    try {
        connection = JDBCConnection.getJDBCConnection();

        // Chuẩn bị câu lệnh gọi stored procedure
        String sql = "CALL delete_hoc_sinh(?)";
        
        // Thiết lập tham số
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, maHocSinh);
        
        
        // Thực thi stored procedure
        preparedStatement.execute();
        System.out.print("thuc thi thanh cong");

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//        Connection connection = JDBCConnection.getJDBCConnection();
//
//        String sql = "delete from hocsinh where mahocsinh = ?";
//        
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, maHocSinh);
//            
//            int res = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
    }
    
    public List<HocSinh> timKiemHocSinh(String maHocSinh, String hoTen){
        List<HocSinh> dsHocSinh = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        
        String sql = "select * from hocsinh h join lop l on l.malop = h.malop where h.mahocsinh = ? or h.hoten like ?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maHocSinh);
            preparedStatement.setString(2,"%" + hoTen + "%");
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Lop lop = new Lop(
                    rs.getInt("MALOP"),
                    rs.getString("TENLOP"),
                    rs.getString("NIENKHOA"));
                HocSinh hocsinh = new HocSinh(
                rs.getString("MAHOCSINH"),
                rs.getString("HOTEN"),
                rs.getDate("NGAYSINH"),
                rs.getString("DIACHI"),
                rs.getString("SODIENTHOAIPHUHUYNH"),
                lop);
                dsHocSinh.add(hocsinh);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return dsHocSinh;
    }
}
