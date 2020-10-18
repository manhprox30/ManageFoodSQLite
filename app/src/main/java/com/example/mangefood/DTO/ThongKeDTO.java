package com.example.mangefood.DTO;

import android.graphics.Bitmap;

public class ThongKeDTO  {
    private int SoLuong;
    private String TenMonAn;
    private int GiaTien;
    private Bitmap HinhAnh;

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public int getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(int giaTien) {
        GiaTien = giaTien;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }



    public Bitmap getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(Bitmap hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
