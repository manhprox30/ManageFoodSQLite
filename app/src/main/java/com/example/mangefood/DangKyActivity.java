package com.example.mangefood;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mangefood.DAO.NhanVienDAO;
import com.example.mangefood.DAO.QuyenDAO;
import com.example.mangefood.DTO.NhanVienDTO;
import com.example.mangefood.DTO.QuyenDTO;
import com.example.mangefood.FragmentApp.DatePickerFragment;
import com.example.mangefood.FragmentApp.HienThiNhanVienFragment;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    EditText edTenDangNhapDK, edMatKhauDK, edNgaySinhDK, edCMNDDK;
    Button btnDongYDK, btnThoatDK;
    RadioGroup rgGioiTinh;
    RadioButton rdNam, rdNu;
    TextView txtTieuDeDangKy;
    String sGioiTinh;
    Spinner spinQuyen;
    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;
    int manv = 0;
    int landautien = 0;
    List<QuyenDTO> quyenDTOList;
    List<String> dataAdapter;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        truyenID();

        btnDongYDK.setOnClickListener(this);
        btnThoatDK.setOnClickListener(this);
        edNgaySinhDK.setOnFocusChangeListener(this);

        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);


        quyenDTOList = quyenDAO.LayDanhSachQuyen();
        dataAdapter = new ArrayList<String>();

        for (int i = 0; i < quyenDTOList.size(); i++) {
            String tenquyen = quyenDTOList.get(i).getTenQuyen();
            dataAdapter.add(tenquyen);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataAdapter);
        spinQuyen.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        manv = getIntent().getIntExtra("manv", 0);
        landautien = getIntent().getIntExtra("landautien", 0);
        // Them quyen nhan vien
        if (landautien == 1) {
            quyenDAO.ThemQuyen("Quản lí");
            quyenDAO.ThemQuyen("Nhân Viên");
        }


// Cap nhat nhan vien
        if (manv != 0) {
            txtTieuDeDangKy.setText(getResources().getString(R.string.capnhatnhanvien));
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manv);

            edTenDangNhapDK.setText(nhanVienDTO.getTENDN());
            edMatKhauDK.setText(nhanVienDTO.getMATKHAU());
            edNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edCMNDDK.setText(String.valueOf(nhanVienDTO.getCMND()));


            String gioitinh = nhanVienDTO.getGIOITINH();
            if (gioitinh.equals("Nam")) {
                rdNam.setChecked(true);
            } else {
                rdNu.setChecked(true);
            }

            edNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edCMNDDK.setText(String.valueOf(nhanVienDTO.getCMND()));
        }

    }

    public void truyenID() {
        edTenDangNhapDK = findViewById(R.id.edTenDangNhapDK);
        edMatKhauDK = findViewById(R.id.edMatKhauDK);
        edNgaySinhDK = findViewById(R.id.edNgaySinhDK);
        txtTieuDeDangKy = findViewById(R.id.txtTieuDeDangKy);
        rdNam = findViewById(R.id.rdNam);
        rdNu = findViewById(R.id.rdNu);
        edCMNDDK = findViewById(R.id.edCMNDDK);
        btnDongYDK = findViewById(R.id.btnDongYDK);
        btnThoatDK = findViewById(R.id.btnThoatDK);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        spinQuyen = findViewById(R.id.spinQuyen);
    }

    private void DongYThemNhanVien() {
        String sTenDangNhap = edTenDangNhapDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;

            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }
        String sNgaySinh = edNgaySinhDK.getText().toString();
        int sCMND = Integer.parseInt(edCMNDDK.getText().toString());

        if (sTenDangNhap == null || sTenDangNhap.equals("")) {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loinhaptendangnhap), Toast.LENGTH_SHORT).show();
        } else if (sMatKhau == null || sMatKhau.equals("")) {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loinhapmatkhau), Toast.LENGTH_SHORT).show();
        } else {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDN(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setCMND(sCMND);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setGIOITINH(sGioiTinh);
            if (landautien != 0) {
                //gán mặc định quyền nhân viên là admin
                nhanVienDTO.setMAQUYEN(1);
                nhanVienDTO.setTENQUYEN("Quản lí");
            } else {
                //gán quyền bằng quyền mà admin chọn khi tạo nhân viên
                int vitri = spinQuyen.getSelectedItemPosition();
                int maquyen = quyenDTOList.get(vitri).getMaQuyen();
                nhanVienDTO.setMAQUYEN(maquyen);


            }

            long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
            if (kiemtra != 0) {
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SuaNhanVien() {
        String sTenDangNhap = edTenDangNhapDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();
        String sNgaySinh = edNgaySinhDK.getText().toString();
        int sCMND = Integer.parseInt(edCMNDDK.getText().toString());
        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;

            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }

        NhanVienDTO nhanVienDTO = new NhanVienDTO();

        nhanVienDTO.setMAQUYEN(quyenDTOList.get(spinQuyen.getSelectedItemPosition()).getMaQuyen());
        nhanVienDTO.setMANV(manv);
        nhanVienDTO.setTENDN(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setCMND(sCMND);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setGIOITINH(sGioiTinh);

        boolean kiemtra = nhanVienDAO.SuaNhanVien(nhanVienDTO);
        if (kiemtra) {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDongYDK:
                if (manv != 0) {
                    // Thực hiện code sua nhân viên
                    SuaNhanVien();
                } else {
                    // Thực hiện thêm mới nhân viên
                    DongYThemNhanVien();
                }
                break;
            case R.id.btnThoatDK:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id) {
            case R.id.edNgaySinhDK:
                if (hasFocus) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(), "Ngày Sinh");
                    //xuat popup ngaysinh
                }
                ;
                break;
        }
    }

}
