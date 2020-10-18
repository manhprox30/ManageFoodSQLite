package com.example.mangefood.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mangefood.CustomAdapter.AdapterHienThiNhanVien;
import com.example.mangefood.DAO.NhanVienDAO;
import com.example.mangefood.DTO.NhanVienDTO;
import com.example.mangefood.DangKyActivity;
import com.example.mangefood.DangNhapActivity;
import com.example.mangefood.R;
import com.example.mangefood.ThemBanAnActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class HienThiNhanVienFragment extends Fragment {

    FloatingActionButton floatingActionButton, fab_addnhanvien, fab_logout;
    boolean anhien = false;

    ListView listNhanVien;
    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> nhanVienDTOList;
    int maquyen;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien, container, false);
        setHasOptionsMenu(true);

        floatingActionButton = view.findViewById(R.id.floating_addEmployee);
        fab_addnhanvien = view.findViewById(R.id.fab_addnhanvien);
        fab_logout = view.findViewById(R.id.fab_logout);

        listNhanVien = view.findViewById(R.id.listViewNhanVien);

        nhanVienDAO = new NhanVienDAO(getActivity());

        HienThiDanhSachNhanVien();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        if (maquyen == 1) {
            registerForContextMenu(listNhanVien);
        }
        phanQuyen();
        return view;
    }

    public void hienfab() {
        fab_addnhanvien.show();
        fab_logout.show();
    }

    public void anfab() {
        fab_logout.hide();
        fab_addnhanvien.hide();
    }

    public void phanQuyen() {
        if (maquyen == 1) {
            fab_addnhanvien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                    startActivity(iDangKy);
                }
            });
            fab_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iDangNhap = new Intent(getActivity(), DangNhapActivity.class);
                    startActivity(iDangNhap);
                }
            });
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (anhien == false) {
                        hienfab();
                        anhien = true;
                    } else {
                        anfab();
                        anhien = false;
                    }
                }
            });
        } else {
            fab_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iDangNhap = new Intent(getActivity(), DangNhapActivity.class);
                    startActivity(iDangNhap);
                }
            });
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (anhien == false) {
                        fab_logout.show();
                        anhien = true;
                    } else {
                        fab_logout.hide();
                        anhien = false;
                    }
                }
            });
        }
    }

    private void HienThiDanhSachNhanVien() {
        nhanVienDTOList = nhanVienDAO.LayDanhSachNhanVien();

        AdapterHienThiNhanVien adapterHienThiNhanVien = new AdapterHienThiNhanVien(getActivity(), R.layout.custom_layout_hienthinhanvien, nhanVienDTOList);
        listNhanVien.setAdapter(adapterHienThiNhanVien);
        adapterHienThiNhanVien.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manhanvien = nhanVienDTOList.get(vitri).getMANV();

        switch (id) {
            case R.id.itSua:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                iDangKy.putExtra("manv", manhanvien);
                startActivity(iDangKy);
                ;
                break;

            case R.id.itXoa:
                boolean kiemtra = nhanVienDAO.XoaNhanVien(manhanvien);
                if (kiemtra) {
                    HienThiDanhSachNhanVien();
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
                ;
                break;
        }
        return true;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        if(maquyen == 1){
//            MenuItem itThemBanAn = menu.add(1,R.id.itThemNhanVien,1,R.string.themnhanvien);
//            itThemBanAn.setIcon(R.drawable.nhanvien);
//            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        }
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id){
//            case R.id.itThemNhanVien:
//                 Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
//                startActivity(iDangKy);
//                ;break;
//        }
//        return true;
//    }
}
