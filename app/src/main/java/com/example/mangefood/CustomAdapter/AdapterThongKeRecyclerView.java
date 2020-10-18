package com.example.mangefood.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangefood.DTO.ThongKeDTO;
import com.example.mangefood.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterThongKeRecyclerView extends RecyclerView.Adapter<AdapterThongKeRecyclerView.MyViewHolder> {

    private Context context;
    private List<ThongKeDTO> thongKeDTOS;


    public AdapterThongKeRecyclerView(List<ThongKeDTO> thongKeDTOS, Context context) {
        this.thongKeDTOS = thongKeDTOS;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_hienthithongke, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ThongKeDTO thongKeDTO = thongKeDTOS.get(position);
        holder.txt_tenmon.setText(thongKeDTO.getTenMonAn());
        holder.txt_giatien.setText(String.valueOf(thongKeDTO.getGiaTien()));
        holder.txt_soluong.setText(String.valueOf(thongKeDTO.getSoLuong()));

        Bitmap hinhanh = thongKeDTO.getHinhAnh();
        holder.img_anhmon.setImageBitmap(hinhanh);


    }

    @Override
    public int getItemCount() {
        return thongKeDTOS.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tenmon, txt_giatien, txt_soluong;
        ImageView img_anhmon;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            txt_tenmon = itemView.findViewById(R.id.txt_thongke_tenmon);
            txt_giatien = itemView.findViewById(R.id.txt_thongke_giatien);
            txt_soluong = itemView.findViewById(R.id.txt_thongke_soluong);
            img_anhmon = itemView.findViewById(R.id.img_thongke_anhmon);

        }
    }
}
