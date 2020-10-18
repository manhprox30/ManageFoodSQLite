package com.example.mangefood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mangefood.DTO.MonAnDTO;
import com.example.mangefood.Database.CreateDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MonAnDAO {

    SQLiteDatabase database;
    public ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;

    public MonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMonAn(MonAnDTO monAnDTO){
        Bitmap bitmap = monAnDTO.getHinhAnh();
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN,monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN,monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI,monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, imageInBytes);

        long kiemtra = database.insert(CreateDatabase.TB_MONAN,null,contentValues);
        if(kiemtra != 0){
            return true;
        }else{
            return false;
        }

    }

    public List<MonAnDTO> LayDanhSachMonAnTheoLoai(int maloai){
        List<MonAnDTO> monAnDTOs = new ArrayList<MonAnDTO>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' ";
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            MonAnDTO monAnDTO = new MonAnDTO();
            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));

            Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            monAnDTO.setHinhAnh(objectBitmap);
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            monAnDTOs.add(monAnDTO);
            cursor.moveToNext();
        }

        return monAnDTOs;

    }

}
