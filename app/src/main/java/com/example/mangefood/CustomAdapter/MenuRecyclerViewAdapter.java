package com.example.mangefood.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangefood.DTO.LoaiMonAnDTO;
import com.example.mangefood.DTO.MonAnDTO;
import com.example.mangefood.R;

import java.util.ArrayList;
import java.util.List;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.MyViewHolder> {

    List<LoaiMonAnDTO> menus;
    Context context;
    int maban;
    OnItemmClickListener mOnClickListener;


    public MenuRecyclerViewAdapter(List<LoaiMonAnDTO> menus, Context context, int maban) {
        this.menus = menus;
        this.context = context;
        this.maban = maban;
    }
    public interface OnItemmClickListener {
        void onClick(int position);
    }
    public void setOnClickListener(OnItemmClickListener listener ){
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LoaiMonAnDTO menu = menus.get(position);
        String title = menu.getTenLoai();
        List<MonAnDTO> foods = menu.getMonAnDTOS();
//        Log.d("dulieu", foods.get(0).getHinhAnh());

        holder.txt_title.setText(title);
        FoodRecyclerViewAdapter foodRecyclerViewAdapter = new FoodRecyclerViewAdapter(context, foods, maban);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(foodRecyclerViewAdapter);

    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            recyclerView = itemView.findViewById(R.id.list_food);

        }
    }
}
