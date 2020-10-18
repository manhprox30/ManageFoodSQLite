package com.example.mangefood.FragmentApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangefood.CustomAdapter.AdapterThongKeRecyclerView;
import com.example.mangefood.DAO.GoiMonDAO;
import com.example.mangefood.DTO.ThongKeDTO;
import com.example.mangefood.R;

import java.util.ArrayList;
import java.util.List;

public class HienThiThongKeFrament extends Fragment {

    Button btn_thongke;
    TextView txt_tongcong;
    EditText edt_date;
    GoiMonDAO goiMonDAO;
    List<ThongKeDTO> thongKeDTOS;
    RecyclerView recyclerView;
    float tongtien = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithongke, container, false);
        btn_thongke = view.findViewById(R.id.btn_thongke);
        txt_tongcong = view.findViewById(R.id.txt_tongcongthongke);
        edt_date = view.findViewById(R.id.edt_date);
        recyclerView = view.findViewById(R.id.list_thongke);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        goiMonDAO = new GoiMonDAO(getActivity());
        thongKeDTOS = new ArrayList<>();

        btn_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = edt_date.getText().toString();
                thongKeDTOS = goiMonDAO.LayDanhSachMonAnTheoNgayGoi(date);
                AdapterThongKeRecyclerView adapterThongKeRecyclerView = new AdapterThongKeRecyclerView(thongKeDTOS, getContext());
                recyclerView.setAdapter(adapterThongKeRecyclerView);
                adapterThongKeRecyclerView.notifyDataSetChanged();

                //hien thi tong cong

                if (tongtien == 0) {
                    for (int i = 0; i < thongKeDTOS.size(); i++) {
                        int soluong = thongKeDTOS.get(i).getSoLuong();
                        int giatien = thongKeDTOS.get(i).getGiaTien();

                        tongtien += (soluong * giatien); // tongtien = tongtien + (soluong*giatien)
                        txt_tongcong.setText(String.valueOf(tongtien));
                    }
                }
                else tongtien = 0;


            }
        });


        return view;
    }
}
