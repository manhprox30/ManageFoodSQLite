package com.example.mangefood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangefood.DAO.BanAnDAO;
import com.example.mangefood.DAO.GoiMonDAO;
import com.example.mangefood.DTO.GoiMonDTO;
import com.example.mangefood.DTO.MonAnDTO;
import com.example.mangefood.R;
import com.example.mangefood.SoLuongActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.MyViewHolder> {

    Context context;
    int maban ;
    List<MonAnDTO> foods;
    OnItemClickListener mOnClickListener;
    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;


    public interface OnItemClickListener {
        void onClick(int position);
    }

    public FoodRecyclerViewAdapter(Context context, List<MonAnDTO> foods, int maban) {
        this.context = context;
        this.foods = foods;
        this.maban = maban;

        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
    }

    public FoodRecyclerViewAdapter() {
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        MonAnDTO food = foods.get(position);
        holder.txt_name.setText(food.getTenMonAn());
        Bitmap hinhanh = food.getHinhAnh();
        holder.img_avatar.setImageBitmap(hinhanh);

        // xu li them goi mon va cap nhap goi mon
        if (maban != 0){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iSoLuong = new Intent(context, SoLuongActivity.class);
                    iSoLuong.putExtra("maban",maban);
                    iSoLuong.putExtra("mamonan",position + 1 );
                    context.startActivity(iSoLuong);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        ImageView img_avatar;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_food);
            img_avatar = itemView.findViewById(R.id.img_avatar);


        }
    }
}
