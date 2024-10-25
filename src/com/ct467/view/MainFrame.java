package com.ct467.view;

import com.ct467.model.ChuNhiem;
import com.ct467.model.Diem;
import com.ct467.model.HocSinh;
import com.ct467.model.GiaoVien;
import com.ct467.model.Lop;
import com.ct467.model.TaiKhoan;
import com.ct467.model.PhongLop;
import com.ct467.service.ChuNhiemService;
import com.ct467.service.DiemService;

import com.ct467.service.GiaoVienService;
import com.ct467.service.HocSinhService;
import com.ct467.service.LopService;
import com.ct467.service.PhongLopService;
import com.ct467.service.TaiKhoanService;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



public class MainFrame extends javax.swing.JFrame {
    private HocSinhService hocSinhService = new HocSinhService();
    private GiaoVienService giaoVienService = new GiaoVienService();
    private ChuNhiemService chuNhiemService = new ChuNhiemService();
    private PhongLopService phongLopService = new PhongLopService();
    private DiemService diemService = new DiemService();
    private TaiKhoanService taiKhoanService = new TaiKhoanService();
    private LopService lopService = new LopService();
    
    DefaultTableModel defaultHocSinhTableModel, defaultGiaoVienTableModel, defaultChuNhiemTableModel, defaultLopTableModel,
                      defaultDiemTableModel, defaultPhongLopTableModel, defaultThongKeTableModel, defaultTaiKhoanTableModel;
    private ListSelectionListener selectionHocSinhListener, selectionGiaoVienListener, selectionChuNhiemListener, 
                                  selectionLopListener, selectionPhongLopListener, selectionDiemListener, selectionThongKeListener, selectionTaiKhoanListener; 
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    public MainFrame() {
        initComponents();
        
        boby.setUI(new BasicTabbedPaneUI() {
            @Override
            protected int calculateTabWidth(
                    int tabPlacement, int tabIndex, FontMetrics metrics) {
                return 200;
            }
            
            @Override
            protected int calculateTabHeight(
                    int tabPlacement, int tabIndex, int fontHeight) {
                return 40;
            }
            
            @Override
            protected void paintText​(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected){
                Font customFont = new Font(font.getName(), font.getStyle(), 16);
                g.setFont(customFont);
                g.setColor(isSelected ? new Color(255, 255, 255): new Color(96, 96, 96));
                int x = textRect.x + (textRect.width - metrics.stringWidth(title)) / 2;
                int y = textRect.y + (textRect.height + metrics.getAscent()) / 2;
                g.drawString(title, x-10, y);
            }
            @Override
            protected void paintTabBackground​(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected){
                g.setColor(isSelected ? new Color(96, 96, 96) : new Color(224, 224, 224));
                g.fillRect(x, y, w, h);
            }
        });

    }
    
