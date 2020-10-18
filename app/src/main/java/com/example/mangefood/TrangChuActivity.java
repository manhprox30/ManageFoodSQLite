package com.example.mangefood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mangefood.FragmentApp.HienThiBanAnFagment;
import com.example.mangefood.FragmentApp.HienThiNhanVienFragment;
import com.example.mangefood.FragmentApp.HienThiThongKeFrament;
import com.example.mangefood.FragmentApp.HienThiThucDonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class TrangChuActivity extends AppCompatActivity  {


    BottomNavigationView bottomNavigationView;
//    Toolbar toolbar;
    TextView txtTenNhanVien_Navigation;
    FragmentManager fragmentManager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

//        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.navigationbottom);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFagment hienThiBanAnFagment = new HienThiBanAnFagment();
        tranHienThiBanAn.replace(R.id.content, hienThiBanAnFagment);
        tranHienThiBanAn.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.itTrangChu:
                        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                        HienThiBanAnFagment hienThiBanAnFagment = new HienThiBanAnFagment();
//                        tranHienThiBanAn.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                        tranHienThiBanAn.replace(R.id.content,hienThiBanAnFagment);
                        tranHienThiBanAn.commit();
                        return true;


                    case R.id.itThucDon:
                        FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                        HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
//                        tranHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                        tranHienThiThucDon.replace(R.id.content,hienThiThucDonFragment);
                        tranHienThiThucDon.commit();
                        return true;

                    case R.id.itNhanVien:
                        FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
                        HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
//                        tranNhanVien.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                        tranNhanVien.replace(R.id.content,hienThiNhanVienFragment);
                        tranNhanVien.commit();
                        return true;

                    case R.id.itThongKe:
                        FragmentTransaction tranThongKe = fragmentManager.beginTransaction();
                        HienThiThongKeFrament hienThiThongKeFrament = new HienThiThongKeFrament();
//                        tranNhanVien.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                        tranThongKe.replace(R.id.content,hienThiThongKeFrament);
                        tranThongKe.commit();
                        return true;


                }
                return false;
            }
        });



    }




}
