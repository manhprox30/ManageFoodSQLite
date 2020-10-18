package com.example.mangefood.DTO;


import android.graphics.Bitmap;

public class MonAnDTO {

    int MaMonAn, MaLoai;
    String TenMonAn;
    String GiaTien;
    Bitmap HinhAnh;

    public Bitmap getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(Bitmap hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getMaMonAn() {
        return MaMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        MaMonAn = maMonAn;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }

    public String getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(String giaTien) {
        GiaTien = giaTien;
    }


}
