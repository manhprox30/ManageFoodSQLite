package com.example.mangefood;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mangefood.DAO.BanAnDAO;
import com.example.mangefood.DAO.GoiMonDAO;
import com.example.mangefood.DTO.ChiTietGoiMonDTO;
import com.example.mangefood.DTO.GoiMonDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener {

    int maban, mamonan;
    Button btnDongYThemSoLuong;
    EditText edSoLuong;
    GoiMonDAO goiMonDAO;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themsoluong);

        banAnDAO = new BanAnDAO(this);
        btnDongYThemSoLuong = findViewById(R.id.btnDongYThemSoLuong);
        edSoLuong = findViewById(R.id.edSoLuongMonAn);

        goiMonDAO = new GoiMonDAO(this);

        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        mamonan = intent.getIntExtra("mamonan", 0);

        btnDongYThemSoLuong.setOnClickListener(this);

    }

    public void themGoiMon() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String ngaygoi = dateFormat.format(calendar.getTime());

        GoiMonDTO goiMonDTO = new GoiMonDTO();
        goiMonDTO.setMaBan(maban);
        goiMonDTO.setNgayGoi(ngaygoi);
        goiMonDTO.setTinhTrang("false");

        long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
        banAnDAO.CapNhatLaiTinhTrangBan(maban, "true");
        if (kiemtra == 0) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
        }
    }

    public void themMonAn() {

        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");// lay ma goi mon chua thanh toan
        boolean kiemtra = goiMonDAO.KiemTraMonAnDaTonTai(magoimon, mamonan);
        if (kiemtra) {
            //tiến hành cập nhật món ăn đã tồn tại
            int soluongcu = goiMonDAO.LaySoLuongMonAnTheoMaGoiMon(magoimon, mamonan);
            int soluongmoi = Integer.parseInt(edSoLuong.getText().toString());
            int tongsoluong = soluongcu + soluongmoi;

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(tongsoluong);

            boolean kiemtracapnhat = goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
            if (kiemtracapnhat) {
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                // xu li quay ve fragment ban an va update tinh trang
            } else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }

        } else {
            //thêm món ăn
            int soluonggoi = Integer.parseInt(edSoLuong.getText().toString());
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(soluonggoi);

            boolean kiemtracapnhat = goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
            if (kiemtracapnhat) {
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                // xu li quay ve fragment ban an va update tinh trang
            } else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
        //neu ban an chua goi mon 
        if (tinhtrang.equals("false")) {
            themGoiMon();
        }
        themMonAn();
        finish();
    }
}