    public void dienBangHS(){
        defaultHocSinhTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        hocSinhTable.setModel(defaultHocSinhTableModel);
        
        defaultHocSinhTableModel.addColumn("Mã Học sinh");
        defaultHocSinhTableModel.addColumn("Họ Và Tên");
        defaultHocSinhTableModel.addColumn("Ngày Sinh");
        defaultHocSinhTableModel.addColumn("Địa Chỉ");
        defaultHocSinhTableModel.addColumn("SĐT Phụ Huynh");
        defaultHocSinhTableModel.addColumn("Tên Lớp");
        
        JTableHeader header = hocSinhTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        hocSinhTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        hocSinhTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        hocSinhTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        hocSinhTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        hocSinhTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        hocSinhTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        hocSinhTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        hocSinhTable.getColumnModel().getColumn(5).setPreferredWidth(20);
        List<HocSinh> dsHocSinh = this.hocSinhService.getDSHocSinh();
        for(HocSinh hocSinh : dsHocSinh){
            defaultHocSinhTableModel.addRow(new Object[]{
                hocSinh.getMaHocSinh(),
                hocSinh.getHoTen(),
                formatter.format(hocSinh.getNgaySinh()),
                hocSinh.getDiaChi(),
                hocSinh.getSdtPhuHuynh(),
                hocSinh.getLop().getTenLop()
            });
        }
        
        ListSelectionModel selectionModel =  hocSinhTable.getSelectionModel();
        selectionHocSinhListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = hocSinhTable.getSelectedRow();
                maHocSinhInput.setText(String.valueOf(hocSinhTable.getValueAt(row, 0)));
                hoTenInput.setText(String.valueOf(hocSinhTable.getValueAt(row, 1)));
                ngaySinhInput.setText(String.valueOf(hocSinhTable.getValueAt(row, 2)));
                diaChiInput.setText(String.valueOf(hocSinhTable.getValueAt(row, 3)));
                sdtPhuHuynhInput.setText(String.valueOf(hocSinhTable.getValueAt(row, 4)));
                maLopList.setSelectedItem((lopService.getLopByTen((String)hocSinhTable.getValueAt(row, 5))));
                themHocSinhBtn.setEnabled(false);
                maHocSinhInput.setEditable(false);
            }

        };
        selectionModel.addListSelectionListener(selectionHocSinhListener);
    }
    
    public void dienBangGV(){
        defaultGiaoVienTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        giaoVienTable.setModel(defaultGiaoVienTableModel);
        
        defaultGiaoVienTableModel.addColumn("Mã Giáo Viên");
        defaultGiaoVienTableModel.addColumn("Họ Và Tên");
        defaultGiaoVienTableModel.addColumn("Ngày Sinh");
        defaultGiaoVienTableModel.addColumn("Địa Chỉ");
        defaultGiaoVienTableModel.addColumn("Số Điện Thoại");
        
        JTableHeader header = giaoVienTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        giaoVienTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        giaoVienTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        giaoVienTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        giaoVienTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        
        giaoVienTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        giaoVienTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        giaoVienTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        
        List<GiaoVien> dsGiaoVien = this.giaoVienService.getDSGiaoVien();
        for(GiaoVien giaoVien : dsGiaoVien){
            defaultGiaoVienTableModel.addRow(new Object[]{
                giaoVien.getMaGiaoVien(),
                giaoVien.getHoTen(),
                formatter.format(giaoVien.getNgaySinh()),
                giaoVien.getDiaChi(),
                giaoVien.getSoDienThoai(),
            });
        }
        
        ListSelectionModel selectionModel =  giaoVienTable.getSelectionModel();
        selectionGiaoVienListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = giaoVienTable.getSelectedRow();
                maGiaoVienInput.setText(String.valueOf(giaoVienTable.getValueAt(row, 0)));
                hoTenGVInput.setText(String.valueOf(giaoVienTable.getValueAt(row, 1)));
                ngaySinhGVInput.setText(String.valueOf(giaoVienTable.getValueAt(row, 2)));
                diaChiGVInput.setText(String.valueOf(giaoVienTable.getValueAt(row, 3)));
                sdtGVInput.setText(String.valueOf(giaoVienTable.getValueAt(row, 4)));
                themGVBtn.setEnabled(false);
                maGiaoVienInput.setEditable(false);
            }

        };
        selectionModel.addListSelectionListener(selectionGiaoVienListener);
    }

    public void dienBangCN(){
        defaultChuNhiemTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        chuNhiemTable.setModel(defaultChuNhiemTableModel);
        defaultChuNhiemTableModel.addColumn("Mã lớp");
        defaultChuNhiemTableModel.addColumn("Tên lớp");
        defaultChuNhiemTableModel.addColumn("Mã giáo viên");
        defaultChuNhiemTableModel.addColumn("Tên giáo viên");
        defaultChuNhiemTableModel.addColumn("Năm học");
        
        JTableHeader header = chuNhiemTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        chuNhiemTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        chuNhiemTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        chuNhiemTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        chuNhiemTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);         

        List<ChuNhiem> dsChuNhiem = this.chuNhiemService.getDSChuNhiem();
        for(ChuNhiem chuNhiem : dsChuNhiem){
            defaultChuNhiemTableModel.addRow(new Object[]{
                chuNhiem.getLop().getMaLop(),
                chuNhiem.getLop().getTenLop(),               
                chuNhiem.getGiaoVien().getMaGiaoVien(),
                chuNhiem.getGiaoVien().getHoTen(),
                chuNhiem.getNamHoc(),
            });
        }
        
        ListSelectionModel selectionModel =  chuNhiemTable.getSelectionModel();
        selectionChuNhiemListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = chuNhiemTable.getSelectedRow();
                maLopCNInput.setSelectedItem((lopService.getLop((Integer)chuNhiemTable.getValueAt(row, 0))));
                maGVCNInput.setSelectedItem(giaoVienService.getGiaoVien((Integer) chuNhiemTable.getValueAt(row, 2)));
                namHocCNInput.setText(String.valueOf(chuNhiemTable.getValueAt(row, 4)));
                themCNBtn.setEnabled(false);
            }

        };
        selectionModel.addListSelectionListener(selectionChuNhiemListener);
    }
    
    public void dienBangPL(){
        defaultPhongLopTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        phongLopTable.setModel(defaultPhongLopTableModel);
        defaultPhongLopTableModel.addColumn("Số phòng");
        defaultPhongLopTableModel.addColumn("Mã lớp");
        defaultPhongLopTableModel.addColumn("Tên lớp");
        defaultPhongLopTableModel.addColumn("Học kì - Năm học");
        
        JTableHeader header = phongLopTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        phongLopTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        phongLopTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        phongLopTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        phongLopTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        List<PhongLop> dsPhongLop = this.phongLopService.getDSPhongLop();
        for(PhongLop phongLop : dsPhongLop){
            defaultPhongLopTableModel.addRow(new Object[]{
                phongLop.getPhongHoc().getSoPhong(),
                phongLop.getLop().getMaLop(),
                phongLop.getLop().getTenLop(),               
                phongLop.getHocKi_namHoc(),
            });
        }
        
        ListSelectionModel selectionModel =  phongLopTable.getSelectionModel();
        selectionPhongLopListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = phongLopTable.getSelectedRow();
                soPhongPLInput.setText(String.valueOf(phongLopTable.getValueAt(row, 0)));
                maLopPLInput.setText(String.valueOf(phongLopTable.getValueAt(row, 1)));
                hKNHPLInput.setText(String.valueOf(phongLopTable.getValueAt(row, 3)));
                themPhongLopBtn.setEnabled(false);
            }

        };
        selectionModel.addListSelectionListener(selectionPhongLopListener);
    }
    
    public void dienBangDiem(){
        defaultDiemTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        diemTable.setModel(defaultDiemTableModel);
        defaultDiemTableModel.addColumn("Mã học sinh");
        defaultDiemTableModel.addColumn("Mã môn");
        defaultDiemTableModel.addColumn("Điểm miệng");
        defaultDiemTableModel.addColumn("Điểm 15 phút");
        defaultDiemTableModel.addColumn("Điểm 1 tiết");
        defaultDiemTableModel.addColumn("Điểm thi");
        
        JTableHeader header = diemTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        diemTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        diemTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        diemTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        diemTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        diemTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        diemTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        List<Diem> dsDiem = this.diemService.getDSDiem();
        for(Diem diem : dsDiem){
            defaultDiemTableModel.addRow(new Object[]{
                diem.getMaHocSinh(),
                diem.getMaMonHoc(),
                diem.getDiemMieng(),               
                diem.getDiem15Phut(),
                diem.getDiem1Tiet(),
                diem.getDiemThi(),
            });
        }
        
        ListSelectionModel selectionModel =  diemTable.getSelectionModel();
        selectionDiemListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = diemTable.getSelectedRow();
                soPhongPLInput.setText(String.valueOf(phongLopTable.getValueAt(row, 0)));
                maLopPLInput.setText(String.valueOf(phongLopTable.getValueAt(row, 1)));
                hKNHPLInput.setText(String.valueOf(phongLopTable.getValueAt(row, 3)));
                themPhongLopBtn.setEnabled(false);
            }

        };
        selectionModel.addListSelectionListener(selectionDiemListener);
    }
    
    public void dienBangTK(){
        defaultTaiKhoanTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        taiKhoanTable.setModel(defaultTaiKhoanTableModel);
        defaultTaiKhoanTableModel.addColumn("Username");
        defaultTaiKhoanTableModel.addColumn("Password");
        defaultTaiKhoanTableModel.addColumn("Vai trò");
        defaultTaiKhoanTableModel.addColumn("Mã giáo viên");
        defaultTaiKhoanTableModel.addColumn("Mã học sinh");
        
        JTableHeader header = taiKhoanTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        taiKhoanTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        taiKhoanTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        taiKhoanTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        taiKhoanTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        taiKhoanTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        List<TaiKhoan> dsTaiKhoan = this.taiKhoanService.getDSTaiKhoan();
        for(TaiKhoan taiKhoan : dsTaiKhoan){
            defaultTaiKhoanTableModel.addRow(new Object[]{
                taiKhoan.getUsername(),
                taiKhoan.getPasword(),
                taiKhoan.getVaiTro(),
                taiKhoan.getMaGiaoVien(),
                taiKhoan.getMaHocSinh()
            });
        }
        
        ListSelectionModel selectionModel =  taiKhoanTable.getSelectionModel();
        selectionTaiKhoanListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = taiKhoanTable.getSelectedRow();
                soPhongPLInput.setText(String.valueOf(taiKhoanTable.getValueAt(row, 0)));
                maLopPLInput.setText(String.valueOf(taiKhoanTable.getValueAt(row, 1)));
                hKNHPLInput.setText(String.valueOf(taiKhoanTable.getValueAt(row, 3)));
                themPhongLopBtn.setEnabled(false);
            }

        };
        selectionModel.addListSelectionListener(selectionTaiKhoanListener);
    }
    
    public void resetHSInput(){
        maHocSinhInput.setText("");
        hoTenInput.setText("");
        ngaySinhInput.setText("");
        diaChiInput.setText("");
        sdtPhuHuynhInput.setText("");
        maLopList.setSelectedIndex(0);
        themHocSinhBtn.setEnabled(true);
        maHocSinhInput.setEditable(true);
    }
    
    public void resetGVInput(){
        maGiaoVienInput.setText("");
        hoTenGVInput.setText("");
        ngaySinhGVInput.setText("");
        diaChiGVInput.setText("");
        sdtGVInput.setText("");
        maGiaoVienInput.setEditable(true);
        themGVBtn.setEnabled(true);   
    }
    
    public void resetCNInput(){
        maLopCNInput.setSelectedIndex(0);
        maGVCNInput.setSelectedIndex(0);
        namHocCNInput.setText("");
        maLopCNInput.setEnabled(true);
        maGVCNInput.setEnabled(true);
        themCNBtn.setEnabled(true);
    }
    
    public void resetLopInput(){
        soPhongPLInput.setText("");
        maLopPLInput.setText("");
        hKNHPLInput.setText("");
        themPhongLopBtn.setEnabled(true);
        soPhongPLInput.setEditable(true);
    }
    
    public void resetPLInput(){
        soPhongPLInput.setText("");
        maLopPLInput.setText("");
        hKNHPLInput.setText("");
        themPhongLopBtn.setEnabled(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        boby = new javax.swing.JTabbedPane();
        hocSinhTab = new javax.swing.JPanel();
        thongTin_TimKiem = new javax.swing.JPanel();
        thongTinHocSinh = new javax.swing.JPanel();
        maHocSinhLabel = new javax.swing.JLabel();
        hoTenLabel = new javax.swing.JLabel();
        ngaySinhLabel = new javax.swing.JLabel();
        diaChiLabel = new javax.swing.JLabel();
        sdtPhuHuynhLabel = new javax.swing.JLabel();
        maLopHSLabel = new javax.swing.JLabel();
        maHocSinhInput = new javax.swing.JTextField();
        hoTenInput = new javax.swing.JTextField();
        sdtPhuHuynhInput = new javax.swing.JTextField();
        diaChiInput = new javax.swing.JTextField();
        ngaySinhInput = new javax.swing.JFormattedTextField();
        maLopList = new javax.swing.JComboBox<>();
        List<Lop> dsLop = this.lopService.getDSLop();
        for(Lop lop : dsLop) {
            maLopList.addItem(lop);
        }
        themHocSinhBtn = new javax.swing.JButton();
        suaHocSinhBtn = new javax.swing.JButton();
        xoaHocSinhBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        timKiemHocSinh = new javax.swing.JPanel();
        hoTenHSTimKiemLabel = new javax.swing.JLabel();
        hoTenHSTimKiemInput = new javax.swing.JTextField();
        maHSTimKiemLabel = new javax.swing.JLabel();
        maHSTimKiemInput = new javax.swing.JTextField();
        timKiemBtn = new javax.swing.JButton();
        resetBangHSBtn = new javax.swing.JButton();
        danhSachHocSinh = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        hocSinhTable = new javax.swing.JTable();
        danhSachHocSinhLabel = new javax.swing.JLabel();
        giaoVienTab = new javax.swing.JPanel();
        thongTin_TimKiemGV = new javax.swing.JPanel();
        thongTinGiaoVien = new javax.swing.JPanel();
        maGiaoVienLabel = new javax.swing.JLabel();
        hoTenGVLabel = new javax.swing.JLabel();
        ngaySinhGVLabel = new javax.swing.JLabel();
        diaChiGVLabel = new javax.swing.JLabel();
        sdtGVLabel = new javax.swing.JLabel();
        maGiaoVienInput = new javax.swing.JTextField();
        hoTenGVInput = new javax.swing.JTextField();
        sdtGVInput = new javax.swing.JTextField();
        diaChiGVInput = new javax.swing.JTextField();
        ngaySinhGVInput = new javax.swing.JFormattedTextField();
        themGVBtn = new javax.swing.JButton();
        suaGVBtn = new javax.swing.JButton();
        xoaGVBtn = new javax.swing.JButton();
        resetInputGVBtn = new javax.swing.JButton();
        timKiemGiaoVien = new javax.swing.JPanel();
        hoTenGVTimKiemLabel = new javax.swing.JLabel();
        hoTenGVTimKiemInput = new javax.swing.JTextField();
        maGVTimKiemLabel = new javax.swing.JLabel();
        maGVTimKiemInput = new javax.swing.JTextField();
        timKiemGVBtn = new javax.swing.JButton();
        resetBangGVBtn = new javax.swing.JButton();
        danhSachGiaoVien = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        giaoVienTable = new javax.swing.JTable();
        danhSachGiaoVienLabel = new javax.swing.JLabel();
        chuNhiemTab = new javax.swing.JPanel();
        thongTin_TimKiemCN = new javax.swing.JPanel();
        thongTinChuNhiem = new javax.swing.JPanel();
        maLopCNLabel = new javax.swing.JLabel();
        maGVCNLabel = new javax.swing.JLabel();
        namHocCNLabel = new javax.swing.JLabel();
        namHocCNInput = new javax.swing.JTextField();
        themCNBtn = new javax.swing.JButton();
        xoaCNBtn = new javax.swing.JButton();
        resetInputCNBtn = new javax.swing.JButton();
        maGVCNInput = new javax.swing.JComboBox<>();
        List<GiaoVien> dsGVCNInput = this.giaoVienService.getDSGiaoVien();
        for(GiaoVien giaoVien : dsGVCNInput) {
            maGVCNInput.addItem(giaoVien);
        }
        maLopCNInput = new javax.swing.JComboBox<>();
        List<Lop> dsLopCNInput = this.lopService.getDSLop();
        for(Lop lop : dsLopCNInput) {
            maLopCNInput.addItem(lop);
        }
        timKiemChuNhiem = new javax.swing.JPanel();
        maGVCNTimKiemLabel = new javax.swing.JLabel();
        maLopCNTimKiemLabel = new javax.swing.JLabel();
        timKiemCNBtn = new javax.swing.JButton();
        resetBangCNBtn = new javax.swing.JButton();
        namHocCNTimKiemLabel = new javax.swing.JLabel();
        namHocCNTimKiemInput = new javax.swing.JTextField();
        maLopCNTimKiemnput = new javax.swing.JTextField();
        maGVCNTimKiemInput = new javax.swing.JTextField();
        danhSachChuNhiem = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        chuNhiemTable = new javax.swing.JTable();
        danhSachChuNhiemLabel = new javax.swing.JLabel();
        phongHocTab = new javax.swing.JPanel();
        thongTin_TimKiemPhongHoc = new javax.swing.JPanel();
        thongTinPhongHoc = new javax.swing.JPanel();
        sọPhongPLLabel = new javax.swing.JLabel();
        maLopPLLabel = new javax.swing.JLabel();
        hKNHPLLabel = new javax.swing.JLabel();
        hKNHPLInput = new javax.swing.JTextField();
        themPhongLopBtn = new javax.swing.JButton();
        suaPhongLopBtn = new javax.swing.JButton();
        xoaPhongLopBtn = new javax.swing.JButton();
        resetInputPhongLopBtn = new javax.swing.JButton();
        maLopPLInput = new javax.swing.JTextField();
        soPhongPLInput = new javax.swing.JTextField();
        timKiemPhongHoc = new javax.swing.JPanel();
        soPhongTimKiemLabel = new javax.swing.JLabel();
        timKiemPhongHocBtn = new javax.swing.JButton();
        resetBangPhongHocBtn = new javax.swing.JButton();
        nienKhoaLopTimKiemLabel = new javax.swing.JLabel();
        hKNHTimKiemInput = new javax.swing.JTextField();
        soPhongTimKiemInput = new javax.swing.JTextField();
        danhSachLop = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        phongLopTable = new javax.swing.JTable();
        danhSachLopLabel = new javax.swing.JLabel();
        diemTab = new javax.swing.JPanel();
        thongTin_TimKiemPhongHoc1 = new javax.swing.JPanel();
        thongTinPhongHoc1 = new javax.swing.JPanel();
        sọPhongLabel1 = new javax.swing.JLabel();
        tenLopLabel1 = new javax.swing.JLabel();
        nienKhoaLopLabel1 = new javax.swing.JLabel();
        hKNHInput1 = new javax.swing.JTextField();
        themPhongHocBtn1 = new javax.swing.JButton();
        suaPhongHocBtn1 = new javax.swing.JButton();
        xoaPhongHocBtn1 = new javax.swing.JButton();
        resetInputPhongHocBtn1 = new javax.swing.JButton();
        maLopPHInput1 = new javax.swing.JTextField();
        soPhongInput1 = new javax.swing.JTextField();
        timKiemPhongHoc1 = new javax.swing.JPanel();
        soPhongTimKiemLabel1 = new javax.swing.JLabel();
        timKiemPhongHocBtn1 = new javax.swing.JButton();
        resetBangPhongHocBtn1 = new javax.swing.JButton();
        nienKhoaLopTimKiemLabel1 = new javax.swing.JLabel();
        hKNHTimKiemInput1 = new javax.swing.JTextField();
        soPhongTimKiemInput1 = new javax.swing.JTextField();
        danhSachLop1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        diemTable = new javax.swing.JTable();
        danhSachLopLabel1 = new javax.swing.JLabel();
        thongKeTab = new javax.swing.JPanel();
        thongTin_TimKiemPhongHoc2 = new javax.swing.JPanel();
        thongTinPhongHoc2 = new javax.swing.JPanel();
        sọPhongLabel2 = new javax.swing.JLabel();
        tenLopLabel2 = new javax.swing.JLabel();
        nienKhoaLopLabel2 = new javax.swing.JLabel();
        hKNHInput2 = new javax.swing.JTextField();
        themPhongHocBtn2 = new javax.swing.JButton();
        suaPhongHocBtn2 = new javax.swing.JButton();
        xoaPhongHocBtn2 = new javax.swing.JButton();
        resetInputPhongHocBtn2 = new javax.swing.JButton();
        maLopPHInput2 = new javax.swing.JTextField();
        soPhongInput2 = new javax.swing.JTextField();
        timKiemPhongHoc2 = new javax.swing.JPanel();
        soPhongTimKiemLabel2 = new javax.swing.JLabel();
        timKiemPhongHocBtn2 = new javax.swing.JButton();
        resetBangPhongHocBtn2 = new javax.swing.JButton();
        nienKhoaLopTimKiemLabel2 = new javax.swing.JLabel();
        hKNHTimKiemInput2 = new javax.swing.JTextField();
        soPhongTimKiemInput2 = new javax.swing.JTextField();
        danhSachLop2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        thongKeTable = new javax.swing.JTable();
        danhSachLopLabel2 = new javax.swing.JLabel();
        taiKhoanTab = new javax.swing.JPanel();
        thongTin_TimKiemPhongHoc3 = new javax.swing.JPanel();
        thongTinPhongHoc3 = new javax.swing.JPanel();
        sọPhongLabel3 = new javax.swing.JLabel();
        tenLopLabel3 = new javax.swing.JLabel();
        nienKhoaLopLabel3 = new javax.swing.JLabel();
        hKNHInput3 = new javax.swing.JTextField();
        themPhongHocBtn3 = new javax.swing.JButton();
        suaPhongHocBtn3 = new javax.swing.JButton();
        xoaPhongHocBtn3 = new javax.swing.JButton();
        resetInputPhongHocBtn3 = new javax.swing.JButton();
        maLopPHInput3 = new javax.swing.JTextField();
        soPhongInput3 = new javax.swing.JTextField();
        timKiemPhongHoc3 = new javax.swing.JPanel();
        soPhongTimKiemLabel3 = new javax.swing.JLabel();
        timKiemPhongHocBtn3 = new javax.swing.JButton();
        resetBangPhongHocBtn3 = new javax.swing.JButton();
        nienKhoaLopTimKiemLabel3 = new javax.swing.JLabel();
        hKNHTimKiemInput3 = new javax.swing.JTextField();
        soPhongTimKiemInput3 = new javax.swing.JTextField();
        danhSachLop3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        taiKhoanTable = new javax.swing.JTable();
        danhSachLopLabel3 = new javax.swing.JLabel();
        header = new javax.swing.JMenuBar();
        taikhoanHeader = new javax.swing.JMenu();
        troVeHeader = new javax.swing.JMenu();
        thoatHeader = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hệ Thống Quản Lý Trường THPT XYZ");

        boby.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        boby.setPreferredSize(new java.awt.Dimension(500, 500));

        hocSinhTab.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        hocSinhTab.setLayout(new java.awt.BorderLayout());

        thongTin_TimKiem.setPreferredSize(new java.awt.Dimension(350, 615));
        thongTin_TimKiem.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        thongTinHocSinh.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin học sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        thongTinHocSinh.setPreferredSize(new java.awt.Dimension(340, 450));

        maHocSinhLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maHocSinhLabel.setText("Mã học sinh");

        hoTenLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hoTenLabel.setText("Họ và tên");

        ngaySinhLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ngaySinhLabel.setText("Ngày sinh");

        diaChiLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        diaChiLabel.setText("Địa chỉ");

        sdtPhuHuynhLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sdtPhuHuynhLabel.setText("SĐT phụ huynh");

        maLopHSLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopHSLabel.setText("Mã lớp");

        maHocSinhInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maHocSinhInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maHocSinhInputActionPerformed(evt);
            }
        });

        hoTenInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hoTenInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoTenInputActionPerformed(evt);
            }
        });

        sdtPhuHuynhInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sdtPhuHuynhInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sdtPhuHuynhInputActionPerformed(evt);
            }
        });

        diaChiInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        diaChiInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaChiInputActionPerformed(evt);
            }
        });

        ngaySinhInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        ngaySinhInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngaySinhInputActionPerformed(evt);
            }
        });

        maLopList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maLopListActionPerformed(evt);
            }
        });

        themHocSinhBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        themHocSinhBtn.setText("Thêm");
        themHocSinhBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themHocSinhBtnActionPerformed(evt);
            }
        });

        suaHocSinhBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        suaHocSinhBtn.setText("Sửa");
        suaHocSinhBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaHocSinhBtnActionPerformed(evt);
            }
        });

        xoaHocSinhBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoaHocSinhBtn.setText("Xóa");
        xoaHocSinhBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaHocSinhBtnActionPerformed(evt);
            }
        });

        resetBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetBtn.setText("Đặt lại");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thongTinHocSinhLayout = new javax.swing.GroupLayout(thongTinHocSinh);
        thongTinHocSinh.setLayout(thongTinHocSinhLayout);
        thongTinHocSinhLayout.setHorizontalGroup(
            thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinHocSinhLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xoaHocSinhBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themHocSinhBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaHocSinhBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(thongTinHocSinhLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinHocSinhLayout.createSequentialGroup()
                        .addComponent(maLopHSLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(thongTinHocSinhLayout.createSequentialGroup()
                        .addComponent(sdtPhuHuynhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sdtPhuHuynhInput))))
            .addGroup(thongTinHocSinhLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ngaySinhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hoTenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maHocSinhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diaChiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinHocSinhLayout.createSequentialGroup()
                        .addComponent(maLopList, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 105, Short.MAX_VALUE))
                    .addComponent(maHocSinhInput)
                    .addComponent(hoTenInput)
                    .addComponent(ngaySinhInput)
                    .addComponent(diaChiInput)))
        );
        thongTinHocSinhLayout.setVerticalGroup(
            thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinHocSinhLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maHocSinhInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maHocSinhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hoTenLabel)
                    .addComponent(hoTenInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ngaySinhLabel)
                    .addComponent(ngaySinhInput, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(diaChiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diaChiInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sdtPhuHuynhLabel)
                    .addComponent(sdtPhuHuynhInput, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maLopHSLabel)
                    .addComponent(maLopList, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themHocSinhBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaHocSinhBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(thongTinHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xoaHocSinhBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        thongTin_TimKiem.add(thongTinHocSinh);

        timKiemHocSinh.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm học sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N

        hoTenHSTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hoTenHSTimKiemLabel.setText("Họ tên");

        hoTenHSTimKiemInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoTenHSTimKiemInputActionPerformed(evt);
            }
        });

        maHSTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maHSTimKiemLabel.setText("Mã học sinh");

        maHSTimKiemInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maHSTimKiemInputActionPerformed(evt);
            }
        });

        timKiemBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timKiemBtn.setText("Tìm kiếm");
        timKiemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemBtnActionPerformed(evt);
            }
        });

        resetBangHSBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetBangHSBtn.setText("Reset");
        resetBangHSBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBangHSBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout timKiemHocSinhLayout = new javax.swing.GroupLayout(timKiemHocSinh);
        timKiemHocSinh.setLayout(timKiemHocSinhLayout);
        timKiemHocSinhLayout.setHorizontalGroup(
            timKiemHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemHocSinhLayout.createSequentialGroup()
                .addGroup(timKiemHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timKiemHocSinhLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(timKiemHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(timKiemHocSinhLayout.createSequentialGroup()
                                .addComponent(hoTenHSTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hoTenHSTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(timKiemHocSinhLayout.createSequentialGroup()
                                .addComponent(maHSTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(maHSTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(timKiemHocSinhLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(timKiemBtn)
                        .addGap(47, 47, 47)
                        .addComponent(resetBangHSBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        timKiemHocSinhLayout.setVerticalGroup(
            timKiemHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemHocSinhLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(timKiemHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maHSTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maHSTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(timKiemHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hoTenHSTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hoTenHSTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(timKiemHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timKiemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBangHSBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        thongTin_TimKiem.add(timKiemHocSinh);

        hocSinhTab.add(thongTin_TimKiem, java.awt.BorderLayout.LINE_END);

        hocSinhTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hocSinhTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        hocSinhTable.setToolTipText("");
        hocSinhTable.setRowHeight(30);
        hocSinhTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(hocSinhTable);

        danhSachHocSinhLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        danhSachHocSinhLabel.setText("Danh Sách Học Sinh");

        javax.swing.GroupLayout danhSachHocSinhLayout = new javax.swing.GroupLayout(danhSachHocSinh);
        danhSachHocSinh.setLayout(danhSachHocSinhLayout);
        danhSachHocSinhLayout.setHorizontalGroup(
            danhSachHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, danhSachHocSinhLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(danhSachHocSinhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        danhSachHocSinhLayout.setVerticalGroup(
            danhSachHocSinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(danhSachHocSinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(danhSachHocSinhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        hocSinhTab.add(danhSachHocSinh, java.awt.BorderLayout.CENTER);

        boby.addTab("Học sinh", hocSinhTab);

        giaoVienTab.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        giaoVienTab.setLayout(new java.awt.BorderLayout());

        thongTin_TimKiemGV.setPreferredSize(new java.awt.Dimension(350, 615));
        thongTin_TimKiemGV.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        thongTinGiaoVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin học sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        thongTinGiaoVien.setPreferredSize(new java.awt.Dimension(340, 450));

        maGiaoVienLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maGiaoVienLabel.setText("Mã giáo viên");

        hoTenGVLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hoTenGVLabel.setText("Họ và tên");

        ngaySinhGVLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ngaySinhGVLabel.setText("Ngày sinh");

        diaChiGVLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        diaChiGVLabel.setText("Địa chỉ");

        sdtGVLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sdtGVLabel.setText("Số điện thoại");

        maGiaoVienInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maGiaoVienInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maGiaoVienInputActionPerformed(evt);
            }
        });

        hoTenGVInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hoTenGVInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoTenGVInputActionPerformed(evt);
            }
        });

        sdtGVInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sdtGVInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sdtGVInputActionPerformed(evt);
            }
        });

        diaChiGVInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        diaChiGVInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaChiGVInputActionPerformed(evt);
            }
        });

        ngaySinhGVInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        ngaySinhGVInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngaySinhGVInputActionPerformed(evt);
            }
        });

        themGVBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        themGVBtn.setText("Thêm");
        themGVBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themGVBtnActionPerformed(evt);
            }
        });

        suaGVBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        suaGVBtn.setText("Sửa");
        suaGVBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaGVBtnActionPerformed(evt);
            }
        });

        xoaGVBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoaGVBtn.setText("Xóa");
        xoaGVBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaGVBtnActionPerformed(evt);
            }
        });

        resetInputGVBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetInputGVBtn.setText("Đặt lại");
        resetInputGVBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetInputGVBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thongTinGiaoVienLayout = new javax.swing.GroupLayout(thongTinGiaoVien);
        thongTinGiaoVien.setLayout(thongTinGiaoVienLayout);
        thongTinGiaoVienLayout.setHorizontalGroup(
            thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinGiaoVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sdtGVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sdtGVInput, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
            .addGroup(thongTinGiaoVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ngaySinhGVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hoTenGVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maGiaoVienLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diaChiGVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(maGiaoVienInput)
                    .addComponent(hoTenGVInput)
                    .addComponent(ngaySinhGVInput)
                    .addComponent(diaChiGVInput)))
            .addGroup(thongTinGiaoVienLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xoaGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resetInputGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        thongTinGiaoVienLayout.setVerticalGroup(
            thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinGiaoVienLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maGiaoVienInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maGiaoVienLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hoTenGVLabel)
                    .addComponent(hoTenGVInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ngaySinhGVLabel)
                    .addComponent(ngaySinhGVInput, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(diaChiGVLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diaChiGVInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sdtGVLabel)
                    .addComponent(sdtGVInput, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(thongTinGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetInputGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xoaGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        thongTin_TimKiemGV.add(thongTinGiaoVien);

        timKiemGiaoVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm giáo viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N

        hoTenGVTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hoTenGVTimKiemLabel.setText("Họ tên");

        hoTenGVTimKiemInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoTenGVTimKiemInputActionPerformed(evt);
            }
        });

        maGVTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maGVTimKiemLabel.setText("Mã giáo viên");

        maGVTimKiemInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maGVTimKiemInputActionPerformed(evt);
            }
        });

        timKiemGVBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timKiemGVBtn.setText("Tìm kiếm");
        timKiemGVBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemGVBtnActionPerformed(evt);
            }
        });

        resetBangGVBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetBangGVBtn.setText("Reset");
        resetBangGVBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBangGVBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout timKiemGiaoVienLayout = new javax.swing.GroupLayout(timKiemGiaoVien);
        timKiemGiaoVien.setLayout(timKiemGiaoVienLayout);
        timKiemGiaoVienLayout.setHorizontalGroup(
            timKiemGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemGiaoVienLayout.createSequentialGroup()
                .addGroup(timKiemGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timKiemGiaoVienLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(timKiemGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maGVTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hoTenGVTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(timKiemGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hoTenGVTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maGVTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(timKiemGiaoVienLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(timKiemGVBtn)
                        .addGap(47, 47, 47)
                        .addComponent(resetBangGVBtn)))
                .addGap(2, 2, 2))
        );
        timKiemGiaoVienLayout.setVerticalGroup(
            timKiemGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemGiaoVienLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(timKiemGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maGVTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maGVTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(timKiemGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hoTenGVTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hoTenGVTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(timKiemGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timKiemGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBangGVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        thongTin_TimKiemGV.add(timKiemGiaoVien);

        giaoVienTab.add(thongTin_TimKiemGV, java.awt.BorderLayout.LINE_END);

        giaoVienTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        giaoVienTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        giaoVienTable.setToolTipText("");
        giaoVienTable.setRowHeight(30);
        giaoVienTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(giaoVienTable);

        danhSachGiaoVienLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        danhSachGiaoVienLabel.setText("Danh Sách Giáo Viên");

        javax.swing.GroupLayout danhSachGiaoVienLayout = new javax.swing.GroupLayout(danhSachGiaoVien);
        danhSachGiaoVien.setLayout(danhSachGiaoVienLayout);
        danhSachGiaoVienLayout.setHorizontalGroup(
            danhSachGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, danhSachGiaoVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(danhSachGiaoVienLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        danhSachGiaoVienLayout.setVerticalGroup(
            danhSachGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(danhSachGiaoVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(danhSachGiaoVienLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        giaoVienTab.add(danhSachGiaoVien, java.awt.BorderLayout.CENTER);

        boby.addTab("Giáo Viên", giaoVienTab);

        chuNhiemTab.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chuNhiemTab.setLayout(new java.awt.BorderLayout());

        thongTin_TimKiemCN.setPreferredSize(new java.awt.Dimension(350, 615));
        thongTin_TimKiemCN.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 32));

        thongTinChuNhiem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chủ nhiệm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        thongTinChuNhiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        thongTinChuNhiem.setPreferredSize(new java.awt.Dimension(340, 350));

        maLopCNLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopCNLabel.setText("Lớp");

        maGVCNLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maGVCNLabel.setText("Giáo Viên");

        namHocCNLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        namHocCNLabel.setText("Năm Học");

        namHocCNInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        namHocCNInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namHocCNInputActionPerformed(evt);
            }
        });

        themCNBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        themCNBtn.setText("Thêm");
        themCNBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themCNBtnActionPerformed(evt);
            }
        });

        xoaCNBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoaCNBtn.setText("Xóa");
        xoaCNBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaCNBtnActionPerformed(evt);
            }
        });

        resetInputCNBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetInputCNBtn.setText("Đặt lại");
        resetInputCNBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetInputCNBtnActionPerformed(evt);
            }
        });

        maGVCNInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        maLopCNInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout thongTinChuNhiemLayout = new javax.swing.GroupLayout(thongTinChuNhiem);
        thongTinChuNhiem.setLayout(thongTinChuNhiemLayout);
        thongTinChuNhiemLayout.setHorizontalGroup(
            thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                        .addGroup(thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                                .addComponent(maGVCNLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(maGVCNInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                                .addComponent(maLopCNLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(maLopCNInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thongTinChuNhiemLayout.createSequentialGroup()
                        .addComponent(namHocCNLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(namHocCNInput)
                        .addContainerGap())))
            .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                .addGroup(thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(resetInputCNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(themCNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(xoaCNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        thongTinChuNhiemLayout.setVerticalGroup(
            thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maLopCNLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maLopCNInput, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(maGVCNLabel))
                    .addGroup(thongTinChuNhiemLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(maGVCNInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(namHocCNInput)
                    .addComponent(namHocCNLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(thongTinChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themCNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xoaCNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(resetInputCNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        thongTin_TimKiemCN.add(thongTinChuNhiem);

        timKiemChuNhiem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm chủ nhiệm\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        timKiemChuNhiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        maGVCNTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maGVCNTimKiemLabel.setText("Mã giáo viên");

        maLopCNTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopCNTimKiemLabel.setText("Mã lớp");

        timKiemCNBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timKiemCNBtn.setText("Tìm kiếm");
        timKiemCNBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemCNBtnActionPerformed(evt);
            }
        });

        resetBangCNBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetBangCNBtn.setText("Reset");
        resetBangCNBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBangCNBtnActionPerformed(evt);
            }
        });

        namHocCNTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        namHocCNTimKiemLabel.setText("Năm học");

        namHocCNTimKiemInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namHocCNTimKiemInputActionPerformed(evt);
            }
        });

        maLopCNTimKiemnput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        maGVCNTimKiemInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout timKiemChuNhiemLayout = new javax.swing.GroupLayout(timKiemChuNhiem);
        timKiemChuNhiem.setLayout(timKiemChuNhiemLayout);
        timKiemChuNhiemLayout.setHorizontalGroup(
            timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemChuNhiemLayout.createSequentialGroup()
                .addGroup(timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timKiemChuNhiemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(namHocCNTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(namHocCNTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(timKiemChuNhiemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maGVCNTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maLopCNTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maLopCNTimKiemnput)
                            .addComponent(maGVCNTimKiemInput)))
                    .addGroup(timKiemChuNhiemLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(timKiemCNBtn)
                        .addGap(47, 47, 47)
                        .addComponent(resetBangCNBtn)))
                .addContainerGap())
        );
        timKiemChuNhiemLayout.setVerticalGroup(
            timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemChuNhiemLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maLopCNTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maLopCNTimKiemnput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maGVCNTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maGVCNTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namHocCNTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namHocCNTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(timKiemChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timKiemCNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBangCNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        thongTin_TimKiemCN.add(timKiemChuNhiem);

        chuNhiemTab.add(thongTin_TimKiemCN, java.awt.BorderLayout.LINE_END);

        chuNhiemTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chuNhiemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        chuNhiemTable.setToolTipText("");
        chuNhiemTable.setRowHeight(30);
        chuNhiemTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(chuNhiemTable);

        danhSachChuNhiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        danhSachChuNhiemLabel.setText("Danh Sách Chủ Nhiệm");

        javax.swing.GroupLayout danhSachChuNhiemLayout = new javax.swing.GroupLayout(danhSachChuNhiem);
        danhSachChuNhiem.setLayout(danhSachChuNhiemLayout);
        danhSachChuNhiemLayout.setHorizontalGroup(
            danhSachChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, danhSachChuNhiemLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(danhSachChuNhiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        danhSachChuNhiemLayout.setVerticalGroup(
            danhSachChuNhiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(danhSachChuNhiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(danhSachChuNhiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        chuNhiemTab.add(danhSachChuNhiem, java.awt.BorderLayout.CENTER);

        boby.addTab("Chủ Nhiệm", chuNhiemTab);

        phongHocTab.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        phongHocTab.setLayout(new java.awt.BorderLayout());

        thongTin_TimKiemPhongHoc.setPreferredSize(new java.awt.Dimension(350, 615));
        thongTin_TimKiemPhongHoc.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 32));

        thongTinPhongHoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phòng học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        thongTinPhongHoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        thongTinPhongHoc.setPreferredSize(new java.awt.Dimension(340, 350));

        sọPhongPLLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sọPhongPLLabel.setText("Số phòng");

        maLopPLLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopPLLabel.setText("Mã lớp");

        hKNHPLLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hKNHPLLabel.setText("Học kỳ - Năm học:");

        hKNHPLInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hKNHPLInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKNHPLInputActionPerformed(evt);
            }
        });

        themPhongLopBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        themPhongLopBtn.setText("Thêm");
        themPhongLopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themPhongLopBtnActionPerformed(evt);
            }
        });

        suaPhongLopBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        suaPhongLopBtn.setText("Sửa");
        suaPhongLopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaPhongLopBtnActionPerformed(evt);
            }
        });

        xoaPhongLopBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoaPhongLopBtn.setText("Xóa");
        xoaPhongLopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaPhongLopBtnActionPerformed(evt);
            }
        });

        resetInputPhongLopBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetInputPhongLopBtn.setText("Đặt lại");
        resetInputPhongLopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetInputPhongLopBtnActionPerformed(evt);
            }
        });

        maLopPLInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopPLInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maLopPLInputActionPerformed(evt);
            }
        });

        soPhongPLInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soPhongPLInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soPhongPLInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thongTinPhongHocLayout = new javax.swing.GroupLayout(thongTinPhongHoc);
        thongTinPhongHoc.setLayout(thongTinPhongHocLayout);
        thongTinPhongHocLayout.setHorizontalGroup(
            thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinPhongHocLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xoaPhongLopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themPhongLopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resetInputPhongLopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaPhongLopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(thongTinPhongHocLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinPhongHocLayout.createSequentialGroup()
                        .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sọPhongPLLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(maLopPLLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maLopPLInput)
                            .addComponent(soPhongPLInput)))
                    .addGroup(thongTinPhongHocLayout.createSequentialGroup()
                        .addComponent(hKNHPLLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(hKNHPLInput, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        thongTinPhongHocLayout.setVerticalGroup(
            thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinPhongHocLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sọPhongPLLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soPhongPLInput))
                .addGap(25, 25, 25)
                .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maLopPLLabel)
                    .addComponent(maLopPLInput))
                .addGap(26, 26, 26)
                .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hKNHPLInput)
                    .addComponent(hKNHPLLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themPhongLopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaPhongLopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(thongTinPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetInputPhongLopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xoaPhongLopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        thongTin_TimKiemPhongHoc.add(thongTinPhongHoc);

        timKiemPhongHoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm phòng học\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        timKiemPhongHoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        soPhongTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soPhongTimKiemLabel.setText("Số phòng");

        timKiemPhongHocBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timKiemPhongHocBtn.setText("Tìm kiếm");
        timKiemPhongHocBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemPhongHocBtnActionPerformed(evt);
            }
        });

        resetBangPhongHocBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetBangPhongHocBtn.setText("Reset");
        resetBangPhongHocBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBangPhongHocBtnActionPerformed(evt);
            }
        });

        nienKhoaLopTimKiemLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nienKhoaLopTimKiemLabel.setText("Niên khóa");

        hKNHTimKiemInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKNHTimKiemInputActionPerformed(evt);
            }
        });

        soPhongTimKiemInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout timKiemPhongHocLayout = new javax.swing.GroupLayout(timKiemPhongHoc);
        timKiemPhongHoc.setLayout(timKiemPhongHocLayout);
        timKiemPhongHocLayout.setHorizontalGroup(
            timKiemPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemPhongHocLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timKiemPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timKiemPhongHocLayout.createSequentialGroup()
                        .addComponent(nienKhoaLopTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hKNHTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(timKiemPhongHocLayout.createSequentialGroup()
                        .addComponent(soPhongTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(soPhongTimKiemInput))
                    .addGroup(timKiemPhongHocLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(timKiemPhongHocBtn)
                        .addGap(47, 47, 47)
                        .addComponent(resetBangPhongHocBtn)))
                .addContainerGap())
        );
        timKiemPhongHocLayout.setVerticalGroup(
            timKiemPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemPhongHocLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(timKiemPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(soPhongTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soPhongTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(timKiemPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nienKhoaLopTimKiemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hKNHTimKiemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(timKiemPhongHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timKiemPhongHocBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBangPhongHocBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        thongTin_TimKiemPhongHoc.add(timKiemPhongHoc);

        phongHocTab.add(thongTin_TimKiemPhongHoc, java.awt.BorderLayout.LINE_END);

        phongLopTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        phongLopTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        phongLopTable.setToolTipText("");
        phongLopTable.setRowHeight(30);
        phongLopTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(phongLopTable);

        danhSachLopLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        danhSachLopLabel.setText("Danh Sách Phòng Học");

        javax.swing.GroupLayout danhSachLopLayout = new javax.swing.GroupLayout(danhSachLop);
        danhSachLop.setLayout(danhSachLopLayout);
        danhSachLopLayout.setHorizontalGroup(
            danhSachLopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, danhSachLopLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(danhSachLopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        danhSachLopLayout.setVerticalGroup(
            danhSachLopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(danhSachLopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(danhSachLopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        phongHocTab.add(danhSachLop, java.awt.BorderLayout.CENTER);

        boby.addTab("Phòng học", phongHocTab);

        diemTab.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        diemTab.setLayout(new java.awt.BorderLayout());

        thongTin_TimKiemPhongHoc1.setPreferredSize(new java.awt.Dimension(350, 615));
        thongTin_TimKiemPhongHoc1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 32));

        thongTinPhongHoc1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phòng học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        thongTinPhongHoc1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        thongTinPhongHoc1.setPreferredSize(new java.awt.Dimension(340, 350));

        sọPhongLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sọPhongLabel1.setText("Số phòng");

        tenLopLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tenLopLabel1.setText("Mã lớp");

        nienKhoaLopLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nienKhoaLopLabel1.setText("Học kỳ - Năm học:");

        hKNHInput1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hKNHInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKNHInput1ActionPerformed(evt);
            }
        });

        themPhongHocBtn1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        themPhongHocBtn1.setText("Thêm");
        themPhongHocBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themPhongHocBtn1ActionPerformed(evt);
            }
        });

        suaPhongHocBtn1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        suaPhongHocBtn1.setText("Sửa");
        suaPhongHocBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaPhongHocBtn1ActionPerformed(evt);
            }
        });

        xoaPhongHocBtn1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoaPhongHocBtn1.setText("Xóa");
        xoaPhongHocBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaPhongHocBtn1ActionPerformed(evt);
            }
        });

        resetInputPhongHocBtn1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetInputPhongHocBtn1.setText("Đặt lại");
        resetInputPhongHocBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetInputPhongHocBtn1ActionPerformed(evt);
            }
        });

        maLopPHInput1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopPHInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maLopPHInput1ActionPerformed(evt);
            }
        });

        soPhongInput1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soPhongInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soPhongInput1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thongTinPhongHoc1Layout = new javax.swing.GroupLayout(thongTinPhongHoc1);
        thongTinPhongHoc1.setLayout(thongTinPhongHoc1Layout);
        thongTinPhongHoc1Layout.setHorizontalGroup(
            thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinPhongHoc1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xoaPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resetInputPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(thongTinPhongHoc1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinPhongHoc1Layout.createSequentialGroup()
                        .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sọPhongLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(tenLopLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maLopPHInput1)
                            .addComponent(soPhongInput1)))
                    .addGroup(thongTinPhongHoc1Layout.createSequentialGroup()
                        .addComponent(nienKhoaLopLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(hKNHInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        thongTinPhongHoc1Layout.setVerticalGroup(
            thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinPhongHoc1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sọPhongLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maLopPHInput1))
                .addGap(25, 25, 25)
                .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenLopLabel1)
                    .addComponent(soPhongInput1))
                .addGap(26, 26, 26)
                .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hKNHInput1)
                    .addComponent(nienKhoaLopLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(thongTinPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetInputPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xoaPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        thongTin_TimKiemPhongHoc1.add(thongTinPhongHoc1);

        timKiemPhongHoc1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm phòng học\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        timKiemPhongHoc1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        soPhongTimKiemLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soPhongTimKiemLabel1.setText("Số phòng");

        timKiemPhongHocBtn1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timKiemPhongHocBtn1.setText("Tìm kiếm");
        timKiemPhongHocBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemPhongHocBtn1ActionPerformed(evt);
            }
        });

        resetBangPhongHocBtn1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetBangPhongHocBtn1.setText("Reset");
        resetBangPhongHocBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBangPhongHocBtn1ActionPerformed(evt);
            }
        });

        nienKhoaLopTimKiemLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nienKhoaLopTimKiemLabel1.setText("Niên khóa");

        hKNHTimKiemInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKNHTimKiemInput1ActionPerformed(evt);
            }
        });

        soPhongTimKiemInput1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout timKiemPhongHoc1Layout = new javax.swing.GroupLayout(timKiemPhongHoc1);
        timKiemPhongHoc1.setLayout(timKiemPhongHoc1Layout);
        timKiemPhongHoc1Layout.setHorizontalGroup(
            timKiemPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemPhongHoc1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timKiemPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timKiemPhongHoc1Layout.createSequentialGroup()
                        .addComponent(nienKhoaLopTimKiemLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hKNHTimKiemInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(timKiemPhongHoc1Layout.createSequentialGroup()
                        .addComponent(soPhongTimKiemLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(soPhongTimKiemInput1))
                    .addGroup(timKiemPhongHoc1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(timKiemPhongHocBtn1)
                        .addGap(47, 47, 47)
                        .addComponent(resetBangPhongHocBtn1)))
                .addContainerGap())
        );
        timKiemPhongHoc1Layout.setVerticalGroup(
            timKiemPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemPhongHoc1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(timKiemPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(soPhongTimKiemLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soPhongTimKiemInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(timKiemPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nienKhoaLopTimKiemLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hKNHTimKiemInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(timKiemPhongHoc1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timKiemPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBangPhongHocBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        thongTin_TimKiemPhongHoc1.add(timKiemPhongHoc1);

        diemTab.add(thongTin_TimKiemPhongHoc1, java.awt.BorderLayout.LINE_END);

        diemTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        diemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        diemTable.setToolTipText("");
        diemTable.setRowHeight(30);
        diemTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(diemTable);

        danhSachLopLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        danhSachLopLabel1.setText("Danh Sách Phòng Học");

        javax.swing.GroupLayout danhSachLop1Layout = new javax.swing.GroupLayout(danhSachLop1);
        danhSachLop1.setLayout(danhSachLop1Layout);
        danhSachLop1Layout.setHorizontalGroup(
            danhSachLop1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, danhSachLop1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(danhSachLopLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        danhSachLop1Layout.setVerticalGroup(
            danhSachLop1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(danhSachLop1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(danhSachLopLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        diemTab.add(danhSachLop1, java.awt.BorderLayout.CENTER);

        boby.addTab("Điểm", diemTab);

        thongKeTab.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        thongKeTab.setLayout(new java.awt.BorderLayout());

        thongTin_TimKiemPhongHoc2.setPreferredSize(new java.awt.Dimension(350, 615));
        thongTin_TimKiemPhongHoc2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 32));

        thongTinPhongHoc2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phòng học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        thongTinPhongHoc2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        thongTinPhongHoc2.setPreferredSize(new java.awt.Dimension(340, 350));

        sọPhongLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sọPhongLabel2.setText("Số phòng");

        tenLopLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tenLopLabel2.setText("Mã lớp");

        nienKhoaLopLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nienKhoaLopLabel2.setText("Học kỳ - Năm học:");

        hKNHInput2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hKNHInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKNHInput2ActionPerformed(evt);
            }
        });

        themPhongHocBtn2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        themPhongHocBtn2.setText("Thêm");
        themPhongHocBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themPhongHocBtn2ActionPerformed(evt);
            }
        });

        suaPhongHocBtn2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        suaPhongHocBtn2.setText("Sửa");
        suaPhongHocBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaPhongHocBtn2ActionPerformed(evt);
            }
        });

        xoaPhongHocBtn2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoaPhongHocBtn2.setText("Xóa");
        xoaPhongHocBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaPhongHocBtn2ActionPerformed(evt);
            }
        });

        resetInputPhongHocBtn2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetInputPhongHocBtn2.setText("Đặt lại");
        resetInputPhongHocBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetInputPhongHocBtn2ActionPerformed(evt);
            }
        });

        maLopPHInput2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopPHInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maLopPHInput2ActionPerformed(evt);
            }
        });

        soPhongInput2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soPhongInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soPhongInput2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thongTinPhongHoc2Layout = new javax.swing.GroupLayout(thongTinPhongHoc2);
        thongTinPhongHoc2.setLayout(thongTinPhongHoc2Layout);
        thongTinPhongHoc2Layout.setHorizontalGroup(
            thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinPhongHoc2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xoaPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resetInputPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(thongTinPhongHoc2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinPhongHoc2Layout.createSequentialGroup()
                        .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sọPhongLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(tenLopLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maLopPHInput2)
                            .addComponent(soPhongInput2)))
                    .addGroup(thongTinPhongHoc2Layout.createSequentialGroup()
                        .addComponent(nienKhoaLopLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(hKNHInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        thongTinPhongHoc2Layout.setVerticalGroup(
            thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinPhongHoc2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sọPhongLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soPhongInput2))
                .addGap(25, 25, 25)
                .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenLopLabel2)
                    .addComponent(maLopPHInput2))
                .addGap(26, 26, 26)
                .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hKNHInput2)
                    .addComponent(nienKhoaLopLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(thongTinPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetInputPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xoaPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        thongTin_TimKiemPhongHoc2.add(thongTinPhongHoc2);

        timKiemPhongHoc2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm phòng học\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        timKiemPhongHoc2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        soPhongTimKiemLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soPhongTimKiemLabel2.setText("Số phòng");

        timKiemPhongHocBtn2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timKiemPhongHocBtn2.setText("Tìm kiếm");
        timKiemPhongHocBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemPhongHocBtn2ActionPerformed(evt);
            }
        });

        resetBangPhongHocBtn2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetBangPhongHocBtn2.setText("Reset");
        resetBangPhongHocBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBangPhongHocBtn2ActionPerformed(evt);
            }
        });

        nienKhoaLopTimKiemLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nienKhoaLopTimKiemLabel2.setText("Niên khóa");

        hKNHTimKiemInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKNHTimKiemInput2ActionPerformed(evt);
            }
        });

        soPhongTimKiemInput2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout timKiemPhongHoc2Layout = new javax.swing.GroupLayout(timKiemPhongHoc2);
        timKiemPhongHoc2.setLayout(timKiemPhongHoc2Layout);
        timKiemPhongHoc2Layout.setHorizontalGroup(
            timKiemPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemPhongHoc2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timKiemPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timKiemPhongHoc2Layout.createSequentialGroup()
                        .addComponent(nienKhoaLopTimKiemLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hKNHTimKiemInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(timKiemPhongHoc2Layout.createSequentialGroup()
                        .addComponent(soPhongTimKiemLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(soPhongTimKiemInput2))
                    .addGroup(timKiemPhongHoc2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(timKiemPhongHocBtn2)
                        .addGap(47, 47, 47)
                        .addComponent(resetBangPhongHocBtn2)))
                .addContainerGap())
        );
        timKiemPhongHoc2Layout.setVerticalGroup(
            timKiemPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemPhongHoc2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(timKiemPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(soPhongTimKiemLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soPhongTimKiemInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(timKiemPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nienKhoaLopTimKiemLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hKNHTimKiemInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(timKiemPhongHoc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timKiemPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBangPhongHocBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        thongTin_TimKiemPhongHoc2.add(timKiemPhongHoc2);

        thongKeTab.add(thongTin_TimKiemPhongHoc2, java.awt.BorderLayout.LINE_END);

        thongKeTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        thongKeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        thongKeTable.setToolTipText("");
        thongKeTable.setRowHeight(30);
        thongKeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(thongKeTable);

        danhSachLopLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        danhSachLopLabel2.setText("Danh Sách Phòng Học");

        javax.swing.GroupLayout danhSachLop2Layout = new javax.swing.GroupLayout(danhSachLop2);
        danhSachLop2.setLayout(danhSachLop2Layout);
        danhSachLop2Layout.setHorizontalGroup(
            danhSachLop2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, danhSachLop2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(danhSachLopLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        danhSachLop2Layout.setVerticalGroup(
            danhSachLop2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(danhSachLop2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(danhSachLopLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        thongKeTab.add(danhSachLop2, java.awt.BorderLayout.CENTER);

        boby.addTab("Thống kê", thongKeTab);

        taiKhoanTab.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        taiKhoanTab.setLayout(new java.awt.BorderLayout());

        thongTin_TimKiemPhongHoc3.setPreferredSize(new java.awt.Dimension(350, 615));
        thongTin_TimKiemPhongHoc3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 32));

        thongTinPhongHoc3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phòng học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        thongTinPhongHoc3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        thongTinPhongHoc3.setPreferredSize(new java.awt.Dimension(340, 350));

        sọPhongLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sọPhongLabel3.setText("Số phòng");

        tenLopLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tenLopLabel3.setText("Mã lớp");

        nienKhoaLopLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nienKhoaLopLabel3.setText("Học kỳ - Năm học:");

        hKNHInput3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hKNHInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKNHInput3ActionPerformed(evt);
            }
        });

        themPhongHocBtn3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        themPhongHocBtn3.setText("Thêm");
        themPhongHocBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themPhongHocBtn3ActionPerformed(evt);
            }
        });

        suaPhongHocBtn3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        suaPhongHocBtn3.setText("Sửa");
        suaPhongHocBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaPhongHocBtn3ActionPerformed(evt);
            }
        });

        xoaPhongHocBtn3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoaPhongHocBtn3.setText("Xóa");
        xoaPhongHocBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaPhongHocBtn3ActionPerformed(evt);
            }
        });

        resetInputPhongHocBtn3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetInputPhongHocBtn3.setText("Đặt lại");
        resetInputPhongHocBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetInputPhongHocBtn3ActionPerformed(evt);
            }
        });

        maLopPHInput3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        maLopPHInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maLopPHInput3ActionPerformed(evt);
            }
        });

        soPhongInput3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soPhongInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soPhongInput3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thongTinPhongHoc3Layout = new javax.swing.GroupLayout(thongTinPhongHoc3);
        thongTinPhongHoc3.setLayout(thongTinPhongHoc3Layout);
        thongTinPhongHoc3Layout.setHorizontalGroup(
            thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinPhongHoc3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xoaPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resetInputPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(thongTinPhongHoc3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinPhongHoc3Layout.createSequentialGroup()
                        .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sọPhongLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(tenLopLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maLopPHInput3)
                            .addComponent(soPhongInput3)))
                    .addGroup(thongTinPhongHoc3Layout.createSequentialGroup()
                        .addComponent(nienKhoaLopLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(hKNHInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        thongTinPhongHoc3Layout.setVerticalGroup(
            thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinPhongHoc3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sọPhongLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soPhongInput3))
                .addGap(25, 25, 25)
                .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenLopLabel3)
                    .addComponent(maLopPHInput3))
                .addGap(26, 26, 26)
                .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hKNHInput3)
                    .addComponent(nienKhoaLopLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(thongTinPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetInputPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xoaPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        thongTin_TimKiemPhongHoc3.add(thongTinPhongHoc3);

        timKiemPhongHoc3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm phòng học\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 20))); // NOI18N
        timKiemPhongHoc3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        soPhongTimKiemLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        soPhongTimKiemLabel3.setText("Số phòng");

        timKiemPhongHocBtn3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timKiemPhongHocBtn3.setText("Tìm kiếm");
        timKiemPhongHocBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemPhongHocBtn3ActionPerformed(evt);
            }
        });

        resetBangPhongHocBtn3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetBangPhongHocBtn3.setText("Reset");
        resetBangPhongHocBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBangPhongHocBtn3ActionPerformed(evt);
            }
        });

        nienKhoaLopTimKiemLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nienKhoaLopTimKiemLabel3.setText("Niên khóa");

        hKNHTimKiemInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKNHTimKiemInput3ActionPerformed(evt);
            }
        });

        soPhongTimKiemInput3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout timKiemPhongHoc3Layout = new javax.swing.GroupLayout(timKiemPhongHoc3);
        timKiemPhongHoc3.setLayout(timKiemPhongHoc3Layout);
        timKiemPhongHoc3Layout.setHorizontalGroup(
            timKiemPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemPhongHoc3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timKiemPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timKiemPhongHoc3Layout.createSequentialGroup()
                        .addComponent(nienKhoaLopTimKiemLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hKNHTimKiemInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(timKiemPhongHoc3Layout.createSequentialGroup()
                        .addComponent(soPhongTimKiemLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(soPhongTimKiemInput3))
                    .addGroup(timKiemPhongHoc3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(timKiemPhongHocBtn3)
                        .addGap(47, 47, 47)
                        .addComponent(resetBangPhongHocBtn3)))
                .addContainerGap())
        );
        timKiemPhongHoc3Layout.setVerticalGroup(
            timKiemPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timKiemPhongHoc3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(timKiemPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(soPhongTimKiemLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soPhongTimKiemInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(timKiemPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nienKhoaLopTimKiemLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hKNHTimKiemInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(timKiemPhongHoc3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timKiemPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBangPhongHocBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        thongTin_TimKiemPhongHoc3.add(timKiemPhongHoc3);

        taiKhoanTab.add(thongTin_TimKiemPhongHoc3, java.awt.BorderLayout.LINE_END);

        taiKhoanTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        taiKhoanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        taiKhoanTable.setToolTipText("");
        taiKhoanTable.setRowHeight(30);
        taiKhoanTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(taiKhoanTable);

        danhSachLopLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        danhSachLopLabel3.setText("Danh Sách Phòng Học");

        javax.swing.GroupLayout danhSachLop3Layout = new javax.swing.GroupLayout(danhSachLop3);
        danhSachLop3.setLayout(danhSachLop3Layout);
        danhSachLop3Layout.setHorizontalGroup(
            danhSachLop3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, danhSachLop3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(danhSachLopLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        danhSachLop3Layout.setVerticalGroup(
            danhSachLop3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(danhSachLop3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(danhSachLopLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        taiKhoanTab.add(danhSachLop3, java.awt.BorderLayout.CENTER);

        boby.addTab("Tài khoản", taiKhoanTab);

        getContentPane().add(boby, java.awt.BorderLayout.CENTER);
        boby.getAccessibleContext().setAccessibleName("");
        boby.getAccessibleContext().setAccessibleDescription("");

        taikhoanHeader.setText("Tài khoản");
        header.add(taikhoanHeader);

        troVeHeader.setText("Trờ về");
        header.add(troVeHeader);

        thoatHeader.setText("Thoát");
        header.add(thoatHeader);

        setJMenuBar(header);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void maHocSinhInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maHocSinhInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maHocSinhInputActionPerformed

    private void hoTenInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoTenInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hoTenInputActionPerformed

    private void sdtPhuHuynhInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sdtPhuHuynhInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sdtPhuHuynhInputActionPerformed

    private void diaChiInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaChiInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diaChiInputActionPerformed

    private void ngaySinhInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngaySinhInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ngaySinhInputActionPerformed

    private void themHocSinhBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themHocSinhBtnActionPerformed
    if(maHocSinhInput.getText().equals("") || hoTenInput.getText().equals("")){
        JOptionPane.showMessageDialog(this, 
                "Bạn phải nhập mã và họ tên học sinh", 
                "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
    else{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = formatter.parse(ngaySinhInput.getText());
        } catch (ParseException ex) {
            ex.printStackTrace(); // Xử lý lỗi parse
        }
        HocSinh hocSinh = new HocSinh(
                maHocSinhInput.getText(), 
                hoTenInput.getText(), 
                date,
                diaChiInput.getText(), 
                sdtPhuHuynhInput.getText(), 
                (Lop) maLopList.getSelectedItem());
        
        // Gọi phương thức để thêm học sinh
        this.hocSinhService.addHocSinh(hocSinh); 
        
        resetHSInput();
        JOptionPane.showMessageDialog(this, 
                "Thêm học sinh " + maHocSinhInput.getText() + " thành công", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        
        // Cập nhật bảng hiển thị
        defaultHocSinhTableModel.addRow(new Object[]{
            hocSinh.getMaHocSinh(),
            hocSinh.getHoTen(),
            formatter.format(hocSinh.getNgaySinh()),
            hocSinh.getDiaChi(),
            hocSinh.getSdtPhuHuynh(),
            hocSinh.getLop().getMaLop()
        });
    }   

//        if(maHocSinhInput.getText().equals("") || hoTenInput.getText().equals("")){
//            JOptionPane.showMessageDialog(this, 
//                    "Bạn phải nhập mã và họ tên học sinh", 
//                    "Thông báo", JOptionPane.ERROR_MESSAGE);
//        }
//        else{
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = new Date();
//            try {
//                date = formatter.parse(ngaySinhInput.getText());
//            } catch (ParseException ex) {
//            }
//            HocSinh hocSinh = new HocSinh(
//                    maHocSinhInput.getText(), 
//                    hoTenInput.getText(), 
//                    date,
//                    diaChiInput.getText(), 
//                    sdtPhuHuynhInput.getText(), 
//                    (Lop) maLopList.getSelectedItem());
//            this.hocSinhService.addHocSinh(hocSinh); 
//            resetHSInput();
//            JOptionPane.showMessageDialog(this, 
//                    "Thêm học sinh " + maHocSinhInput.getText() + " thành công", 
//                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            defaultHocSinhTableModel.addRow(new Object[]{
//                hocSinh.getMaHocSinh(),
//                hocSinh.getHoTen(),
//                formatter.format(hocSinh.getNgaySinh()),
//                hocSinh.getDiaChi(),
//                hocSinh.getSdtPhuHuynh(),
//                hocSinh.getLop().getMaLop()
//            });
//        }
        
    }//GEN-LAST:event_themHocSinhBtnActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        resetHSInput();
    }//GEN-LAST:event_resetBtnActionPerformed

    private void maLopListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maLopListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maLopListActionPerformed

    private void suaHocSinhBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaHocSinhBtnActionPerformed
        if(hocSinhTable.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, 
                    "Bạn chưa chọn học sinh cần sửa", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            Date date = new Date();
            try {
                date = formatter.parse(ngaySinhInput.getText());
            } catch (ParseException ex) {
            }
            HocSinh hocsinh = new HocSinh(
                    maHocSinhInput.getText(), 
                    hoTenInput.getText(), 
                    date,
                    diaChiInput.getText(), 
                    sdtPhuHuynhInput.getText(), 
                    (Lop) maLopList.getSelectedItem());
            this.hocSinhService.updateHocSinh(hocsinh);

            int row = hocSinhTable.getSelectedRow();
            hocSinhTable.setValueAt(maHocSinhInput.getText(),row, 0);
            hocSinhTable.setValueAt(hoTenInput.getText(),row, 1);
            hocSinhTable.setValueAt(formatter.format(date), row, 2);
            hocSinhTable.setValueAt(diaChiInput.getText(), row, 3);
            hocSinhTable.setValueAt(sdtPhuHuynhInput.getText(), row, 4);
            hocSinhTable.setValueAt(((Lop) maLopList.getSelectedItem()).getTenLop() , row, 5);
            JOptionPane.showMessageDialog(this, 
                    "Sửa học sinh " + maHocSinhInput.getText() + " thành công", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_suaHocSinhBtnActionPerformed

    private void hoTenHSTimKiemInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoTenHSTimKiemInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hoTenHSTimKiemInputActionPerformed

    private void maHSTimKiemInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maHSTimKiemInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maHSTimKiemInputActionPerformed

    private void resetBangHSBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBangHSBtnActionPerformed
        ListSelectionModel selectionModel =  hocSinhTable.getSelectionModel();
        selectionModel.removeListSelectionListener(selectionHocSinhListener);
        defaultHocSinhTableModel.setRowCount(0);
        dienBangHS();
        resetHSInput();
        maHSTimKiemInput.setText("");
        hoTenHSTimKiemInput.setText("");
    }//GEN-LAST:event_resetBangHSBtnActionPerformed

    private void timKiemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemBtnActionPerformed
        if(maHSTimKiemInput.getText().equals("") && hoTenHSTimKiemInput.getText().equals("")){
            JOptionPane.showMessageDialog(this, 
                    "Bạn chưa nhập thông tin tìm kiếm", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            List<HocSinh> dsHocSinh = this.hocSinhService.timKiemHocSinh(maHSTimKiemInput.getText(), hoTenHSTimKiemInput.getText());
            ListSelectionModel selectionModel =  hocSinhTable.getSelectionModel();
            selectionModel.removeListSelectionListener(selectionHocSinhListener);
            defaultHocSinhTableModel.setRowCount(0);
            selectionModel.addListSelectionListener(selectionHocSinhListener);
            for(HocSinh hocSinh : dsHocSinh){
                defaultHocSinhTableModel.addRow(new Object[]{
                    hocSinh.getMaHocSinh(),
                    hocSinh.getHoTen(),
                    formatter.format(hocSinh.getNgaySinh()),
                    hocSinh.getDiaChi(),
                    hocSinh.getSdtPhuHuynh(),
                    hocSinh.getLop().getTenLop()
                });
            }
        }
        

    }//GEN-LAST:event_timKiemBtnActionPerformed

    private void xoaHocSinhBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaHocSinhBtnActionPerformed
        if(hocSinhTable.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, 
                    "Bạn chưa chọn học sinh cần xóa", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhanXoaHS = JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa học sinh " + maHocSinhInput.getText() +" không ?");
            if(xacNhanXoaHS == JOptionPane.YES_OPTION){
                this.hocSinhService.deleteHocSinh(maHocSinhInput.getText());
            }
            ListSelectionModel selectionModel =  hocSinhTable.getSelectionModel();
            
            int selectedRow = hocSinhTable.getSelectedRow();
            selectionModel.removeListSelectionListener(selectionHocSinhListener);
            defaultHocSinhTableModel.removeRow(selectedRow);
            selectionModel.addListSelectionListener(selectionHocSinhListener);
            resetHSInput();
            JOptionPane.showMessageDialog(this, 
                    "Xóa học sinh " + maHocSinhInput.getText() + " thành công", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_xoaHocSinhBtnActionPerformed

    private void maGiaoVienInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maGiaoVienInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maGiaoVienInputActionPerformed

    private void hoTenGVInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoTenGVInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hoTenGVInputActionPerformed

    private void sdtGVInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sdtGVInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sdtGVInputActionPerformed

    private void diaChiGVInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaChiGVInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diaChiGVInputActionPerformed

    private void ngaySinhGVInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngaySinhGVInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ngaySinhGVInputActionPerformed

    private void themGVBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themGVBtnActionPerformed
        if(maGiaoVienInput.getText().equals("") || hoTenGVInput.getText().equals("")){
            JOptionPane.showMessageDialog(this, 
                    "Bạn phải nhập mã và họ tên giáo viên", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        else{
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try {
                date = formatter.parse(ngaySinhGVInput.getText());
            } catch (ParseException ex) {
            }
            GiaoVien giaoVien = new GiaoVien(
                    Integer.parseInt(maGiaoVienInput.getText()), 
                    hoTenGVInput.getText(), 
                    date,
                    diaChiGVInput.getText(), 
                    sdtGVInput.getText());
            this.giaoVienService.addGiaoVien(giaoVien); 
            resetGVInput();
            JOptionPane.showMessageDialog(this, 
                    "Thêm giáo viên " + maGiaoVienInput.getText() + " thành công", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            defaultGiaoVienTableModel.addRow(new Object[]{
                giaoVien.getMaGiaoVien(),
                giaoVien.getHoTen(),
                formatter.format(giaoVien.getNgaySinh()),
                giaoVien.getDiaChi(),
                giaoVien.getSoDienThoai()
            });
        }
    }//GEN-LAST:event_themGVBtnActionPerformed

    private void suaGVBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaGVBtnActionPerformed
        if(giaoVienTable.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, 
                    "Bạn chưa chọn giáo viên cần sửa", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            Date date = new Date();
            try {
                date = formatter.parse(ngaySinhGVInput.getText());
            } catch (ParseException ex) {
            }
            GiaoVien giaoVien = new GiaoVien(
                    Integer.parseInt(maGiaoVienInput.getText()), 
                    hoTenGVInput.getText(), 
                    date,
                    diaChiGVInput.getText(), 
                    sdtGVInput.getText());
            this.giaoVienService.updateGiaoVien(giaoVien);

            int row = giaoVienTable.getSelectedRow();
            giaoVienTable.setValueAt(maGiaoVienInput.getText(),row, 0);
            giaoVienTable.setValueAt(hoTenGVInput.getText(),row, 1);
            giaoVienTable.setValueAt(formatter.format(date), row, 2);
            giaoVienTable.setValueAt(diaChiGVInput.getText(), row, 3);
            giaoVienTable.setValueAt(sdtGVInput.getText(), row, 4);
            JOptionPane.showMessageDialog(this, 
                    "Sửa giáo viên " + maGiaoVienInput.getText() + " thành công", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_suaGVBtnActionPerformed

    private void xoaGVBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaGVBtnActionPerformed
        if(giaoVienTable.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, 
                    "Bạn chưa chọn giáo viên cần xóa", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhanXoaGV = JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa giáo viên " + maGiaoVienInput.getText() +" không ?");
            if(xacNhanXoaGV == JOptionPane.YES_OPTION){
                this.giaoVienService.deleteGiaoVien(Integer.parseInt(maGiaoVienInput.getText()));
            }
            
            ListSelectionModel selectionModel =  giaoVienTable.getSelectionModel();
            
            int selectedRow = giaoVienTable.getSelectedRow();
            selectionModel.removeListSelectionListener(selectionGiaoVienListener);
            defaultGiaoVienTableModel.removeRow(selectedRow);
            selectionModel.addListSelectionListener(selectionGiaoVienListener);
            resetGVInput();
            JOptionPane.showMessageDialog(this, 
                    "Xóa giáo viên " + maGiaoVienInput.getText() + " thành công", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_xoaGVBtnActionPerformed

    private void resetInputGVBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetInputGVBtnActionPerformed
        resetGVInput();
    }//GEN-LAST:event_resetInputGVBtnActionPerformed

    private void hoTenGVTimKiemInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoTenGVTimKiemInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hoTenGVTimKiemInputActionPerformed

    private void maGVTimKiemInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maGVTimKiemInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maGVTimKiemInputActionPerformed

    private void timKiemGVBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemGVBtnActionPerformed
        if(maGVTimKiemInput.getText().equals("") && hoTenGVTimKiemInput.getText().equals("")){
            JOptionPane.showMessageDialog(this, 
                    "Bạn chưa nhập thông tin tìm kiếm", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            int maGV = maGVTimKiemInput.getText().equals("") ? 0 : Integer.parseInt(maGVTimKiemInput.getText());
            List<GiaoVien> dsGiaoVien = this.giaoVienService.timKiemGiaoVien(
                    maGV, 
                    hoTenGVTimKiemInput.getText());
            ListSelectionModel selectionModel =  giaoVienTable.getSelectionModel();
            selectionModel.removeListSelectionListener(selectionGiaoVienListener);
            defaultGiaoVienTableModel.setRowCount(0);
            selectionModel.addListSelectionListener(selectionGiaoVienListener);
            for(GiaoVien giaoVien : dsGiaoVien){
                defaultGiaoVienTableModel.addRow(new Object[]{
                    giaoVien.getMaGiaoVien(),
                    giaoVien.getHoTen(),
                    formatter.format(giaoVien.getNgaySinh()),
                    giaoVien.getDiaChi(),
                    giaoVien.getSoDienThoai()
                });
            }
        }
    }//GEN-LAST:event_timKiemGVBtnActionPerformed

    private void resetBangGVBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBangGVBtnActionPerformed
        ListSelectionModel selectionModel =  giaoVienTable.getSelectionModel();
        selectionModel.removeListSelectionListener(selectionGiaoVienListener);
        defaultGiaoVienTableModel.setRowCount(0);
        dienBangGV();
        resetGVInput();
        maGVTimKiemInput.setText("");
        hoTenGVTimKiemInput.setText("");
    }//GEN-LAST:event_resetBangGVBtnActionPerformed

    private void themCNBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themCNBtnActionPerformed
        if(namHocCNInput.getText().equals("")){
            JOptionPane.showMessageDialog(this, 
                    "Bạn phải nhập năm học", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        else{
            ChuNhiem chuNhiem = new ChuNhiem(
                    (Lop) maLopCNInput.getSelectedItem(), 
                    (GiaoVien) maGVCNInput.getSelectedItem(), 
                    namHocCNInput.getText());
            this.chuNhiemService.addChuNhiem(chuNhiem); 
            resetCNInput();
            JOptionPane.showMessageDialog(this, 
                    "Thêm chủ nhiệm thành công", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            defaultChuNhiemTableModel.addRow(new Object[]{
                chuNhiem.getLop().getMaLop(),
                chuNhiem.getLop().getTenLop(),               
                chuNhiem.getGiaoVien().getMaGiaoVien(),
                chuNhiem.getGiaoVien().getHoTen(),
                chuNhiem.getNamHoc(),
            });
        }
    }//GEN-LAST:event_themCNBtnActionPerformed

    private void xoaCNBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaCNBtnActionPerformed
        if(chuNhiemTable.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, 
                    "Bạn chưa chọn chủ nhiệm cần xóa", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhanXoaCN = JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa chủ nhiệm này không ?");
            if(xacNhanXoaCN == JOptionPane.YES_OPTION){
                this.chuNhiemService.deleteChuNhiem((Lop) maLopCNInput.getSelectedItem(), (GiaoVien) maGVCNInput.getSelectedItem());
            }
            
            ListSelectionModel selectionModel =  chuNhiemTable.getSelectionModel();
            
            int selectedRow = chuNhiemTable.getSelectedRow();
            selectionModel.removeListSelectionListener(selectionChuNhiemListener);
            defaultChuNhiemTableModel.removeRow(selectedRow);
            selectionModel.addListSelectionListener(selectionChuNhiemListener);
            resetCNInput();
            JOptionPane.showMessageDialog(this, 
                    "Xóa chủ nhiệm thành công", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_xoaCNBtnActionPerformed

    private void resetInputCNBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetInputCNBtnActionPerformed
        resetCNInput();
    }//GEN-LAST:event_resetInputCNBtnActionPerformed

    private void timKiemCNBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemCNBtnActionPerformed
        if(maGVCNTimKiemInput.getText().equals("") && maLopCNTimKiemnput.getText().equals("") && namHocCNTimKiemInput.getText().equals("")){
            JOptionPane.showMessageDialog(this, 
                    "Bạn chưa nhập thông tin tìm kiếm", 
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        } else {
            int maGV = maGVCNTimKiemInput.getText().equals("") ? 0 : Integer.parseInt(maGVCNTimKiemInput.getText());
            int maLop = maLopCNTimKiemnput.getText().equals("") ? 0 : Integer.parseInt(maLopCNTimKiemnput.getText());
            String namHoc = namHocCNTimKiemInput.getText();
            List<ChuNhiem> dsChuNhiem = this.chuNhiemService.timKiemChuNhiem(
                    maLop, 
                    maGV,
                    namHoc);
            ListSelectionModel selectionModel =  chuNhiemTable.getSelectionModel();
            selectionModel.removeListSelectionListener(selectionChuNhiemListener);
            defaultChuNhiemTableModel.setRowCount(0);
            selectionModel.addListSelectionListener(selectionChuNhiemListener);
            for(ChuNhiem chuNhiem : dsChuNhiem){
                defaultChuNhiemTableModel.addRow(new Object[]{
                    chuNhiem.getLop().getMaLop(),
                    chuNhiem.getLop().getTenLop(),               
                    chuNhiem.getGiaoVien().getMaGiaoVien(),
                    chuNhiem.getGiaoVien().getHoTen(),
                    chuNhiem.getNamHoc(),
                });
            }
        }
        
        
    }//GEN-LAST:event_timKiemCNBtnActionPerformed

    private void resetBangCNBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBangCNBtnActionPerformed
        ListSelectionModel selectionModel =  chuNhiemTable.getSelectionModel();
        selectionModel.removeListSelectionListener(selectionChuNhiemListener);
        defaultChuNhiemTableModel.setRowCount(0);
        dienBangCN();
        resetCNInput();
        maLopCNTimKiemnput.setText("");
        maGVCNTimKiemInput.setText("");
        namHocCNTimKiemInput.setText("");
    }//GEN-LAST:event_resetBangCNBtnActionPerformed

    private void namHocCNInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namHocCNInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namHocCNInputActionPerformed

    private void namHocCNTimKiemInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namHocCNTimKiemInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namHocCNTimKiemInputActionPerformed

    private void hKNHTimKiemInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKNHTimKiemInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hKNHTimKiemInputActionPerformed

    private void resetBangPhongHocBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBangPhongHocBtnActionPerformed
        ListSelectionModel selectionModel =  phongLopTable.getSelectionModel();
        selectionModel.removeListSelectionListener(selectionLopListener);
        defaultLopTableModel.setRowCount(0);
        resetLopInput();
        soPhongTimKiemInput.setText("");
        hKNHTimKiemInput.setText("");
    }//GEN-LAST:event_resetBangPhongHocBtnActionPerformed

    private void timKiemPhongHocBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemPhongHocBtnActionPerformed
        if(soPhongTimKiemInput.getText().equals("") && hKNHTimKiemInput.getText().equals("")){
            JOptionPane.showMessageDialog(this,
                "Bạn chưa nhập thông tin tìm kiếm",
                "Thông báo", JOptionPane.ERROR_MESSAGE);
        } else {
            String tenLop = soPhongTimKiemInput.getText();
            String nienKhoa = hKNHTimKiemInput.getText();
            List<Lop> dsLop = this.lopService.timKiemLop(
                tenLop,
                nienKhoa);
            ListSelectionModel selectionModel =  phongLopTable.getSelectionModel();
            selectionModel.removeListSelectionListener(selectionLopListener);
            defaultLopTableModel.setRowCount(0);
            selectionModel.addListSelectionListener(selectionLopListener);
            for(Lop lop : dsLop){
                defaultLopTableModel.addRow(new Object[]{
                    lop.getMaLop(),
                    lop.getTenLop(),
                    lop.getNienKhoa(),
                });
            }
        }
    }//GEN-LAST:event_timKiemPhongHocBtnActionPerformed

    private void soPhongPLInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soPhongPLInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_soPhongPLInputActionPerformed

    private void maLopPLInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maLopPLInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maLopPLInputActionPerformed

    private void resetInputPhongLopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetInputPhongLopBtnActionPerformed
        resetPLInput();
    }//GEN-LAST:event_resetInputPhongLopBtnActionPerformed

    private void xoaPhongLopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaPhongLopBtnActionPerformed
        if(phongLopTable.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this,
                "Bạn chưa chọn lớp cần xóa",
                "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            int xacNhanXoaCN = JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa lớp " + soPhongPLInput.getText() + " không ?");
            if(xacNhanXoaCN == JOptionPane.YES_OPTION){
                this.lopService.deleteLop(Integer.parseInt(soPhongPLInput.getText()));
            }

            ListSelectionModel selectionModel =  phongLopTable.getSelectionModel();

            int selectedRow = phongLopTable.getSelectedRow();
            selectionModel.removeListSelectionListener(selectionLopListener);
            defaultLopTableModel.removeRow(selectedRow);
            selectionModel.addListSelectionListener(selectionLopListener);
            resetLopInput();
            JOptionPane.showMessageDialog(this,
                "Xóa lớp " +  soPhongPLInput.getText() + " thành công",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_xoaPhongLopBtnActionPerformed

    private void suaPhongLopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaPhongLopBtnActionPerformed
        if(phongLopTable.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this,
                "Bạn chưa chọn lớp cần sửa",
                "Thông báo", JOptionPane.ERROR_MESSAGE);
        }else{
            Lop lop = new Lop(
                Integer.parseInt(soPhongPLInput.getText()),
                maLopPLInput.getText(),
                hKNHPLInput.getText());
            this.lopService.updateLop(lop);

            int row = phongLopTable.getSelectedRow();
            phongLopTable.setValueAt(soPhongPLInput.getText(),row, 0);
            phongLopTable.setValueAt(maLopPLInput.getText(),row, 1);
            phongLopTable.setValueAt(hKNHPLInput.getText(),row, 2);

            JOptionPane.showMessageDialog(this,
                "Sửa lớp " + soPhongPLInput.getText() + " thành công",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_suaPhongLopBtnActionPerformed

    private void themPhongLopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themPhongLopBtnActionPerformed
        if(soPhongPLInput.getText().equals("") && maLopPLInput.getText().equals("") && hKNHPLInput.getText().equals("")){
            JOptionPane.showMessageDialog(this,
                "Bạn phải chưa nhập đủ thông tin lớp",
                "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        else{
            Lop lop = new Lop(
                Integer.parseInt(soPhongPLInput.getText()),
                maLopPLInput.getText(),
                hKNHPLInput.getText());
            this.lopService.addLop(lop);
            JOptionPane.showMessageDialog(this,
                "Thêm lớp " + soPhongPLInput.getText() + " thành công",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            defaultLopTableModel.addRow(new Object[]{
                lop.getMaLop(),
                lop.getTenLop(),
                lop.getNienKhoa(),
            });
        }
    }//GEN-LAST:event_themPhongLopBtnActionPerformed

    private void hKNHPLInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKNHPLInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hKNHPLInputActionPerformed

    private void hKNHInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKNHInput1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hKNHInput1ActionPerformed

    private void themPhongHocBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themPhongHocBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_themPhongHocBtn1ActionPerformed

    private void suaPhongHocBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaPhongHocBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_suaPhongHocBtn1ActionPerformed

    private void xoaPhongHocBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaPhongHocBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xoaPhongHocBtn1ActionPerformed

    private void resetInputPhongHocBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetInputPhongHocBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetInputPhongHocBtn1ActionPerformed

    private void maLopPHInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maLopPHInput1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maLopPHInput1ActionPerformed

    private void soPhongInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soPhongInput1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_soPhongInput1ActionPerformed

    private void timKiemPhongHocBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemPhongHocBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timKiemPhongHocBtn1ActionPerformed

    private void resetBangPhongHocBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBangPhongHocBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetBangPhongHocBtn1ActionPerformed

    private void hKNHTimKiemInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKNHTimKiemInput1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hKNHTimKiemInput1ActionPerformed

    private void hKNHInput2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKNHInput2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hKNHInput2ActionPerformed

    private void themPhongHocBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themPhongHocBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_themPhongHocBtn2ActionPerformed

    private void suaPhongHocBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaPhongHocBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_suaPhongHocBtn2ActionPerformed

    private void xoaPhongHocBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaPhongHocBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xoaPhongHocBtn2ActionPerformed

    private void resetInputPhongHocBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetInputPhongHocBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetInputPhongHocBtn2ActionPerformed

    private void maLopPHInput2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maLopPHInput2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maLopPHInput2ActionPerformed

    private void soPhongInput2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soPhongInput2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_soPhongInput2ActionPerformed

    private void timKiemPhongHocBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemPhongHocBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timKiemPhongHocBtn2ActionPerformed

    private void resetBangPhongHocBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBangPhongHocBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetBangPhongHocBtn2ActionPerformed

    private void hKNHTimKiemInput2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKNHTimKiemInput2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hKNHTimKiemInput2ActionPerformed

    private void hKNHInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKNHInput3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hKNHInput3ActionPerformed

    private void themPhongHocBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themPhongHocBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_themPhongHocBtn3ActionPerformed

    private void suaPhongHocBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaPhongHocBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_suaPhongHocBtn3ActionPerformed

    private void xoaPhongHocBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaPhongHocBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xoaPhongHocBtn3ActionPerformed

    private void resetInputPhongHocBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetInputPhongHocBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetInputPhongHocBtn3ActionPerformed

    private void maLopPHInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maLopPHInput3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maLopPHInput3ActionPerformed

    private void soPhongInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soPhongInput3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_soPhongInput3ActionPerformed

    private void timKiemPhongHocBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemPhongHocBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timKiemPhongHocBtn3ActionPerformed

    private void resetBangPhongHocBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBangPhongHocBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetBangPhongHocBtn3ActionPerformed

    private void hKNHTimKiemInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKNHTimKiemInput3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hKNHTimKiemInput3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane boby;
    private javax.swing.JPanel chuNhiemTab;
    private javax.swing.JTable chuNhiemTable;
    private javax.swing.JPanel danhSachChuNhiem;
    private javax.swing.JLabel danhSachChuNhiemLabel;
    private javax.swing.JPanel danhSachGiaoVien;
    private javax.swing.JLabel danhSachGiaoVienLabel;
    private javax.swing.JPanel danhSachHocSinh;
    private javax.swing.JLabel danhSachHocSinhLabel;
    private javax.swing.JPanel danhSachLop;
    private javax.swing.JPanel danhSachLop1;
    private javax.swing.JPanel danhSachLop2;
    private javax.swing.JPanel danhSachLop3;
    private javax.swing.JLabel danhSachLopLabel;
    private javax.swing.JLabel danhSachLopLabel1;
    private javax.swing.JLabel danhSachLopLabel2;
    private javax.swing.JLabel danhSachLopLabel3;
    private javax.swing.JTextField diaChiGVInput;
    private javax.swing.JLabel diaChiGVLabel;
    private javax.swing.JTextField diaChiInput;
    private javax.swing.JLabel diaChiLabel;
    private javax.swing.JPanel diemTab;
    private javax.swing.JTable diemTable;
    private javax.swing.JPanel giaoVienTab;
    private javax.swing.JTable giaoVienTable;
    private javax.swing.JTextField hKNHInput1;
    private javax.swing.JTextField hKNHInput2;
    private javax.swing.JTextField hKNHInput3;
    private javax.swing.JTextField hKNHPLInput;
    private javax.swing.JLabel hKNHPLLabel;
    private javax.swing.JTextField hKNHTimKiemInput;
    private javax.swing.JTextField hKNHTimKiemInput1;
    private javax.swing.JTextField hKNHTimKiemInput2;
    private javax.swing.JTextField hKNHTimKiemInput3;
    private javax.swing.JMenuBar header;
    private javax.swing.JTextField hoTenGVInput;
    private javax.swing.JLabel hoTenGVLabel;
    private javax.swing.JTextField hoTenGVTimKiemInput;
    private javax.swing.JLabel hoTenGVTimKiemLabel;
    private javax.swing.JTextField hoTenHSTimKiemInput;
    private javax.swing.JLabel hoTenHSTimKiemLabel;
    private javax.swing.JTextField hoTenInput;
    private javax.swing.JLabel hoTenLabel;
    private javax.swing.JPanel hocSinhTab;
    private javax.swing.JTable hocSinhTable;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JComboBox<GiaoVien> maGVCNInput;
    private javax.swing.JLabel maGVCNLabel;
    private javax.swing.JTextField maGVCNTimKiemInput;
    private javax.swing.JLabel maGVCNTimKiemLabel;
    private javax.swing.JTextField maGVTimKiemInput;
    private javax.swing.JLabel maGVTimKiemLabel;
    private javax.swing.JTextField maGiaoVienInput;
    private javax.swing.JLabel maGiaoVienLabel;
    private javax.swing.JTextField maHSTimKiemInput;
    private javax.swing.JLabel maHSTimKiemLabel;
    private javax.swing.JTextField maHocSinhInput;
    private javax.swing.JLabel maHocSinhLabel;
    private javax.swing.JComboBox<Lop> maLopCNInput;
    private javax.swing.JLabel maLopCNLabel;
    private javax.swing.JLabel maLopCNTimKiemLabel;
    private javax.swing.JTextField maLopCNTimKiemnput;
    private javax.swing.JLabel maLopHSLabel;
    private javax.swing.JComboBox<Lop> maLopList;
    private javax.swing.JTextField maLopPHInput1;
    private javax.swing.JTextField maLopPHInput2;
    private javax.swing.JTextField maLopPHInput3;
    private javax.swing.JTextField maLopPLInput;
    private javax.swing.JLabel maLopPLLabel;
    private javax.swing.JTextField namHocCNInput;
    private javax.swing.JLabel namHocCNLabel;
    private javax.swing.JTextField namHocCNTimKiemInput;
    private javax.swing.JLabel namHocCNTimKiemLabel;
    private javax.swing.JFormattedTextField ngaySinhGVInput;
    private javax.swing.JLabel ngaySinhGVLabel;
    private javax.swing.JFormattedTextField ngaySinhInput;
    private javax.swing.JLabel ngaySinhLabel;
    private javax.swing.JLabel nienKhoaLopLabel1;
    private javax.swing.JLabel nienKhoaLopLabel2;
    private javax.swing.JLabel nienKhoaLopLabel3;
    private javax.swing.JLabel nienKhoaLopTimKiemLabel;
    private javax.swing.JLabel nienKhoaLopTimKiemLabel1;
    private javax.swing.JLabel nienKhoaLopTimKiemLabel2;
    private javax.swing.JLabel nienKhoaLopTimKiemLabel3;
    private javax.swing.JPanel phongHocTab;
    private javax.swing.JTable phongLopTable;
    private javax.swing.JButton resetBangCNBtn;
    private javax.swing.JButton resetBangGVBtn;
    private javax.swing.JButton resetBangHSBtn;
    private javax.swing.JButton resetBangPhongHocBtn;
    private javax.swing.JButton resetBangPhongHocBtn1;
    private javax.swing.JButton resetBangPhongHocBtn2;
    private javax.swing.JButton resetBangPhongHocBtn3;
    private javax.swing.JButton resetBtn;
    private javax.swing.JButton resetInputCNBtn;
    private javax.swing.JButton resetInputGVBtn;
    private javax.swing.JButton resetInputPhongHocBtn1;
    private javax.swing.JButton resetInputPhongHocBtn2;
    private javax.swing.JButton resetInputPhongHocBtn3;
    private javax.swing.JButton resetInputPhongLopBtn;
    private javax.swing.JTextField sdtGVInput;
    private javax.swing.JLabel sdtGVLabel;
    private javax.swing.JTextField sdtPhuHuynhInput;
    private javax.swing.JLabel sdtPhuHuynhLabel;
    private javax.swing.JTextField soPhongInput1;
    private javax.swing.JTextField soPhongInput2;
    private javax.swing.JTextField soPhongInput3;
    private javax.swing.JTextField soPhongPLInput;
    private javax.swing.JTextField soPhongTimKiemInput;
    private javax.swing.JTextField soPhongTimKiemInput1;
    private javax.swing.JTextField soPhongTimKiemInput2;
    private javax.swing.JTextField soPhongTimKiemInput3;
    private javax.swing.JLabel soPhongTimKiemLabel;
    private javax.swing.JLabel soPhongTimKiemLabel1;
    private javax.swing.JLabel soPhongTimKiemLabel2;
    private javax.swing.JLabel soPhongTimKiemLabel3;
    private javax.swing.JButton suaGVBtn;
    private javax.swing.JButton suaHocSinhBtn;
    private javax.swing.JButton suaPhongHocBtn1;
    private javax.swing.JButton suaPhongHocBtn2;
    private javax.swing.JButton suaPhongHocBtn3;
    private javax.swing.JButton suaPhongLopBtn;
    private javax.swing.JLabel sọPhongLabel1;
    private javax.swing.JLabel sọPhongLabel2;
    private javax.swing.JLabel sọPhongLabel3;
    private javax.swing.JLabel sọPhongPLLabel;
    private javax.swing.JPanel taiKhoanTab;
    private javax.swing.JTable taiKhoanTable;
    private javax.swing.JMenu taikhoanHeader;
    private javax.swing.JLabel tenLopLabel1;
    private javax.swing.JLabel tenLopLabel2;
    private javax.swing.JLabel tenLopLabel3;
    private javax.swing.JButton themCNBtn;
    private javax.swing.JButton themGVBtn;
    private javax.swing.JButton themHocSinhBtn;
    private javax.swing.JButton themPhongHocBtn1;
    private javax.swing.JButton themPhongHocBtn2;
    private javax.swing.JButton themPhongHocBtn3;
    private javax.swing.JButton themPhongLopBtn;
    private javax.swing.JMenu thoatHeader;
    private javax.swing.JPanel thongKeTab;
    private javax.swing.JTable thongKeTable;
    private javax.swing.JPanel thongTinChuNhiem;
    private javax.swing.JPanel thongTinGiaoVien;
    private javax.swing.JPanel thongTinHocSinh;
    private javax.swing.JPanel thongTinPhongHoc;
    private javax.swing.JPanel thongTinPhongHoc1;
    private javax.swing.JPanel thongTinPhongHoc2;
    private javax.swing.JPanel thongTinPhongHoc3;
    private javax.swing.JPanel thongTin_TimKiem;
    private javax.swing.JPanel thongTin_TimKiemCN;
    private javax.swing.JPanel thongTin_TimKiemGV;
    private javax.swing.JPanel thongTin_TimKiemPhongHoc;
    private javax.swing.JPanel thongTin_TimKiemPhongHoc1;
    private javax.swing.JPanel thongTin_TimKiemPhongHoc2;
    private javax.swing.JPanel thongTin_TimKiemPhongHoc3;
    private javax.swing.JButton timKiemBtn;
    private javax.swing.JButton timKiemCNBtn;
    private javax.swing.JPanel timKiemChuNhiem;
    private javax.swing.JButton timKiemGVBtn;
    private javax.swing.JPanel timKiemGiaoVien;
    private javax.swing.JPanel timKiemHocSinh;
    private javax.swing.JPanel timKiemPhongHoc;
    private javax.swing.JPanel timKiemPhongHoc1;
    private javax.swing.JPanel timKiemPhongHoc2;
    private javax.swing.JPanel timKiemPhongHoc3;
    private javax.swing.JButton timKiemPhongHocBtn;
    private javax.swing.JButton timKiemPhongHocBtn1;
    private javax.swing.JButton timKiemPhongHocBtn2;
    private javax.swing.JButton timKiemPhongHocBtn3;
    private javax.swing.JMenu troVeHeader;
    private javax.swing.JButton xoaCNBtn;
    private javax.swing.JButton xoaGVBtn;
    private javax.swing.JButton xoaHocSinhBtn;
    private javax.swing.JButton xoaPhongHocBtn1;
    private javax.swing.JButton xoaPhongHocBtn2;
    private javax.swing.JButton xoaPhongHocBtn3;
    private javax.swing.JButton xoaPhongLopBtn;
    // End of variables declaration//GEN-END:variables
}
