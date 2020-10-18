package com.example.mangefood.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mangefood.CustomAdapter.MenuRecyclerViewAdapter;
import com.example.mangefood.DAO.LoaiMonAnDAO;
import com.example.mangefood.DAO.MonAnDAO;
import com.example.mangefood.DTO.LoaiMonAnDTO;
import com.example.mangefood.R;
import com.example.mangefood.ThemThucDonActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HienThiThucDonFragment extends Fragment {

    RecyclerView menuRecyclerView;
    List<LoaiMonAnDTO> menus;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    FloatingActionButton floatingActionButton;
    int maban = 0;
    int maquyen;
    SharedPreferences sharedPreferences;
    MenuRecyclerViewAdapter adapter;

    public void setMaban(int maban) {
        this.maban = maban;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_thucdon, container, false);
        setHasOptionsMenu(true);

        menus = new ArrayList<>();
        menuRecyclerView = view.findViewById(R.id.list_menu);
        floatingActionButton = view.findViewById(R.id.floating_addMenu);
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        Bundle bDuLieuThucDon = getArguments();
        if(bDuLieuThucDon != null) {
            int maban = bDuLieuThucDon.getInt("maban");
            setMaban(maban);
        }

        setData();
        phanQuyen();
        anThem();

        return view;
    }


    public void phanQuyen() {
        if (maquyen == 1) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                    startActivity(iThemThucDon);
                    getActivity().overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                    ;
                }
            });
        } else {
            floatingActionButton.setVisibility(View.GONE);
        }
    }
    public void anThem(){
        if (maban != 0 ){
            floatingActionButton.setVisibility(View.GONE);
        }
    }


    public void setData() {


        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        monAnDAO = new MonAnDAO(getActivity());
        int size = loaiMonAnDAO.LayDanhSachLoaiMonAn().size();

        for (int i = 1; i <= size; i++) {
            String title = loaiMonAnDAO.LayLoaiMonAnTheoMaLoai(i).getTenLoai();
            menus.add(new LoaiMonAnDTO(title, monAnDAO.LayDanhSachMonAnTheoLoai(i)));
        }

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        adapter = new MenuRecyclerViewAdapter(menus, getContext(), maban);
        menuRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
