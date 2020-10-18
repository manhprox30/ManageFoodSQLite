package com.example.mangefood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mangefood.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.example.mangefood.DAO.LoaiMonAnDAO;
import com.example.mangefood.DAO.MonAnDAO;
import com.example.mangefood.DTO.LoaiMonAnDTO;
import com.example.mangefood.DTO.MonAnDTO;

import java.io.IOException;
import java.util.List;


public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
    Bitmap imageBitmap;
    ImageButton imThemLoaiThucDon;
    Spinner spinLoaiThucDon;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;
    ImageView imHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    Uri imageFilePath;
    EditText edTenMonAn,edGiaTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imThemLoaiThucDon = (ImageButton) findViewById(R.id.imThemLoaiThucDon);
        spinLoaiThucDon = (Spinner) findViewById(R.id.spinLoaiMonAn);
        imHinhThucDon = (ImageView) findViewById(R.id.imHinhThucDon);
        btnDongYThemMonAn = (Button) findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = (Button) findViewById(R.id.btnThoatThemMonAn);
        edTenMonAn = (EditText) findViewById(R.id.edThemTenMonAn);
        edGiaTien = (EditText) findViewById(R.id.edThemGiaTien);

        HienThiSpinnerLoaiMonAn();

        imThemLoaiThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);


    }

    private void HienThiSpinnerLoaiMonAn (){
        loaiMonAnDTOs = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(ThemThucDonActivity.this,R.layout.custom_layout_spinloaithucdon,loaiMonAnDTOs);
        spinLoaiThucDon.setAdapter(adapterHienThiLoaiMonAn);
        adapterHienThiLoaiMonAn.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imThemLoaiThucDon:
                Intent iThemLoaiMonAn = new Intent(ThemThucDonActivity.this,ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiMonAn,REQUEST_CODE_THEMLOAITHUCDON);break;

            case R.id.imHinhThucDon:
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(iMoHinh, REQUEST_CODE_MOHINH);
                ;break;

            case R.id.btnDongYThemMonAn:
                int vitri = spinLoaiThucDon.getSelectedItemPosition();
                int maloai = loaiMonAnDTOs.get(vitri).getMaLoai();
                String tenmonan = edTenMonAn.getText().toString();
                String giatien = edGiaTien.getText().toString();

                if(tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("") ){
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh(imageBitmap);
                    monAnDTO.setMaLoai(maloai);
                    monAnDTO.setTenMonAn(tenmonan);

                    boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
                    if(kiemtra){
                        Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                        // xu li quay ve fragment HienThiThucDon
                    }else{
                        Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this,getResources().getString(R.string.loithemmonan),Toast.LENGTH_SHORT).show();
                }

                ;break;

            case R.id.btnThoatThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            if(resultCode == Activity.RESULT_OK){
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon",false);
                if(kiemtra){
                    HienThiSpinnerLoaiMonAn();
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                }
            }
        }else if(REQUEST_CODE_MOHINH == requestCode){
            if(resultCode == Activity.RESULT_OK && data !=null){

                imageFilePath = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                    imHinhThucDon.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
