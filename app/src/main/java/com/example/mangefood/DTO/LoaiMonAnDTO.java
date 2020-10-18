package com.example.mangefood.DTO;


import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDTO {
    int MaLoai;
    String TenLoai;
    String HinhAnh;
    List<MonAnDTO> monAnDTOS;

    public LoaiMonAnDTO(String tenLoai, String hinhAnh, List<MonAnDTO> monAnDTOS) {
        TenLoai = tenLoai;
        HinhAnh = hinhAnh;
        this.monAnDTOS = monAnDTOS;

    }

    public LoaiMonAnDTO(String tenLoai, List<MonAnDTO> monAnDTOS) {
        TenLoai = tenLoai;
        this.monAnDTOS = monAnDTOS;
    }

    public LoaiMonAnDTO() {
    }

    public List<MonAnDTO> getMonAnDTOS() {
        return monAnDTOS;
    }

    public void setMonAnDTOS(ArrayList<MonAnDTO> monAnDTOS) {
        this.monAnDTOS = monAnDTOS;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }



    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }


}
